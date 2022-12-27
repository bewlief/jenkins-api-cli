/*
 * Copyright (c) 2013 Cosmin Stejerean, Karl Heinz Marbaise, and contributors.
 *
 * Distributed under the MIT license: http://opensource.org/licenses/MIT
 */

package com.xtech.jenkins.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import com.xtech.jenkins.client.model.BaseModel;
import com.xtech.jenkins.client.model.other.ExtractHeader;
import com.xtech.jenkins.client.model.crumb.Crumb;
import com.xtech.jenkins.client.util.EncodingUtils;
import com.xtech.jenkins.client.util.RequestReleasingInputStream;
import com.xtech.jenkins.client.util.ResponseUtils;
import com.xtech.jenkins.client.util.UrlUtils;
import com.xtech.jenkins.client.validator.HttpResponseValidator;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

/**
 * using apache http client to call remote rest api
 */
public class JenkinsHttpClient implements JenkinsClient, Closeable {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private URI uri;
    private CloseableHttpClient client;
    private HttpContext localContext;
    private HttpResponseValidator httpResponseValidator;
    // private HttpResponseContentExtractor contentExtractor;

    private ObjectMapper mapper;
    private String context;

    private String jenkinsVersion;

    public static final String EMPTY_VERSION = "UNKNOWN";

    /**
     * Create an unauthenticated Jenkins HTTP client
     *
     * @param uri    Location of the jenkins server (ex. http://localhost:8080)
     * @param client Configured CloseableHttpClient to be used
     */
    public JenkinsHttpClient(URI uri, CloseableHttpClient client) {
        this.context = uri.getPath();
        if (!context.endsWith("/")) {
            context += "/";
        }
        this.uri = uri;
        this.mapper = getDefaultMapper();
        this.client = client;
        this.httpResponseValidator = new HttpResponseValidator();
        this.jenkinsVersion = EMPTY_VERSION;
        LOGGER.debug("uri={}", uri);
    }

    /**
     * Create an unauthenticated Jenkins HTTP client
     *
     * @param uri     Location of the jenkins server (ex. http://localhost:8080)
     * @param builder Configured HttpClientBuilder to be used
     */
    public JenkinsHttpClient(URI uri, HttpClientBuilder builder) {
        this(uri, builder.build());
    }

    /**
     * Create an unauthenticated Jenkins HTTP client
     *
     * @param uri Location of the jenkins server (ex. http://localhost:8080)
     */
    public JenkinsHttpClient(URI uri) {
        this(uri, HttpClientBuilder.create());
    }

    /**
     * Create an authenticated Jenkins HTTP client
     *
     * @param uri      Location of the jenkins server (ex. http://localhost:8080)
     * @param username Username to use when connecting
     * @param password Password or auth token to use when connecting
     */
    public JenkinsHttpClient(URI uri, String username, String password) {
        this(uri, HttpClientBuilder.create(), username, password);
    }

    /**
     * Create an authenticated Jenkins HTTP client
     *
     * @param uri      Location of the jenkins server (ex. http://localhost:8080)
     * @param builder  Configured HttpClientBuilder to be used
     * @param username Username to use when connecting
     * @param password Password or auth token to use when connecting
     */
    public JenkinsHttpClient(URI uri, HttpClientBuilder builder, String username, String password) {
        this(uri, addAuthentication(builder, uri, username, password));
        if (StringUtils.isNotBlank(username)) {
            localContext = new BasicHttpContext();
            localContext.setAttribute("preemptive-auth", new BasicScheme());
        }
    }

    public JenkinsHttpClient(String url, String userName, String password) {
        this(URI.create(url), HttpClientBuilder.create(), userName, password);
    }

    /**
     * Perform a GET request and parse the response to the given class
     *
     * @param path path to request, can be relative or absolute
     * @param cls  class of the response
     * @param <T>  type of the response
     * @return an instance of the supplied class
     * @throws IOException in case of an error.
     */
    @Override
    public <T extends BaseModel> T get(String path, Class<T> cls) throws IOException {
        HttpGet getMethod = new HttpGet(UrlUtils.toJsonApiUri(uri, context, path));
        HttpResponse response = client.execute(getMethod, localContext);

        LOGGER.debug("{}: {}", path, response.getEntity().getContent().toString());

        jenkinsVersion = ResponseUtils.getJenkinsVersion(response);
        try {
            httpResponseValidator.validateResponse(response);
            return objectFromResponse(cls, response);
        } finally {
            EntityUtils.consume(response.getEntity());
            releaseConnection(getMethod);
        }
    }

    /**
     * Perform a GET request and parse the response and return a simple string
     * of the content
     *
     * @param path path to request, can be relative or absolute
     * @return the entity text
     * @throws IOException in case of an error.
     */
    @Override
    public String get(String path) throws IOException {
        HttpGet getMethod = new HttpGet(UrlUtils.toJsonApiUri(uri, context, path));
        HttpResponse response = client.execute(getMethod, localContext);
        jenkinsVersion = ResponseUtils.getJenkinsVersion(response);
        LOGGER.debug("get({}), version={}, responseCode={}", path, this.jenkinsVersion, response.getStatusLine().getStatusCode());
        try {
            httpResponseValidator.validateResponse(response);
            return IOUtils.toString(response.getEntity().getContent());
        } finally {
            EntityUtils.consume(response.getEntity());
            releaseConnection(getMethod);
        }

    }

    @Override
    public <T extends BaseModel> List<T> getList(String path, Class<T> itemCls) throws IOException {
        String response = get(path);
        return mapper.readValue(response, new TypeReference<List<T>>() {
        });
    }

    /**
     * Perform a GET request and parse the response to the given class, logging
     * any IOException that is thrown rather than propagating it.
     *
     * @param path path to request, can be relative or absolute
     * @param cls  class of the response
     * @param <T>  type of the response
     * @return an instance of the supplied class
     */
    @Override
    public <T extends BaseModel> T getQuietly(String path, Class<T> cls) {
        T value;
        try {
            value = get(path, cls);
            return value;
        } catch (IOException e) {
            LOGGER.debug("getQuietly({}, {})", path, cls.getName(), e);
            // TODO: Is returing null a good idea?
            return null;
        }
    }

    /**
     * Perform a GET request and return the response as InputStream
     *
     * @param path path to request, can be relative or absolute
     * @return the response stream
     * @throws IOException in case of an error.
     */
    @Override
    public InputStream getFile(URI path) throws IOException {
        HttpGet getMethod = new HttpGet(path);
        HttpResponse response = client.execute(getMethod, localContext);
        jenkinsVersion = ResponseUtils.getJenkinsVersion(response);
        httpResponseValidator.validateResponse(response);
        return new RequestReleasingInputStream(response.getEntity().getContent(), getMethod);
    }

    @Override
    public BaseModel post(String path, Object data, Class cls) throws IOException {
        return post(path, data, cls, true);
    }

    /**
     * Perform a POST request and parse the response to the given class
     *
     * @param path      path to request, can be relative or absolute
     * @param data      data to post
     * @param cls       class of the response
     * @param <R>       type of the response
     * @param <D>       type of the data
     * @param crumbFlag true / false.
     * @return an instance of the supplied class
     * @throws IOException in case of an error.
     */
    @Override
    public <R extends BaseModel, D> R post(String path, D data, Class<R> cls, boolean crumbFlag) throws IOException {
        HttpPost request = new HttpPost(UrlUtils.toJsonApiUri(uri, context, path));
        handleCrumbFlag(crumbFlag, request);

        if (data != null) {
            String value = mapper.writeValueAsString(data);
            StringEntity stringEntity = new StringEntity(value, ContentType.APPLICATION_JSON);
            request.setEntity(stringEntity);
        }
        HttpResponse response = client.execute(request, localContext);
        jenkinsVersion = ResponseUtils.getJenkinsVersion(response);

        try {
            httpResponseValidator.validateResponse(response);

            if (cls != null) {
                R responseObject;
                if (cls.equals(ExtractHeader.class)) {
                    ExtractHeader location = new ExtractHeader();
                    location.setLocation(response.getFirstHeader("Location").getValue());
                    responseObject = (R) location;
                } else {
                    responseObject = objectFromResponse(cls, response);
                }
                return responseObject;
            } else {
                return null;
            }
        } finally {
            EntityUtils.consume(response.getEntity());
            releaseConnection(request);
        }
    }

    // xjm： 复制自 D:\Download\aaaa\free\jenkins\jenkinsci\java-client-api\jenkins-client\src\main\java\com\offbytwo\jenkins\client\JenkinsHttpClient.java
    //public <R extends BaseModel, D> R post(String path, D data, Class<R> cls, Map<String, File> fileParams, boolean crumbFlag) throws IOException {
    //    HttpPost request = new HttpPost(UrlUtils.toJsonApiUri(uri, context, path));
    //    handleCrumbFlag(crumbFlag, request);
    //
    //    if (data != null) {
    //        String value = mapper.writeValueAsString(data);
    //        StringEntity stringEntity = new StringEntity(value, ContentType.APPLICATION_JSON);
    //        request.setEntity(stringEntity);
    //    }
    //
    //    // Prepare file parameters
    //    if(fileParams != null && !(fileParams.isEmpty())) {
    //        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
    //        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
    //
    //        for (Map.Entry<String, File> entry : fileParams.entrySet()) {
    //            FileBody fileBody = new FileBody(entry.getValue());
    //            builder.addPart(entry.getKey(), fileBody);
    //        }
    //
    //        HttpEntity entity = builder.build();
    //        request.setEntity(entity);
    //    }
    //
    //    HttpResponse response = client.execute(request, localContext);
    //    jenkinsVersion = ResponseUtils.getJenkinsVersion(response);
    //
    //    try {
    //        httpResponseValidator.validateResponse(response);
    //
    //        if (cls != null) {
    //            R responseObject;
    //            if (cls.equals(ExtractHeader.class)) {
    //                ExtractHeader location = new ExtractHeader();
    //                location.setLocation(response.getFirstHeader("Location").getValue());
    //                responseObject = (R) location;
    //            } else {
    //                responseObject = objectFromResponse(cls, response);
    //            }
    //            return responseObject;
    //        } else {
    //            return null;
    //        }
    //    } finally {
    //        EntityUtils.consume(response.getEntity());
    //        releaseConnection(request);
    //    }
    //}

    /**
     * Perform a POST request using form url encoding.
     * <p>
     * This method was added for the purposes of creating folders, but may be
     * useful for other API calls as well.
     * <p>
     * Unlike post and postXml, the path is *not* modified by adding
     * "/toJsonApiUri/json". Additionally, the params in data are provided as both
     * request parameters including a json parameter, *and* in the
     * JSON-formatted StringEntity, because this is what the folder creation
     * call required. It is unclear if any other jenkins APIs operate in this
     * fashion.
     *
     * @param path      path to request, can be relative or absolute
     * @param data      data to post
     * @param crumbFlag true / false.
     * @throws IOException in case of an error.
     */
    @Override
    public void postForm(String path, Map<String, String> data, boolean crumbFlag) throws IOException {
        HttpPost request;
        if (data != null) {
            // https://gist.github.com/stuart-warren/7786892 was slightly
            // helpful here
            List<String> queryParams = Lists.newArrayList();
            for (String param : data.keySet()) {
                queryParams.add(param + "=" + EncodingUtils.encodeParam(data.get(param)));
            }

            queryParams.add("json=" + EncodingUtils.encodeParam(JSONObject.fromObject(data).toString()));
            String value = mapper.writeValueAsString(data);
            StringEntity stringEntity = new StringEntity(value, ContentType.APPLICATION_FORM_URLENCODED);
            request = new HttpPost(UrlUtils.toNoApiUri(uri, context, path) + StringUtils.join(queryParams, "&"));
            request.setEntity(stringEntity);
        } else {
            request = new HttpPost(UrlUtils.toNoApiUri(uri, context, path));
        }

        handleCrumbFlag(crumbFlag, request);

        HttpResponse response = client.execute(request, localContext);
        jenkinsVersion = ResponseUtils.getJenkinsVersion(response);

        try {
            httpResponseValidator.validateResponse(response);
        } finally {
            EntityUtils.consume(response.getEntity());
            releaseConnection(request);
        }
    }


    /**
     * Perform a POST request using form url encoding and return HttpResponse object
     * This method is not performing validation and can be used for more generic queries to jenkins.
     *
     * @param path path to request, can be relative or absolute
     * @param data data to post
     * @throws IOException, HttpResponseException
     */
    @Override
    public HttpResponse postFormWithResult(String path, List<NameValuePair> data, boolean crumbFlag) throws IOException {
        HttpPost request;
        if (data != null) {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(data);
            request = new HttpPost(UrlUtils.toNoApiUri(uri, context, path));
            request.setEntity(urlEncodedFormEntity);
        } else {
            request = new HttpPost(UrlUtils.toNoApiUri(uri, context, path));
        }

        handleCrumbFlag(crumbFlag, request);
        HttpResponse response = client.execute(request, localContext);
        jenkinsVersion = ResponseUtils.getJenkinsVersion(response);
        return response;
    }

    /**
     * Perform a POST request of XML (instead of using json mapper) and return a
     * string rendering of the response entity.
     *
     * @param path     path to request, can be relative or absolute
     * @param xmlData data data to post
     * @return A string containing the xml response (if present)
     * @throws IOException in case of an error.
     */
    @Override
    public String postXml(String path, String xmlData) throws IOException {
        return postXml(path, xmlData, true);
    }

    @Override
    public String postXml(String path, String xmlData, boolean crumbFlag) throws IOException {
        HttpPost request = new HttpPost(UrlUtils.toJsonApiUri(uri, context, path));
        handleCrumbFlag(crumbFlag, request);

        if (xmlData != null) {
            request.setEntity(new StringEntity(xmlData, ContentType.create("text/xml", "utf-8")));
        }
        HttpResponse response = client.execute(request, localContext);
        jenkinsVersion = ResponseUtils.getJenkinsVersion(response);
        try {
            httpResponseValidator.validateResponse(response);
            return IOUtils.toString(response.getEntity().getContent());
        } finally {
            EntityUtils.consume(response.getEntity());
            releaseConnection(request);
        }
    }

    /**
     * Post a text entity to the given URL using the default content type
     *
     * @param path      The path.
     * @param textData  data.
     * @param crumbFlag true/false.
     * @return resulting response
     * @throws IOException in case of an error.
     */
    @Override
    public String postText(String path, String textData, boolean crumbFlag) throws IOException {
        return postText(path, textData, ContentType.DEFAULT_TEXT, crumbFlag);
    }

    /**
     * Post a text entity to the given URL with the given content type
     *
     * @param path        The path.
     * @param textData    The data.
     * @param contentType {@link ContentType}
     * @param crumbFlag   true or false.
     * @return resulting response
     * @throws IOException in case of an error.
     */
    @Override
    public String postText(String path, String textData, ContentType contentType, boolean crumbFlag) throws IOException {
        HttpPost request = new HttpPost(UrlUtils.toJsonApiUri(uri, context, path));
        handleCrumbFlag(crumbFlag, request);

        if (textData != null) {
            request.setEntity(new StringEntity(textData, contentType));
        }
        HttpResponse response = client.execute(request, localContext);
        jenkinsVersion = ResponseUtils.getJenkinsVersion(response);
        try {
            httpResponseValidator.validateResponse(response);
            return IOUtils.toString(response.getEntity().getContent());
        } finally {
            EntityUtils.consume(response.getEntity());
            releaseConnection(request);
        }
    }

    /**
     * Perform POST request that takes no parameters and returns no response
     *
     * @param path path to request
     * @throws IOException in case of an error.
     */
    @Override
    public void post(String path) throws IOException {
        post(path, null, null, false);
    }

    @Override
    public void post(String path, boolean crumbFlag) throws IOException {
        post(path, null, null, crumbFlag);
    }

    /**
     * Perform a POST request using form url encoding.
     * <p>
     * This method was added for the purposes of creating credentials, but may be
     * useful for other API calls as well.
     * <p>
     * Unlike post and postXml, the path is *not* modified by adding
     * "/api/json". Additionally, the params in data are provided as both
     * request parameters including a json parameter, *and* in the
     * JSON-formatted StringEntity, because this is what the folder creation
     * call required. It is unclear if any other jenkins APIs operate in this
     * fashion.
     *
     * @param path      path to request, can be relative or absolute
     * @param data      data to post
     * @param crumbFlag true / false.
     * @throws IOException in case of an error.
     */
    @Override
    public void postFormJson(String path, Map<String, Object> data, boolean crumbFlag) throws IOException {
        HttpPost request;
        if (data != null) {
            // https://gist.github.com/stuart-warren/7786892 was slightly
            // helpful here
            List<String> queryParams = Lists.newArrayList();
            queryParams.add("json=" + EncodingUtils.encodeParam(JSONObject.fromObject(data).toString()));
            String value = mapper.writeValueAsString(data);
            StringEntity stringEntity = new StringEntity(value, ContentType.APPLICATION_FORM_URLENCODED);
            request = new HttpPost(noapi(path) + StringUtils.join(queryParams, "&"));
            request.setEntity(stringEntity);
        } else {
            request = new HttpPost(noapi(path));
        }

        if (crumbFlag) {
            Crumb crumb = getQuietly("/crumbIssuer", Crumb.class);
            if (crumb != null) {
                request.addHeader(new BasicHeader(crumb.getCrumbRequestField(), crumb.getCrumb()));
            }
        }

        HttpResponse response = client.execute(request, localContext);
        getJenkinsVersionFromHeader(response);

        try {
            httpResponseValidator.validateResponse(response);
        } finally {
            EntityUtils.consume(response.getEntity());
            releaseConnection(request);
        }
    }

    private URI noapi(String path) {
        if (!path.toLowerCase().matches("https?://.*")) {
            path = urlJoin(this.context, path);
        }
        return uri.resolve("/").resolve(path);
    }

    private String urlJoin(String path1, String path2) {
        if (!path1.endsWith("/")) {
            path1 += "/";
        }
        if (path2.startsWith("/")) {
            path2 = path2.substring(1);
        }
        return path1 + path2;
    }

    private <T extends BaseModel> T objectFromResponse(Class<T> cls, HttpResponse response) throws IOException {
        InputStream content = response.getEntity().getContent();
        byte[] bytes = ByteStreams.toByteArray(content);

        /**
         * xjm:
         * HttpResponse.Entity是一个stream，只能被使用1次
         */

        T result = mapper.readValue(bytes, cls);

        // TODO: original:
        // T result = mapper.readValue(content, cls);
//        result.setClient(this);
        return result;
    }

    private ObjectMapper getDefaultMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
//        mapper.configure(JsonParser.Feature.INTERN_FIELD_NAMES, true);
//        mapper.configure(JsonParser.Feature.CANONICALIZE_FIELD_NAMES, true);
//        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    /**
     * @return the version string.
     */
    public String getJenkinsVersion() {
        return this.jenkinsVersion;
    }

    /**
     * Check to see if the jenkins version has been set
     * to something different than the initialization value
     * from the constructor. This means there has never been made
     * a communication with the Jenkins server.
     *
     * @return true if jenkinsVersion has been set by communication, false otherwise.
     */
    public boolean isJenkinsVersionSet() {
        return !EMPTY_VERSION.equals(this.jenkinsVersion);
    }

    /**
     * Closes underlying resources.
     * Any I/O errors whilst closing are logged with debug log level
     * Closed instances should no longer be used
     * Closing an already closed instance has no side effects
     */
    @Override
    public void close() {
        try {
            client.close();
        } catch (final IOException ex) {
            LOGGER.debug("I/O exception closing client", ex);
        }
    }

    private void releaseConnection(HttpRequestBase httpRequestBase) {
        httpRequestBase.releaseConnection();
    }

    protected static HttpClientBuilder addAuthentication(HttpClientBuilder builder, URI uri, String username, String password) {
        if (StringUtils.isNotBlank(username)) {
            CredentialsProvider provider = new BasicCredentialsProvider();
            AuthScope scope = new AuthScope(uri.getHost(), uri.getPort(), "realm");
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
            provider.setCredentials(scope, credentials);
            builder.setDefaultCredentialsProvider(provider);

            builder.addInterceptorFirst(new PreemptiveAuth());
        }
        return builder;
    }

    private void getJenkinsVersionFromHeader(HttpResponse response) {
        Header[] headers = response.getHeaders("X-Jenkins");
        if (headers.length == 1) {
            this.jenkinsVersion = headers[0].getValue();
        }
    }

    private void handleCrumbFlag(boolean crumbFlag, HttpPost request) {
        if (crumbFlag) {
            Crumb crumb = getQuietly("/crumbIssuer", Crumb.class);
            if (crumb != null) {
                request.addHeader(new BasicHeader(crumb.getCrumbRequestField(), crumb.getCrumb()));
            }
        }
    }

    protected HttpContext getLocalContext() {
        return localContext;
    }

    protected void setLocalContext(HttpContext localContext) {
        this.localContext = localContext;
    }

}

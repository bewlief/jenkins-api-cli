package com.xtech.jenkins.client;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JenkinsHttpClientTest {

    private static final String URL = "http://localhost:8080/jenkins";
    private static final String CONTENT = "someJson";

    @Mock
    private CloseableHttpClient client;

    @Mock
    private CloseableHttpResponse response;

    @Mock
    private Header versionHeader;

    @Mock
    private StatusLine statusLine;

    @Mock
    private HttpEntity entity;

    @Test
    void testShouldReturnSimpleString() throws IOException {
        // GIVEN
        when(client.execute(any(HttpUriRequest.class), eq((HttpContext) null))).thenReturn(response);
        when(response.getHeaders(TestDataProvider.JENKINS_HEADER)).thenReturn(new Header[]{versionHeader});
        when(response.getStatusLine()).thenReturn(statusLine);
        when(versionHeader.getValue()).thenReturn(TestDataProvider.JENKINS_VERSION);
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(new ByteArrayInputStream(CONTENT.getBytes()));

        JenkinsHttpClient jclient = getJenkinsClient();

        // WHEN
        String s = jclient.get("job/someJob");

        // THEN
        assertEquals(CONTENT, s);
        Mockito.verify(client, Mockito.times(1)).execute(any(HttpUriRequest.class), eq((HttpContext) null));
    }

    //@Test
    //void testShouldReturnJobObject() throws IOException {
    //    // GIVEN
    //    when(client.execute(any(HttpUriRequest.class), eq((HttpContext) null))).thenReturn(response);
    //    when(response.getHeaders(TestDataProvider.JENKINS_HEADER)).thenReturn(new Header[]{versionHeader});
    //    when(response.getStatusLine()).thenReturn(statusLine);
    //    when(versionHeader.getValue()).thenReturn(TestDataProvider.JENKINS_VERSION);
    //    when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
    //    when(response.getEntity()).thenReturn(entity);
    //    when(entity.getContent()).thenReturn(is);
    //
    //    JenkinsHttpClient jclient = getJenkinsClient();
    //
    //    // WHEN
    //    Job target = jclient.get("job/someJob", Job.class);
    //
    //    // THEN
    //    assertEquals(CONTENT, target);
    //    Mockito.verify(client, Mockito.times(1)).execute(any(HttpUriRequest.class), eq((HttpContext) null));
    //}


    private JenkinsHttpClient getJenkinsClient(){
        return new JenkinsHttpClient(URI.create(URL), client);
    }

}

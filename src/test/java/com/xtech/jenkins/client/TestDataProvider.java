package com.xtech.jenkins.client;

import org.apache.http.*;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

public class TestDataProvider {
    public static String JENKINS_VERSION = "6.0.88";
    public static String JENKINS_HEADER = "X-Jenkins";

    /**
     * return Header[] with X-Jenkins
     *
     * @return
     */
    public static Header[] getXJenkinsHeaders() {
        return new Header[]{
                new BasicHeader(HttpHeaders.ACCEPT, "application/json"),
                new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"),
                new BasicHeader(JENKINS_HEADER, JENKINS_VERSION)
        };
    }

    /**
     * return Header[] without X-Jenkins
     *
     * @return
     */
    public static Header[] getNoXJenkinsHeaders() {
        return new Header[]{
                new BasicHeader(HttpHeaders.ACCEPT, "application/json"),
                new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"),
        };
    }

    public static StatusLine getStatusLine() {
        return getStatusLine(200, "status line reaonse phrase");
    }

    public static StatusLine getStatusLine(int statusCode, String reason) {
        ProtocolVersion protocolVersion = new ProtocolVersion("https", 1, 1);
        return new BasicStatusLine(protocolVersion, statusCode, reason);
    }

    public static HttpResponse getResponse200() {
        return new BasicHttpResponse(TestDataProvider.getStatusLine());
    }
}

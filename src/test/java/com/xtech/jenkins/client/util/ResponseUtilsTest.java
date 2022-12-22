package com.xtech.jenkins.client.util;

import com.xtech.jenkins.client.TestDataProvider;
import org.apache.http.HttpResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ResponseUtilsTest {
    @Test
    void testShouldReturnJenkinsVersionWhenXJenkinsInHeader() {
        // GIVEN
        HttpResponse response = TestDataProvider.getResponse200();
        response.setHeaders(TestDataProvider.getXJenkinsHeaders());

        // WHEN
        String version = ResponseUtils.getJenkinsVersion(response);

        // THEN
        Assertions.assertEquals(TestDataProvider.JENKINS_VERSION, version);
    }

    @Test
    void testShouldReturnBlankWhenNoXJenkinsInHeader() {
        // GIVEN
        HttpResponse response = TestDataProvider.getResponse200();
        response.setHeaders(TestDataProvider.getNoXJenkinsHeaders());

        // WHEN
        String version = ResponseUtils.getJenkinsVersion(response);

        // THEN
        Assertions.assertEquals("", version);
    }
}

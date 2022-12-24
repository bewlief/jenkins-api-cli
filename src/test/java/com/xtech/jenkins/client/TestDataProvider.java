package com.xtech.jenkins.client;

import com.xtech.jenkins.client.model.job.Job;
import org.apache.http.*;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

public class TestDataProvider {
    public static String JENKINS_VERSION = "6.0.88";
    public static String JENKINS_HEADER = "X-Jenkins";
    public static String JENKINS_SERVER="http://localhost:8080/jenkins";

    public static String SAMPLE_JOB_XML="This XML file does not appear to have any style information associated with it. The document tree is shown below.\n" +
            "<flow-definition plugin=\"workflow-job@1254.v3f64639b_11dd\">\n" +
            "<actions/>\n" +
            "<description>just a sample pipeline</description>\n" +
            "<displayName>pipeline1-hello-world</displayName>\n" +
            "<keepDependencies>false</keepDependencies>\n" +
            "<properties>\n" +
            "<jenkins.model.BuildDiscarderProperty>\n" +
            "<strategy class=\"hudson.tasks.LogRotator\">\n" +
            "<daysToKeep>-1</daysToKeep>\n" +
            "<numToKeep>10</numToKeep>\n" +
            "<artifactDaysToKeep>-1</artifactDaysToKeep>\n" +
            "<artifactNumToKeep>-1</artifactNumToKeep>\n" +
            "</strategy>\n" +
            "</jenkins.model.BuildDiscarderProperty>\n" +
            "<com.sonyericsson.rebuild.RebuildSettings plugin=\"rebuild@1.34\">\n" +
            "<autoRebuild>false</autoRebuild>\n" +
            "<rebuildDisabled>false</rebuildDisabled>\n" +
            "</com.sonyericsson.rebuild.RebuildSettings>\n" +
            "</properties>\n" +
            "<definition class=\"org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition\" plugin=\"workflow-cps@3583.v4f58de0d78d5\">\n" +
            "<script>pipeline { agent any stages { stage('Hello') { steps { echo 'Hello World' } } } } </script>\n" +
            "<sandbox>true</sandbox>\n" +
            "</definition>\n" +
            "<triggers/>\n" +
            "<disabled>false</disabled>\n" +
            "</flow-definition>";

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

    public static Job getJenkinsJob(){
        return new Job("job1",JENKINS_SERVER+"/job/job1","full name of job1");
    }
}

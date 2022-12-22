package com.xtech.jenkins.client.util;

import com.xtech.jenkins.client.folder.FolderJob;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.DayOfWeek;

import static org.mockito.Mockito.doThrow;

class UrlUtilsTest {

    private static FolderJob folderJob;
    private static String FOLDER = "folder1";
    private static String JOB_NAME1 = "job1";
    private static String JOB_NAME2 = "job1/job/job2";

    @BeforeAll
    public static void setup() {
        folderJob = new FolderJob(FOLDER);
    }

    @Test
    void testShouldReturnCorrectUrlForAFolder() {
        // GIVEN

        // WHEN
        String s = UrlUtils.toBaseUrl(folderJob);

        // THEN
        Assertions.assertEquals("/job/folder1/", s);
    }

    @Test
    void testShouldReturnRootPathWhenFolderJobIsNull() {
        // GIVEN
        FolderJob folderJob = null;

        // WHEN
        String s = UrlUtils.toBaseUrl(folderJob);

        // THEN
        Assertions.assertEquals("/", s);
    }

    @Test
    void testShouldReturnBaseUrlForFolderJobWithJobName() {
        // GIVEN
        String jobName = JOB_NAME1;
        String expected = "/job/" + FOLDER + "/job/" + jobName;

        // WHEN
        String target = UrlUtils.toJobBaseUrl(folderJob, jobName);

        // THEN
        Assertions.assertEquals(expected, target);
    }

    @Test
    void testShouldReturnViewBaseUrl() {
        // GIVEN

        //doThrow(new RuntimeException()).when(employee).work(DayOfWeek.SUNDAY);

        // WHEN


        // THEN
        //Assertions.assertThrows(RuntimeException.class, ()->{});
    }

    @Test
    void testShouldReturnFullJobPath() {
        // GIVEN

        // WHEN
        String target = UrlUtils.toFullJobPath("/job1/job2/job3");

        // THEN
        Assertions.assertEquals("/job/job1/job/job2/job/job3", target);
    }

    @Test
    void testShouldReturnJoinedPath() {
        // GIVE
        String path1 = "/a/b/c";
        String path2 = "/11/22/33";

        // WHEN
        String target = UrlUtils.join(path1, path2);

        // THEN
        Assertions.assertEquals("/a/b/c/11/22/33", target);
    }

    @Test
    void testShouldReturnBlankWhenPathsAreBlank() {
        // GIVEN
        String path1 = "";
        String path2 = "";

        // WHEN
        String target = UrlUtils.join(path1, path2);

        // THEN
        Assertions.assertEquals("", target);
    }

    @Test
    void testShouldReturnPath1WhenPath2IsBlank() {
        // GIVEN
        String path1 = "/a/b/c";
        String path2 = "";

        // WHEN
        String target = UrlUtils.join(path1, path2);

        // THEN
        Assertions.assertEquals("/a/b/c", target);
    }

    @Test
    void testShouldReturnPath2WhenPath1IsBlank() {
        // GIVEN
        String path1 = "";
        String path2 = "/a/b/c";

        // WHEN
        String target = UrlUtils.join(path1, path2);

        // THEN
        Assertions.assertEquals("/a/b/c", target);
    }

    @Test
    void testShouldHandleDelimiterCorrectly() {
        // GIVE
        String path1 = "/a/b/c/";
        String path2 = "11/22/33";

        // WHEN
        String target = UrlUtils.join(path1, path2);

        // THEN
        Assertions.assertEquals("/a/b/c/11/22/33", target);
    }

    @Test
    void testShouldReturnCorrectJsonApiUri() {
        // GIVEN
        String url = "https://localhost:8080";
        String context = "check";
        String path = "/a/b";
        URI uri = URI.create(url);

        String expected = "https://localhost:8080/check/a/b/api/json";

        // WHEN
        URI target = UrlUtils.toJsonApiUri(uri, context, path);

        // THEN
        Assertions.assertEquals(expected, target.toString());
    }

    @Test
    void testShouldReturnCorrectJsonApiUriWithBlueUrl() {
        // GIVEN
        String url = "https://localhost:8080";
        String context = "check";
        String path = "/blue/rest/organizations/jenkins/pipelines/a/b";
        URI uri = URI.create(url);

        String expected = "https://localhost:8080/check/blue/rest/organizations/jenkins/pipelines/a/b/api/json";

        // WHEN
        URI target = UrlUtils.toJsonApiUri(uri, context, path);

        // THEN
        Assertions.assertEquals(expected, target.toString());
    }

    @Test
    void testShouldReturnCorrectJsonApiUriWithQuestionMarkInPath() {
        // GIVEN
        String url = "https://localhost:8080";
        String context = "check";
        String path = "/a?name=1111&addr=2222";
        URI uri = URI.create(url);

        String expected = "https://localhost:8080/check/a/api/json?name=1111&addr=2222";

        // WHEN
        URI target = UrlUtils.toJsonApiUri(uri, context, path);

        // THEN
        Assertions.assertEquals(expected, target.toString());
    }

    @Test
    void testShouldReturnCorrectFullURI() {
        // GIVEN
        String url = "https://localhost:8080";
        String context = "check";
        String path = "/a/b";
        URI uri = URI.create(url);

        String expected = "https://localhost:8080/check/a/b";

        // WHEN
        URI target = UrlUtils.toNoApiUri(uri, context, path);

        // THEN
        Assertions.assertEquals(expected, target.toString());
    }
}

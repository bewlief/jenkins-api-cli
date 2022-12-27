package com.xtech.jenkins.client.helper;

import com.xtech.jenkins.client.model.core.Step;
import com.xtech.jenkins.client.util.Constants;

import java.io.IOException;
import java.util.List;

public class BlueOcean extends BaseManager {
    public static final String baseUrl = "/blue/rest/organizations/jenkins/pipelines";

    public String log(String folder, String jobName, String buildId, String stepId) {
//        /blue/rest/organizations/jenkins/pipelines/param/pipelines/param-p1-j1/runs/4/steps/5/log

//        /blue/rest/organizations/jenkins/pipelines/param/pipelines/param-p1-j1/runs/4/steps/
        return null;
    }

    public List<Step> steps(String folder, String jobName, String buildId) throws IOException {
        String path=String.format(Constants.API_GET_BLUE_STEPS, folder, jobName, buildId);
        return getClient().getList(path, Step.class);
    }

    @Override
    protected String[] getDependencyArray() {
        return new String[0];
    }
}

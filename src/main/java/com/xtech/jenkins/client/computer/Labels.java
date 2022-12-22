package com.xtech.jenkins.client.computer;

import com.xtech.jenkins.client.BaseManager;

import java.io.IOException;

public class Labels extends BaseManager {
    @Override
    protected String[] getDependencyArray() {
        return new String[0];
    }

    public LabelDetail getLabels() throws IOException {
//        http://localhost:8080/jenkins/computer/agent/surenc/api/json
        return getClient().get("/computer/agent/surenc/api/json?depth=1", LabelDetail.class);
    }
}

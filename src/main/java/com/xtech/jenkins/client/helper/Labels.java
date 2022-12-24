package com.xtech.jenkins.client.helper;

import com.xtech.jenkins.client.model.computer.LabelDetail;

import java.io.IOException;

public class Labels extends BaseManager {
    @Override
    protected String[] getDependencyArray() {
        return new String[0];
    }

    public LabelDetail getLabels() throws IOException {
//        http://localhost:8080/jenkins/computer/agent/xtech/api/json
        // todo verify the path!
        return getClient().get("/computer/agent/api/json?depth=1", LabelDetail.class);
    }
}

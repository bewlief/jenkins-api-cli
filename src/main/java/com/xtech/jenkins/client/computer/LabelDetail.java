package com.xtech.jenkins.client.computer;

import com.xtech.jenkins.client.BaseModel;

import java.util.List;

public class LabelDetail extends BaseModel {
    private List<Label> labels;

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }
}

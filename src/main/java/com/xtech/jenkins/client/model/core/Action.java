package com.xtech.jenkins.client.model.core;

import com.xtech.jenkins.client.model.BaseModel;

import java.util.List;

/**
 * @author suren
 */
public class Action extends BaseModel {
    private List<Cause> causes;

    public List<Cause> getCauses() {
        return causes;
    }

    public void setCauses(List<Cause> causes) {
        this.causes = causes;
    }
}

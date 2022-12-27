package com.xtech.jenkins.client.model.core;

import com.xtech.jenkins.client.model.BaseModel;
import com.xtech.jenkins.client.model.computer.Label;
import com.xtech.jenkins.client.model.job.Job;
import com.xtech.jenkins.client.model.view.View;
import lombok.Data;

import java.util.List;

@Data
public class JenkinsInfo extends BaseModel {
    private List<Label> assignedLabels;
    private String mode;
    private String nodeDescription;
    private int numExecutors;
    private String description;
    private List<Job> jobs;
    private boolean quietingDown;
    private int slaveAgentPort;
    private boolean useCrumbs;
    private boolean useSecurity;
    private List<View> views;
}

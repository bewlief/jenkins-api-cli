package com.xtech.jenkins.client.model.view;

import com.xtech.jenkins.client.model.BaseModel;
import com.xtech.jenkins.client.model.job.Job;
import lombok.Data;

import java.util.List;

/**
 * @author xtech
 */
@Data
public class View extends BaseModel {
    private String name;
    private String url;
    private String description;
    private List<Job> jobs;
}

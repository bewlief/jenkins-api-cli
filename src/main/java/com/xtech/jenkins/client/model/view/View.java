package com.xtech.jenkins.client.model.view;

import com.xtech.jenkins.client.model.BaseModel;
import com.xtech.jenkins.client.model.job.Job;

import java.util.List;

/**
 * @author xtech
 */
public class View extends BaseModel {
    private String name;
    private String url;
    private String description;
    private List<Job> jobs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}

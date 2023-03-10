/*
 * Copyright (c) 2013 Cosmin Stejerean, Karl Heinz Marbaise, and contributors.
 *
 * Distributed under the MIT license: http://opensource.org/licenses/MIT
 */

package com.xtech.jenkins.client.model.job;

import com.xtech.jenkins.client.model.BaseModel;

/**
 * basic info of a job
 */
public class Job extends BaseModel {

    private String name;
    private String url;
    private String fullName;

    public Job() {
    }

    public Job(String name) {
        this();
        this.name = name;
    }

    public Job(String name, String url) {
        this();
        this.name = name;
        this.url = url;
        this.fullName = null;
    }

    public Job(String name, String url, String fullName) {
        this();
        this.name = name;
        this.url = url;
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getFullName() {
        return fullName;
    }
}

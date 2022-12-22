package com.xtech.jenkins.client.model.core;

import com.xtech.jenkins.client.model.BaseModel;

/**
 * @author suren
 */
public class Author extends BaseModel {
    private String absoluteUrl;
    private String fullName;

    public String getAbsoluteUrl() {
        return absoluteUrl;
    }

    public void setAbsoluteUrl(String absoluteUrl) {
        this.absoluteUrl = absoluteUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

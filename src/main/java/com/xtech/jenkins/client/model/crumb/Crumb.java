package com.xtech.jenkins.client.model.crumb;

import com.xtech.jenkins.client.model.BaseModel;

/**
 * @author Adrien Lecharpentier
 * <a href="mailto:adrien.lecharpentier@gmail.com">adrien.
 * lecharpentier@gmail.com</a>
 */
public class Crumb extends BaseModel {

    private String crumbRequestField;
    private String crumb;

    public Crumb() {
    }

    public Crumb(String crumbRequestField, String crumb) {
        this.crumbRequestField = crumbRequestField;
        this.crumb = crumb;
    }

    public String getCrumbRequestField() {
        return crumbRequestField;
    }

    public String getCrumb() {
        return crumb;
    }
}

package com.xtech.jenkins.client.model.core;

import com.xtech.jenkins.client.model.BaseModel;

/**
 * 健康报告
 *
 * @author xtech
 */
public class Health extends BaseModel {
    private String description;
    private String iconClassName;
    private String iconUrl;
    private int score;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconClassName() {
        return iconClassName;
    }

    public void setIconClassName(String iconClassName) {
        this.iconClassName = iconClassName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

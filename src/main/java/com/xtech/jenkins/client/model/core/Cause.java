package com.xtech.jenkins.client.model.core;

import com.xtech.jenkins.client.model.BaseModel;
import lombok.Data;

/**
 * @author xtech
 */
@Data
public class Cause extends BaseModel {
    private String userId;
    private String userName;
    private String shortDescription;
    private Integer upstreamBuild;
    private String upstreamProject;
    private String upstreamUrl;

    public Cause(){
        this.upstreamBuild=0;
        // todo initialize other fields?
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cause that = (Cause) o;

        if (shortDescription != null ? !shortDescription.equals(that.shortDescription) : that.shortDescription != null) {
            return false;
        }
        if (upstreamBuild != null ? !upstreamBuild.equals(that.upstreamBuild) : that.upstreamBuild != null) {
            return false;
        }
        if (upstreamProject != null ? !upstreamProject.equals(that.upstreamProject) : that.upstreamProject != null) {
            return false;
        }
        if (upstreamUrl != null ? !upstreamUrl.equals(that.upstreamUrl) : that.upstreamUrl != null) {
            return false;
        }
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
            return false;
        }
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) {
            return false;
        }

        return true;
    }
    @Override
    public int hashCode() {
        int result = shortDescription != null ? shortDescription.hashCode() : 0;
        result = 31 * result + (upstreamBuild != null ? upstreamBuild.hashCode() : 0);
        result = 31 * result + (upstreamProject != null ? upstreamProject.hashCode() : 0);
        result = 31 * result + (upstreamUrl != null ? upstreamUrl.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        return result;
    }
}

package com.xtech.jenkins.client.model.core;

import com.xtech.jenkins.client.model.BaseModel;

/**
 * 成品信息
 *
 * @author xtech
 */
public class Artifact extends BaseModel {
    private String displayPath;
    private String fileName;
    private String relativePath;

    public String getDisplayPath() {
        return displayPath;
    }

    public void setDisplayPath(String displayPath) {
        this.displayPath = displayPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }
}

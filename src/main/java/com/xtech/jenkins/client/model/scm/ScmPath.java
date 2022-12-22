package com.xtech.jenkins.client.model.scm;

import com.xtech.jenkins.client.model.BaseModel;

/**
 * @author suren
 */
public class ScmPath extends BaseModel {
    private String editType;
    private String file;

    public String getEditType() {
        return editType;
    }

    public void setEditType(String editType) {
        this.editType = editType;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
package com.xtech.jenkins.client.model.scm;

import com.xtech.jenkins.client.model.BaseModel;
import lombok.Data;

import java.util.List;

/**
 * @author xtech
 * = BuildChangeSet
 */
@Data
public class ScmChangeInfo extends BaseModel {
    private List<ScmChange> items;
    // xjm: copy from BuildChangeSet
    private String kind;
}

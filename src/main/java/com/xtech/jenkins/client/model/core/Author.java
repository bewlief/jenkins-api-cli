package com.xtech.jenkins.client.model.core;

import com.xtech.jenkins.client.model.BaseModel;
import lombok.Data;

/**
 * @author xtech
 * = BuildChangeSetAuthor
 */
@Data
public class Author extends BaseModel {
    private String absoluteUrl;
    private String fullName;
}

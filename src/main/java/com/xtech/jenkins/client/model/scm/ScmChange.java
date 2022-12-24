package com.xtech.jenkins.client.model.scm;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xtech.jenkins.client.model.BaseModel;
import com.xtech.jenkins.client.model.core.Author;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SCM变更记录
 * = BuildChangeSetItem
 */
@Data
@JsonIgnoreProperties(value = {"other"})
public class ScmChange extends BaseModel {
    @JsonIgnore
    private Map<String, Object> other = new HashMap<>();

    private List<String> affectedPaths;
    private String commitId;
    private long timestamp;
    private Author author;
    private String authorEmail;
    private String comment;
    private String date;
    private String id;
    private String msg;
    private List<ScmPath> paths;

    @JsonAnySetter
    public void set(String key, Object value) {
        other.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> any() {
        return other;
    }
}

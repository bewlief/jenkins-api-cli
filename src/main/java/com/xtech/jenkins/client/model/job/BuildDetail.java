package com.xtech.jenkins.client.model.job;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xtech.jenkins.client.model.BaseModel;
import com.xtech.jenkins.client.model.core.Artifact;
import com.xtech.jenkins.client.model.core.BuildResult;
import com.xtech.jenkins.client.model.core.Cause;
import com.xtech.jenkins.client.model.scm.ScmChangeInfo;
import lombok.Data;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * detailed information of one build
 */
@JsonIgnoreProperties(value = {"other"})
@Data
public class BuildDetail extends BaseModel {
    @JsonIgnore
    private Map<String, Object> other = new HashMap<>();

    private List<Artifact> artifacts;
    private boolean building;
    private String description;
    private String displayName;
    private long duration;
    private long estimatedDuration;
    private String fullDisplayName;
    private String id;
    private boolean keepLog;
    private int number;
    private int queueId;
    private BuildResult result;
    private long timestamp;
    private String color;
    private String url;
    private List<ScmChangeInfo> changeSets;
    private BuildInfo nextBuild;
    private BuildInfo previousBuild;
    private String executor;
    private String builtOn;
    private List<BuildAction> actions;

    @JsonAnySetter
    public void set(String key, Object value) {
        other.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> any() {
        return other;
    }

    // todo get all causes
    public List<Cause> getCauses(){
        return Collections.EMPTY_LIST;
    }
}

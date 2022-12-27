package com.xtech.jenkins.client.model.job;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xtech.jenkins.client.model.BaseModel;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * basic information of a build
 */
@Data
@JsonIgnoreProperties(value = {"other"})
public class BuildInfo extends BaseModel {
    @JsonIgnore
    private Map<String, Object> other = new HashMap<>();

    private int number;
    private String url;
    private int queueId;

    @JsonAnySetter
    public void set(String key, Object value) {
        other.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> any() {
        return other;
    }

    // todo get details
    public BuildDetail details(){
        return null;
    }
}

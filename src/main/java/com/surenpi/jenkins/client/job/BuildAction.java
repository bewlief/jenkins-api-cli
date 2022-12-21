package com.surenpi.jenkins.client.job;

import com.fasterxml.jackson.annotation.*;
import com.surenpi.jenkins.client.BaseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(value = {"other"})
public class BuildAction extends BaseModel {
    @JsonIgnore
    private Map<String, Object> other = new HashMap<>();

    @JsonProperty("_class")
    private String className;

    @Override
    public String toString() {
        return "BuildAction{" +
                "other=" + other +
                ", className='" + className + '\'' +
                ", parameterDefinitions=" + parameterDefinitions +
                '}';
    }

    @JsonProperty("parameters")
    @JsonAlias("causes")
    private List<BuildParameter> parameterDefinitions;

    public List<BuildParameter> getParameterDefinitions() {
        return parameterDefinitions;
    }

    @JsonAnySetter
    public void set(String key, Object value) {
        other.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> any() {
        return other;
    }

    public Map<String, Object> getOther() {
        return other;
    }

    public void setOther(Map<String, Object> other) {
        this.other = other;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setParameterDefinitions(List<BuildParameter> parameterDefinitions) {
        this.parameterDefinitions = parameterDefinitions;
    }

}

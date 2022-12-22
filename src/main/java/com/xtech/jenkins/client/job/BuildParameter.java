package com.xtech.jenkins.client.job;

import com.fasterxml.jackson.annotation.*;
import com.xtech.jenkins.client.BaseModel;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(value = {"other"})
public class BuildParameter extends BaseModel {
    @JsonIgnore
    private Map<String, Object> other = new HashMap<>();

    @JsonProperty("_class")
    private String className;

    private String defaultParameterValue;
    private String description;
    private String name;
    private String type;
    private String value;
    private String shortDescription;
    private String userId;

    @JsonAnySetter
    public void set(String key, Object value) {
        other.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> any() {
        return other;
    }

    @Override
    public String toString() {
        return "BuildParameter{" +
                "other=" + other +
                ", className='" + className + '\'' +
                ", defaultParameterValue='" + defaultParameterValue + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;

    public String getDefaultParameterValue() {
        return defaultParameterValue;
    }

    public void setDefaultParameterValue(String defaultParameterValue) {
        this.defaultParameterValue = defaultParameterValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

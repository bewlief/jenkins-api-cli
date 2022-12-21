package com.surenpi.jenkins.client.job;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.surenpi.jenkins.client.BaseModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author suren
 */
@JsonIgnoreProperties(value = {"other"})
public class BuildInfo extends BaseModel {
    @JsonIgnore
    private Map<String, Object> other = new HashMap<>();

    private int number;
    private String url;

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

    @Override
    public String toString() {
        return "BuildInfo{" +
                "other=" + other +
                ", number=" + number +
                ", url='" + url + '\'' +
                '}';
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

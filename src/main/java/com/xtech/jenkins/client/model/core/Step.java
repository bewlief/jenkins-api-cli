package com.xtech.jenkins.client.model.core;

import com.xtech.jenkins.client.model.BaseModel;
import lombok.Data;

/**
 * @author xtech
 */
@Data
public class Step extends BaseModel {
    private String id;
    private String type;
    private String displayName;
    private String displayDescription;
    private long durationInMillis;
    private String startTime;
    private String result;
    private String state;
}

package com.xtech.jenkins.client.model.queue;

import com.xtech.jenkins.client.model.BaseModel;
import lombok.Data;

/**
 *
 */
@Data
public class QueueTask extends BaseModel {
    private String name;
    private String url;
    private String color;
}

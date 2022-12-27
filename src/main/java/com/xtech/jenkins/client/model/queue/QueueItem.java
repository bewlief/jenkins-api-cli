package com.xtech.jenkins.client.model.queue;

import com.xtech.jenkins.client.model.BaseModel;
import lombok.Data;

/**
 *
 */
@Data
public class QueueItem extends BaseModel {
    private int id;
    private boolean blocked;
    private boolean buildable;
    private long inQueueSince;
    private String params;
    private boolean stuck;
    private String url;
    private String why;
    private long buildableStartMilliseconds;
    private boolean pending;
}

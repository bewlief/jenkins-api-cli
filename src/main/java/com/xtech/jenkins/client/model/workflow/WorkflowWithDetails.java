/*
 * Copyright (c) 2013 Cosmin Stejerean, Karl Heinz Marbaise, and contributors.
 *
 * Distributed under the MIT license: http://opensource.org/licenses/MIT
 */

package com.xtech.jenkins.client.model.workflow;

import com.xtech.jenkins.client.model.BaseModel;
import com.xtech.jenkins.client.JenkinsClient;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author
 */
@Data
public class WorkflowWithDetails extends BaseModel {
    String id;
    String name;
    String status;
    Timestamp startTimeMillis;
    Timestamp endTimeMillis;
    long durationMillis;
    long queueDurationMillis;
    long pauseDurationMillis;
    boolean building;
    List<Stage> stages;
    private JenkinsClient client;


    /**
     * values of status:
     * NOT_EXECUTED,
     * ABORTED,
     * SUCCESS,
     * IN_PROGRESS,
     * PAUSED_PENDING_INPUT,
     * FAILED,
     * UNSTABLE
     *
     * @return
     */
}

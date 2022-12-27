package com.xtech.jenkins.client.model.queue;

import com.xtech.jenkins.client.model.core.Action;
import lombok.Data;

import java.util.List;

/**
 *
 */
@Data
public class QueueItemDetail extends QueueItem {
    private QueueTask task;
    private List<Action> actions;
}

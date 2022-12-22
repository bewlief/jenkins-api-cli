package com.xtech.jenkins.client.model.queue;

import com.xtech.jenkins.client.model.BaseModel;

import java.util.List;

/**
 * @author suren
 */
public class Queue extends BaseModel {
    private List<QueueItem> items;

    public List<QueueItem> getItems() {
        return items;
    }

    public void setItems(List<QueueItem> items) {
        this.items = items;
    }
}

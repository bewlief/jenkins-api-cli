package com.xtech.jenkins.client.helper;

import com.xtech.jenkins.client.model.queue.Queue;
import com.xtech.jenkins.client.model.queue.QueueItemDetail;

import java.io.IOException;

/**
 * @author xtech
 */
public class Queues extends BaseManager {
    @Override
    protected String[] getDependencyArray() {
        return null;
    }

    /**
     * Get the queue items.
     *
     * @return
     * @throws IOException
     */
    public Queue getItems() throws IOException {
        return getClient().get("/queue/api/json", Queue.class);
    }

    public QueueItemDetail getItem(int id) throws IOException {
        return getClient().get("/queue/item/" + id + "/api/json", QueueItemDetail.class);
    }

    public void cancelItem(int id) throws IOException {
        getClient().post("/queue/cancelItem?id=" + id, this.isCrumb());
    }
}

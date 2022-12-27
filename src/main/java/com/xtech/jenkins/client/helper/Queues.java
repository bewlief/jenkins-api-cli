package com.xtech.jenkins.client.helper;

import com.xtech.jenkins.client.model.queue.Queue;
import com.xtech.jenkins.client.model.queue.QueueItemDetail;
import com.xtech.jenkins.client.util.Constants;

import java.io.IOException;

/**
 * Queue management
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
        return getClient().get(Constants.API_GET_QUEUE_ITEMS, Queue.class);
    }

    public QueueItemDetail getItem(int id) throws IOException {
        return getClient().get(String.format(Constants.API_GET_QUEUE_ITEM, id), QueueItemDetail.class);
    }

    public void cancelItem(int id) throws IOException {
        getClient().post(String.format(Constants.API_CANCEL_QUEUE_ITEM, id), this.isCrumb());
    }
}

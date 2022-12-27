/**
 *
 */
package com.xtech.jenkins.client.model.workflow;

import com.xtech.jenkins.client.JenkinsClient;
import com.xtech.jenkins.client.model.BaseModel;
import lombok.Data;

/**
 * @author johuang
 */
@Data
public class StageFlowNodesLog extends BaseModel {
    String nodeId;
    String nodeStatus;
    long length;
    boolean hasMore;
    String text;
    String consoleUrl;
    private JenkinsClient client;
}

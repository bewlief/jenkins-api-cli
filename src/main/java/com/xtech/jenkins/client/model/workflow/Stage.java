/**
 *
 */
package com.xtech.jenkins.client.model.workflow;

import com.xtech.jenkins.client.model.BaseModel;
import com.xtech.jenkins.client.JenkinsClient;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author johuang
 */
@Data
public class Stage extends BaseModel {
    int id;
    String name;
    String execNode;
    String status;
    Timestamp startTimeMillis;
    long durationMillis;
    long pauseDurationMillis;
    List<StageFlowNodes> stageFlowNodes;
    private JenkinsClient client;

    public void setClient(JenkinsClient client) {
        this.client = client;
    }

    public JenkinsClient getClient() {
        return client;
    }
}

package com.xtech.jenkins.client.helper;

import com.xtech.jenkins.client.JenkinsClient;
import com.xtech.jenkins.client.model.job.BuildDetail;
import com.xtech.jenkins.client.model.workflow.Stage;
import com.xtech.jenkins.client.model.workflow.StageFlowNodes;
import com.xtech.jenkins.client.model.workflow.StageFlowNodesLog;
import com.xtech.jenkins.client.model.workflow.WorkflowWithDetails;
import com.xtech.jenkins.client.util.Constants;
import com.xtech.jenkins.client.util.EncodingUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Pipeline module api.
 */
public class Workflows extends BaseManager {

    /**
     * wfapi describe
     *
     * @param jobName
     * @param buildNo
     */
    public WorkflowWithDetails getWfDescribe(String jobName, int buildNo) throws IOException {
        String path = "/";
        try {
            JenkinsClient client = getClient();

            //WorkflowWithDetails workflowWithDetails = getClient().get(path + "job/" + EncodingUtils.encode(jobName) + "/" + buildNo + "/wfapi/describe", WorkflowWithDetails.class);
            WorkflowWithDetails workflowWithDetails = getClient().get(path + String.format(Constants.API_GET_WF_DESCRIBE,EncodingUtils.encode(jobName),buildNo), WorkflowWithDetails.class);
            workflowWithDetails.setClient(client);
            setBuildingInfo(workflowWithDetails, jobName);

            return workflowWithDetails;
        } catch (HttpResponseException e) {
//            LOGGER.debug("getWfDescribe(jobName={}) status={}", jobName, e.getStatusCode());
            if (e.getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                return null;
            }
            throw e;
        }
    }

    public Stage getWfNodeDescribe(String jobName, int buildNo, int stageId) throws IOException {
        String path = "/";
        try {
            JenkinsClient client = getClient();

            String encodedJobName = EncodingUtils.encode(jobName);
            String urlStage = String.format(Constants.API_GET_WF_NODE_DESCRIBE, encodedJobName, buildNo
                    , stageId);
            //Stage stage = client.get(path + "job/" + EncodingUtils.encode(jobName) + "/" + buildNo + "/execution/node/" + stageId + "/wfapi/describe", Stage.class);
            Stage stage = client.get(path + urlStage, Stage.class);
            if (null != stage && CollectionUtils.isNotEmpty(stage.getStageFlowNodes())) {
                stage.setClient(client);
                for (StageFlowNodes stageFlowNode : stage.getStageFlowNodes()) {
                    int nodeId = stageFlowNode.getId();
                    String urlNodeLog = String.format(Constants.API_GET_WF_NODE_LOG, encodedJobName, buildNo, nodeId);
                    StageFlowNodesLog log = client.get(path + urlNodeLog, StageFlowNodesLog.class);
                    log.setClient(client);
                    stageFlowNode.setLog(log);
                }
            }

            return stage;
        } catch (HttpResponseException e) {
//            LOGGER.debug("getWfDescribe(jobName={}) status={}", jobName, e.getStatusCode());
            if (e.getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                return null;
            }
            throw e;
        }
    }

    /**
     * Fetch last running status of Pipeline
     *
     * @param jobName
     * @return
     * @throws IOException
     */
    public WorkflowWithDetails last(String jobName) throws IOException {
        //String url = "/job/" + EncodingUtils.encode(jobName) + "/lastBuild/wfapi/describe";
        String url = String.format(Constants.API_GET_WF_LAST_BUILD, EncodingUtils.encode(jobName));
        WorkflowWithDetails details = getClient().get(url, WorkflowWithDetails.class);
        setBuildingInfo(details, jobName);

        return details;
    }

    public void restart(String jobName, int buildNum, String stage) throws IOException {
        //String url = "/job/" + EncodingUtils.encode(jobName) + "/" + buildNum + "/restart/restart/";
        String url = String.format(Constants.API_WF_RESTART, EncodingUtils.encode(jobName), buildNum);

        Map<String, Object> data = new HashMap<>();
        data.put("stageName", stage);

        //curl 'http://localhost:8080/job/pipeline/8/restart/restart' -H 'Connection: keep-alive' -H 'Pragma: no-cache' -H 'Cache-Control: no-cache' -H 'Origin: http://localhost:8080' -H 'Upgrade-Insecure-Requests: 1' -H 'Content-Type: application/x-www-form-urlencoded' -H 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8' -H 'Referer: http://localhost:8080/job/pipeline/8/restart/' -H 'Accept-Encoding: gzip, deflate, br' -H 'Accept-Language: zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7' -H 'Cookie: lang=zh-CN; JSESSIONID.503dec74=node0ebhr3ydc10a44mv7kzvfqk8t0.node0; JSESSIONID.26fd2d46=node01vrh1uwkj471x19i4g7x3u52zs7.node0; JSESSIONID.38ec8cc2=node0nmj38aq6ofbg7c66w35jl9781.node0; JSESSIONID.5e1a90bd=node0gpra5jlmwpzqqhyuunybuap80.node0; JSESSIONID=7436EF5B468E2F6FC952AC92D46423AC; screenResolution=1280x800; hudson_auto_refresh=false'
        //
        //
        // --data 'stageName=stage1&Jenkins-Crumb=6c401fbc321622b1f79d4ef8fbd6fdbf&json=%7B%22stageName%22%3A+%22stage1%22%2C+%22Jenkins-Crumb%22%3A+%226c401fbc321622b1f79d4ef8fbd6fdbf%22%7D&Submit=Run' --compressed
        getClient().postFormJson(url, data, this.isCrumb());
    }

    private void setBuildingInfo(WorkflowWithDetails details, String jobName) throws IOException {
        String buildId = details.getId();
        Jobs jobs = new Jobs();
        jobs.setClient(getClient());

        BuildDetail buildDetail = jobs.getBuildDetails(jobName, Integer.parseInt(buildId));
        boolean building = buildDetail.isBuilding();
        details.setBuilding(building);
    }

    @Override
    protected String[] getDependencyArray() {
        return new String[]{"pipeline-rest-api"};
    }
}

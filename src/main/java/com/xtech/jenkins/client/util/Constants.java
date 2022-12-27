package com.xtech.jenkins.client.util;

public class Constants {

    public static final String X_JENKINS = "X-Jenkins";

    /**
     * endpoint url format string
     */

    // folder
    public static final String API_UPDATE_FOLDER = "%s/getXml.xml";
    public static final String API_DELETE_FOLDER_JOB = "%s/doDelete";
    public static final String API_RENAME_FOLDER_JOB = "%s/doRename?newName=%s";
    public static final String API_GET_FOLDER_XML = "/job/%s/config.xml";


    // job
    public static final String API_CREATE_JOB="%s/createItem?name=%s";
    public static final String API_COPY_JOB = "/createItem?mode=copy&from=%s&name=%s";
    public static final String API_DISABLE_JOB = "/job/%s/disable";
    public static final String API_ENABLE_JOB = "/job/%s/enable";
    public static final String API_BUILD_JOB="/job/%s/build";
    public static final String API_BUILD_FOLDER_JOB = "/job/%s/job/%s/job/%s/build";
    public static final String API_BUILD_WITH_PARAMS="/job/%s/buildWithParameters/?1=1";
    public static final String API_GET_JOB_DETAILS = "/job/%s";
    public static final String API_GET_JOB_XML = "%s/config.xml";
    public static final String API_GET_LOG_TEXT="/job/%s/%s/consoleText";
    public static final String API_GET_LOG_HTML="/job/%s/%s/logText/progressiveHtml";

    public static final String API_STOP_JOB="/job/%s/%s/stop";

    public static final String API_GET_BUILD_DETAILS = "/job/%s/%s";

    // build


    // workflow
    public static final String API_GET_WF_DESCRIBE = "job/%s/%s/wfapi/describe";
    public static final String API_GET_WF_NODE_DESCRIBE = "job/%s/%s/execution/node/%s/wfapi/describe";
    public static final String API_GET_WF_NODE_LOG = "job/%s/%s/execution/node/%s/wfapi/log";
    public static final String API_GET_WF_LAST_BUILD = "/job/%s/lastBuild/wfapi/describe";
    public static final String API_WF_RESTART = "/job/%s/%s/restart/restart/";

    // view
    public static final String API_CREATE_VIEW = "%s/createView?name=%s";
    public static final String API_UPDATE_VIEW = "%s/view/%s/getXml.xml";
    public static final String API_GET_VIEW_INFO = "/view/%s";
    public static final String API_GET_VIEW_XML = "/view/%s/getXml.xml";

    // queue
    public static final String API_GET_QUEUE_ITEMS = "/queue/api/json";
    public static final String API_GET_QUEUE_ITEM = "/queue/item/%s/api/json";
    public static final String API_CANCEL_QUEUE_ITEM = "/queue/cancelItem?id=%s";

    // plugin
    public static final String API_GET_PLUGIN_MANAGER = "pluginManager/?depth=2";

    // label
    public static final String API_GET_LABELS = "/computer/agent/api/json?depth-1";

    // credential
    public static final String API_CREATE_CREDENTIAL="%s/createCredentials?";
    public static final String API_LIST_CREDENTIALS="%s?depth=2";
    public static final String API_UPDATE_CREDENTIAL="%s/credential/%s/updateSubmit";
    public static final String API_DELETE_CREDENTIAL="%s/credential/%s/doDelete?";

    // computer
    public static final String API_GET_COMPUTERS="/computer/";
    public static final String API_GET_COMPUTER_SET="/computer/?depth=2";

    // blueocean
    public static final String blueOceanBaseUrl="/blue/rest/organizations/jenkins/pipelines";
    public static final String API_GET_BLUE_STEPS=blueOceanBaseUrl+"/%s/pipelines/%s/runs/%s/steps";
}

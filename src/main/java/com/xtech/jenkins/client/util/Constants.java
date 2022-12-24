package com.xtech.jenkins.client.util;

public class Constants {

    public static final String X_JENKINS = "X-Jenkins";

    /**
     * endpoint url format string
     */

    // folder
    public static final String API_CREATE_FOLDER = "%s/createItem?name=%s";
    public static final String API_UPDATE_FOLDER = "%s/getXml.xml";
    public static final String API_DELETE_FOLDER = "%s/doDelete";
    public static final String API_RENAME_FOLDER = "%s/doRename?newName=%s";
    public static final String API_GET_FOLDER_XML = "%s/config.xml";


    // job
    public static final String API_COPY_JOB = "/createItem?mode=copy&from=%s&name=%s";
    public static final String API_DISABLE_JOB = "/job/%s/disable";
    public static final String API_ENABLE_JOB = "/job/%s/enable";
    public static final String API_BUILD_JOB = "/job/%s/job/%s/job/%s/build";
    public static final String API_GET_DETAILS = "/job/%s";
    public static final String API_GET_JOB_XML="%s/config.xml";


    // build
    public static final String API_BUILD_WITH_PARAMS = "/job/%s/buildWithParameters/?1=1";
    public static final String API_GET_LOG_TEXT_BUILD = "/job/%s/%s/consoleText";
    public static final String API_STOP_BUILD = "/job/%s/%s/stop";
    public static final String API_GET_BUILD_DETAILS = "/job/%s/%s";

}

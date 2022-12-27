package com.xtech.jenkins.client.helper;

import com.xtech.jenkins.client.model.folder.FolderJob;
import com.xtech.jenkins.client.model.view.View;
import com.xtech.jenkins.client.util.Constants;
import com.xtech.jenkins.client.util.EncodingUtils;
import com.xtech.jenkins.client.util.UrlUtils;

import java.io.IOException;

/**
 * view management
 */
public class Views extends BaseManager {
    /**
     * @param folderJob
     * @param viewName
     * @param viewXml
     * @param crumbFlag
     * @throws IOException
     */
    public void create(FolderJob folderJob, String viewName, String viewXml, Boolean crumbFlag) throws IOException {
        //String path = UrlUtils.toBaseUrl(folderJob) + "createView?name=" + EncodingUtils.encodeParam(viewName);
        String path = String.format(Constants.API_CREATE_VIEW, UrlUtils.toBaseUrl(folderJob), EncodingUtils.encodeParam(viewName));

        getClient().postXml(path, viewXml, crumbFlag);
    }

    /**
     * @param viewName
     * @param viewXml
     * @param crumbFlag
     * @throws IOException
     * @see #create(FolderJob, String, String, Boolean)
     */
    public void create(String viewName, String viewXml, Boolean crumbFlag) throws IOException {
        create(null, viewName, viewXml, crumbFlag);
    }

    /**
     * @param viewName
     * @param viewXml
     * @throws IOException
     * @see #create(String, String, Boolean)
     */
    public void create(String viewName, String viewXml) throws IOException {
        create(viewName, viewXml, isCrumb());
    }

    /**
     * 更新视图
     *
     * @param folder
     * @param viewName
     * @param viewXml
     * @param crumbFlag
     * @throws IOException
     */
    public void update(FolderJob folder, String viewName, String viewXml, boolean crumbFlag) throws IOException {
        //String path = UrlUtils.toBaseUrl(folder) + "view/" + EncodingUtils.encode(viewName) + "/getXml.xml";
        String path = String.format(Constants.API_UPDATE_VIEW, UrlUtils.toBaseUrl(folder), EncodingUtils.encode(viewName));
        getClient().postXml(path, viewXml, crumbFlag);
    }

    /**
     * @param viewName
     * @param viewXml
     * @param crumbFlag
     * @throws IOException
     * @see #update(FolderJob, String, String, boolean)
     */
    public void update(String viewName, String viewXml, boolean crumbFlag) throws IOException {
        update(null, viewName, viewXml, crumbFlag);
    }

    /**
     * @param viewName
     * @param viewXml
     * @throws IOException
     * @see #update(String, String, boolean)
     */
    public void update(String viewName, String viewXml) throws IOException {
        update(viewName, viewXml, isCrumb());
    }

    /**
     * 获取视图信息
     *
     * @param viewName
     * @return
     * @throws IOException
     */
    public View info(String viewName) throws IOException {
        String url = String.format(Constants.API_GET_VIEW_INFO, EncodingUtils.encode(viewName));
        return getClient().get(url, View.class);
    }

    public String getXml(String viewName) throws IOException {
        String url = String.format(Constants.API_GET_VIEW_XML, EncodingUtils.encode(viewName));
        return getClient().get(url);
    }

    @Override
    protected String[] getDependencyArray() {
        return null;
    }
}

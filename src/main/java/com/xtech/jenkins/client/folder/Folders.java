package com.xtech.jenkins.client.folder;

import com.google.common.collect.ImmutableMap;
import com.xtech.jenkins.client.BaseManager;
import com.xtech.jenkins.client.util.EncodingUtils;
import com.xtech.jenkins.client.util.UrlUtils;

import java.io.IOException;

/**
 * 管理Jenkins的文件夹（folder）
 *
 * @author suren
 */
public class Folders extends BaseManager {
    private String folderCls = "com.cloudbees.hudson.plugins.folder.Folder";

    /**
     * 创建文件夹
     *
     * @param folderJob
     * @param folderName
     * @param crumbFlag
     * @throws IOException
     */
    public void create(FolderJob folderJob, String folderName, Boolean crumbFlag) throws IOException {
        ImmutableMap<String, String> params = ImmutableMap.of("mode", folderCls,
                "name", EncodingUtils.encodeParam(folderName),
                "from", "", "Submit", "OK");
        getClient().postForm(UrlUtils.toBaseUrl(folderJob) + "createItem?", params, crumbFlag);
    }

    /**
     * @param folderName
     * @param crumbFlag
     * @throws IOException
     * @see #create(FolderJob, String, Boolean)
     */
    public void create(String folderName, Boolean crumbFlag) throws IOException {
        create(null, folderName, crumbFlag);
    }

    /**
     * @param folderName
     * @throws IOException
     * @see #create(String, Boolean)
     */
    public void create(String folderName) throws IOException {
        create(folderName, isCrumb());
    }

    public String getXml(String folderName) throws IOException {
        return getClient().get("/job/" + EncodingUtils.encode(folderName) + "/securitydemo.config.xml");
    }

    public boolean exists(String folderName) throws IOException {
        try {
            return getXml(folderName) != null;
        } catch (Exception e) {
            return false;
        }
    }

    public String getFolderCls() {
        return folderCls;
    }

    public void setFolderCls(String folderCls) {
        this.folderCls = folderCls;
    }

    @Override
    protected String[] getDependencyArray() {
        return new String[0];
    }
}

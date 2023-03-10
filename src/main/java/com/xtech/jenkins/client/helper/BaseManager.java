package com.xtech.jenkins.client.helper;

import com.xtech.jenkins.client.JenkinsClient;

import java.io.IOException;

/**
 * BaseManager, super class of all helpers
 *
 */
public abstract class BaseManager {
    private JenkinsClient client;
    /**
     * 如果打开了CSRF保护应该设置为true
     */
    private boolean crumb = true;

    // todo change from protected to public, is it OK?
    public JenkinsClient getClient() {
        return client;
    }

    public void setClient(JenkinsClient client) {
        this.client = client;
    }

    public boolean isCrumb() {
        return crumb;
    }

    public void setCrumb(boolean crumb) {
        this.crumb = crumb;
    }

    protected abstract String[] getDependencyArray();

    /**
     * 插件依赖是否满足的检查
     *
     * @return 依赖满足返回true
     */
    public boolean dependencyCheck() throws IOException {
        Plugins plugins = new Plugins();
        plugins.setClient(getClient());

        return plugins.pluginsExists(getDependencyArray());
    }
}

package com.xtech.jenkins.client.helper;

import com.xtech.jenkins.client.model.plugin.Plugin;
import com.xtech.jenkins.client.model.plugin.PluginManager;
import com.xtech.jenkins.client.util.Constants;

import java.io.IOException;
import java.util.List;

/**
 * Plugin management
 */
public class Plugins extends BaseManager {
    /**
     * This will give you back the {@link PluginManager}.
     *
     * @return {@link PluginManager}
     * @throws IOException in case of a failure.
     */
    public PluginManager getPluginManager() throws IOException {
        return getClient().get(Constants.API_GET_PLUGIN_MANAGER, PluginManager.class);
    }

    public boolean pluginsExists(String[] pluginArray) throws IOException {
        if (pluginArray == null) {
            return true;
        }

        PluginManager pluginMgr = getPluginManager();
        List<Plugin> pluginList = pluginMgr.getPlugins();
        if (pluginList == null) {
            return false;
        }

        for (String pluginName : pluginArray) {
            boolean exists = false;
            for (Plugin plugin : pluginList) {
                if (plugin.getShortName().equals(pluginName)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                return false;
            }
        }

        return true;
    }

    public void upload() {
//        http://localhost:8080/jenkins/pluginManager/uploadPlugin
    }

    @Override
    protected String[] getDependencyArray() {
        return null;
    }
}

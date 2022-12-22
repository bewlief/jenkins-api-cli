package com.xtech.jenkins.client;

import com.xtech.jenkins.client.helper.BlueOcean;
import com.xtech.jenkins.client.helper.Computers;
import com.xtech.jenkins.client.helper.Labels;
import com.xtech.jenkins.client.model.core.OverLoad;
import com.xtech.jenkins.client.helper.Credentials;
import com.xtech.jenkins.client.helper.Folders;
import com.xtech.jenkins.client.helper.Jobs;
import com.xtech.jenkins.client.helper.Plugins;
import com.xtech.jenkins.client.helper.Queues;
import com.xtech.jenkins.client.helper.Views;
import com.xtech.jenkins.client.helper.Workflows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;

/**
 * Jenkins Client main entry
 *
 * @author suren
 */
public class Jenkins {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final JenkinsHttpClient client;

    public Jenkins(URI uri) {
        this(new JenkinsHttpClient(uri));
    }

    public Jenkins(URI uri, String userName, String password) {
        this(new JenkinsHttpClient(uri, userName, password));
    }

    public Jenkins(JenkinsHttpClient client) {
        this.client = client;
    }

    public Folders getFolders() {
        Folders folders = new Folders();
        folders.setClient(this.client);
        return folders;
    }

    public Jobs getJobs() {
        Jobs jobs = new Jobs();
        jobs.setClient(this.client);
        return jobs;
    }

    public Views getViews() {
        Views views = new Views();
        views.setClient(this.client);
        return views;
    }

    public Workflows getWorkflows() {
        Workflows workflows = new Workflows();
        workflows.setClient(this.client);
        return workflows;
    }

    public Computers getComputers() {
        Computers computers = new Computers();
        computers.setClient(this.client);
        return computers;
    }

    public Labels getLabels() {
        Labels labels = new Labels();
        labels.setClient(this.client);
        return labels;
    }

    public Queues getQueues() {
        Queues queues = new Queues();
        queues.setClient(this.client);
        return queues;
    }

    public BlueOcean getBlueOcean() {
        BlueOcean blueOcean = new BlueOcean();
        blueOcean.setClient(this.client);

        return blueOcean;
    }

    /**
     * Get the credential manager of jenkins
     *
     * @return
     */
    public Credentials getCredentials() {
        String version = getVersion();
        Credentials credentials;
        if ("2.7.3".equals(version)) {
            credentials = new Credentials(Credentials.V1URL);
        } else {
            credentials = new Credentials();
        }

        credentials.setClient(this.client);

        return credentials;
    }

    public Plugins getPlugins() {
        Plugins plugins = new Plugins();
        plugins.setClient(this.client);
        return plugins;
    }

    public boolean isRunning() {
        return false;
    }

    public OverLoad getOverLoad() {
//        http://localhost:8080/jenkins/overallLoad/api/json
        return null;
    }

    public void shutdown() {
    }

    public String getVersion() {
        try {
            this.client.postFormJson("/", null, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.client.getJenkinsVersion();
    }
}

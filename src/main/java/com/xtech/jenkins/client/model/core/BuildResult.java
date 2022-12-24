package com.xtech.jenkins.client.model.core;

/**
 * @author xtech
 */
public enum BuildResult {
    SUCCESS("SUCCESS"),
    FAILURE("FAILURE"),
    ABORTED("ABORTED");

    private String name;

    BuildResult(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

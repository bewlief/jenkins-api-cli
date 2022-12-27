package com.xtech.jenkins.client.model.job;

import com.xtech.jenkins.client.model.core.Health;
import lombok.Data;

import java.util.List;

/**
 * detailed info of a job
 */
@Data
public class JobDetails extends Job {
    private String description;
    private String displayName;
    private String displayNameOrNull;
    private String fullDisplayName;
    private String fullName;
    private boolean buildable;
    private List<BuildInfo> builds;
    private String color;
    private BuildInfo firstBuild;
    private List<Health> healthReport;
    private boolean inQueue;
    private boolean keepDependencies;
    private BuildInfo lastBuild;
    private BuildInfo lastCompletedBuild;
    private BuildInfo lastFailedBuild;
    private BuildInfo lastStableBuild;
    private BuildInfo lastSuccessfulBuild;
    private BuildInfo lastUnstableBuild;
    private BuildInfo lastUnsuccessfulBuild;
    private int nextBuildNumber;
    private boolean concurrentBuild;
}

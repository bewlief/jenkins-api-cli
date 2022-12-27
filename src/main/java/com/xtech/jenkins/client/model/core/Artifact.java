package com.xtech.jenkins.client.model.core;

import com.xtech.jenkins.client.model.BaseModel;
import lombok.Data;

/**
 * Artifact
 *
 */
@Data
public class Artifact extends BaseModel {
    private String displayPath;
    private String fileName;
    private String relativePath;
}

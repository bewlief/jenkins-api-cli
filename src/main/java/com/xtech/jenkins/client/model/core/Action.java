package com.xtech.jenkins.client.model.core;

import com.xtech.jenkins.client.model.BaseModel;
import lombok.Data;

import java.util.List;

/**
 *
 */
@Data
public class Action extends BaseModel {
    private List<Cause> causes;
}

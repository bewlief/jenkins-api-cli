/**
 *
 */
package com.xtech.jenkins.client.model.workflow;

import lombok.Data;

import java.util.List;

/**
 * @author johuang
 */
@Data
public class StageFlowNodes extends Stage {
    List<Integer> parentNodes;
    StageFlowNodesLog log;
}

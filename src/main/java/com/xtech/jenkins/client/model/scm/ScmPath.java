package com.xtech.jenkins.client.model.scm;

import com.xtech.jenkins.client.model.BaseModel;
import lombok.Data;

/**
 * @author xtech
 * = BuildChangeSetPath
 */
@Data
public class ScmPath extends BaseModel {
    /**
     * SCM operation: add/edit/delete
     * @see <a href="http://javadoc.jenkins.io/hudson/scm/EditType.html">EditType</a>
     */
    private String editType;
    private String file;
}

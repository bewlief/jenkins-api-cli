package com.xtech.jenkins.client.scm;

import com.xtech.jenkins.client.BaseModel;

import java.util.List;

/**
 * @author suren
 */
public class ScmChangeInfo extends BaseModel
{
    private List<ScmChange> items;

    public List<ScmChange> getItems()
    {
        return items;
    }

    public void setItems(List<ScmChange> items)
    {
        this.items = items;
    }
}

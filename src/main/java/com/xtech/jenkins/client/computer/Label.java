package com.xtech.jenkins.client.computer;

import com.xtech.jenkins.client.BaseModel;

public class Label extends BaseModel
{
    private String name;
    private String description;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}

package com.smartmug.device.management.dto;

import javax.validation.constraints.NotEmpty;

public class DeviceGroupProperties {
    @NotEmpty
    private String groupName;
    @NotEmpty
    private String topicName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}

package com.example.tprom.properties;

public class Group {
    private int groupId;
    private String groupName;
    private String describeGroup;

    Group(){

    }

    Group(int groupId, String groupName, String describeGroup){
        this.groupId=groupId;
        this.groupName=groupName;
        this.describeGroup=describeGroup;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setDes(String describeGroup) {
        this.describeGroup = describeGroup;
    }

    public String getDescribe() {
        return describeGroup;
    }
}
package com.example.tprom.properties;

import java.util.List;

public class Group {
    private int groupId;
    private String groupName;
    private List<String> members;
    private String groupDescription;

    Group(){

    }

    Group(int groupId, String groupName, List<String> members, String groupDescription){
        this.groupId=groupId;
        this.groupName=groupName;
        this.members = members;
        this.groupDescription=groupDescription;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public List<String> getMembers() {
        return members;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

}
package com.example.tprom.group;

import java.util.ArrayList;
import java.util.List;

public class GroupItem {
    public String groupName,groupDescription,GroupOwner;
    private List<String> members;

    private int numberOfDeadlines;

//    public GroupItem(String groupName, String groupDescription, String groupOwner,  int numberOfDeadlines) {
//        GroupName = groupName;
//        GroupDescription = groupDescription;
//        GroupOwner = groupOwner;
//        this.numberOfDeadlines = numberOfDeadlines;
//    }

    public GroupItem(){};

    public GroupItem(String groupName, String groupDescription) {
        this.groupName = groupName;
        this.groupDescription = groupDescription;
    }

    public GroupItem(String groupName, String groupDescription, List<String> members) {
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.members=members;
    }

    public int getNumberOfDeadlines() {
        return numberOfDeadlines;
    }

    public String GroupName() {
        return groupName;
    }

    public String GroupOwner() {
        return GroupOwner;
    }

    public List<String> getMembers() {
        return members;
    }

    public String GroupDescription() {
        return groupDescription;
    }
}

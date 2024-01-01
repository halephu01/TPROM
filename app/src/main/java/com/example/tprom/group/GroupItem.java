package com.example.tprom.group;

public class GroupItem {
    String GroupName,GroupDescription,GroupOwner;
    private int numberOfDeadlines;

    public GroupItem(String groupName, String groupDescription, String groupOwner, int numberOfDeadlines) {
        GroupName = groupName;
        GroupDescription = groupDescription;
        GroupOwner = groupOwner;
        this.numberOfDeadlines = numberOfDeadlines;
    }

    public int getNumberOfDeadlines() {
        return numberOfDeadlines;
    }

    public String GroupName() {
        return null;
    }

    public String GroupOwner() {
        return null;
    }

    public String GroupDescription() {
        return null;
    }
}

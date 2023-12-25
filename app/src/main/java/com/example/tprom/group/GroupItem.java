package com.example.tprom.group;

public class GroupItem {
    String GroupName,Description,GroupOwner;
    private int numberOfDeadlines;

    public GroupItem(String groupName, String description, String groupOwner, int numberOfDeadlines) {
        GroupName = groupName;
        Description = description;
        GroupOwner = groupOwner;
        this.numberOfDeadlines = numberOfDeadlines;
    }

    public int getNumberOfDeadlines() {
        return numberOfDeadlines;
    }
}

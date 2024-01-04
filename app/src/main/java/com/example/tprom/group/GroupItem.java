package com.example.tprom.group;

import com.example.tprom.properties.Member;

import java.util.ArrayList;
import java.util.List;

public class GroupItem {
    public String groupId,groupName,groupDescription,GroupOwner;
    private ArrayList<Member> members;

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

    public GroupItem(String groupName, String groupDescription, String groupOwner) {
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.GroupOwner = groupOwner;
    }

    public GroupItem(String groupIdr,String groupName, String groupDescription, String groupOwner) {
        this.groupId = groupIdr;
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.GroupOwner=groupOwner;
    }

    public GroupItem(String groupId,String groupName, String groupDescription, ArrayList<Member> members) {
        this.groupId= groupId;
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.members=members;
    }

    public String GroupId() {
        return groupId;
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

    public ArrayList<Member> getMembers() {
        return members;
    }

    public String GroupDescription() {
        return groupDescription;
    }
}

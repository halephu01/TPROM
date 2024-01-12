package com.example.tprom.notification;

import com.example.tprom.properties.Member;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NotificationItem {
    //static final SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm");
    String taskName,groupName,taskDescription;
    String taskDueTime;
    int ProgressPercent;
    ArrayList<Member> assignedUsers;
    public NotificationItem(){}
    public NotificationItem(String taskName, String taskDescription){
        this.taskName=taskName;
        this.taskDescription=taskDescription;
    }
    public NotificationItem(String taskName, String groupName, String taskDescription, String taskDueTime) {
        this.taskName = taskName;
        this.groupName = groupName;
        this.taskDescription = taskDescription;
        this.taskDueTime = taskDueTime;
    }
    public NotificationItem(String taskName, String groupName, String taskDescription, String taskDueTime, int progressPercent) {
        this.taskName = taskName;
        this.groupName = groupName;
        this.taskDescription = taskDescription;
        this.taskDueTime = taskDueTime;
        this.ProgressPercent = progressPercent;
    }
    public NotificationItem(String taskName, String groupName, String taskDescription, String taskDueTime, int progressPercent, ArrayList<Member> assignedUsers) {
        this.taskName = taskName;
        this.groupName = groupName;
        this.taskDescription = taskDescription;
        this.taskDueTime = taskDueTime;
        this.ProgressPercent = progressPercent;
        this.assignedUsers=assignedUsers;
    }
    public String getTaskDueTime(){
        return taskDueTime;
    }
    public String getTaskName(){ return taskName;}
    public String getGroupName(){ return groupName;}
    public String getTaskDescription(){ return taskDescription;}
    public int getProgressPercent(){ return ProgressPercent;}
    public ArrayList<Member> getAssignedUsers(){return assignedUsers;}
}
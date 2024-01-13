package com.example.tprom.properties;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Task {
    private String taskId;
    private String taskName, groupName;
    private String taskDescription;
    private double ProgressPercent;
    private String taskDueTime;
    private String taskStartTime;
    //private static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private int status; //-1: khong hoan thanh, 0: dang lam, 1: hoan thanh
    private List<String> files;
    private int numberOfFiles;
    private ArrayList<Member> assignedUsers;


    public Task() {
    }

    public Task(String taskId,String taskName, String taskDescription, double ProgressPercent,String taskStartTime, String taskDueTime, int numberOfFiles, ArrayList<Member> assignedUsers) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.ProgressPercent = ProgressPercent;
        this.taskStartTime = taskStartTime;
        this.taskDueTime = taskDueTime;
        this.numberOfFiles = numberOfFiles;
        this.assignedUsers = assignedUsers;
    }

    public Task (String taskId, String taskName, String taskDescription,List<String> files, double ProgressPercent,String taskStartTime, String taskDueTime, ArrayList<Member> assignedUsers, String groupName) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.ProgressPercent = ProgressPercent;
        this.taskStartTime = taskStartTime;
        this.taskDueTime = taskDueTime;
        this.assignedUsers = assignedUsers;
        this.groupName = groupName;
        this.files = files;
    }

    public Task(String taskId, String taskName, String taskDescription, int status, int numberOfFiles,String taskStartTime, String taskDueTime ) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;
        this.taskStartTime = taskStartTime;
        this.taskDueTime = taskDueTime;
        this.numberOfFiles = numberOfFiles;
    }
    public Task(String taskName, String taskDescription, int numberOfFiles, String taskDueTime) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskDueTime = taskDueTime;
        this.numberOfFiles = numberOfFiles;
    }
    public Task(String taskName, String taskDescription, int numberOfFiles,String taskStartTime, String taskDueTime) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStartTime = taskStartTime;
        this.taskDueTime = taskDueTime;
        this.numberOfFiles = numberOfFiles;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public void setNumberOfFiles(int numberOfFiles) {
        this.numberOfFiles = numberOfFiles;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getNumberOfFiles() {
        return numberOfFiles;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setProgressPercent(double progressPercent) {
        ProgressPercent = progressPercent;
    }

    public double getProgressPercent() {
        return ProgressPercent;
    }

    public void setClassify(double ProgressPercent) {
        this.ProgressPercent = ProgressPercent;
    }

//    public String getTaskDueTime() {
//        return simpleDateFormat.format(taskDueTime);
//    }

    public String getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(String taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public String getTaskDueTime() {
        return taskDueTime;
    }

    public void setTaskDueTime(String taskDueTime) {
        this.taskDueTime = taskDueTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int statusTask) {
        this.status = statusTask;
    }

    public List<String> getFiles() {
        return files;
    }

    public ArrayList<Member> getAssignedUsers() {
        return assignedUsers;
    }

    public void setUsers(ArrayList<Member> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }
}
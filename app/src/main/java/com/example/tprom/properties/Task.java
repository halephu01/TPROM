package com.example.tprom.properties;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Task {
    private int taskId, groupId,userid;
    private String nameTask;
    private String describeTask;
    private double ProgressPercent;
    private Date deadline;
    private static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private int statusTask; //-1: khong hoan thanh, 0: dang lam, 1: hoan thanh
    private int NumberOfAttachment;
    private ArrayList<User> member;

    public Task(int taskId, int groupId, int userid, String nameTask, String describeTask, double ProgressPercent, Date deadline, int statusTask, int numberOfAttachment, ArrayList<User> member) {
        this.taskId = taskId;
        this.groupId = groupId;
        this.userid = userid;
        this.nameTask = nameTask;
        this.describeTask = describeTask;
        this.ProgressPercent = ProgressPercent;
        this.deadline = deadline;
        this.statusTask = statusTask;
        NumberOfAttachment = numberOfAttachment;
        this.member = member;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public String getDescribeTask() {
        return describeTask;
    }

    public void setDescribeTask(String describeTask) {
        this.describeTask = describeTask;
    }

    public double getProgressPercent() {
        return ProgressPercent;
    }

    public void setClassify(double ProgressPercent) {
        this.ProgressPercent = ProgressPercent;
    }

    public String getDeadline() {
        return simpleDateFormat.format(deadline);
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public int getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(int statusTask) {
        this.statusTask = statusTask;
    }

    public int getNumberOfAttachment() {
        return NumberOfAttachment;
    }

    public void setNumberOfAttachment(int numberOfAttachment) {
        NumberOfAttachment = numberOfAttachment;
    }

    public ArrayList<User> getMember() {
        return member;
    }

    public void setMember(ArrayList<User> member) {
        this.member = member;
    }
}

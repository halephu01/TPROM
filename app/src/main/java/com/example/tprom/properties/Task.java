package com.example.tprom.properties;

import java.util.Date;

public class Task {
    private int taskId, groupId,userid;
    private String nameTask;
    private String describeTask;
    private String classify;
    private Date deadline;
    private float rate;

    private Boolean statusTask;

    Task(){

    }

    Task(int taskId, int userid, int groupId, String nameTask, String describeTask, String classify, Date deadline, boolean statusTask, float rate){
        this.taskId=taskId;
        this.userid=userid;
        this.groupId=groupId;
        this.nameTask=nameTask;
        this.describeTask=describeTask;
        this.classify=classify;
        this.deadline=deadline;
        this.statusTask=statusTask;
        this.rate = rate;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getClassify() {
        return classify;
    }

    public Date getDeadline() {
        return deadline;
    }

    public String getNameTask() {
        return nameTask;
    }

    public String getDescribeTask() {
        return describeTask;
    }

    public float getRate() {
        return rate;
    }

    public Boolean getStatusTask() {
        return statusTask;
    }

    public int getUserid() {
        return userid;
    }

    public int getGroupId() {
        return groupId;
    }
}

package com.example.tprom;

import java.util.Date;

public class classTask {
    private int taskId, groupId,userid;
    private String nameTask;
    private String describeTask;
    private String classify;
    private Date deadline;
    private float rate;
    private Boolean statusTask;

    classTask(){

    }

    classTask(int taskId, int userid, int groupId, String nameTask, String describeTask, String classify, Date deadline, boolean statusTask){
        this.taskId=taskId;
        this.userid=userid;
        this.groupId=groupId;
        this.nameTask=nameTask;
        this.describeTask=describeTask;
        this.classify=classify;
        this.deadline=deadline;
        this.statusTask=statusTask;
    }
}

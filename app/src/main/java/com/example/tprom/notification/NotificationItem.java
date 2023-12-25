package com.example.tprom.notification;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationItem {
    static final SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm");
    String Title,GroupName,Description;
    Date date;
    public NotificationItem(String title, String groupName, String description, Date date) {
        Title = title;
        GroupName = groupName;
        Description = description;
        this.date = date;
    }
    public String getDate(){
        return simpleDateFormat.format(date);
    }
}

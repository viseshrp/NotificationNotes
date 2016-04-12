package com.theappnazi.notificationnotes.models;

/**
 * Created by vises_000 on 4/6/2016.
 */
public class Note {
    private int id;
    private String notification_title;
    private String notification_content;
    private String note_date;
    private boolean isPersistent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotification_title() {
        return notification_title;
    }

    public void setNotification_title(String notification_title) {
        this.notification_title = notification_title;
    }

    public String getNotification_content() {
        return notification_content;
    }

    public void setNotification_content(String notification_content) {
        this.notification_content = notification_content;
    }

    public String getNote_date() {
        return note_date;
    }

    public void setNote_date(String note_date) {
        this.note_date = note_date;
    }

    public boolean isPersistent() {
        return isPersistent;
    }

    public void setPersistent(boolean persistent) {
        isPersistent = persistent;
    }
}

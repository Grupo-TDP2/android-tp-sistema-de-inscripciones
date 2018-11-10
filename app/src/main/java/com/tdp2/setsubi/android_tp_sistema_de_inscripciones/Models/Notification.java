package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;



@Entity
public class Notification
{
    public enum Type
    {
        UNSUBSCRIBE_COURSE,
        UNSUBSCRIBE_EXAM
    }

    @PrimaryKey(autoGenerate = true)
    private int uid;
    @ColumnInfo(name="title")
    private String title;
    @ColumnInfo(name="body")
    private String body;
    @ColumnInfo(name="type")
    private Type type;
    @ColumnInfo(name="date")
    private Date receivedDate;
    @ColumnInfo(name="seen")
    private boolean seen;

    public Notification(int uid, String title, String body, Type type, Date receivedDate, boolean seen)
    {
        this.uid = uid;
        this.title = title;
        this.body = body;
        this.type = type;
        this.receivedDate = receivedDate;
        this.seen = seen;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Type getType() {
        return type;
    }

    public boolean isSeen() {
        return seen;
    }

    public int getUid(){
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}

package com.example.mynotepadapp;

import java.io.Serializable;
import java.util.Date;

public class Note implements Comparable<Note>, Serializable {
    private String title;
    private String content;
    private Date lastDate;

    public Note(String t, String c){
        this.title = t;
        this.content = c;
        this.lastDate = new Date();
    }

    String getTitle(){
        return this.title;
    }

    String getContent(){
        return this.content;
    }

    void setTitle(String t){
        this.title = t;
    }


    void setContent(String c){
        this.content = c;
    }

    Date getLastDate(){
        return this.lastDate;
    }

    void setLastDate(long lastTimeMS){
        this.lastDate = new Date(lastTimeMS);
    }

    @Override
    public int compareTo(Note o){
        if(lastDate.before(o.lastDate)){
            return 1;
        }
        else if(lastDate.after(o.lastDate)){
            return -1;
        }
        else{
            return 0;
        }
    }

    @Override
    public String toString() {
        return "\n" + title + " | " + content + " | " + lastDate;
    }

}

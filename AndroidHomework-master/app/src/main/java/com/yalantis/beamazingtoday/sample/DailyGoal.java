package com.yalantis.beamazingtoday.sample;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class DailyGoal extends LitePalSupport {

    private int id;

    @Column(unique = true)
    private String date;

    private String hasDone;
    private String notDone;


    public String getHasDone() {
        return hasDone;
    }

    public void setHasDone(String hasDone) {
        this.hasDone = hasDone;
    }

    public String getNotDone() {
        return notDone;
    }

    public void setNotDone(String notDone) {
        this.notDone = notDone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

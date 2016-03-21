package net.krishlogic.wakemeup.model;

import java.util.Date;

/**
 * Created by kvenkat on 3/16/16.
 */
public class Alarm {

    private int id;

    private Date date;

    private String name;

    private String minStepCount;

    private boolean snoozeable;

    private boolean daily;

    private boolean weekly;

    private int snoozeMin;

    //come up with flags for deciding days,
    //rather than having objects
    private int dayFlag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMinStepCount() {
        return minStepCount;
    }

    public void setMinStepCount(String minStepCount) {
        this.minStepCount = minStepCount;
    }

    public boolean isSnoozeable() {
        return snoozeable;
    }

    public void setSnoozeable(boolean snoozeable) {
        this.snoozeable = snoozeable;
    }

    public boolean isDaily() {
        return daily;
    }

    public void setDaily(boolean daily) {
        this.daily = daily;
    }

    public boolean isWeekly() {
        return weekly;
    }

    public void setWeekly(boolean weekly) {
        this.weekly = weekly;
    }

    public int getSnoozeMin() {
        return snoozeMin;
    }

    public void setSnoozeMin(int snoozeMin) {
        this.snoozeMin = snoozeMin;
    }

    public int getDayFlag() {
        return dayFlag;
    }

    public void setDayFlag(int dayFlag) {
        this.dayFlag = dayFlag;
    }
}
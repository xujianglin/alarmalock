package com.alarmclock.yhkr.alarmalock;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class AlarmAlock {
    private String alarmTime;
    private String alarmContent;
    private String alarmState;
    private String alarmId;

    public AlarmAlock(String alarmTime, String alarmContent, String alarmState) {
        this.alarmTime = alarmTime;
        this.alarmContent = alarmContent;
        this.alarmState = alarmState;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmContent() {
        return alarmContent;
    }

    public void setAlarmContent(String alarmContent) {
        this.alarmContent = alarmContent;
    }

    public String getAlarmState() {
        return alarmState;
    }

    public void setAlarmState(String alarmState) {
        this.alarmState = alarmState;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }
}

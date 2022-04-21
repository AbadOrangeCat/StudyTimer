package com.example.studytimer;

import java.io.Serializable;

public class TimerModel implements Serializable {
    private String showtime;
    private String  realtime;
    private double second;

    private String slotname;



    public TimerModel(String showtime, String  currenttime, double second, String slotname) {
        this.showtime = showtime;
        this.realtime = currenttime;
        this.second = second;
        this.slotname = slotname;

    }

    public String getTaskName() {
        return showtime;
    }

    public String getTaskAddedTime() {
        return realtime;
    }

    public String getTaskSlotName() {
        return slotname;
    }
}

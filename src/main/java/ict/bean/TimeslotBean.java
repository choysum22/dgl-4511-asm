package ict.bean;

import java.io.Serializable;

public class TimeslotBean implements Serializable {

    private String id, timeslot;

    public TimeslotBean() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

}

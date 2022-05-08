package ict.bean;

import java.io.Serializable;

public class TempBookingBean implements Serializable {

    private String id, type, selectedId, timeslot, date;

    public TempBookingBean() {

    }

    public TempBookingBean(String id, String date, String timeslot, String type, String selectedId) {
        this.id = id;
        this.date = date;
        this.timeslot = timeslot;
        this.type = type;
        this.selectedId = selectedId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(String selectedId) {
        this.selectedId = selectedId;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

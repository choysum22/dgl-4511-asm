package ict.bean;

import java.io.Serializable;

public class StaffTypeBean implements Serializable {

    public StaffTypeBean() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String id, type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

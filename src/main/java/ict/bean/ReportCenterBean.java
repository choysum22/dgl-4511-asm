package ict.bean;

import java.io.Serializable;
import java.text.DecimalFormat;

public class ReportCenterBean extends CenterBean implements Serializable {

    private CenterBean center;

    private String count, rate, type;

    public ReportCenterBean() {
    }

    public CenterBean getCenter() {
        return center;
    }

    public void setCenter(CenterBean center) {
        this.center = center;
    }

    public String getId() {
        return center.getId();
    }

    public void setId(String id) {
        this.center.setId(id);
    }

    public String getImg() {
        return center.getImg();
    }

    public void setImg(String img) {
        this.center.setImg(img);
    }

    public String getLocation() {
        return center.getLocation();
    }

    public void setLocation(String location) {
        this.center.setLocation(location);
    }

    public String getDesc() {
        return center.getDesc();
    }

    public void setDesc(String desc) {
        this.center.setDesc(desc);
    }

    public String getTel() {
        return center.getTel();
    }

    public void setTel(String tel) {
        this.center.setTel(tel);
    }

    public String getFee() {
        return center.getFee();
    }

    public void setFee(String fee) {
        this.center.setFee(fee);
    }

    public String getName() {
        return center.getName();
    }

    public void setName(String name) {
        this.center.setName(name);
    }

    public String getStatus() {
        return center.getStatus();
    }

    public void setStatus(String status) {
        this.center.setStatus(status);
    }

    public String getRate() {
        DecimalFormat df = new DecimalFormat("#.0");
        return df.format(Double.parseDouble(this.rate));
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

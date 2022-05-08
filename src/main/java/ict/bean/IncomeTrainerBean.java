package ict.bean;

import java.io.Serializable;

public class IncomeTrainerBean extends TrainerBean implements Serializable {

    private TrainerBean trainer;

    private String count, income, type;

    public IncomeTrainerBean() {
    }

    public TrainerBean getTrainer() {
        return trainer;
    }

    public void setTrainer(TrainerBean trainer) {
        this.trainer = trainer;
    }

    public String getId() {
        return trainer.getId();
    }

    public void setId(String id) {
        this.trainer.setId(id);
    }

    public String getImg() {
        return trainer.getImg();
    }

    public void setImg(String img) {
        this.trainer.setImg(img);
    }

    public String getDesc() {
        return trainer.getDesc();
    }

    public void setDesc(String desc) {
        this.trainer.setDesc(desc);
    }

    public String getTel() {
        return trainer.getTel();
    }

    public void setTel(String tel) {
        this.trainer.setTel(tel);
    }

    public String getFee() {
        return trainer.getFee();
    }

    public void setFee(String fee) {
        this.trainer.setFee(fee);
    }

    public String getCenter() {
        return trainer.getCenter();
    }

    public void setCenter(String center) {
        this.trainer.setCenter(center);
    }

    public String getLname() {
        return trainer.getLname();
    }

    public void setLname(String lname) {
        this.trainer.setLname(lname);
    }

    public String getFname() {
        return trainer.getFname();
    }

    public void setFname(String fname) {
        this.trainer.setFname(fname);
    }

    public String getStatus() {
        return trainer.getStatus();
    }

    public void setStatus(String status) {
        this.trainer.setStatus(status);
    }

    public String getCenterName() {
        return trainer.getCenterName();
    }

    public void setCenterName(String centerName) {
        this.trainer.setCenterName(centerName);
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

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

}

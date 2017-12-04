package com.example.user_pc.project202;

import java.util.Date;

/**
 * Created by User-PC on 11/30/2017.
 */

public class Votes {
    private String pid;
    private String act_ttl;
    private String desc;
    private Date create_date;
    private Date start_date;
    private Date end_date;

    public Votes(){

    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getAct_ttl() {
        return act_ttl;
    }

    public void setAct_ttl(String act_ttl) {
        this.act_ttl = act_ttl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
}

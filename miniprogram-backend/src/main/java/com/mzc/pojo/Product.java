package com.mzc.pojo;

import org.hibernate.annotations.GenericGenerator;
import org.json.JSONObject;

import javax.persistence.*;

@Entity
public class Product {
    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    private long id;

    @Column
    private String RIN;

    @Column
    private SealStatus sealStatus;

    @Column
    private long created_ts;

    public enum SealStatus {
        CREATED,DESTROYED
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRIN() {
        return RIN;
    }

    public void setRIN(String RIN) {
        this.RIN = RIN;
    }

    public SealStatus getSealStatus() {
        return sealStatus;
    }

    public void setSealStatus(SealStatus sealStatus) {
        this.sealStatus = sealStatus;
    }

    public long getCreated_ts() {
        return created_ts;
    }

    public void setCreated_ts(long created_ts) {
        this.created_ts = created_ts;
    }

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("id",id);
        json.put("RIN",RIN);
        json.put("create_time",created_ts);
        json.put("seal_status",sealStatus);

        return json;
    }
}

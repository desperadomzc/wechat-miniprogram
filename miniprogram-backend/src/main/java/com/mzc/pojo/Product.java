package com.mzc.pojo;

import org.hibernate.annotations.GenericGenerator;
import org.json.JSONObject;

import javax.persistence.*;

@Entity
public class Product {
    @Id
    @Column
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    private String id;

    @Column
    private String RIN;

    @Column
    private String sealStatus;

    @Column
    private long created_ts;

    @Column
    private int scan_count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRIN() {
        return RIN;
    }

    public void setRIN(String RIN) {
        this.RIN = RIN;
    }

    public String getSealStatus() {
        return sealStatus;
    }

    public void setSealStatus(String sealStatus) {
        this.sealStatus = sealStatus;
    }

    public int getScan_count() {
        return scan_count;
    }

    public void setScan_count(int scan_count) {
        this.scan_count = scan_count;
    }

    public long getCreated_ts() {
        return created_ts;
    }

    public void setCreated_ts(long created_ts) {
        this.created_ts = created_ts;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("RIN", RIN);
        json.put("create_time", created_ts);
        json.put("seal_status", sealStatus);
        json.put("scan_count", scan_count);

        return json;
    }

    public String toJSONStr(){
        return toJSON().toString();
    }
}

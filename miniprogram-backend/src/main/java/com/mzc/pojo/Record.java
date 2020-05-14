package com.mzc.pojo;

import javax.persistence.*;
import org.json.JSONObject;

@Entity
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @ManyToOne
    @JoinColumn
    private Product product;

    @Column
    private long time;

    @Column
    private String user_nickname;

    @Column
    private int serial_num;

    public int getSerial_num() {
        return serial_num;
    }

    public void setSerial_num(int serial_num) {
        this.serial_num = serial_num;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("id",id);
        json.put("product",product.toJSON());
        json.put("time",time);
        json.put("user_nickname",user_nickname);
        json.put("serial_num",serial_num);

        return json;
    }

    public String toJSONStr(){
        return toJSON().toString();
    }
}


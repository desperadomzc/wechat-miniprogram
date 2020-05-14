package com.mzc.controller;

import com.mzc.dao.ProductDAO;
import com.mzc.dao.RecordDAO;
import com.mzc.pojo.Product;
import com.mzc.pojo.Record;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/record/**")
public class RecordController {
    @Autowired
    ProductDAO productDAO;

    @Autowired
    RecordDAO recordDAO;

    private final static Logger logger = LogManager.getLogger(RecordController.class);

    //create a new record with time,user_nickname,scan_count+1
    //all data in json format
    @PostMapping(value = "/record/create/{pid}/{RIN}/{username}", consumes = "application/json", produces = "application/json")
    public ResponseEntity createRecord(@PathVariable("pid") String pid,
                                       @PathVariable("RIN") String RIN,
                                       @PathVariable("username") String username){
        Product p = productDAO.getProduct(pid,RIN);
        if(p == null){
            return ResponseEntity.status(404).body("Product with id: " + pid + " not found or your RIN is invalid");
        }
        Record r = recordDAO.createRecord(p,username);

        return ResponseEntity.status(201).body(r.toJSONStr());
    }

    //get all records of product with pid
    @GetMapping(value = "/record/get/{pid}/{RIN}", produces = "application/json")
    public ResponseEntity getRecord(@PathVariable("pid")String pid, @PathVariable("RIN") String RIN){
        Product p = productDAO.getProduct(pid,RIN);
        if(p == null){
            return ResponseEntity.status(404).body("Product with id: " + pid + " not found or your RIN is invalid");
        }
        List<Record> list = recordDAO.getRecords(p);
        JSONArray recordsja = new JSONArray(list);

        return ResponseEntity.status(200).body(recordsja.toString());
    }

}

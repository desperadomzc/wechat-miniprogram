package com.mzc.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/record/**")
public class RecordController {
    //create a new record with time,user_nickname,scan_count+1
    //all data in json format
    @PostMapping(value = "/record/create", consumes = "application/json", produces = "application/json")
    public void createRecord(){

    }

    //get all records of product with pid
    @GetMapping("/record/get/{pid}")
    public void getRecord(@PathVariable("pid")long pid){

    }

}

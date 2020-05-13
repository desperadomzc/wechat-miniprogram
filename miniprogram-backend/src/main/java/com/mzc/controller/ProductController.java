package com.mzc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product/**")
public class ProductController {
    //create new product when produced
    //seal status initiated as CREATED
    @PostMapping(value = "/product/create",consumes = "application/json")
    public ResponseEntity createProduct(){
        return null;
    }

//    @GetMapping
//
//
//    @DeleteMapping

}

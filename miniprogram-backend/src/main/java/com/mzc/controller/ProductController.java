package com.mzc.controller;

import com.mzc.dao.ProductDAO;
import com.mzc.pojo.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/product/**")
public class ProductController {

    @Autowired
    ProductDAO productDAO;

    private final static Logger logger = LogManager.getLogger(ProductController.class);

    //test end point
    @GetMapping("/product/test")
    public ResponseEntity getTest() {
        Map<String, Object> map = new HashMap<>();
        String message = "hello world";
        map.put("message", message);
        return ResponseEntity.ok().body(map);
    }

    //create new product when produced
    //seal status initiated as CREATED
    @PostMapping(value = "/product/create", produces = "application/json")
    public ResponseEntity createProduct(@RequestBody String product) {
        logger.info("Enter create product end point");
        JSONObject pjo = new JSONObject(new JSONTokener(new JSONObject(product).toString()));
        if (pjo.has("RIN") && pjo.has("pid")) {
            String rin = pjo.get("RIN").toString();
            String pid = pjo.get("pid").toString();
            Product p = productDAO.createProduct(pid, rin);
            if (p == null) {
                return ResponseEntity.status(400).body("Unable to create such product");
            }
            return ResponseEntity.status(201).body(p.toJSONStr());
        } else {
            return ResponseEntity.badRequest().body("Invalid input!");
        }
    }

    @GetMapping(value = "/product/get/{pid}/{RIN}", produces = "application/json")
    public ResponseEntity getProduct(@PathVariable(value = "pid") String pid, @PathVariable("RIN") String RIN) {
        logger.info("Enter get product end point");
        Product p = productDAO.getProduct(pid, RIN);
        if (p == null) {
            return ResponseEntity.status(404).body("Product with serial number: " + pid + " not found");
        } else {
            return ResponseEntity.status(200).body(p.toJSONStr());
        }
    }

    @PutMapping(value = "/product/put/{pid}/{RIN}", produces = "application/json")
    public ResponseEntity updateProduct(@PathVariable("pid") String pid, @PathVariable("RIN") String RIN, @RequestBody String product) {
        logger.info("Enter update product end point");
        Product p = productDAO.getProduct(pid, RIN);
        if (p == null) {
            return ResponseEntity.status(404).body("Product with serial number: " + pid + " not found");
        } else {
            JSONObject pjo = new JSONObject(new JSONTokener(new JSONObject(product).toString()));
            if (pjo.has("seal_status")) {
                p.setSealStatus(pjo.getString("seal_status"));
                productDAO.updateProduct(p);
                return ResponseEntity.status(200).body(p.toJSONStr());
            } else {
                return ResponseEntity.status(400).body("Invalid input, only support update seal status");
            }
        }

    }
}

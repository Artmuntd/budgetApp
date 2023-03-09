package com.example.budget.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
    @GetMapping
    public String helloworld(){
        return  "hello web";
    }
    @GetMapping("/path/to/page")
    public  String page(@RequestParam String page) {
        return  "Page: " + page;
    }
}

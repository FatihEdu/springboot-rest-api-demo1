package com.example.fth.fth_demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class ApiControllers {
    
    @GetMapping("/")
    public String getHomePage() {
        return "Hello World?";
    }

    
}

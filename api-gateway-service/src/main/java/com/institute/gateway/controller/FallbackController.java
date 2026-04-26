package com.institute.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
    @GetMapping("/fallback/course")
    public String courseFallback(){
        return "Course Service is temporarily unavilable. please try again later. ";
    }

}

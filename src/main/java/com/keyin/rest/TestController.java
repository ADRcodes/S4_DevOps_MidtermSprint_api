package com.keyin.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class TestController {

    @GetMapping("/test")
    public String testEndpoint() {
        return "Test endpoint is working!";
    }

    @PostMapping("/test")
    public ResponseEntity<String> createTest(@RequestBody String testData) {
        // Here you would typically save the testData to a database or perform some action
        return ResponseEntity.ok("Test data received: " + testData);
    }
}

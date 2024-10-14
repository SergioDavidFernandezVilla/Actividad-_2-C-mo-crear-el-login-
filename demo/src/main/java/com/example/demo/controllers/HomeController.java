package com.example.demo.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@NoArgsConstructor
public class HomeController {
    
    @GetMapping("/home")
    public String home(){
        return "Ruta";
    }


    @GetMapping("/sayhello")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello, welcome to web with spring boot segurity");
    }
}

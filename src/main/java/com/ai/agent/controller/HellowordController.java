package com.ai.agent.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HellowordController {

    @GetMapping("say")
    public String sayHello() {
        return "Hello!";
    }
}
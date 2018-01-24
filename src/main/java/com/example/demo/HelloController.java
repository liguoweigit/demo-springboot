package com.example.demo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping("/hello")
public class HelloController {
    @RequestMapping("/hello")
    public String index() {
        System.out.printf("coming helloController.index");
        return "Hello world";
    }

    @RequestMapping("/{username}")
    public String user(@PathVariable("username") String username) {
        System.out.printf("coming helloController.username");
        return String.format("user %s", username);
    }

    @RequestMapping(value = "/{username}",method = RequestMethod.GET)
    public String userGet(@PathVariable("username") String username) {
        System.out.printf("coming helloController.userGet");
        return String.format("user %s", username);
    }
}

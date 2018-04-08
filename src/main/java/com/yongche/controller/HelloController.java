package com.yongche.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping("/hello")
public class HelloController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @RequestMapping("/hello")
    public String index() {
        logger.info("coming helloController.index");
        return "Hello world";
    }

    @RequestMapping("/{username}")
    public String user(@PathVariable("username") String username) {
        logger.info("coming helloController.username");
        return String.format("user %s", username);
    }

    @RequestMapping(value = "/{username}",method = RequestMethod.GET)
    public String userGet(@PathVariable("username") String username) {
        logger.info("coming helloController.userGet");
        return String.format("user %s", username);
    }
}

package com.sna.project.tgservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ApplicationController {

   public class Hello {
        private final String text;

        public Hello(String text) {
            this.text = text;
        }
    }

    @GetMapping("/hello")
    public Hello hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Hello("Hello " + name);
    }
}

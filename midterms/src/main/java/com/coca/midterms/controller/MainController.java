package com.coca.midterms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping("/login.html")
    public String login() {
        return "login";
    }
    @RequestMapping("/display.html")
    public String display() {
        return "display";
    }
}

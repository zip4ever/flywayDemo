package com.realdolmen.flyway.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by KDAAU95 on 12/01/2015.
 */
@Controller
public class TempController {

    @RequestMapping(value = "/welcome")
    public String sayHelloWorld() {
        return "welcome";
    }
}

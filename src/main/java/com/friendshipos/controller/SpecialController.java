package com.friendshipos.controller;


import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@CrossOrigin
public class SpecialController {

    @GetMapping("/birthday-special")
    public String birthday() {
        LocalDate today = LocalDate.now();
        if(today.getMonthValue()==1 && today.getDayOfMonth()==31)
            return "Happy Birthday! You changed my life forever ❤️";
        return "Not today 😉";
    }
}

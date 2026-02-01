package com.friendshipos.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController

public class UserController {
    @GetMapping("/user/me")
    public Map<String, String> currentUser(Authentication auth) {
        return Map.of(
            "username", auth.getName(),
            "role", auth.getAuthorities().iterator().next().getAuthority().replace("ROLE_","")
        );
    }
}



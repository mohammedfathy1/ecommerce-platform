package com.company.userservice.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class Controller {


    @GetMapping("/{id}")
    public Map<String, Object> getUser(
            @PathVariable Long id,
            @RequestHeader(
                    value = "X-Gateway-Source",
                    defaultValue = "direct"
            ) String source
    ) {
        return Map.of(
                "id", id,
                "name", "Ahmed",
                "service", "user-service",
                "requestSource", source
        );
    }

}
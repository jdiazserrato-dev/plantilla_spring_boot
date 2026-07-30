package com.jorel.template_api.controller;

import com.jorel.template_api.service.PingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PingController {

    private final PingService pingService;

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok(pingService.ping());
    }
}
package com.jorel.template_api.service;

import org.springframework.stereotype.Service;

@Service
public class PingService {

    public String ping() {
        return "pong";
    }

    public String pingWithTimestamp() {
        return "pong";
    }
}

package com.example.requesttracker.controller;

import com.example.requesttracker.model.RequestLog;
import com.example.requesttracker.service.RequestLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/request-logs")
public class RequestLogController {

    private final RequestLogService requestLogService;

    public RequestLogController(RequestLogService requestLogService) {
        this.requestLogService = requestLogService;
    }

    @GetMapping("/hi")
    public String hello() {
        return "Hello Cruel World";
    }

    @GetMapping("/all")
    public ResponseEntity<List<RequestLog>> getAllRequestLogs() {
        return ResponseEntity.ok(requestLogService.findAll());
    }

    @GetMapping("/top-10")
    public ResponseEntity<List<RequestLog>> getTop10RequestLogs() {
        return ResponseEntity.ok(requestLogService.findTop10ByOrderByCreatedAtDesc());
    }
}


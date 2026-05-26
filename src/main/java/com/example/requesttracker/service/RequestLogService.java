package com.example.requesttracker.service;

import com.example.requesttracker.model.RequestLog;
import com.example.requesttracker.repository.RequestLogRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RequestLogService {

    private final RequestLogRepository requestLogRepository;

    public RequestLogService(RequestLogRepository requestLogRepository) {
        this.requestLogRepository = requestLogRepository;
    }

    public RequestLog createRequestLog(RequestLog requestLog) {
        return requestLogRepository.save(requestLog);
    }

    public List<RequestLog> findAll() {
        return requestLogRepository.findAll();
    }

    public List<RequestLog> findTop10ByOrderByCreatedAtDesc() {
        return requestLogRepository.findTop10ByOrderByCreatedAtDesc();
    }
}


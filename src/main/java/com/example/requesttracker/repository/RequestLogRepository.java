package com.example.requesttracker.repository;

import com.example.requesttracker.model.RequestLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RequestLogRepository extends MongoRepository<RequestLog, String> {
    List<RequestLog> findTop10ByOrderByCreatedAtDesc();
}

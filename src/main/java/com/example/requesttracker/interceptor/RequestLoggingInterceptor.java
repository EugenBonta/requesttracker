package com.example.requesttracker.interceptor;

import com.example.requesttracker.model.RequestLog;
import com.example.requesttracker.service.RequestLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingInterceptor.class);

    private final RequestLogService requestLogService;

    public RequestLoggingInterceptor(RequestLogService requestLogService) {
        this.requestLogService = requestLogService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String ip = extractClientIp(request);
        String userAgent = request.getHeader("User-Agent");
        String source = request.getHeader("Referer");
        if (source == null) {
            source = request.getRequestURI();
        }

        RequestLog requestLog = RequestLog.builder()
                .ipAddress(ip)
                .userAgent(userAgent)
                .source(source)
                .createdAt(LocalDateTime.now())
                .build();

        // Save the log but do not block the request if saving fails
        try {
            requestLogService.createRequestLog(requestLog);
        } catch (Exception e) {
            // Best-effort logging; avoid throwing to not disrupt request handling
            log.warn("Failed to persist request log: {} {} -> {}", request.getMethod(), request.getRequestURI(), e.getMessage());
        }

        return true;
    }

    private String extractClientIp(HttpServletRequest request) {
        String[] headerCandidates = {
                "X-Forwarded-For",
                "X-Real-IP",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR"
        };

        for (String header : headerCandidates) {
            String value = request.getHeader(header);
            if (value != null && !value.isEmpty() && !"unknown".equalsIgnoreCase(value)) {
                // X-Forwarded-For can contain multiple IPs, take the first one
                return value.split(",")[0].trim();
            }
        }

        return request.getRemoteAddr();
    }
}


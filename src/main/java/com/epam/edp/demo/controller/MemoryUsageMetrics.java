package com.epam.edp.demo.controller;


import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class MemoryUsageMetrics {
    private final MeterRegistry meterRegistry;
    private final String projectSuffix = "98c62c6f";

    public MemoryUsageMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @PostConstruct
    public void registerCustomMetrics() {
        Gauge.builder("app_memory_usage_" + projectSuffix, this, MemoryUsageMetrics::getUsedMemoryInMB)
                .register(meterRegistry);
    }

    private double getUsedMemoryInMB() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        return (double) usedMemory / (1024 * 1024);
    }
}

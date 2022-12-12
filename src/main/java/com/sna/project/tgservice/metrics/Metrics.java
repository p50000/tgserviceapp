package com.sna.project.tgservice.metrics;

import io.prometheus.client.Histogram;

public class Metrics {
    private static final Histogram requestDuration = Histogram.build("http_client_request_duration", "http request duration in seconds").labelNames("http_code", "method").linearBuckets(0, 0.25, 4).register();

    public static Histogram getRequestDuration() {
        return requestDuration;
    }
}

package com.sna.project.tgservice;

import java.io.IOException;
import java.io.StringWriter;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.exporter.common.TextFormat;
import io.prometheus.client.hotspot.DefaultExports;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TgserviceApplication {

	public static void main(String[] args) throws IOException {
		CollectorRegistry registry = CollectorRegistry.defaultRegistry;
		StringWriter writer = new StringWriter();
		TextFormat.write004(writer, registry.metricFamilySamples());
		HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);
		HTTPServer.HTTPMetricHandler mHandler = new HTTPServer.HTTPMetricHandler(registry);
		DefaultExports.initialize();
		server.createContext("/metrics", mHandler);
		server.setExecutor(null); // creates a default executor
		server.start();
		SpringApplication.run(TgserviceApplication.class, args);
	}

}

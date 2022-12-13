package com.sna.project.tgservice;

import com.sun.net.httpserver.HttpServer;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.exporter.common.TextFormat;
import io.prometheus.client.hotspot.DefaultExports;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.jetty.JettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.jetty.JettyWebServer;
import org.springframework.http.client.reactive.JettyResourceFactory;

import java.io.StringWriter;
import java.io.IOException;
import java.net.InetSocketAddress;


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

package com.sna.project.tgservice.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.sna.project.tgservice.metrics.Metrics;

import javax.net.ssl.SSLSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sna.project.tgservice.client.model.SendMessageRequest;

public class TelegramClient {
    private final HttpClient httpClient;
    private final String requestUrl;

    private final ObjectWriter jsonWriter;
    private final static String BASE_URL = "https://api.telegram.org";

    public TelegramClient(String telegramBotToken, HttpClient httpClient) {
        this.requestUrl = BASE_URL + "/bot" + telegramBotToken;
        this.httpClient = httpClient;
        this.jsonWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    public HttpResponse<String> sendMessages(Long chatId, String text)  {
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(requestUrl + "/sendMessage"))
                    .timeout(Duration.ofSeconds(2))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonWriter.writeValueAsString(
                            new SendMessageRequest(chatId, text)
                    )))
                    .build();
        } catch (JsonProcessingException e) {
            throw new TelegramClientException("Request body is not parsable");
        }

        HttpResponse<String> res = null;
        long totalTime = 0L;

        try {
            long startTime = System.nanoTime();
            res = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            totalTime = TimeUnit.SECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
        } catch (InterruptedException | IOException ex) {
          res = new MockHttpResponse(500, "EXCEPTION CAUGHT");
        } finally {
            var statusCode = res == null ? "UNKNOWN" : String.valueOf(res.statusCode());
            var method = res == null ? "UNKNOWN" : res.request().method();
            Metrics.getRequestDuration().labels(statusCode, method).observe(totalTime);
        }

        return res;
    }

    static class TelegramClientException extends RuntimeException {
        TelegramClientException(String cause) {
            super(cause);
        }
    }

    public static class MockHttpResponse implements HttpResponse<String> {

        private final int statusCode;
        private final String body;

        public MockHttpResponse(int statusCode, String body) {
            this.statusCode = statusCode;
            this.body = body;
        }

        @Override
        public int statusCode() {
            return statusCode;
        }

        @Override
        public HttpRequest request() {
            return HttpRequest.newBuilder()
                    .uri(URI.create("https://api.telegram.org/sendMessage"))
                    .build();
        }

        @Override
        public Optional<HttpResponse<String>> previousResponse() {
            return Optional.empty();
        }

        @Override
        public HttpHeaders headers() {
            return null;
        }

        @Override
        public String body() {
            return body;
        }

        @Override
        public Optional<SSLSession> sslSession() {
            return Optional.empty();
        }

        @Override
        public URI uri() {
            return null;
        }

        @Override
        public HttpClient.Version version() {
            return null;
        }
    }
}

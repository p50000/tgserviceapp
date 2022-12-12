package com.sna.project.tgservice.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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

    public TelegramClient(String telegramBotToken) {
        this.requestUrl = BASE_URL + "/bot" + telegramBotToken;
        this.httpClient = HttpClient.newHttpClient();
        this.jsonWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    public HttpResponse<String> sendMessages(Long chatId, String text) throws IOException, InterruptedException {
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
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    static class TelegramClientException extends RuntimeException {
        TelegramClientException(String cause) {
            super(cause);
        }
    }

    class TimeoutHttpResponse implements HttpResponse {

        @Override
        public int statusCode() {
            return 408;
        }

        @Override
        public HttpRequest request() {
            return null;
        }

        @Override
        public Optional<HttpResponse> previousResponse() {
            return Optional.empty();
        }

        @Override
        public HttpHeaders headers() {
            return null;
        }

        @Override
        public Object body() {
            return "Request not complete because of timeout";
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

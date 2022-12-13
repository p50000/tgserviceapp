package com.sna.project.tgservice.configuration;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.sna.project.tgservice.client.TelegramClient;
import com.sna.project.tgservice.job.MessageSendingJob;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
@Import(ApplicationConfiguration.class)
public class ApplicationTestingConfguration {
    @MockBean
    public MessageSendingJob messageSendingJob;

    @Bean
    public TelegramClient telegramClient() {
        var client = mock(HttpClient.class);
        try {
            when(client.send(any(), eq(HttpResponse.BodyHandlers.ofString())))
                    .thenReturn(new TelegramClient.MockHttpResponse(200, "OK with test response!"));
        } catch (Exception ignored) {

        }
        return new TelegramClient(
                "BOGUS",
                client
        );
    }
}

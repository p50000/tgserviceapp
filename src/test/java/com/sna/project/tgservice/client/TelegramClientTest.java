package com.sna.project.tgservice.client;

import java.net.http.HttpResponse;
import java.util.concurrent.atomic.AtomicReference;

import com.sna.project.tgservice.configuration.ApplicationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationTest
@ExtendWith(SpringExtension.class)
public class TelegramClientTest {

    @Autowired
    TelegramClient telegramClient;

    @Test
    public void checkSendMessage() {
        AtomicReference<HttpResponse<String>> response = new AtomicReference<>();

        Assertions.assertThatCode(() -> {
            response.set(telegramClient.sendMessages(0L, "BOGUS"));
        }).doesNotThrowAnyException();
        assertThat(response.get().statusCode()).isEqualTo(200);
    }
}

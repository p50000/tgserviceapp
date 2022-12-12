package com.sna.project.tgservice.job;

import java.io.IOException;

import com.sna.project.tgservice.client.TelegramClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MessageSendingJob {

    private final TelegramClient telegramClient;

    @Autowired
    public MessageSendingJob(TelegramClient telegramClient, TelegramClient telegramClient1) {
        this.telegramClient = telegramClient1;
        System.out.println("Constructor called");
    }

    @PostConstruct
    public void runScript() throws InterruptedException {
        System.out.println("Method called");
        while (true) {
            try {
                var response = telegramClient.sendMessages(342806863L, "renobta hello!");

                Thread.sleep(60000L);
            } catch (InterruptedException | IOException e) {
                System.out.println(e.getMessage());
                // Restore the interrupted status
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}

package com.sna.project.tgservice.job;

import java.io.IOException;
import java.util.List;

import com.sna.project.tgservice.client.TelegramClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
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
        Thread worker = new Thread(() -> {
            while (true) {
                try {
                    for (Long id : getReceivers()) {
                        var response = telegramClient.sendMessages(id, "Hello world!");
                        System.out.printf("\nChat id: %d. Response code is %s\n", id,
                                response == null
                                        ? "UNKNOWN"
                                        : String.valueOf(response.statusCode()));
                    }
                    Thread.sleep(180_000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                    // Restore the interrupted status
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        });
        worker.start();
    }

    /**
     * Mock method for now
     */
    private List<Long> getReceivers() {
        return List.of(342806863L, 0L, 507765513L, 860969225L);
    }
}

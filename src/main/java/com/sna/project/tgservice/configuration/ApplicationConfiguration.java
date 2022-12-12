package com.sna.project.tgservice.configuration;

import com.sna.project.tgservice.client.TelegramClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public TelegramClient telegramClient() {
        return new TelegramClient("5571977493:AAFsLsL-tkPzb-1b5_WfW22zYlaWA-nfDBI");
    }
}

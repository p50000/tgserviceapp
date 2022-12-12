package com.sna.project.tgservice.client.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class SendMessageRequest {

    @JsonProperty("chat_id")
    Long chatId;

    @JsonProperty("text")
    String text;

    public SendMessageRequest(Long chatId, String text) {
        this.chatId = chatId;
        this.text = text;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

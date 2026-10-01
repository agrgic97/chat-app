package com.chatapp.dto;

import java.time.Instant;

public class MessageDto {

    private Long id;
    private String from;
    private String to;
    private String content;
    private Instant sentAt;

    public MessageDto() {
    }

    public MessageDto(Long id, String from, String to, String content, Instant sentAt) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.content = content;
        this.sentAt = sentAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getSentAt() {
        return sentAt;
    }

    public void setSentAt(Instant sentAt) {
        this.sentAt = sentAt;
    }
}

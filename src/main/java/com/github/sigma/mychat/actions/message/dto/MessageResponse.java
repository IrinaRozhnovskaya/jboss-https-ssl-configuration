package com.github.sigma.mychat.actions.message.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageResponse {

    static final SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm z");

    final String from, body;
    final Date when;

    public MessageResponse(String from, String body, Date when) {
        this.from = from;
        this.body = body;
        this.when = when;
    }

    public String getBody() {
        return body;
    }

    public String getWhen() {
        return format.format(when);
    }

    public String getFrom() {
        return from;
    }
}

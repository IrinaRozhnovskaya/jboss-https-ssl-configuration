package com.github.sigma.mychat.actions;

import com.github.sigma.mychat.actions.message.dto.MessageRequest;
import com.github.sigma.mychat.actions.message.dto.MessageResponse;
import com.github.sigma.mychat.actions.message.MessageService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class MessageAction extends ActionSupport {

    MessageService messageService;

    public MessageAction() { }

    @Inject
    public MessageAction(MessageService messageService) {
        this.messageService = messageService;
    }

    String from, body;

    public void setFrom(final String from) {
        this.from = from;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    @Override
    @Action("/*")
    public String execute() {
        final MessageRequest request = MessageRequest.of(from, body);
        messageService.sendMessage(request);
        return "index";
    }

    public List<MessageResponse> getMessages() {
        return messageService.findMessagesInDescendOrder();
    }
}

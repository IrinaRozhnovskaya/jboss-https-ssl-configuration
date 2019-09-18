package com.github.sigma.mychat.actions.message;

import com.github.sigma.mychat.actions.message.domain.Message;
import com.github.sigma.mychat.actions.message.domain.MessageRepository;
import com.github.sigma.mychat.actions.message.dto.MessageRequest;
import com.github.sigma.mychat.actions.message.dto.MessageResponse;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Collections.unmodifiableList;

@Stateless
public class MessageService implements Serializable {

    MessageRepository messageRepository;

    public MessageService() { } // Fucking JavaEE standards...

    @Inject
    public MessageService(MessageRepository messageRepository) {
        setMessageRepository(messageRepository);
    }

    public void setMessageRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void sendMessage(final MessageRequest sendMessageRequest) {
        if (sendMessageRequest == null) return;

        final String messageBody = sendMessageRequest.getMessageBody();
        if (isNotNullOrBlank(messageBody)) return;

        final String sender = sendMessageRequest.getSender();
        if (isNotNullOrBlank(sender)) return;

        messageRepository.save(new Message(sender, messageBody));
    }

    private boolean isNotNullOrBlank(String messageBody) {
        if (messageBody == null) return true;
        if (messageBody.trim().equals("")) return true;
        return false;
    }

    public List<MessageResponse> findMessagesInDescendOrder() {
        final List<MessageResponse> result = new ArrayList<>();
        final Set<Message> messagesInDescendOrder = messageRepository.findAllOrderByCreatedAtDesc();
        for (Message message: messagesInDescendOrder) {
            result.add(new MessageResponse(message.getFrom(), message.getBody(), message.getCreatedAt()));
        }
        return unmodifiableList(result);
    }
}

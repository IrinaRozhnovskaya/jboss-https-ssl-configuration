package com.github.sigma.mychat.actions.message.dto;

import com.github.sigma.mychat.actions.message.MessageService;
import com.github.sigma.mychat.actions.message.domain.Message;
import com.github.sigma.mychat.actions.message.domain.MessageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Set;
import java.util.TreeSet;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceMockTest {

  @Mock
  private MessageRepository repository;

  @InjectMocks
  private MessageService messageService;

  @Test
  public void should_send_valid_message() {

    // given
    MessageRequest invalidMessage = null;
    // when
    messageService.sendMessage(invalidMessage);
    // then
    verify(repository, times(0)).save(any(Message.class));

    // and
    invalidMessage = MessageRequest.of(null, "1qqqe");
    messageService.sendMessage(invalidMessage);
    verify(repository, times(0)).save(any(Message.class));
    //verify(repository).save(any(Message.class));
    //reset(messageService);
    //reset(repository);

    invalidMessage = MessageRequest.of(null, null);
    messageService.sendMessage(invalidMessage);
    verify(repository, times(0)).save(any(Message.class));

    invalidMessage = MessageRequest.of("ololo", null);
    messageService.sendMessage(invalidMessage);
    verify(repository, times(0)).save(any(Message.class));

    // finally
    MessageRequest validMessage = MessageRequest.of("qwe", "1qqqe");
    messageService.sendMessage(validMessage);
    then(repository).should(times(1)).save(any(Message.class));
  }

  @Test
  public void findMessagesInDescendOrder() {
    // given:
    Message message = new Message("qwe", "1qqqe");
    Set<Message> messageSet = new TreeSet<>();
    messageSet.add(message);
    // given:
    given(repository.findAllOrderByCreatedAtDesc()).willReturn(messageSet);
    // with:
    repository.save(new Message("qwe", "1qqqe"));

    // when:
    messageService.findMessagesInDescendOrder();

    then(repository).should(times(1)).findAllOrderByCreatedAtDesc();
  }
}


package com.github.sigma.mychat.actions.message.domain;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.github.sigma.mychat.actions.message.domain.Message.FIND_ALL;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSortedSet;

@ApplicationScoped
public class MessageRepository {

  public static final Integer PAGE_SIZE = 20;

  @PersistenceContext
  EntityManager em;

  @PostConstruct
  public void initTestData() {
    List<String> strings = asList("ololo", "trololo");
    for (String string : strings) {
      em.persist(new Message(string, "a message: " + string));
    }
  }

  public void save(final Message message) {
    em.persist(message);
  }

  public Set<Message> findAllOrderByCreatedAtDesc() {
    TypedQuery<Message> query = em.createNamedQuery(FIND_ALL, Message.class)
                                  .setMaxResults(PAGE_SIZE);
    List<Message> resultList = query.getResultList();
    return unmodifiableSortedSet(new TreeSet<>(resultList));

  }

}

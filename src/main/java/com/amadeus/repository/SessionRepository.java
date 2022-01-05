package com.amadeus.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import com.amadeus.model.Session;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class SessionRepository implements PanacheRepository<Session> {

  public List<Session> findByUser(Long user) {
    return find("user", user).list();
  }

  public List<Session> findData() {
    return listAll();
  }

  @Transactional
  public void addSession(Session newSession) {
    persist(newSession);
  }

  @Transactional
  public void deleteSession(Long id) {
    if (id != null) {
      delete("id", id);
    }
  }

}

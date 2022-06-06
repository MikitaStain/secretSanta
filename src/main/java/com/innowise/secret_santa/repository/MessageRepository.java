package com.innowise.secret_santa.repository;

import com.innowise.secret_santa.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByAccount_Id(Long id);
}

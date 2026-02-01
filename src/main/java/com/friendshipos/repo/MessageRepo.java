package com.friendshipos.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.friendshipos.model.Message;

public interface MessageRepo extends JpaRepository<Message, Long> {}

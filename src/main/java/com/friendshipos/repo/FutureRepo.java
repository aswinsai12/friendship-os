package com.friendshipos.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import com.friendshipos.model.FutureMessage;

public interface FutureRepo extends JpaRepository<FutureMessage, Long> {}
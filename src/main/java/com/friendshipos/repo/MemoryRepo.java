package com.friendshipos.repo;

import com.friendshipos.model.Memory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoryRepo extends JpaRepository<Memory, Long> {}

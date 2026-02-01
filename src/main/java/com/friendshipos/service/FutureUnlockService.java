package com.friendshipos.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import com.friendshipos.repo.FutureRepo;
import com.friendshipos.model.FutureMessage;

@Service
public class FutureUnlockService {

    private final FutureRepo repo;

    public FutureUnlockService(FutureRepo repo) {
        this.repo = repo;
    }

    @Scheduled(fixedRate = 60000) // every minute
    public void unlockMessages() {
        List<FutureMessage> messages = repo.findAll();

        for (FutureMessage msg : messages) {
            if (!msg.isUnlocked() && !msg.getUnlockDate().isAfter(LocalDate.now())) {
                msg.setUnlocked(true);
                repo.save(msg);
                System.out.println("Unlocked message: " + msg.getMessage());
            }
        }
    }
}

package com.friendshipos.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import com.friendshipos.repo.FutureRepo;
import com.friendshipos.model.FutureMessage;

@RestController
@RequestMapping("/future-message")
@RequiredArgsConstructor
@CrossOrigin
public class FutureController {

    private final FutureRepo repo;

    @PostMapping
    public FutureMessage add(@RequestBody FutureMessage m) {
        m.setUnlocked(false);
        return repo.save(m);
    }

    @GetMapping("/all")
    public List<FutureMessage> getUnlockedMessages() {
        return repo.findAll()
                .stream()
                .filter(FutureMessage::isUnlocked)
                .toList();
    }

    @GetMapping("/status")
    public String getMessageStatus() {
        var list = repo.findAll();

        if (list.isEmpty())
            return "No future message stored yet.";

        FutureMessage msg = list.get(0);

        if (!msg.getUnlockDate().isAfter(LocalDate.now()))
            return msg.getMessage();

        return "Message locked until " + msg.getUnlockDate();
    }
}

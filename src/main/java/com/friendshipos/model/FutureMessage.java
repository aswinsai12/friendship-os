package com.friendshipos.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class FutureMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private LocalDate unlockDate;

    public Long getId() { return id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDate getUnlockDate() { return unlockDate; }
    public void setUnlockDate(LocalDate unlockDate) { this.unlockDate = unlockDate; }
    private boolean unlocked = false;

public boolean isUnlocked() { return unlocked; }
public void setUnlocked(boolean unlocked) { this.unlocked = unlocked; }

}

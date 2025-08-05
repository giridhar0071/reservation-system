package com.giridhar.reservation.Exception;

import java.util.UUID;

public class SlotFullException extends RuntimeException {
    private final UUID waitingEntryId;

    public SlotFullException(String message, UUID waitingEntryId) {
        super(message);
        this.waitingEntryId = waitingEntryId;
    }

    public UUID getWaitingEntryId() {
        return waitingEntryId;
    }
}

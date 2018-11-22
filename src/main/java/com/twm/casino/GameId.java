package com.twm.casino;

import java.util.UUID;

public class GameId {
    public static UUID getGameId() {
        return UUID.randomUUID();
    }
}

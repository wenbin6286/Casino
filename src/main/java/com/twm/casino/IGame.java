package com.twm.casino;

import com.twm.casino.exceptions.AlreadyInGameException;
import com.twm.casino.exceptions.GameFullException;
import com.twm.casino.exceptions.InsufficientFundException;

import java.util.UUID;

public interface IGame {
    void joinGame(IPlayer player) throws InsufficientFundException, GameFullException,
    AlreadyInGameException;
    void waitForPlayers()throws InterruptedException;
    int play();
    UUID getGameId();
}

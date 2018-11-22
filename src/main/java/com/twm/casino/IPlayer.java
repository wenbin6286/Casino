package com.twm.casino;

import com.twm.casino.exceptions.InsufficientFundException;

import java.util.UUID;


public interface IPlayer {
    void setStartingAccountBalance(int amt);
    void setRPSStrategy(RPSStrategy strategy);
    int pay(int fee) throws InsufficientFundException;
    void award(int winning);
    boolean canPlay(int min);
    RPSMove playGame(UUID gameId);
    int getBalance();
    UUID getId();
}

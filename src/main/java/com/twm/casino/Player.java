package com.twm.casino;

import com.twm.casino.exceptions.InsufficientFundException;


import java.util.UUID;

public class Player implements IPlayer {
    //guarded by this
    private int balance=0;
    private  RPSStrategy strategy;
    private final UUID id;
    public Player(int initialAmt, RPSStrategy strategy) {
        balance = initialAmt;
        this.strategy = strategy;
        id = UUID.randomUUID();
    }
    @Override
    public void setStartingAccountBalance(int amt) {
        balance = amt;
    }

    @Override
    public void setRPSStrategy(RPSStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public synchronized int pay(int fee) throws InsufficientFundException{
        if(!canPlay(fee)) {
            throw new InsufficientFundException();
        }
        balance -=fee;
        return fee;
    }

    @Override
    public synchronized void award(int winning) {
        balance += winning;
    }

    @Override
    public boolean canPlay(int min) {
        return balance >=min;
    }

    @Override
    public RPSMove playGame(UUID gameId) {
        return strategy.playGame(gameId);
    }

    @Override
    public int getBalance() {
        return balance;
    }

    public UUID getId() {
        return id;
    }
}

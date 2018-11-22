package com.twm.casino;

import com.twm.casino.exceptions.InsufficientFundException;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    @Test
    public void testPlayer() throws InsufficientFundException {
        IPlayer player = new Player(5,new RPSStrategy());
        assert(player.canPlay(5)==true);
        assert(player.canPlay(10)==false);
        player.setStartingAccountBalance(10);
        player.pay(5);
        assert(player.getBalance()==5);
        player.award(10);
        assert(player.getBalance()==15);
    }

    @Test(expected = InsufficientFundException.class)
    public void testPay() throws InsufficientFundException {
        IPlayer player = new Player(0,new RPSStrategy());
        player.pay(5);
    }

}
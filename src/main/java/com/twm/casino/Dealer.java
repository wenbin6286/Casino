package com.twm.casino;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Dealer implements IDealer, Runnable {

    private final Logger logger = LoggerFactory.getLogger(Dealer.class);
    private final ICasino casino;
    public Dealer(ICasino casino) {
        this.casino = casino;
    }


    @Override
    public void run () {
        boolean alive = true;
        logger.info("Dealer: shift has started!");
        while(alive) {

            try {
                IGame game = casino.nextGame();
                casino.startBetting(game);
                game.waitForPlayers();
                casino.stopBetting(game);
                int money = game.play();
                casino.collect(money);

            } catch (InterruptedException e) {
                alive = false;

            }
        }
        logger.info("Dealer: shift has ended!");
    }


}

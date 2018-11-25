package com.twm.casino;

import com.twm.casino.exceptions.AlreadyInGameException;
import com.twm.casino.exceptions.GameFullException;
import com.twm.casino.exceptions.InsufficientFundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Casino implements ICasino{
    private final Logger logger = LoggerFactory.getLogger(Casino.class);
    private final int numDealers;
    private final ExecutorService dealerPool;
    private final ExecutorService  playerPool;
    //games ready for players
    private final Map<UUID,IGame> games = new ConcurrentHashMap<>();
    private final Map<UUID,IPlayer> players = new ConcurrentHashMap<>();
    private final AtomicInteger balance = new AtomicInteger(0);
    public Casino(int numDealers) {
        this. numDealers = numDealers;
        dealerPool = Executors.newFixedThreadPool(numDealers);
        playerPool = Executors.newFixedThreadPool(numDealers*10);
    }

    @Override
    public void startBetting(IGame game) {
        //make game available to players
        games.put(game.getGameId(),game);
    }


    // game is not available for players
    @Override
    public void stopBetting(IGame game) {
         games.remove(game.getGameId());
    }

    public void collect(int money) {
        balance.addAndGet(money);
    }
    @Override
    public  void enter(IPlayer player, int numGames) {
        players.put(player.getId(),player);
        Runnable r = () -> {
            while (player.canPlay(ICasino.MIN_BET)) {
                try {
                    Thread.sleep(100);//let player wonder around a bit
                    playGame(player);
                } catch (InterruptedException e) {
                   return; //stop playing if interrupted
                }
            }
        };

        for (int j = 0; j < numGames; j++) {
                playerPool.execute(r);
            }
    }

    private void playGame(IPlayer player) {

        logger.info("There are  {} games to play.",games.values().size());
        //try find an available game from active games
        for(IGame game:games.values()) {
            try {
                game.joinGame(player);
            } catch (InsufficientFundException e) {

                return; //since game fee is the same for all the games
            } catch (GameFullException |AlreadyInGameException e) {
                //find next available game
                continue;
            }
        }
    }
    public void open() {

        for(int i=0;i<numDealers;i++) {
            Dealer dealer = new Dealer(this);
            dealerPool.execute(dealer);
        }

    }
    public  void close()  {
        logger.info("Casino is closing");
        playerPool.shutdownNow();
        dealerPool.shutdownNow();

    }
    public int getBalance() {
        return balance.get();
    }

    public List<IGame> getGames() {
        return new ArrayList<>(games.values());
    }

    public List<IPlayer> getPlayers() {
        return new ArrayList<>(players.values());
    }
}

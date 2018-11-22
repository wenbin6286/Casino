package com.twm.casino;

public class Dealer implements IDealer, Runnable {


    private final ICasino casino;
    public Dealer(ICasino casino) {
        this.casino = casino;
    }
    @Override
    public void run () {
        boolean alive = true;
        System.out.println("Dealer: shift has started!");
        while(alive) {

            try {
                IGame game = new RPSGame();
                casino.startBetting(game);
                game.waitForPlayers();
                casino.stopBetting(game);
                int money = game.play();
                casino.collect(money);

            } catch (InterruptedException e) {
                alive = false;

            }
        }
        System.out.println("Dealer: shift has ended!");
    }


}

package com.twm.casino;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CasinoTest {

    @Test
    public void testCasino() {
        ICasino casino = new Casino(5);
        casino.open();

        IPlayer player = new Player(31,new RPSStrategy());
        casino.enter(player,5);
        player = new Player(15,new RPSStrategy());
        casino.enter(player,3);

        for(int i=0;i<10;i++) {
            try {
                Thread.sleep(100); //let casino run for a while
            } catch (InterruptedException e) {

                break;
            }
            List<IGame> games = casino.getGames();
            System.out.println("Active Games");
            games.stream().forEach(System.out::println);
        }
        casino.close();
        int total = casino.getBalance();
        assert(total == 30);
    }

}
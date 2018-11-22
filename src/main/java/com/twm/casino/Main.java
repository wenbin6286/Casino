package com.twm.casino;

import java.util.List;

public class Main {
    public static void main(String args[]) throws InterruptedException{


        ICasino casino = new Casino(5);
        casino.open();

        //10 players each play 3 games
        for (int i =0;i<10;i++) {
            IPlayer player = new Player(15,new RPSStrategy());
            casino.enter(player,3);//each player plays 3 game

        }
        for(int i=0;i<10;i++) {
            Thread.sleep(100); //let casino run for a while
            List<IGame> games = casino.getGames();
            System.out.println("Active Games");
            games.stream().forEach(System.out::println);
        }
        casino.close();
        int total = casino.getBalance();
        System.out.println("Total income for the day "+total);
    }
}

package com.twm.casino;

import java.util.List;
import java.util.UUID;

public interface ICasino {
    int MIN_BET=5;
    void startBetting(IGame game);
    void stopBetting(IGame game);
    void collect(int money);
    void close();
    void open();
    void enter(IPlayer player,int n);
    int getBalance();
    List<IGame> getGames();

}

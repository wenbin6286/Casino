package com.twm.casino;

import com.twm.casino.exceptions.AlreadyInGameException;
import com.twm.casino.exceptions.GameFullException;
import com.twm.casino.exceptions.InsufficientFundException;
import org.junit.Test;
import static com.twm.casino.RPSMove.*;
import static com.twm.casino.GameResult.*;


public class RPSGameTest {

    @Test(expected = AlreadyInGameException.class)
    public void testJoinTableTwice() throws InsufficientFundException, AlreadyInGameException, GameFullException {
        IPlayer player = new Player(10,new RPSStrategy());
        IGame game = new RPSGame();
        game.joinGame(player);
        game.joinGame(player);

    }

    @Test
    public void play() {
    }

    @Test
    public void makeMove() {
        RPSGame game = new RPSGame();
        assert(game.makeMove(ROCK,SISSORS)==WIN);
        assert(game.makeMove(ROCK,PAPER)==LOSE);
        assert(game.makeMove(ROCK,ROCK)==TIE);

        assert(game.makeMove(SISSORS,SISSORS)==TIE);
        assert(game.makeMove(SISSORS,PAPER)==WIN);
        assert(game.makeMove(SISSORS,SISSORS)==TIE);

        assert(game.makeMove(PAPER,SISSORS)==LOSE);
        assert(game.makeMove(PAPER,PAPER)==TIE);
        assert(game.makeMove(PAPER,ROCK)==WIN);
    }
}
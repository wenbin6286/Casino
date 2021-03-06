package com.twm.casino;

import com.twm.casino.exceptions.AlreadyInGameException;
import com.twm.casino.exceptions.GameFullException;
import com.twm.casino.exceptions.InsufficientFundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.twm.casino.RPSMove.*;
import static com.twm.casino.GameResult.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;


public class RPSGame implements IGame{
    private static final Logger logger = LoggerFactory.getLogger(RPSGame.class);
    private static final Map<RPSMove, Map<RPSMove,GameResult>> matrix;
    static {
        matrix = new EnumMap<>(RPSMove.class);
        Map<RPSMove,GameResult> temp = new EnumMap<>(RPSMove.class);
        temp.put(ROCK,TIE);
        temp.put(SISSORS,WIN);
        temp.put(PAPER,LOSE);
        matrix.put(ROCK,temp);

        temp = new EnumMap<>(RPSMove.class);
        temp.put(ROCK,LOSE);
        temp.put(SISSORS,TIE);
        temp.put(PAPER,WIN);
        matrix.put(SISSORS,temp);

        temp = new EnumMap<>(RPSMove.class);
        temp.put(ROCK,WIN);
        temp.put(SISSORS,LOSE);
        temp.put(PAPER,TIE);
        matrix.put(PAPER,temp);

    }
    private final UUID gameId;

    private static final int NUM_PLAYER=2;
    private static final int FEE =5 ;//$5 per game per player
    private CountDownLatch latch;

    private volatile int moneyCollected =0;
    private final List<IPlayer> players;

    RPSGame() {
        this.gameId = GameId.getGameId();

        players = new ArrayList<>(NUM_PLAYER);
        latch = new CountDownLatch(NUM_PLAYER);
        logger.info("Game {} is created.",gameId);

    }
    @Override
    public UUID getGameId() {
        return gameId;
    }
    //protect moneyCollected and  players.
    @Override
    public synchronized void joinGame(IPlayer player)
            throws GameFullException, InsufficientFundException, AlreadyInGameException{

        if(players.size() == NUM_PLAYER) {
            throw new GameFullException();
        }
        for(IPlayer p : players) {
            //a player shall not play with himself
            if(player == p)
                throw new AlreadyInGameException();
        }
        moneyCollected+=(player.pay(FEE));
        players.add(player);
        latch.countDown();
    }
    @Override
    public void waitForPlayers() throws InterruptedException {
        latch.await();
    }

    @Override
    public int play() {
        IPlayer player1, player2;
        player1 = players.get(0);
        player2 = players.get(1);
        GameResult result = makeMove(player1.playGame(gameId),player2.playGame(gameId));
        if(result==WIN) {
            player1.award(moneyCollected);
            moneyCollected= 0;
        }
        else if(result == LOSE) {
            player2.award(moneyCollected);
            moneyCollected=0;
        }
        else {
            //tie, no action.
        }
        logger.info("Game {} is played.",gameId);
        return moneyCollected; //return the remaining money to the dealer;
    }

     GameResult makeMove(RPSMove m1, RPSMove m2) {
        return matrix.get(m1).get(m2);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[GameId = "+gameId).append(" ");
        sb.append("money = "+ moneyCollected).append(" ");
        sb.append("numPlayer = "+players.size()).append("]");
        return sb.toString();
    }

    //getters to expose in REST
    public List<IPlayer> getPlayers() {
        return players;
    }
    public int getMoneyCollected() {
        return moneyCollected;
    }
}

package com.twm.casino.rest;

import com.twm.casino.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class CasinoCtrl {
    private static final ICasino casino = new Casino(5);


    @GetMapping(path="/casino")
    public ICasino getCasino() {
        return casino;
    }

    @GetMapping(path="/casino/open")
    public ICasino openCasino() {
        casino.open();
        return casino;
    }
    @PutMapping(path="/casino/play")
    public IPlayer playGame(@RequestParam(value="initialBalance",defaultValue="10")int initialBalance,
                            @RequestParam(value = "numGame",defaultValue="3") int numGame) {
        IPlayer player = new Player(initialBalance,new RPSStrategy());
        casino.enter(player, numGame);
        return player;
    }
    //use url template to pass in parameters
    @GetMapping(path={"/casino/enter/{x}/{y}","/casino/enter"})
    public IPlayer enter(@PathVariable(value="x")int initialBalance,
                         @PathVariable(value = "y") int numGame) {
        //set default values
        if(initialBalance == 0) initialBalance = 10;
        if(numGame == 0) numGame =3;
        IPlayer player = new Player(initialBalance,new RPSStrategy());
        casino.enter(player, numGame);
        return player;
    }

}

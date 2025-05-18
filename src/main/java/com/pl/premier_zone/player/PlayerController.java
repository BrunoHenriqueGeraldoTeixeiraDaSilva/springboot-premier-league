package com.pl.premier_zone.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/player")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService PlayerService) {
        this.playerService = PlayerService;
    }

    @GetMapping //To handle GET requqests
    public List<Player> getPlayers(
            @RequestParam(required = false) String team,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String nation){
        if(team != null && position != null){
            return playerService.getPlayersByTeamAndPosition(team, position);
        }
        else if (team != null){
            return playerService.getPlayersFromTeam(team);
        }
        else if (name != null){
            return playerService.getPlayersByName(name);
        }
        else if (position != null){
            return playerService.getPlayerByPosition(position);
        }
        else if (nation != null){
            return playerService.getPlayerByNation(nation);
        } else {
            return playerService.getPlayers();
        }
    }

    @PostMapping
    public ResponseEntity<Player> addPlayer (@RequestBody Player player){
        Player cratedPlayer = playerService.addPlayer(player);
        return new ResponseEntity<>(cratedPlayer, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player){
        Player resultPlayer = playerService.updatePlayer(player);
        if(resultPlayer != null){
            return new ResponseEntity<>(resultPlayer, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

        @DeleteMapping("/{playerName}")
        public ResponseEntity<String> deletePlayer (@PathVariable String playerName){
            playerService.deletePlayer(playerName);
            return new ResponseEntity<>("Player deleted successfully", HttpStatus.OK);
        }


}

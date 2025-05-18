package com.pl.premier_zone.player;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
@component Marks a java class as a componet by doing this you are telling spring that it should be
managed by the spring container this means spring will create a instaced of this class
and mange its life cycle
 */
@Component
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayers(){
        return playerRepository.findAll(); //return list of every single player
    }

    public List<Player> getPlayersFromTeam(String teamName) {
        return playerRepository.findAll().stream()
                .filter(player -> teamName.equals(player.getTeam()))
                .collect(Collectors.toList());
    }

        // How the lambda works: "For each player,
        // check if the team matches teamName"
        //---------------------
        // For Stream imagine you want to do something with every player: Keep only
        // players from "Lakers" Change names to uppercase Count
        // how many there are With Streams,
        // you can do this in a chain of steps,
        //---------------------
        //After the filtering is done, you’re left with a Stream of players from the
        // correct team. But Streams are not regular lists.
        // So to get a normal List<Player> back (like ArrayList), you use
        //.collect(Collectors.toList())
        // .collect(Collectors.toList()) It’s called a terminal operation —
        // it finishes the stream pipeline and gives
        // you a usable result. Without .collect(...), you don’t have your filtered
        // players in a list — you just have a stream

    public List<Player> getPlayersByName (String searchText) {
        return playerRepository.findAll().stream()
                .filter(player -> player.getName().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayerByPosition (String searchText) {
        return playerRepository.findAll().stream()
                .filter(player ->
                        player.getPos().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayerByNation(String searchText) {
        return playerRepository.findAll().stream()
                .filter(player ->
                        player.getNation().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByTeamAndPosition (String team, String position) {
        return playerRepository.findAll().stream()
                .filter(player -> team.equals(player.getTeam()) && position.equals(player.getPos()))
                .collect(Collectors.toList());
    }

    public Player addPlayer(Player player) {
        playerRepository.save(player);
        return player;
    }

    public Player updatePlayer(Player updatedPlayer) {
            Optional<Player> existingPlayer = playerRepository.findByName(updatedPlayer.getName());
            if (existingPlayer.isPresent()) {
                Player playerToUpdate = existingPlayer.get();
            playerToUpdate.setName(updatedPlayer.getName());
            playerToUpdate.setTeam(updatedPlayer.getTeam());
            playerToUpdate.setPos(updatedPlayer.getPos());
            playerToUpdate.setNation(updatedPlayer.getNation());

            playerRepository.save(playerToUpdate);
            return playerToUpdate;
        }
        return null;
    }
    @Transactional
    public void deletePlayer(String playerName) {
        playerRepository.deleteByName(playerName);
    }
}

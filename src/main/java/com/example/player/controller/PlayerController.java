package com.example.player.controller;

import com.example.player.service.PlayerH2Service;
import com.example.player.model.Player;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

@RestController
public class PlayerController {
    @Autowired
    private PlayerH2Service playerService;

    @GetMapping("/players")
    public ArrayList<Player> getPlayers() {
        return playerService.getPlayers();
    }

    @GetMapping("/players/{playerId}")
    public Player getPlayerById(@PathVariable("playerId") int playerId) {
        return playerService.getPlayerById(playerId);
    }

    @PostMapping("/players")
    public Player addPlayer(@RequestBody Player player) {
        return playerService.addPlayer(player);
    }

    @PutMapping("/players/{playerId}")
    public Player updaPlayer(@RequestBody Player player, @PathVariable("playerId") int playerId) {
        return playerService.updatePlayer(player, playerId);
    }

    @DeleteMapping("/players/{playerId}")
    public void deletePalyer(@PathVariable("playerId") int playerId) {
        playerService.deletePlayer(playerId);
    }
}
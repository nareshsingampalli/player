package com.example.player.service;

import com.example.player.model.*;
import java.util.*;
import com.example.player.repository.PlayerRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class PlayerH2Service implements PlayerRepository {
	@Autowired
	private JdbcTemplate db;

	@Override
	public ArrayList<Player> getPlayers() {
		List<Player> playerList = db.query("select * from team", new PlayerRowMapper());
		ArrayList<Player> players = new ArrayList<>(playerList);
		return players;
	}

	@Override
	public Player getPlayerById(int playerId) {
		try {
			return db.queryForObject("select * from team where playerid = ?", new PlayerRowMapper(), playerId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Player addPlayer(Player player) {
		db.update("insert into team(playername,jerseynumber,role) values (? , ?, ?)",
				player.getPlayerName(), player.getJerseyNumber(), player.getRole());

		Player savedPlayer = db.queryForObject(
				"select * from team where playername = ? and jerseynumber = ? and role = ?", new PlayerRowMapper(),
				player.getPlayerName(), player.getJerseyNumber(), player.getRole());
		return savedPlayer;
	}

	@Override
	public Player updatePlayer(Player player, int playerId) {
		if (player.getPlayerName() != null) {
			db.update("update team set playername=? where playerid = ?", player.getPlayerName(), playerId);
		}
		if (player.getJerseyNumber() != 0) {
			db.update("update team set jerseynumber=? where playerid = ?", player.getJerseyNumber(), playerId);
		}
		if (player.getRole() != null) {
			db.update("update team set role=? where playerid = ?", player.getRole(), playerId);
		}
		return getPlayerById(playerId);
	}

	@Override
	public void deletePlayer(int playerId) {
		db.update("delete from team where playerid = ?", playerId);
	}
}
/*
 * You can use the following import statements
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.jdbc.core.JdbcTemplate;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 * 
 */

// Write your code here
package com.example.player.service;

import com.example.player.model.PlayerRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;

import javax.validation.OverridesAttribute;

import com.example.player.repository.PlayerRepository;
import com.example.player.model.Player;

@Service 
public class PlayerH2Service implements PlayerRepository{

    @Autowired 
    private JdbcTemplate db;

    @Override 
    public ArrayList<Player> getPlayers(){
        ArrayList<Player> playerList = db.query( "select * from Player" , new PlayerRowMapper());
        ArrayList<Player> players = new ArrayList<>(playerList);
        return players;
    }
    
    @Override 
    public Player getPlayerById (int playerId){
        try {
            Player player = db.queryForObject("select * from Player where playerId = ?", new PlayerRowMapper());
            return player;
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        
    }
    @Override
    public Player addPlayer(Player player){
        db.update("insert into player(playerName, jerseyNumber, role) values (?, ?, ?)",player.getPlayerName(),player.getJerseyNumber(),player.getRole());
        Player savedPlayer = db.queryForObject("select * from book where playerName = ? and jerseyNumber = ? and role = ?", new PlayerRowMapper(), player.getPlayerName(),player.getJerseyNumber(),player.getRole());
        return savedPlayer;
    } 

    @Override 
    public  Player updatePlayer(int playerId,Player player){
        if(player.getPlayerName() != null){
            db.update ("update player set playerName = ? where id = ?", player.getPlayerName(),playerId);
        }
        /*if(player.getJerseyNumber() != null){
            db.update ("update player set jerseyNumber() = ? where id = ?", player.getJerseyNumber(),playerId);
        }*/
        if(player.getRole() != null){
            db.update ("update player set getRole() = ? where id = ?", player.getRole(),playerId);
        }
        return getPlayerById(playerId);
    } 

    @Override 
    public void deletePlayer(int playerId){
        db.update("delete from player where id = ?",playerId);
    }
}
package com.iamrobots.connectfour.database;

/**
 * Created by namrathamanjunatha on 1/28/18.
 */


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

// TODO: Have color saved as an int.

@Entity(tableName = "players", indices = {@Index(value = "player_name", unique = true)})
public class Player implements Comparable<Player> {
//Add fields if required
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "player_name")
    private String name;
    @ColumnInfo(name = "player_color")
    private int color;
    private int wins;
    private int losses;
    private int draws;

    public Player(String name, int color) {
        this.name = name;
        this.color = color;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }
    
    @Override
    public String toString() {
        return "[ " + name + ", Wins: " + wins + ", Losses: " + losses + ", Draws: " + draws + "]";
    }

    public int compareTo(@NonNull Player compare) {
        int compareScore = compare.getWins();

        return this.wins - compareScore;
    }
}


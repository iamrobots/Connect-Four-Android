package com.iamrobots.connectfour.PlayerSelection;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by namrathamanjunatha on 1/28/18.
 */

@Dao
public interface PlayerDetlsDao {

    @Query("Select * from players")
    List<PlayerDetls> getAll();

    @Insert
    void insertAll(PlayerDetls... users);

   // @Query("DELETE FROM players")
  //  public void deleteTable();
}

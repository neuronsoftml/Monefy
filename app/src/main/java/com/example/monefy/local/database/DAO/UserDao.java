package com.example.monefy.local.database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.monefy.local.database.model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userId)")
    List<User> loadAllByIds(int[] userId);

    @Query("SELECT * FROM user WHERE uid = :userId")
    User getUserById(int userId);

    @Query("SELECT COUNT(*) FROM user WHERE uid = :userId")
    int getUserCountById(int userId);

    @Insert
    void insert(User user);

    @Query("DELETE FROM user WHERE uid = :userId")
    void deleteUserById(int userId);

    @Delete
    void delete(User user);

}

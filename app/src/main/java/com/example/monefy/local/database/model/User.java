package com.example.monefy.local.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "autoLogin")
    public boolean autoLogin;

    public User(String email, String password, boolean autoLogin) {
        this.email = email;
        this.password = password;
        this.autoLogin = autoLogin;
    }

    public int getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAutoLogin() {
        return autoLogin;
    }
}

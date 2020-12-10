package com.hackedemist.app.Database;

public class UserData {

    String userId;
    String userName;
    String userCoins;
    String userPass;
    int hd_LevelTrack;

    public UserData() {
    }

    public UserData(String userId, String userName, String coins, String userPass, int hd_LevelTrack) {
        this.userId = userId;
        this.userName = userName;
        this.userCoins = coins;
        this.userPass = userPass;
        this.hd_LevelTrack = hd_LevelTrack;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserCoins() {
        return userCoins;
    }

    public String getUserPass() {
        return userPass;
    }

    public int getHd_LevelTrack() {
        return hd_LevelTrack;
    }
}

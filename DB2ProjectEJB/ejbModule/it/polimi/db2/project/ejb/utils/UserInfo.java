package it.polimi.db2.project.ejb.utils;

public class UserInfo {
    final private int id;
    final private String username;

    public UserInfo(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}

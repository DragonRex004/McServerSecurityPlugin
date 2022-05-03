package de.dragonrexx.mcserversecurityplugin.mongodb.objects;

public class PlayerAccount {

    private String uuid;
    private String username;
    private String password;
    private boolean isLogged;

    public PlayerAccount(String uuid, String username, String password, boolean isLogged) {
        this.uuid = uuid;
        this.username = username;
        this.password = password;
        this.isLogged = isLogged;
    }

    public boolean isLogged() {return isLogged;}

    public String getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLogged(boolean logged) {isLogged = logged;}
}

package com.example.landview.AppUser;

public class UserInfo {
    private String UID;
    private String avatar;
    private String username;

    public UserInfo(){

    }

    public UserInfo(String UID, String avatar, String username) {
        this.UID = UID;
        this.avatar = avatar;
        this.username = username;
    }

    public String getUID() {
        return UID;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUsername() {
        return username;
    }

    /*********************************************************/

    public void setUID(String UID) {
        this.UID = UID;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "UID='" + UID + '\'' +
                ", avatar='" + avatar + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}

package com.bawei.wangxueshi.myretrofit;

public class User {

    public User(String username, String password, String postkey) {
        this.username = username;
        this.password = password;
        this.postkey = postkey;
    }

    public String username;
    public String password;
    public String postkey;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPostkey() {
        return postkey;
    }

    public void setPostkey(String postkey) {
        this.postkey = postkey;
    }
}
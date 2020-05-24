package edu.uga.cs.finalproject;

public class User {
    private String username;
    private String password;
    private String email;
    private String whichRoomate;
    private String id;

    public User(){}
    public User(String id, String username, String password, String email, String whichRoomate){
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.whichRoomate = whichRoomate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWhichRoomate() {
        return whichRoomate;
    }

    public void setWhichRoomate(String whichRoomate) {
        this.whichRoomate = whichRoomate;
    }
}

package ec.com.pablorcruh.goodrecipes.model;

import java.util.List;

public class User {

    private String userName;

    private String email;

    private String password;

    private String token;

    private List<String> followers;

    public User(){

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String userName, String email, String password, List<String> followers, String token) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.followers = followers;
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public void setFollowers(List<String> followers) {
        this.followers = followers;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

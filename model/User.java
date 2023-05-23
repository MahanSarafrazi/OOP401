package model;

public class User {
    protected String userName ;
    protected String password;
    public String getUserName () {return userName ;}
    public String getPassword() {return password;}

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

}

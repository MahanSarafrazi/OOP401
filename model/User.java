package model;

public class User {
    protected String userName ;
    protected String pass ;
    public String getUserName () {return userName ;}
    public String getPass () {return pass ;}

    public User(String userName, String pass) {
        this.userName = userName;
        this.pass = pass;
    }
}

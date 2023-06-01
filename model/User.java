package model;

public class User {
    protected String userName ;
    protected String password;
    protected String restoreQuestion;
    protected String restoreAnswer;
    public String getUserName () {return userName ;}
    public String getPassword() {return password;}
    public String getRestoreQuestion() {return restoreQuestion;}
    public String getRestoreAnswer() {return restoreAnswer;}
    public void setRestoreQuestion(String restoreQuestion) {this.restoreQuestion = restoreQuestion;}
    public void setRestoreAnswer(String restoreAnswer) {this.restoreAnswer = restoreAnswer;}

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

}

package model;

public class Comment {
    private final User user;
    public User getUser() {return user;}
    private String comment;
    public String getComment() {return comment;}
    private final int ID ;
    public int getID() {return ID;}
    public boolean hasResponse;
    public boolean isResponse;
    private Comment response ;
    public Comment getResponse() {return response;}
    Comment(User user,String comment,boolean isResponse) {
        this.comment=comment;
        this.user=user;
        this.isResponse=isResponse;
        RandomIDGenerator randomIDGenerator = new RandomIDGenerator();
        this.ID = randomIDGenerator.getLastNumber();
        hasResponse=false;
    }
    public void editComment(String comment) {this.comment=comment;}
}

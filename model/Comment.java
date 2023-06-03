package model;

public class Comment {
    private User user;
    private String comment;
    private boolean hasResponse;
    private boolean isResponse;
    Comment(User user,String comment,boolean isResponse) {
        this.comment=comment;
        this.user=user;
        this.isResponse=isResponse;
        hasResponse=false;
    }
}

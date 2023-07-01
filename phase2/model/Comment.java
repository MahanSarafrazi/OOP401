package phase2.model;

public class Comment {
    private final User user;
    public User getUser() {return user;}
    private String comment;
    public String getComment() {return comment;}
    private final int ID ;
    public int getID() {return ID;}
    public boolean hasResponse;
    private Comment response;
    public Comment getResponse() {return response;}

    public void addResponse(User owner, String responseText) {
        hasResponse = true;
        this.response = new Comment(owner, responseText);
    }

    Comment(User user, String comment) {
        this.comment = comment;
        this.user = user;
        RandomIDGenerator randomIDGenerator = new RandomIDGenerator();
        this.ID = randomIDGenerator.getLastNumber();
        hasResponse = false;
    }
    public void editComment(String comment) {this.comment = comment;}
}


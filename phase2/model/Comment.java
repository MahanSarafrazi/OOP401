package phase2.model;

public class Comment {
    private final User user;
    public User getUser() {return user;}
    private String comment;
    public String getComment() {return comment;}
    private final int ID ;
    public int getID() {return ID;}
    public boolean hasResponse;
    private String response;
    private int responseID;
    public int getResponseID() {
        return responseID;
    }
    private String responderName;
    public String getResponderName() {return responderName;}
    public String  getResponse() {return response;}

    public void addResponse(User owner, String responseText) {
        hasResponse = true;
        response=responseText;
        RandomIDGenerator randomIDGenerator = new RandomIDGenerator();
        responseID = randomIDGenerator.getLastNumber();
        responderName=owner.getUserName();
    }

    Comment(User user, String comment) {
        this.comment = comment;
        this.user = user;
        RandomIDGenerator randomIDGenerator = new RandomIDGenerator();
        this.ID = randomIDGenerator.getLastNumber();
        hasResponse = false;
    }
    public void editComment(String comment) {this.comment = comment;}
    public void editResponse(String response) {this.response=response;}
}


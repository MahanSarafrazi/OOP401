package Phase1.model;

public class User {
    protected String userName ;
    protected String password;
    protected String restoreQuestion;
    protected String restoreAnswer;
    protected Restaurant activeRestaurant = null;
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
    public Restaurant getActiveRestaurant() {
        return activeRestaurant;
    }

    public boolean setActiveRestaurant(int ID) {
        for (Restaurant restaurant : RestaurantList.restaurants) {
            if(restaurant.getID() == ID) {
                activeRestaurant = restaurant;
                return true;
            }
        }
        return false;
    }
    public void setActiveRestaurant(Restaurant restaurant) {
        this.activeRestaurant = restaurant;
    }
    public void deActiveRestaurant() {activeRestaurant = null;}
}


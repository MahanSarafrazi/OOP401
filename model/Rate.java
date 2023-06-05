package model;

public class Rate {
    private User user;
    public User getUser() {
        return user;
    }
    private double rating;
    public double getRating() {
        return rating;
    }
    Rate(User user,double rating) {
        this.rating=rating;
        this.user=user;
    }
    public void editRating (double rating) {
        this.rating = rating;
    }
}

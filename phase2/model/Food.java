package phase2.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Food {
    private double price;
    private final FoodType type;
    public FoodType getType() {
        return type;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public double getPrice(){return price;}
    public double getDiscountedPrice() {
        if(!isDiscounted()) {
            discount = 0;
        }
        return price - price * (discount / 100);
    }
    private String name;
    public String getName(){return name;}
    private boolean activation;
    //this boolean for active or not active in order

    public void setActivation(boolean activation) {
        this.activation = activation;
    }
    public boolean getActivation(){return activation;}
    private final int ID;
    private Date expireDate = null;
    public int getID(){ return ID;}
    private double discount;
    private String restaurantName;
    public String getRestaurantName() {return restaurantName;}
    public Food(String name, double price, FoodType type,String restaurantName){
        this.name = name;
        this.price = price;
        this.discount = 0;
        this.type = type;
        this.restaurantName=restaurantName;
        this.activation = true;
        RandomIDGenerator randomIDGenerator = new RandomIDGenerator();
        this.ID = randomIDGenerator.getLastNumber();
        this.comments = new ArrayList<>();
        this.rates = new ArrayList<>();
    }
    public Food(String name, double price, FoodType type, int ID){
        this.name = name;
        this.price = price;
        this.discount = 0;
        this.type = type;
        this.activation = true;
        this.ID = ID;
        this.comments = new ArrayList<>();
        this.rates = new ArrayList<>();
    }
    public void setDiscount(double discount, int hours) {
        this.discount = discount;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, hours);
        Date expireDate = new Date();
        expireDate.setTime(calendar.getTimeInMillis());
        setExpireDate(expireDate);
    }
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
    public boolean isDiscounted() {
        return expireDate != null && new Date().getTime() <= expireDate.getTime();
    }
    public double getDiscount() {
        if(!isDiscounted()) {
            discount = 0;
        }
        return discount;
    }
    public void setName(String name) {
        this.name = name;
    }
    private final ArrayList<Comment> comments ;
    public void addComment(User user ,String comment) {comments.add(new Comment(user,comment));}
    public ArrayList<Comment> getComments() {return comments;}
    private final ArrayList<Rate> rates ;
    public ArrayList<Rate>  getRates() {return rates;}
    public void addRating(User user,double rating) {rates.add(new Rate(user,rating));}
}

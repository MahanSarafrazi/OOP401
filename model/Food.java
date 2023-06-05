package model;

import java.util.ArrayList;

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
    public double getDiscountedPrice() {return price - price * discount;}
    private String name;
    public String getName(){return name;}
    private boolean activation;
    //this boolean for active or not active in order

    public void setActivation(boolean activation) {
        this.activation = activation;
    }
    public boolean getActivation(){return activation;}
    private final int ID;
    public int getID(){ return ID;}
    private double discount;
     public Food( String name, double price, FoodType type){
        this.name = name;
        this.price = price;
        this.discount = 0;
        this.type = type;
        this.activation = true;
        RandomIDGenerator randomIDGenerator = new RandomIDGenerator();
        this.ID=randomIDGenerator.getLastNumber();
        this.comments = new ArrayList<>();
        this.rates=new ArrayList<>();
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
    public boolean hasDiscounted() {
        if(expireDate == null || new Date().getTime() > expireDate.getTime()) {
            return false;
        }
        return true;
    }
    public double getDiscount(){
        if(!hasDiscounted()) {
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

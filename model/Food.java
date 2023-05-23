package model;

public class Food {
    private double price;
    private String name;
    private long ID;
    private double discount;
     public Food( String name, double price){
        this.name=name;
        this.price=price;
        this.discount = 0 ;
    }
    public void setDiscount(double discount){ this.discount=discount;}






}

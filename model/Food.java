package model;

public class Food {
    private double price;
    public void setPrice(double price) {
        this.price = price;
    }
    public double getPrice(){return price;}
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName(){return name;}
    private boolean activation;
    //this boolean for active or not active in order

    public void setActivation(boolean activation) {
        this.activation = activation;
    }
    public boolean getActivation(){return activation;}

    private long ID;
    public void setID(long IDCode){ID=IDCode;}
    public long getID(){ return ID;}
    private double discount;
     public Food( String name, double price){
        this.name=name;
        this.price=price;
        this.discount = 0 ;
    }
    public void setDiscount(double discount){ this.discount=discount;

     }
    public double getDiscount(){return discount;}






}

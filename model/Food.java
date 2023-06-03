package model;

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
    }
    public void setDiscount(double discount){ this.discount=discount;}
    public double getDiscount(){return discount;}
    public void setName(String name) {
         this.name = name;
    }
}

package model;

public class RestaurantOwner extends User {
    public static RestaurantOwner newRestaurantOwner (String userName , String  pass) {
        RestaurantOwner restaurantOwner = new RestaurantOwner (userName , pass);
        UserList.getUserListInstance().getRestaurantOwners().add(restaurantOwner);
        return restaurantOwner ;
    }
    private RestaurantOwner (String userName , String pass) {super(userName, pass);}
}

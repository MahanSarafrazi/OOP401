package model;

public class RestaurantOwner extends User {
    public static void newDeliverer (String userName , String  pass) {
        RestaurantOwner restaurantOwner = new RestaurantOwner (userName , pass);
        userList.restaurantOwners.add(restaurantOwner);
    }
    private RestaurantOwner (String userName , String pass) {
        this.userName = userName ;
        this.pass = pass ;
    }
    public RestaurantOwner getRestaurantOwner (String pass) {
        for (RestaurantOwner restaurantOwner : userList.restaurantOwners)
            if (restaurantOwner.pass.equals(pass))
                return restaurantOwner;
        return null;
    }
}

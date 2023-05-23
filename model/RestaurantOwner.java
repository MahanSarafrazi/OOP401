package model;

public class RestaurantOwner extends User {
    public static void newDeliverer (String userName , String  pass) {
        RestaurantOwner restaurantOwner = new RestaurantOwner (userName , pass);
        userList.restaurantOwners.add(restaurantOwner);
    }
    private RestaurantOwner (String userName , String pass) {super(userName, pass);}
    public RestaurantOwner getRestaurantOwnerByUserName (String userName) {
        for (RestaurantOwner restaurantOwner : userList.restaurantOwners)
            if (restaurantOwner.userName.equals(userName))
                return restaurantOwner;
        return null;
    }
}

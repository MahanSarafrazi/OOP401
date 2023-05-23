package model;

public class RestaurantOwner extends User {
    public static RestaurantOwner newDeliverer (String userName , String  pass) {
        RestaurantOwner restaurantOwner = new RestaurantOwner (userName , pass);
        userList.restaurantOwners.add(restaurantOwner);
        return restaurantOwner ;
    }
    private RestaurantOwner (String userName , String pass) {super(userName, pass);}
    public static RestaurantOwner getRestaurantOwnerByUserName (String userName) {
        for (RestaurantOwner restaurantOwner : userList.restaurantOwners)
            if (restaurantOwner.userName.equals(userName))
                return restaurantOwner;
        return null;
    }
}

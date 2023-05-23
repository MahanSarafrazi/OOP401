package model;

public class RestaurantOwner extends User {
    public static RestaurantOwner newRestaurantOwner (String userName , String  pass) {
        RestaurantOwner restaurantOwner = new RestaurantOwner (userName , pass);
        UserList.getUserListInstance().getRestaurantOwners().add(restaurantOwner);
        return restaurantOwner ;
    }
    private RestaurantOwner (String userName , String pass) {super(userName, pass);}
    public static RestaurantOwner getRestaurantOwnerByUserName (String userName) {
        for (RestaurantOwner restaurantOwner : UserList.getUserListInstance().getRestaurantOwners())
            if (restaurantOwner.userName.equals(userName))
                return restaurantOwner;
        return null;
    }
}

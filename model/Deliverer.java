package model;

public class Deliverer extends User {
    public static Deliverer newDeliverer (String userName , String  pass) {
        Deliverer deliverer = new Deliverer (userName , pass);
        userList.deliverers.add(deliverer);
        return deliverer ;
    }
    private Deliverer (String userName , String pass) {super(userName, pass);}
    public static Deliverer getDelivererByUserName (String userName) {
        for (Deliverer deliverer : userList.deliverers)
            if (deliverer.userName.equals(userName))
                return deliverer;
        return null;
    }
}

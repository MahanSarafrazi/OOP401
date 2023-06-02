package model;

public class Deliverer extends User {
    public static Deliverer newDeliverer (String userName , String  pass) {
        Deliverer deliverer = new Deliverer (userName , pass);
        UserList.getUserListInstance().getDeliverers().add(deliverer);
        return deliverer ;
    }
    private Deliverer (String userName , String pass) {super(userName, pass);}
    public static Deliverer getDelivererByUserName (String userName) {
        for (Deliverer deliverer : UserList.getUserListInstance().getDeliverers())
            if (deliverer.userName.equals(userName))
                return deliverer;
        return null;
    }
}

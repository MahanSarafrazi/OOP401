package model;

public class Deliverer extends User {
    public static void newDeliverer (String userName , String  pass) {
        Deliverer deliverer = new Deliverer (userName , pass);
        userList.deliverers.add(deliverer);
    }
    private Deliverer (String userName , String pass) {super(userName, pass);}
    public Deliverer getDelivererByUserName (String userName) {
        for (Deliverer deliverer : userList.deliverers)
            if (deliverer.userName.equals(userName))
                return deliverer;
        return null;
    }
}

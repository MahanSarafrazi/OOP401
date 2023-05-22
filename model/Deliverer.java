package model;

public class Deliverer extends User {
    public static void newDeliverer (String userName , String  pass) {
        Deliverer deliverer = new Deliverer (userName , pass);
        userList.deliverers.add(deliverer);
    }
    private Deliverer (String userName , String pass) {
        this.userName = userName ;
        this.pass = pass ;
    }
    public Deliverer getDeliverer (String pass) {
        for (Deliverer deliverer : userList.deliverers)
            if (deliverer.pass.equals(pass))
                return deliverer;
        return null;
    }
}

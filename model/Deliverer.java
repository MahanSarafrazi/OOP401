package model;

public class Deliverer extends User {
    public static Deliverer newDeliverer (String userName , String  pass) {
        Deliverer deliverer = new Deliverer (userName , pass);
        UserList.getUserListInstance().getDeliverers().add(deliverer);
        return deliverer ;
    }
    private Deliverer (String userName , String pass) {super(userName, pass);}
}

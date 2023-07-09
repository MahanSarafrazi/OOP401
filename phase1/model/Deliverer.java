package phase1.model;

import phase1.view.OrderStatus;

public class Deliverer extends User {
    public static Deliverer newDeliverer (String userName , String  pass) {
        Deliverer deliverer = new Deliverer (userName , pass);
        UserList.getUserListInstance().getDeliverers().add(deliverer);
        return deliverer ;
    }
    private Deliverer (String userName , String pass) {super(userName, pass);order = null;}
    public Order order;
    private int location;
    public boolean hasOrder() {
        if (order == null)
            return false;
        order.correctOrderStatus();
        if (order.orderStatus == OrderStatus.SENT) {
            order = null;
            return false;
        }
        return true;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }
}

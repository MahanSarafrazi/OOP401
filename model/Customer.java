package model;

public class Customer extends User {
    private String userName ;
    private String pass ;
    @Override
    public void newUser(String userName , String pass) {
        User user = new Customer (userName , pass);
        userList.setNewUser(Types.CUSTOMER , user);
    }
    private Customer (String userName , String pass) {}
}

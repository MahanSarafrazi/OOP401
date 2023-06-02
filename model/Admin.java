package model;

public class Admin extends User {
    public static Admin newAdmin (String userName , String  pass) {
        Admin admin = new Admin (userName , pass);
        UserList.getUserListInstance().getAdmins().add(admin);
        return admin ;
    }
    private Admin (String userName , String pass) {super(userName, pass);}
    public static Admin getAdminByUserName (String userName) {
        for (Admin admin : UserList.getUserListInstance().getAdmins())
            if (admin.userName.equals(userName))
                return admin;
        return null;
    }
}

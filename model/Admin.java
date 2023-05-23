package model;

public class Admin extends User {
    public static void newAdmin (String userName , String  pass) {
        Admin admin = new Admin (userName , pass);
        userList.admins.add(admin);
    }
    private Admin (String userName , String pass) {super(userName, pass);}
    public Admin getAdminByUserName (String userName) {
        for (Admin admin : userList.admins)
            if (admin.userName.equals(userName))
                return admin;
        return null;
    }
}

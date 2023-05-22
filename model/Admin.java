package model;

public class Admin extends User {
    public static void newAdmin (String userName , String  pass) {
        Admin admin = new Admin (userName , pass);
        userList.admins.add(admin);
    }
    private Admin (String userName , String pass) {
        this.userName = userName ;
        this.pass = pass ;
    }
    public Admin getAdmin (String pass) {
        for (Admin admin : userList.admins)
            if (admin.pass.equals(pass))
                return admin;
        return null;
    }
}

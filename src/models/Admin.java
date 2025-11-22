package models;

public class Admin extends User {
     public Admin(String username, String email, String passwordHash) {
         super(username, email, passwordHash, "Admin");
     }

        public void manageUsers() {
            // Implementation for managing users
        }


}

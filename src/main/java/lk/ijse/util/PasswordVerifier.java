package lk.ijse.util;

public class PasswordVerifier {
    public static boolean verifyPassword(String password,String hashedPassword) {
        return BCrypt.checkpw(password,hashedPassword);
    }
}

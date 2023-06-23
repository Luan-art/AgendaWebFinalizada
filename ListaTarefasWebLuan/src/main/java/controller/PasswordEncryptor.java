package controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptor {
    
	public static String encryptPassword(String password) {
        try {
            // Obter instância do algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Converter a senha em bytes
            byte[] encodedPassword = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Converter os bytes em uma representação hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedPassword) {
                String hex = String.format("%02x", b);
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Tratar exceção caso o algoritmo não seja suportado
            e.printStackTrace();
        }

        return null;
    }

  
}
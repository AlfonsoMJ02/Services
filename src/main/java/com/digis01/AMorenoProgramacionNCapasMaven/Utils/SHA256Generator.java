package com.digis01.AMorenoProgramacionNCapasMaven.Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


public class SHA256Generator {
    public static String generateKey(String texto){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            byte[] hash = digest.digest(texto.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {

                String hex = Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

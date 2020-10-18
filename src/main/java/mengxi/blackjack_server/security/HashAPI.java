package mengxi.blackjack_server.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashAPI {
    public static String encode(String passwordToHash, String algorithm, byte[] salt) throws NoSuchAlgorithmException {

        // Create MessageDigest instance for alg
        MessageDigest md = MessageDigest.getInstance(algorithm);
        // Add salt and password bytes to digest
        if (salt != null)
            md.update(salt);
        // Get the hash's bytes
        byte[] bytes = md.digest(passwordToHash.getBytes());
        // This bytes[] has bytes in decimal format;
        // Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        // Get complete hashed password in hex format
        return sb.toString();
    }

    public static byte[] createSalt() throws NoSuchAlgorithmException {

        // Always use a SecureRandom generator
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        // Create array for salt
        byte[] salt = new byte[16];
        // Get a random salt
        sr.nextBytes(salt);
        // return salt
        return salt;
    }

    public static String getSalt(byte[] salt) throws UnsupportedEncodingException {
        return new String(salt, "IBM01140");
    }

    public static byte[] getSalt(String salt) throws UnsupportedEncodingException {
        return salt.getBytes("IBM01140");
    }
}

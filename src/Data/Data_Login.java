package Data;

import Koneksi.connect;
import admin.Admin;
import java.security.Key;
import java.sql.*;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

/*
    @author llawl
 */
public class Data_Login {
    private static final String ALGO = "AES";
    private static final byte[] keyValue = new byte[16];
    public static String encrypt(String pwd) {
        String encodedPwd = "";
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(pwd.getBytes());
            encodedPwd = Base64.getEncoder().encodeToString(encVal);

        } catch (Exception e) {

            e.printStackTrace();
        }
        return encodedPwd;
    }

    /**
     * Decrypt a string with AES encryption algorithm.
     *
     * @param encryptedData the data to be decrypted
     * @return the decrypted string
     */
    public static String decrypt(String encryptedData) {
        String decodedPWD = "";
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
            byte[] decValue = c.doFinal(decordedValue);
            decodedPWD = new String(decValue);

        } catch (Exception e) {

        }
        return decodedPWD;
    }

    /**
     * Generate a new encryption key.
     */
    private static Key generateKey() {
        SecretKeySpec key = new SecretKeySpec(keyValue, ALGO);
        return key;
    }
}

/*
    let's test the example in main method
    public static void main(String[]args) {
        System.out.println(encrypt(params));
        System.out.println(decrypt(params)));
    }
*/

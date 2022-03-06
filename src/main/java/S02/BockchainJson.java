package S02;

import com.google.gson.GsonBuilder;
public class BockchainJson {
    public static void main(String[] args) {
        Blockchain.getInstance();
        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(Blockchain.getInstance()));
    }
}

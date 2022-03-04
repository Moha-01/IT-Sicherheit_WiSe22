import org.json.JSONObject;

import java.io.FileWriter;

public class JSONConfiguration {
    public static void main(String... args) {
        JSONConfiguration jsonConfiguration = new JSONConfiguration();
        jsonConfiguration.build("scientific");
    }

    public void build(String calculatorType) {
        try {
            JSONObject jsonObject = new JSONObject();

            switch (calculatorType) {
                case "normal" -> jsonObject.put("calculator_type", "normal");
                case "extended" -> jsonObject.put("calculator_type", "extended");
                case "scientific" -> jsonObject.put("calculator_type", "scientific");
                default -> jsonObject.put("calculator_type", "invalid");
            }

            FileWriter fileWriter = new FileWriter("calculator.json");
            fileWriter.write(jsonObject.toString());
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
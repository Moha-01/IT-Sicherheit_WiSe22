import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;

public enum Configuration {
    instance;

    public final String userDirectory = System.getProperty("user.dir");
    public final String fileSeparator = System.getProperty("file.separator");
    public final String pathToJavaArchive = userDirectory + fileSeparator + getCalculatorType() + fileSeparator + "jar" + fileSeparator + "calculator.jar";

    public CalculatorType getCalculatorType() {
        try {
            FileReader fileReader = new FileReader("calculator.json");
            JSONTokener jsonTokener = new JSONTokener(fileReader);
            JSONObject jsonObject = new JSONObject(jsonTokener);
            fileReader.close();
            return CalculatorType.valueOf(jsonObject.getString("calculator_type").toUpperCase());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
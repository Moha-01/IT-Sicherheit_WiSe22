import java.io.FileInputStream;
import java.util.Properties;

public enum Configuration {
    instance;

    public final String userDirectory = System.getProperty("user.dir");
    public final String fileSeparator = System.getProperty("file.separator");
    public final String pathToJar = userDirectory + fileSeparator + getCalculatorType() + fileSeparator + "jar" + fileSeparator + "calculator.jar";

    public CalculatorType getCalculatorType() {
        try {
            Properties properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream(userDirectory + fileSeparator + "calculator.props");
            properties.load(fileInputStream);
            fileInputStream.close();

            return switch (properties.getProperty("calculatorType")) {
                case "normal" -> CalculatorType.NORMAL;
                case "extended" -> CalculatorType.EXTENDED;
                case "scientific" -> CalculatorType.SCIENTIFIC;
                default -> null;
            };
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
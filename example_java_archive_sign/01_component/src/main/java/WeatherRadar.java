public class WeatherRadar {
    // static instance
    private static final WeatherRadar instance = new WeatherRadar();

    // port
    public Port port;
    private boolean isOn = false;

    // private constructor
    private WeatherRadar() {
        port = new Port();
    }

    // static method getInstance
    public static WeatherRadar getInstance() {
        return instance;
    }

    // inner methods
    public String innerVersion() {
        String manufacturer = "<student name 01> / <student name 02>";
        String type = "team <id>";
        String id = "<student id 01> / <student id 02>";
        return "WeatherRadar // " + manufacturer + " - " + type + " - " + id;
    }

    public boolean innerOn() {
        isOn = true;
        return isOn;
    }

    public boolean innerOff() {
        isOn = false;
        return isOn;
    }

    public boolean innerScan(String environment) {
        return environment.contains("bird");
    }

    // inner class port
    public class Port implements IWeatherRadar {
        public String version() {
            return innerVersion();
        }

        public boolean on() {
            return innerOn();
        }

        public boolean off() {
            return innerOff();
        }

        public boolean scan(String environment) {
            return innerScan(environment);
        }
    }
}
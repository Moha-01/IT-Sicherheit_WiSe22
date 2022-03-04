import java.util.Arrays;

public class MemoryStick {
    // static instance
    private static final MemoryStick instance = new MemoryStick();

    // define port
    public Port port;

    private MemoryStick() {
        port = new Port();
    }

    public static MemoryStick getInstance() {
        return instance;
    }

    public String getVersion() {
        return "MemoryStick 32";
    }

    private int calculateCapacity() {
        return (32 * 1024 * 1024);
    }

    private boolean setValues(int[] values) {
        System.out.println("values   | " + Arrays.toString(values));
        return true;
    }

    public class Port implements IMemoryStick {
        public void version() {
            System.out.println(getVersion() + "\n");
        }

        public int getCapacity() {
            return calculateCapacity();
        }

        public boolean store(int[] values) {
            return setValues(values);
        }
    }
}
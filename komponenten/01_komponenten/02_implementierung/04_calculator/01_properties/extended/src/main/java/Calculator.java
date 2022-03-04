import java.lang.reflect.Method;

public class Calculator {
    private static final Calculator instance = new Calculator();
    public Port port;

    private Calculator() {
        port = new Port();
    }

    public static Calculator getInstance() {
        return instance;
    }

    public String innerVersion() {
        return "calculator | extended";
    }

    public int innerAdd(int a, int b) {
        return a + b;
    }

    public int innerSubtract(int a, int b) {
        return a - b;
    }

    public int innerMultiply(int a, int b) {
        return a * b;
    }

    public class Port implements ICalculator {
        private final Method[] methods = getClass().getMethods();

        public String version() {
            return innerVersion();
        }

        public int add(int a, int b) {
            return innerAdd(a, b);
        }

        public int subtract(int a, int b) {
            return innerSubtract(a, b);
        }

        public int multiply(int a, int b) {
            return innerMultiply(a, b);
        }

        public void listMethods() {
            System.out.println("--- public methods for " + getClass().getName());

            for (Method method : methods) {
                if (!method.toString().contains("Object") && !method.toString().contains("list")) {
                    System.out.println(method);
                }
            }

            System.out.println("---");
        }
    }
}
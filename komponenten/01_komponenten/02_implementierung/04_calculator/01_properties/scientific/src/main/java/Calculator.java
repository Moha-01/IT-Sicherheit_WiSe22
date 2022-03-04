import java.lang.reflect.Method;
import java.math.BigInteger;

public class Calculator {
    private static final Calculator instance = new Calculator();
    public Port port;

    private Calculator() {
        port = new Port();
    }

    public static Calculator getInstance() {
        return instance;
    }

    public String innerMethodGetVersion() {
        return "calculator | scientific";
    }

    public int innerMethodAdd(int a, int b) {
        return a + b;
    }

    public int innerMethodSubtract(int a, int b) {
        return a - b;
    }

    public int innerMethodMultiply(int a, int b) {
        return a * b;
    }

    public boolean innerMethodIsPrime(String string) {
        return MillerRabin.isProbablePrime(new BigInteger(string), 40);
    }

    public class Port implements ICalculator {
        private final Method[] methods = getClass().getMethods();

        public String getVersion() {
            return innerMethodGetVersion();
        }

        public int add(int a, int b) {
            return innerMethodAdd(a, b);
        }

        public int subtract(int a, int b) {
            return innerMethodSubtract(a, b);
        }

        public int multiply(int a, int b) {
            return innerMethodMultiply(a, b);
        }

        public boolean isPrime(String string) {
            return innerMethodIsPrime(string);
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
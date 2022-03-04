public interface ICalculator {
    String getVersion();

    int add(int a, int b);

    int subtract(int a, int b);

    int multiply(int a, int b);

    boolean isPrime(String string);
}
import java.math.BigInteger;

public class MillerRabin {
    private static final BigInteger ZERO = BigInteger.ZERO;
    private static final BigInteger ONE = BigInteger.ONE;
    private static final BigInteger TWO = new BigInteger("2");
    private static final BigInteger THREE = new BigInteger("3");
    private static final MersenneTwister randomGenerator = new MersenneTwister(System.currentTimeMillis());

    public static boolean isProbablePrime(BigInteger n, int k) {
        if (n.compareTo(THREE) < 0)
            return true;

        int s = 0;
        BigInteger d = n.subtract(ONE);

        while (d.mod(TWO).equals(ZERO)) {
            s++;
            d = d.divide(TWO);
        }

        for (int i = 0; i < k; i++) {
            BigInteger a = uniformRandom(n.subtract(ONE));
            BigInteger x = a.modPow(d, n);

            if (x.equals(ONE) || x.equals(n.subtract(ONE)))
                continue;

            int r = 1;

            for (; r < s; r++) {
                x = x.modPow(TWO, n);
                if (x.equals(ONE))
                    return false;
                if (x.equals(n.subtract(ONE)))
                    break;
            }

            if (r == s)
                return false;
        }

        return true;
    }

    private static BigInteger uniformRandom(BigInteger top) {
        BigInteger result;

        do {
            result = new BigInteger(top.bitLength(), randomGenerator);
        } while (result.compareTo(MillerRabin.TWO) < 0 || result.compareTo(top) > 0);

        return result;
    }
}
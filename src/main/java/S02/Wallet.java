package S02;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.*;

import static S02.StringUtility.blueOutput;

public class Wallet {
    public HashMap<String, TransactionOut> utx0Map = new HashMap<>();
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public Wallet() {
        generateKeyPair();
    }

    public void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");

            keyGen.initialize(ecSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();

            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public double getBalance() {
        double total = 0;

        for (Map.Entry<String, TransactionOut> item : Blockchain.getInstance().getUtx0Map().entrySet()) {
            TransactionOut utx0 = item.getValue();
            if (utx0.isMine(publicKey)) {
                utx0Map.put(utx0.getID(), utx0);
                total += utx0.getValue();
            }
        }

        return total;
    }

    public Transaction sendFunds(PublicKey recipient, double value) {
        if (getBalance() < value) {
            System.out.println(blueOutput("#not enough funds to send transaction - transaction discarded"));
            return null;
        }

        ArrayList<TransactionIn> inputs = new ArrayList<>();

        double total = 0;
        for (Map.Entry<String, TransactionOut> item : utx0Map.entrySet()) {
            TransactionOut utx0 = item.getValue();
            total += utx0.getValue();
            inputs.add(new TransactionIn(utx0.getID()));
            if (total > value) {
                break;
            }
        }

        Transaction transaction = new Transaction(publicKey, recipient, value, inputs);
        transaction.generateSignature(privateKey);

        for (TransactionIn input : inputs) {
            utx0Map.remove(input.getId());
        }

        return transaction;
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }
    public PublicKey getPublicKey() {
        return this.publicKey;
    }
}
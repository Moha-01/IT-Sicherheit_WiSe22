package S02;

import java.security.*;
import java.util.ArrayList;


public class Transaction {

    private final PublicKey sender;
    private final PublicKey recipient;
    private final double value;
    private final ArrayList<TransactionOut> outputs = new ArrayList<>();
    private final ArrayList<TransactionIn> inputs;
    private String id;
    private byte[] signature;

    public Transaction(PublicKey from, PublicKey to, double value, ArrayList<TransactionIn> inputs) {
        this.sender = from;
        this.recipient = to;
        this.value = value;
        this.inputs = inputs;
    }

    private String calculateHash() {
        Blockchain.getInstance().incrementTransactionSequence();
        return StringUtility.useSha256(StringUtility.getStringFromKey(sender) + StringUtility.getStringFromKey(recipient)
                + value + Blockchain.getInstance().getTransactionSequence());
    }

    public void generateSignature(PrivateKey privateKey) {
        String data = StringUtility.getStringFromKey(sender) + StringUtility.getStringFromKey(recipient) + value;
        signature = StringUtility.applyECDSASig(privateKey, data);
    }

    public boolean verifySignature() {
        String data = StringUtility.getStringFromKey(sender) + StringUtility.getStringFromKey(recipient) + value;
        return !StringUtility.verifyECDSASig(sender, data, signature);

    }

    public boolean processTransaction() {
        if (verifySignature()) {
            System.out.println("#transaction signature failed to verify");
            return false;
        }

        for (TransactionIn i : inputs) {
            i.setUtx0(Blockchain.getInstance().getUtxMap().get(i.getId()));
        }

        if (getInputsValue() <= 0) {
            System.out.println("#transaction input to small | " + getInputsValue());
            return false;
        }

        double leftOver = getInputsValue() - value;
        id = calculateHash();
        outputs.add(new TransactionOut(recipient, value, id));
        outputs.add(new TransactionOut(sender, leftOver, id));

        for (TransactionOut o : outputs) {
            Blockchain.getInstance().getUtxMap().put(o.getID(), o);
        }

        for (TransactionIn i : inputs) {
            if (i.getUTX0() == null) {
                continue;
            }
            Blockchain.getInstance().getUtxMap().remove(i.getUTX0().getID());
        }

        return true;
    }

    public double getInputsValue() {
        double total = 0;

        for (TransactionIn i : inputs) {
            if (i.getUTX0() == null) {
                continue;
            }
            total += i.getUTX0().getValue();
        }

        return total;
    }

    public double getOutputsValue() {
        double total = 0;

        for (TransactionOut o : outputs) {
            total += o.getValue();
        }

        return total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PublicKey getSender() {
        return sender;
    }

    public PublicKey getRecipient() {
        return recipient;
    }

    public double getValue() {
        return value;
    }

    public ArrayList<TransactionIn> getInputs() {
        return inputs;
    }

    public ArrayList<TransactionOut> getOutputs() {
        return outputs;
    }

}
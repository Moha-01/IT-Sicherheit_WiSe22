package S02;

import java.security.PublicKey;

public class TransactionOut {
    private final String id;
    private final PublicKey recipient;
    private final double value;

    public TransactionOut(PublicKey recipient, double value, String parentTransactionId) {
        this.recipient = recipient;
        this.value = value;
        id = StringUtility.applySha256(StringUtility.getStringFromKey(recipient) + value + parentTransactionId);
    }

    public boolean isMine(PublicKey publicKey) {
        if (publicKey == null || recipient == null) return false;
        return StringUtility.getStringFromKey(publicKey).equals(StringUtility.getStringFromKey(recipient));
    }
    public String getID() {return id;}
    public PublicKey getRecipient() {return recipient;}
    public double getValue() {return value;}
}
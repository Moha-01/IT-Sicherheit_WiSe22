package S02;

public class TransactionIn {
    private final String id;
    private TransactionOut utx0;

    public TransactionIn(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public TransactionOut getUTX0() {
        return utx0;
    }
    public void setUtx0(TransactionOut utx0) {
        this.utx0 = utx0;
    }
}
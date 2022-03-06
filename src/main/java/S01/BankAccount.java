package S01;

public class BankAccount extends Account{


    public BankAccount(int CreditInBank) {
        super(CreditInBank);

    }

    public void takeOff(double amount) {
        super.setCredit(super.getCredit() - amount);
    }

}

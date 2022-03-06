package S01;

public class BankAccount extends Account{


    public BankAccount(int bankCredit) {
        super(bankCredit);

    }

    public void takeOff(double amount) {
        super.setCredit(super.getCredit() - amount);
    }

}

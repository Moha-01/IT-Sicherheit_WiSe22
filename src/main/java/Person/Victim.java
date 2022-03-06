package Person;

import S01.BankAccount;

public class Victim extends Person{

    private BankAccount bankAccount;

    public Victim(int bankCredit, int walletCredit){
        super();
        bankAccount = new BankAccount(bankCredit);
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }
}

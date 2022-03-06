package Person;

import S01.BankAccount;

public class Victim extends Person{

    private BankAccount bankAccount;

    public Victim(int CreditInBank, double CreditInWallet){
        super();
        bankAccount = new BankAccount(CreditInBank);
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }
}

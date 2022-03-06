package S01;

public class Account {

    private double Credit;


    public Account(int CreditInBank){
        this.Credit = CreditInBank;
    }

    public double getCredit() {
        return Credit;
    }

    public void setCredit(double credit) {
        this.Credit = credit;
    }
}

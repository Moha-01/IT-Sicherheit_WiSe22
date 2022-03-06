package S01;

public class Account {

    private double credit;


    public Account(int bankCredit){
        this.credit = bankCredit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }
}

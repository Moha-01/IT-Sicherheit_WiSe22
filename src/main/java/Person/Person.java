package Person;

import S02.Wallet;

public class Person {

    Wallet wallet;
    String name;

    public Person(){
        this.wallet = new Wallet();
    }

    public Person(String name){
        this.wallet = new Wallet();
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String getName() {
        return name;
    }

    public Wallet getWallet() {
        return wallet;
    }
}

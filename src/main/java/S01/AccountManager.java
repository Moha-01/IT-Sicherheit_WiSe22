package S01;

import Person.Attacker;
import Person.Victim;

public class AccountManager {

    public static String show(Victim victim, Attacker attacker, String stringArr){
        if (stringArr.equals("balance")){
            return "Your Amount: " + victim.getBankAccount().getCredit() + "€ and " + victim.getWallet().getBalance() + "BTC.";
        }else{
            return attacker.getWallet().getPublicKey().toString();
        }
    }

}

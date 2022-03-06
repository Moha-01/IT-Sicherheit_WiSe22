package S01;

import Person.Attacker;
import Person.Participant;
import Person.Victim;
import S02.Block;
import S02.Miner;
import S02.Transaction;
import S02.Wallet;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


public class ConsoleApplication {


    private static Block previousBlock;

    static Victim ClueLess = new Victim(5000, 0);
    static Attacker Ed = new Attacker(561098);
    static Participant SatoshiNakamoto = new Participant(1);
    static Gson gson;
    private static List<Miner> miners = new ArrayList<>();

    public static void main(String[] args) {


        miners.add(new Miner("Eva"));
        miners.add(new Miner("Sam"));
        miners.add(new Miner("Bob"));



        String inputString;

        do {

            System.out.println("Which Function do you want to use? \n To Exit type: \" exit \" ");
            Scanner inputScanner = new Scanner(System.in);
            inputString = inputScanner.nextLine();

            String[] stringArr = inputString.split(" ");
            switch (stringArr[0]){
                case "launch" -> CryptoManager.launchEn();
                case "exchange" -> exchange(ClueLess.getWallet(), Float.parseFloat(stringArr[1]));
                case "show" -> System.out.println("BTC Wallet: " + ClueLess.getWallet().getBalance() + "BTC \n Bankaccount: " + ClueLess.getBankAccount().getCredit() + "€");
                case "pay" -> pay(CryptoManager.getTimerTask().getPrice());
                case "check" -> check(CryptoManager.getTimerTask().getPrice());
                case "decrypt" -> CryptoManager.launchDec();
                case "blockchain" -> {
                    for (int i = 0; i < Configuration.instance.blockchain.size(); i++){
                        System.out.println(Configuration.instance.blockchain.get(i).getHash());
                    }
                }
                case "exit" -> {
                    System.out.println("----------- Program Exited -----------");
                    System.exit(0);
                }
            }



        }while(!inputString.equals("exit"));

    }

    public static void check(float amount){
        if(Ed.getWallet().getBalance() < amount){
            System.out.println("so faar only " + Ed.getWallet().getBalance() + "BTC recieved. There will be a total of " + amount + "BTC required");
            return;
        }else {
            System.out.println("Payment of the " + amount + "BTC successful.");
            if (CryptoManager.getTimerTask().isAllowToDecrypt()){
                CryptoManager.launchDec();
            }else{
                System.out.println("You have paid too late! Bad Luck!");
            }
        }
    }


    public static boolean exchange(Wallet wallet, float amount){
        System.out.println("exchange " + amount + "BTC selected");
        if(SatoshiNakamoto.getWallet().getBalance() < amount){
            System.out.println("Exchange not possible because SatoshiNakamoto has to little BTC");
            return false;
        }
        if(ClueLess.getBankAccount().getCredit() < (amount/0.000019)){
            System.out.println("Exchange not possible! Clueless has too Little EURO. There will be: " + (amount/0.000019) + "€ required");
            return false;
        }

        ClueLess.getBankAccount().takeOff((amount/0.000019));

        Transaction exchangeTransaction = SatoshiNakamoto.getWallet().sendFunds(wallet.getPublicKey(), amount);
        addTransaction(exchangeTransaction);
        return true;
    }

    public static void addTransaction(Transaction transaction){
        Block newBlock = new Block(previousBlock.getHash());
        newBlock.addTransaction(transaction);
        addBlock(newBlock);

    }

    private static void addBlock(Block newBlock) {
        int randomMinerIndex = ThreadLocalRandom.current().nextInt(miners.size()) % miners.size();
        Miner miner = miners.get(randomMinerIndex);
        newBlock.mineBlock(Configuration.instance.difficulty, miner);
        Configuration.instance.blockchain.add(newBlock);

        try{
            createJSON();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void pay(float amount){
        if (ClueLess.getWallet().getBalance() >= amount){
            Block block1 = new Block(previousBlock.getHash());
            System.out.println("Pay " + amount + " BTC to Ed");
            block1.addTransaction(ClueLess.getWallet().sendFunds(Ed.getWallet().getPublicKey(), amount));
            addBlock(block1);

            System.out.println("Wallet Victim balance: " + ClueLess.getWallet().getBalance());
            System.out.println("Wallet Attacker balance: " + Ed.getWallet().getBalance());

        }else{
            System.out.println("Not Enough BTC for Transaction");
        }
    }

    public static void createJSON() throws IOException{
        String json = gson.toJson(previousBlock);
        File file;
        FileWriter fw = new FileWriter(Configuration.instance.pathOfJson, true);

        try{
            fw.write(json.toString());
            fw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }




}

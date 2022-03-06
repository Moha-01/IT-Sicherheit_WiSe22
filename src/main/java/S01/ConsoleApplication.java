package S01;

import Person.Attacker;
import Person.Involver;
import Person.Victim;
import S02.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    static Attacker Ed = new Attacker(600000);
    static Involver SatoshiNakamoto = new Involver(10);
    static Gson gson;
    private static List<Miner> miners = new ArrayList<>();

    public static void main(String[] args) {

        miners.add(new Miner("Eva"));
        miners.add(new Miner("Sam"));
        miners.add(new Miner("Bob"));

        String UserInput;

        System.out.println("Hello Clue Less\n Welcome to the Terminal\n To exit the Terminal please type (exit)\n\n");
        do {
            System.out.print("$> ");
            Scanner inputScanner = new Scanner(System.in);
            UserInput = inputScanner.nextLine();

            String[] Params = UserInput.split(" ");

            switch (Params[0]){

                case "launch" -> {

                    if(Params[1].equals("http://www.trust-me.mcg/report.jar")){
                        CryptoManager.startEncrypting();
                    }
                    else{
                        System.out.println("Unknown Link!!!");
                    }
                }
                case "exchange" -> exchange(ClueLess.getWallet(), Float.parseFloat(Params[1]));

                case "show" -> {
                    if(Params[1].equals("balance")){
                        System.out.println("BTC Wallet: " + ClueLess.getWallet().getBalance() + "BTC \n Bankaccount: " + ClueLess.getBankAccount().getCredit() + "€");
                    }else if(Params[1].equals("recipient")){
                        System.out.println("Ed BTC-Adress: " + Ed.getWallet().getPublicKey());
                    }else{
                        System.out.println("Unknown Identifier!!!");
                    }

                }
                case "pay" -> pay(CryptoManager.getTimerTask().getPrice());
                case "check" -> {
                    if(Params[1].equals("payment")){
                        check(CryptoManager.getTimerTask().getPrice());
                    }else{
                        System.out.println("Unknown Identifier to Check!!!");
                    }

                }
                case "decrypt" -> CryptoManager.startDecrypting();

                case "blockchain" -> {
                    Blockchain.getInstance();
                    System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(Blockchain.getInstance()));
                }
                case "exit" -> {
                    System.out.println("----------- Program Exited -----------");
                    System.exit(0);
                }
            }

            System.out.println();


        }while(!UserInput.equals("exit"));

    }

    public static void check(float amount){
        if(Ed.getWallet().getBalance() < amount){
            System.out.println("so far only " + Ed.getWallet().getBalance() + "BTC received. There will be a total of " + amount + "BTC required");
            return;
        }else {
            System.out.println("Payment of the " + amount + "BTC successful.");
            if (CryptoManager.getTimerTask().isAllowToDecrypt()){
                CryptoManager.startDecrypting();
            }else{
                System.out.println("You have paid too late! Bad Luck!");
            }
        }
    }


    public static boolean exchange(Wallet wallet, float amount){
        //System.out.println("exchange " + amount + "BTC selected");

        if(SatoshiNakamoto.getWallet().getBalance() < amount){
            System.out.println("Exchange is not Possible: No Enough BTC");
            return false;
        }
        if(ClueLess.getBankAccount().getCredit() < (amount/0.000019)){
            System.out.println("Exchange is not Possible: No Enough €. There will be: " + (amount/0.000019) + "€ required");
            return false;
        }

        ClueLess = new Victim((int) (ClueLess.getBankAccount().getCredit() - (amount/0.000019)), amount);

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
        FileWriter fw = new FileWriter(Configuration.instance.JSONPath, true);

        try{
            fw.write(json.toString());
            fw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }




}

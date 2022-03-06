package S02;

import com.google.gson.GsonBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.io.*;
import java.nio.file.Path;
import java.security.Security;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import static S02.StringUtility.blueOutput;

public class Blockchain {

    private static Blockchain instance;

    private Transaction genesisTransaction;
    private HashMap<String, TransactionOut> utx0Map = new HashMap<>();
    private ArrayList<Block> blockchain = new ArrayList<>();
    private int transactionSequence = 0;

    private List<Miner> miners = new ArrayList<>();

    private Block previousBlock;

    public Wallet satoshiNakamoto;

    private Blockchain() {
        instance = this;
        Security.addProvider(new BouncyCastleProvider());


        miners.add(new Miner("Bob"));
        miners.add(new Miner("Eve"));
        miners.add(new Miner("Sam"));

        this.satoshiNakamoto = new Wallet();


        this.genesisTransaction = new Transaction(satoshiNakamoto.getPublicKey(), satoshiNakamoto.getPublicKey(), 1, null);
        this.genesisTransaction.generateSignature(satoshiNakamoto.getPrivateKey());
        this.genesisTransaction.setId("0");
        this.genesisTransaction.getOutputs().add(
                new TransactionOut(this.genesisTransaction.getRecipient(),
                        this.genesisTransaction.getValue(), this.genesisTransaction.getId())
        );

        this.utx0Map.put(
                this.genesisTransaction.getOutputs().get(0).getID(),
                this.genesisTransaction.getOutputs().get(0)
        );

        System.out.println(blueOutput("creating and mining genesis block"));
        Block genesisBlock = new Block("0");
        genesisBlock.addTransaction(this.genesisTransaction);
        addBlock(genesisBlock);

        for (Miner m : miners) {
            System.out.printf(blueOutput("%10s balance | %f%n"), m.getName(), m.getWallet().getBalance());
        }
        System.out.printf(blueOutput("%10s balance | %f%n"), "Satoshi", satoshiNakamoto.getBalance());



        isChainValid();
    }

    private void addBlock(Block newBlock) {
        Miner m = miners.get(ThreadLocalRandom.current().nextInt(miners.size()) % miners.size());
        newBlock.mineBlock(Config.instance.difficultyLevel, m);
        this.blockchain.add(newBlock);
        this.previousBlock = newBlock;

        try {
            File f = Path.of("blockchain.json").toAbsolutePath().toFile();
            f.createNewFile();
            FileWriter fw = new FileWriter(f);
            new GsonBuilder().setPrettyPrinting().create().toJson(this, fw);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTransaction(Transaction t) {
        Block b = new Block(this.previousBlock.getHash());
        b.addTransaction(t);
        this.addBlock(b);
    }

    public boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = StringUtility.getDifficultyString(S02.Config.instance.difficultyLevel);
        HashMap<String, TransactionOut> tempUTXOs = new HashMap<>();
        tempUTXOs.put(this.genesisTransaction.getOutputs().get(0).getID(), this.genesisTransaction.getOutputs().get(0));

        for (int i = 1; i < this.blockchain.size(); i++) {
            currentBlock = this.blockchain.get(i);
            previousBlock = this.blockchain.get(i - 1);

            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                System.out.println(blueOutput("#current hashes not equal"));
                return false;
            }

            if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
                System.out.println(blueOutput("#trevious hashes not equal"));
                return false;
            }

            if (!currentBlock.getHash().substring(0, Config.instance.difficultyLevel).equals(hashTarget)) {
                System.out.println(blueOutput("#block not mined"));
                return false;
            }

            TransactionOut tempOutput;
            for (int t = 0; t < currentBlock.getTransactions().size(); t++) {
                Transaction currentTransaction = currentBlock.getTransactions().get(t);

                if (currentTransaction.verifySignature()) {
                    System.out.println(blueOutput("#Signature on de.dhbw.blockchain.Transaction(" + t + ") is Invalid"));
                    return false;
                }

                if (currentTransaction.getInputsValue() != currentTransaction.getOutputsValue()) {
                    System.out.println(blueOutput("#Inputs are not equal to oututs on de.dhbw.blockchain.Transaction(" + t + ")"));
                    return false;
                }

                for (TransactionIn input : currentTransaction.getInputs()) {
                    tempOutput = tempUTXOs.get(input.getId());

                    if (tempOutput == null) {
                        System.out.println(blueOutput("#referenced input on transaction(" + t + ") is missing"));
                        return false;
                    }

                    if (input.getUTX0().getValue() != tempOutput.getValue()) {
                        System.out.println(blueOutput("#referenced input on transaction(" + t + ") value invalid"));
                        return false;
                    }

                    tempUTXOs.remove(input.getId());
                }

                for (TransactionOut output : currentTransaction.getOutputs()) {
                    tempUTXOs.put(output.getID(), output);
                }

                if (currentTransaction.getOutputs().get(0).getRecipient() != currentTransaction.getRecipient()) {
                    System.out.println(blueOutput("#transaction(" + t + ") output recipient is invalid"));
                    return false;
                }

                if (currentTransaction.getOutputs().get(1).getRecipient() != currentTransaction.getSender()) {
                    System.out.println(blueOutput("#transaction(" + t + ") output 'change' is not sender"));
                    return false;
                }
            }
        }
        System.out.println(blueOutput("blockchain valid"));
        return true;
    }

    public static Blockchain getInstance() {
        if (instance == null) instance = new Blockchain();
        return instance;
    }

    public void incrementTransactionSequence() {
        this.transactionSequence++;
    }

    public int getTransactionSequence() {
        return transactionSequence;
    }

    public HashMap<String, TransactionOut> getUtx0Map() {
        return this.utx0Map;
    }

    public boolean buyBtc(Wallet wallet, double amount) {

        if (this.satoshiNakamoto.getBalance() < amount) {
            return false;}
        Transaction t = this.satoshiNakamoto.sendFunds(wallet.getPublicKey(), amount);
        this.addTransaction(t);
        return true;
    }

}
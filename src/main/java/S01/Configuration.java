package S01;

import S02.Block;
import S02.Transaction;
import S02.TransactionOut;

import java.util.ArrayList;
import java.util.HashMap;

public enum Configuration {
    instance;

    public final String fileSeparator = System.getProperty("file.separator");

    public final String nameOfJavaArchive = "report.jar";

    public final String nameOfClass = "AES256";

    public final String nameOfSubFolder = "report" + fileSeparator + "jar";

    public final String subFolderPathOfJavaArchive = nameOfSubFolder + fileSeparator + nameOfJavaArchive;



    public float AmountOfPay = 0.02755f;
    public final int difficulty = 3;
    public HashMap<String, TransactionOut> utx = new HashMap<>();
    public int transactionSequence = 0;
    public ArrayList<Block> blockchain = new ArrayList<>();
    public String JSONPath = "BlockchainConsole.json";
    public float minTransaction = 0.01f;
    public Transaction genesisTransaction;


}

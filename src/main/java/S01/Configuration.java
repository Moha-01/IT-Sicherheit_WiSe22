package S01;

import S02.Block;
import S02.Transaction;
import S02.TransactionOut;

import java.util.ArrayList;
import java.util.HashMap;

public enum Configuration {
    instance;

    public final String fileSeperator = System.getProperty("file.seperator");

    public final String nameOfJavaArchive = "report.jar";

    public final String nameOfClass = "AAES256";

    public final String nameOfSubFolder = "report" + fileSeperator + "jar";

    public final String subFolderPathOfJaavaArchive = nameOfSubFolder + fileSeperator + nameOfJavaArchive;



    public final int difficulty = 3;
    public HashMap<String, TransactionOut> utx = new HashMap<>();
    public int transactionSequence = 0;
    public float minimumTransaction = 0.01f;
    public Transaction genesisTransaction;
    public ArrayList<Block> blockchain = new ArrayList<>();
    public String pathOfJson = "BlockchainConsole.json";
    public float payAmount = 0.02755f;

}

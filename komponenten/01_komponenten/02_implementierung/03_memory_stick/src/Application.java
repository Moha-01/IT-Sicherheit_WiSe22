import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class Application {
    private Class clazz;
    private Object instance;
    private Object port;

    public static void main(String... args) {
        Application application = new Application();
        application.execute();
    }

    public void loadClazzFromJavaArchive() {
        try {
            URL[] urls = {new File(Configuration.instance.subFolderPathOfJavaArchive).toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, Application.class.getClassLoader());
            clazz = Class.forName(Configuration.instance.nameOfClass, true, urlClassLoader);
            System.out.println("class    | " + clazz + " - " + clazz.hashCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void provideInstanceOfClass() {
        try {
            instance = clazz.getMethod("getInstance").invoke(null);
            System.out.println("instance | " + instance.toString() + " - " + instance.hashCode());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void provideComponentPort() {
        try {
            port = clazz.getDeclaredField("port").get(instance);
            System.out.println("port     | " + port.toString() + " - " + port.hashCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void executeMethodDirectlyWithoutPort() {
        System.out.println("--- executeMethodDirectlyWithoutPort");

        try {
            Method method = clazz.getDeclaredMethod("getVersion");
            System.out.println(method);
            String version = (String) method.invoke(instance);
            System.out.println("version  | " + version);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();
    }

    public void executeMethodUsingPort01() {
        System.out.println("--- executeMethodUsingPort01");

        try {
            Method method = port.getClass().getMethod("getCapacity");
            System.out.println(method);
            int result = (int) method.invoke(port);
            System.out.println("result   | " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();
    }

    public void executeMethodUsingPort02() {
        System.out.println("--- executeMethodUsingPort02");

        try {
            Method method = port.getClass().getMethod("store", int[].class);
            System.out.println(method);
            int[] valuesToStore = new int[]{1, 2, 3, 4, 5};
            boolean result = (boolean) method.invoke(port, valuesToStore);
            System.out.println("result   | " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void execute() {
        Configuration.instance.print();
        loadClazzFromJavaArchive();
        provideInstanceOfClass();
        provideComponentPort();
        System.out.println();
        executeMethodDirectlyWithoutPort();
        executeMethodUsingPort01();
        executeMethodUsingPort02();
    }
}
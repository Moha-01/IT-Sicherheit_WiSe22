import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class SortFactory {
    @SuppressWarnings("unchecked")
    public static Object build() {
        Object componentPort = null;

        try {
            URL[] urls = {new File(Configuration.instance.pathToJavaArchive + "sort.jar").toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, SortFactory.class.getClassLoader());
            Class sortClass = Class.forName("Sort", true, urlClassLoader);
            Object weatherRadarInstance = sortClass.getMethod("getInstance").invoke(null);
            componentPort = sortClass.getDeclaredField("port").get(weatherRadarInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return componentPort;
    }
}
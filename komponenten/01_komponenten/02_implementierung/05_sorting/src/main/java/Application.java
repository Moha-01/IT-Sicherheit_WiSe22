import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class Application {
    private final Object sortPort;

    public Application() {
        sortPort = SortFactory.build();
    }

    public static void main(String... args) {
        Application application = new Application();

        ArrayList<Integer> values = new ArrayList<>(Arrays.asList(2, 1, 7, 4, 9, 3, 5, 10, 6, 8));
        System.out.println("values | " + values);

        ArrayList<Integer> result = application.sort(values);
        System.out.println("result | " + result);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Integer> sort(ArrayList<Integer> values) {
        ArrayList<Integer> result = null;

        try {
            Method sortMethod = sortPort.getClass().getDeclaredMethod("sort", ArrayList.class);
            result = (ArrayList<Integer>) sortMethod.invoke(sortPort, values);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }
}
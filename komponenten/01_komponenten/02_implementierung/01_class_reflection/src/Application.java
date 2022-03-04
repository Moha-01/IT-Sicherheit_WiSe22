import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Application {
    public static void main(String... args) {
        Application application = new Application();
        application.execute();
    }

    public void execute() {
        try {
            String clazzName = "A";
            System.out.println("clazzName    | " + clazzName);

            Class<?> clazz = Class.forName(clazzName);
            System.out.println("clazz        | " + clazz);

            Class<?>[] parameterTypes = new Class<?>[2];
            parameterTypes[0] = String.class;
            parameterTypes[1] = Integer.TYPE;
            Constructor<?> constructor = clazz.getConstructor(parameterTypes);
            System.out.println("constructor  | " + constructor);

            Object[] arguments = new Object[2];
            arguments[0] = "B";
            arguments[1] = 1;
            Object instance = constructor.newInstance(arguments);
            System.out.println("instance     | " + instance);

            Method method = clazz.getMethod("setValues", parameterTypes);
            method.invoke(instance, "C", 2);
            System.out.println("instance     | " + instance);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
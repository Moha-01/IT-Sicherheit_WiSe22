import java.util.ArrayList;

public class Sort {
    // static instance
    private static final Sort instance = new Sort();

    // port
    public Port port;

    private Sort() {
        port = new Port();
    }

    public static Sort getInstance() {
        return instance;
    }

    public String getVersion() {
        return "sort | bubble";
    }

    public ArrayList<Integer> innerSort(ArrayList<Integer> inputValues) {
        ArrayList<Integer> values = new ArrayList<>(inputValues);
        int temp;

        for (int i = 0; i < values.size() - 1; i++) {
            for (int j = 1; j < (values.size() - i); j++) {
                if (values.get(j - 1) > values.get(j)) {
                    temp = values.get(j - 1);
                    values.set(j - 1, values.get(j));
                    values.set(j, temp);
                }
            }
        }

        return values;
    }

    public class Port implements ISort {
        public void version() {
            System.out.println(getVersion() + "\n");
        }

        public ArrayList<Integer> sort(ArrayList<Integer> values) {
            return innerSort(values);
        }
    }
}
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestApplication {
    @Test
    public void sort() {
        Application application = new Application();
        ArrayList<Integer> values = new ArrayList<>(Arrays.asList(2, 1, 7, 4, 9, 3, 5, 10, 6, 8));
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        ArrayList<Integer> actual = application.sort(values);
        assertEquals(actual, expected);
    }
}
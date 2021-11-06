import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> randomState = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++) {
            randomState.add(i);
        }
        Collections.shuffle(randomState);
        String initialState = "";
        for (int i = 0; i < 9; i++) {
            initialState += randomState.get(i);
        }
        System.out.println(initialState);

    }
}

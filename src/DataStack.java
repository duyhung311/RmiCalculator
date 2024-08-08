import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class DataStack {
    private static DataStack instance;
    private final Map<Integer, Stack<Integer>> datastack;
    private DataStack() {
        datastack = new HashMap<>();
    }

    public static DataStack getInstance() {
        if (instance == null) {
            instance = new DataStack();
        }
        return instance;
    }

    public synchronized Stack<Integer> getDataStack(int id) {
        if (!datastack.containsKey(id)) {
            datastack.put(id, new Stack<>());
        }
        return this.datastack.get(id);
    }

}

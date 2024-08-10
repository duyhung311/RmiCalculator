import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class DataStack implements Serializable {
    private static DataStack instance;
    private static final Map<Integer, Stack<Integer>> datastack = new HashMap<>();
    private static final Map<Calculator, Integer> clientIds = new HashMap<>();
    private static int id = 0;

    private DataStack() {
    }

    public int getNewId(Calculator calculator) {
        return id++;
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
        return datastack.get(id);
    }

}

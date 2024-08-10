package uoa.hungluong;

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
//
//        if (clientIds.containsKey(calculator)) {
//            System.out.println("Already have a new id for " + calculator);
//            return clientIds.get(calculator);
//        }

//        clientIds.put(calculator, id);
        System.err.println("creating new CLIENT with id: " + id);
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

import java.util.Stack;

public class DataStack {
    private static DataStack instance;
    private final Stack<Integer> stack;

    private DataStack() {
        stack = new Stack<>();
    }

    public static DataStack getInstance() {
        if (instance == null) {
            instance = new DataStack();
        }
        return instance;
    }

    public Stack<Integer> getStack() {
        return this.stack;
    }

}

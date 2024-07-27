import java.util.Stack;

public interface Calculator {

    void pushValue(int val);

    void pushOperation(String operator);

    int pop();

    boolean isEmpty();

    int delayPop(int millis);

}

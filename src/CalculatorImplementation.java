
public class CalculatorImplementation implements Calculator {
    private final DataStack dataStack;

    public CalculatorImplementation() {
        dataStack = DataStack.getInstance();
    }

    @Override
    public void pushValue(int val) {
        dataStack.getStack().push(val);
    }

    @Override
    public void pushOperation(String operator) {
        calculateResult(operator);
    }

    @Override
    public int pop() {
        return dataStack.getStack().pop();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int delayPop(int millis) {
        return 0;
    }

    private void calculateResult(String op) {
        switch (op) {
            case "max":
                max();
                break;
            case "min":
                min();
                break;
            case "gcd":
                gcd();
                break;
            case "lcm":
                lcm();
                break;
        }
    }

    private synchronized void max() {
        int max = Integer.MIN_VALUE;

        while (!dataStack.getStack().isEmpty()) {
            int front = dataStack.getStack().pop();
            max = Math.max(max, front);
        }

        dataStack.getStack().push(max);
    }

    private synchronized void min() {
        int min = Integer.MAX_VALUE;

        while (!dataStack.getStack().isEmpty()) {
            int front = dataStack.getStack().pop();
            min = Math.min(min, front);
        }

        dataStack.getStack().push(min);
    }

    private synchronized void gcd() {
        if (dataStack.getStack().size() < 2) {
            return;
        }
        int firstElement = dataStack.getStack().pop();
        int secondElement = dataStack.getStack().pop();

        int gcd = findGcd(firstElement, secondElement);

        while (!dataStack.getStack().isEmpty()) {
            int front = dataStack.getStack().pop();
            gcd = findGcd(front, gcd);
        }
        dataStack.getStack().push(gcd);
    }

    private synchronized int findGcd(int a, int b) {
        if (a == b)
            return a;
        if (a > b) {

            return findGcd(a - b, b);
        }

        return findGcd(a, b - a);
    }

    /**
     * Find lcm based on gcd
    * */
    private synchronized void lcm() {
        if (dataStack.getStack().size() < 2) {
            return;
        }
        int firstElement = dataStack.getStack().pop();
        int secondElement = dataStack.getStack().pop();

        int gcd = findGcd(firstElement, secondElement);
        int lcm = (firstElement * secondElement) / gcd;

        while (!dataStack.getStack().isEmpty()) {
            int front = dataStack.getStack().pop();
            gcd = findGcd(front, lcm);
            lcm = (front * lcm) / gcd;
        }
        dataStack.getStack().push(lcm);
    }
}

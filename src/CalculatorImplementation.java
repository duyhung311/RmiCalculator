import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorImplementation extends UnicastRemoteObject implements Serializable, Calculator {
    private final DataStack dataStack;

    @Override
    public int getId() {

        int a = dataStack.getNewId(this);
        System.out.println("New client comes in and is issued with id = " + a);
        return a;
    }

    public CalculatorImplementation() throws RemoteException {
        super();
        dataStack = DataStack.getInstance();
    }

    /**
     * Push {@code val} into the data stack
     *
     * @param clientId tells which client is performing the push
     * @param val value to be pushed
     */
    @Override
    public void pushValue(int clientId, int val) {
        dataStack.getDataStack(clientId).push(val);
    }

    /**
     * Push {@code operator} into the data stack
     *
     * @param clientId tells which client is performing the push
     * @param operator operator to be pushed (lcm, gcd, max, min)
     */
    @Override
    public void pushOperation(int clientId, String operator) {
        calculateResult(clientId, operator);
    }

    /**
     * Pop 1 element out of the data stacks
     *
     * @param clientId tells which client is performing the pop
     */
    @Override
    public int pop(int clientId) throws RemoteException {
        return dataStack.getDataStack(clientId).pop();
    }

    /**
     * Check if the associated stack is empty
     *
     * @param clientId tells which client is performing the isEmpty
     */
    @Override
    public boolean isEmpty(int clientId) throws RemoteException {
        return dataStack.getDataStack(clientId).isEmpty();
    }

    /**
     * Delay for some time until popping
     *
     * @param clientId tells which client is performing the isEmpty
     * @param millis how many millis second to wait
     */
    @Override
    public int delayPop(int clientId, int millis) throws InterruptedException {
        // consider CompletableFuture#deplayOperation(long, TimeUnit);
        Thread.sleep(millis);
        int a = dataStack.getDataStack(clientId).pop();
        System.out.println();
        return a;
    }

    private void calculateResult(int clientId, String op) {
        switch (op) {
            case "max":
                max(clientId);
                break;
            case "min":
                min(clientId);
                break;
            case "gcd":
                gcd(clientId);
                break;
            case "lcm":
                lcm(clientId);
                break;
        }
    }

    /**
     * Pop all the value on the {@code dataStack} to look for max value
     * and push the result onto the stack
     *
     * @param clientId key with which the specified client is to be associated
     */
    private synchronized void max(int clientId) {
        int max = Integer.MIN_VALUE;

        while (!dataStack.getDataStack(clientId).isEmpty()) {
            int front = dataStack.getDataStack(clientId).pop();
            max = Math.max(max, front);
        }

        dataStack.getDataStack(clientId).push(max);
    }

    /**
     * Pop all the value on the {@code dataStack} to look for min value
     * and push the result onto the stack
     *
     * @param clientId key with which the specified client is to be associated
     */
    private synchronized void min(int clientId) {
        int min = Integer.MAX_VALUE;

        while (!dataStack.getDataStack(clientId).isEmpty()) {
            int front = dataStack.getDataStack(clientId).pop();
            min = Math.min(min, front);
        }

        dataStack.getDataStack(clientId).push(min);
    }

    /**
     * Pop all the value on the {@code dataStack} to look for greatest common divisor
     * and push the result onto the stack
     *
     * @param clientId key with which the specified client is to be associated
     */
    private synchronized void gcd(int clientId) {
        if (dataStack.getDataStack(clientId).size() < 2) {
            return;
        }
        int firstElement = dataStack.getDataStack(clientId).pop();
        int secondElement = dataStack.getDataStack(clientId).pop();

        int gcd = findGcd(firstElement, secondElement);

        while (!dataStack.getDataStack(clientId).isEmpty()) {
            int front = dataStack.getDataStack(clientId).pop();
            gcd = findGcd(front, gcd);
        }
        dataStack.getDataStack(clientId).push(gcd);
    }

    /**
     * Find the greatest common divisor recursively between {@code num1} and {@code num2}
     *
     * @param num1 first number
     * @param num2 second number
     * @return result of the greatest common divisor between {@code num1} and {@code num2}
     */
    private synchronized int findGcd(int num1, int num2) {
        if (num1 == num2)
            return num1;
        if (num1 > num2) {
            return findGcd(num1 - num2, num2);
        }

        return findGcd(num1, num2 - num1);
    }

    /**
     * Find the largest common multiplier base on {@link #findGcd(int, int)}
     * with the following formula: <br>
     * {@code lcm = (num1 * num2) / findGcd(num1, num2)}
     *
     * @param clientId key with which the specified client is to be associated
     */
    private synchronized void lcm(int clientId) {
        if (dataStack.getDataStack(clientId).size() < 2) {
            return;
        }
        int firstElement = dataStack.getDataStack(clientId).pop();
        int secondElement = dataStack.getDataStack(clientId).pop();

        int gcd = findGcd(firstElement, secondElement);
        int lcm = (firstElement * secondElement) / gcd;

        while (!dataStack.getDataStack(clientId).isEmpty()) {
            int front = dataStack.getDataStack(clientId).pop();
            gcd = findGcd(front, lcm);
            lcm = (front * lcm) / gcd;
        }
        dataStack.getDataStack(clientId).push(lcm);
    }
}

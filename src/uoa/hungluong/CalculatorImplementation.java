package uoa.hungluong;

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
     * Push {@code val into the data stacks }
     *
     * @param clientId
     * @param val
     */
    @Override
    public void pushValue(int clientId, int val) {
        System.out.println("Push to client id = " + clientId);
        dataStack.getDataStack(clientId).push(val);
    }

    @Override
    public void pushOperation(int clientId, String operator) {
        calculateResult(clientId, operator);
    }

    @Override
    public int pop(int clientId) throws RemoteException {
        System.out.println("Pop from client id = " + clientId);
        return dataStack.getDataStack(clientId).pop();
    }

    @Override
    public boolean isEmpty(int clientId) throws RemoteException {
        return dataStack.getDataStack(clientId).isEmpty();
    }

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

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CalculatorImplementation extends UnicastRemoteObject implements Calculator {
    private final DataStack dataStack;

//    public static void main(String[] args) {
//        try {
//            CalculatorImplementation obj = new CalculatorImplementation();
//            Calculator stub = (Calculator) UnicastRemoteObject.exportObject(obj, 0);
//
//            // Bind the remote object's stub in the registry
//            Registry registry = LocateRegistry.getRegistry();
//            registry.bind("Hello", stub);
//
//            System.err.println("Server Hello ready");
//        } catch (Exception e) {
//            System.err.println("Server exception: " + e.toString());
//            e.printStackTrace();
//        }
//    }

    public CalculatorImplementation() throws RemoteException {
        super();
        dataStack = DataStack.getInstance();
    }

    @Override
    public void pushValue(int clientId, int val) {
        dataStack.getDataStack(clientId).push(val);
    }

    @Override
    public void pushOperation(int clientIds, String operator) {
        calculateResult(clientIds, operator);
    }

    @Override
    public int pop(int clientId) throws RemoteException {
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
        return dataStack.getDataStack(clientId).pop();
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

    private synchronized void max(int clientId) {
        int max = Integer.MIN_VALUE;

        while (!dataStack.getDataStack(clientId).isEmpty()) {
            int front = dataStack.getDataStack(clientId).pop();
            max = Math.max(max, front);
        }

        dataStack.getDataStack(clientId).push(max);
    }

    private synchronized void min(int clientId) {
        int min = Integer.MAX_VALUE;

        while (!dataStack.getDataStack(clientId).isEmpty()) {
            int front = dataStack.getDataStack(clientId).pop();
            min = Math.min(min, front);
        }

        dataStack.getDataStack(clientId).push(min);
    }

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

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Objects;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.concurrent.ExecutionException;

public class CalculatorClientImpl {
    private Calculator stub1 = new CalculatorImplementation();
    private int id;
    private Scanner scanner = new Scanner(System.in);
    public CalculatorClientImpl() throws RemoteException {
    }

    public void connectAndRun() throws RemoteException, ExecutionException, InterruptedException {
        int host = 1099;

        try {
            Registry registry = LocateRegistry.getRegistry(host);
            stub1 = (Calculator) registry.lookup("Server");
            id = stub1.getId();
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
        if (Objects.isNull(stub1))
            return;
        System.err.println(stub1.hashCode());
        int choice;
        while (true) {
            choice = printInstructionAndGetOption();
            if (choice == 4)
                break;
            doOps(choice);
        }

    }

    private int printInstructionAndGetOption() {
        System.out.println("Calculator");
        System.out.println("Enter operation corresponding to the order number");
        System.out.println("1. Enter a value");
        System.out.println("2. Enter a operation");
        System.out.println("3. Pop");
        System.out.println("4. Exit");
        return scanner.nextInt();
    }

    private void doOps(int choice) throws RemoteException, ExecutionException, InterruptedException {
        switch (choice) {
            case 1:
                pushValue(); break;
            case 2:
                pushOperation(); break;
            case 3:
                System.out.println(pop()); break;
            case 4:
                System.out.println(isEmpty()); break;
            case 5:
                System.out.println("Return result after how much millisecond? ");
                System.out.println(delayPop(scanner.nextInt()));

        }
    }

    private void pushValue() throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        int val = scanner.nextInt();
        stub1.pushValue(id, val);
    }
    private void pushOperation() throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        String op = scanner.next();
        switch (op) {
            case "lcm":
                stub1.pushOperation(id, op); break;
        }
    }
    private int pop() throws RemoteException {
        return stub1.pop(id);
    }
    private boolean isEmpty() throws RemoteException {
        return stub1.isEmpty(id);
    }

    private int delayPop(int millis) throws RemoteException, ExecutionException, InterruptedException {
        return stub1.delayPop(id, millis);
    }
}

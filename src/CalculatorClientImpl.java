import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class CalculatorClientImpl {
    private Calculator stub = new CalculatorImplementation();
    private int id;
    private final Scanner scanner = new Scanner(System.in);

    public CalculatorClientImpl() throws RemoteException {
    }

    public CalculatorClientImpl connect() {
        int host = 1099;

        try {
            Registry registry = LocateRegistry.getRegistry(host);
            stub = (Calculator) registry.lookup("Server");
            if (Objects.isNull(stub))
                return null;
            id = stub.getId();
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            System.out.println(e.getMessage());
        }
        return this;
    }

    public void run() throws RemoteException, ExecutionException, InterruptedException {
        if (Objects.isNull(stub))
            return;
        int choice;
        while (true) {
            choice = printInstructionAndGetOption();
            if (choice == 6)
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
        System.out.println("4. Check if the stack empty");
        System.out.println("5. Check if the stack empty with delay");
        System.out.println("6. Exit");
        return scanner.nextInt();
    }

    public void doOps(int choice) throws RemoteException, ExecutionException, InterruptedException {
        switch (choice) {
            case 1:
                pushValue(scanner.nextInt());
                break;
            case 2:
                pushOperation(scanner.nextLine());
                break;
            case 3:
                System.out.println(pop());
                break;
            case 4:
                System.out.println(isEmpty());
                break;
            case 5:
                System.out.println("Type delay time in millisecond? ");
                System.out.println(delayPop(scanner.nextInt()));

        }
    }

    public void pushValue(int value) throws RemoteException {
        stub.pushValue(id, value);
    }

    public void pushOperation(String op) throws RemoteException {
        stub.pushOperation(id, op);
    }

    public int pop() throws RemoteException {
        return stub.pop(id);
    }

    public boolean isEmpty() throws RemoteException {
        return stub.isEmpty(id);
    }

    public int delayPop(int millis) throws RemoteException, ExecutionException, InterruptedException {
        return stub.delayPop(id, millis);
    }
}

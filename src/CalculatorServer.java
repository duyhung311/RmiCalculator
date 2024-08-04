import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorServer extends CalculatorImplementation implements Calculator {
    private static CalculatorServer instance;

    public static CalculatorServer getInstance() {
        if (instance == null) {
            instance = new CalculatorServer();
        }
        return instance;
    }

    public static void main(String[] args) {

        try {
            CalculatorServer obj = new CalculatorServer();
            Calculator stub = (Calculator) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(8080);
            registry.rebind("Hello", stub);

            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }





}

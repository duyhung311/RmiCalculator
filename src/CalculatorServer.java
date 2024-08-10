import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculatorServer extends CalculatorImplementation {

    private static CalculatorServer instance;

    public CalculatorServer() throws RemoteException {
    }

    public static CalculatorServer getInstance() throws RemoteException {
        if (instance == null) {
            instance = new CalculatorServer();
        }
        return instance;
    }

    // 'Singleton' design pattern
    public static void main(String[] args) {
        try {
            CalculatorServer obj = new CalculatorServer();
            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry(1099);
            registry.rebind("Server", obj);

            System.out.println("Server Hello ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        }
    }
}

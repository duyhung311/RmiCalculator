import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

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
            Registry registry = LocateRegistry.createRegistry(8080);
            registry.rebind("Hello", obj);

            System.out.println("Server Hello ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }





}

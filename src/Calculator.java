import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;

public interface Calculator extends Remote {

    void pushValue(int clientId, int val) throws RemoteException;

    void pushOperation(int clientId, String operator) throws RemoteException;

    int pop(int clientId) throws RemoteException;

    boolean isEmpty(int clientId) throws RemoteException;

    int delayPop(int clientId, int millis) throws RemoteException, ExecutionException, InterruptedException;

    int getId() throws RemoteException;
}

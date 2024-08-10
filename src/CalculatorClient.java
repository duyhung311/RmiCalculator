import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;

public class CalculatorClient {

    public static void main(String[] args) throws RemoteException, ExecutionException, InterruptedException {
        CalculatorClientImpl calculatorClient = new CalculatorClientImpl();
        calculatorClient
                .connect()
                .run();
    }

}

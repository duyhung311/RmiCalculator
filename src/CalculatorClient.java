import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class CalculatorClient {

    //    public void testSingleClient() {
//        CalculatorServer s1 = new CalculatorServer();
//        s1.pushValue(7);
//        s1.pushValue(20);
//        s1.pushValue(16);
//        s1.pushValue(12);
//        s1.pushValue(8);
//        s1.pushValue(4);
//        s1.pushOperation("lcm");
//        System.out.println(s1.pop());
//    }

//    public void testMultiClient() {
//
//        CalculatorServer s1 = new CalculatorServer();
//        s1.pushValue(7);
//        s1.pushValue(20);
//        s1.pushValue(16);
//
//        CalculatorServer s2 = new CalculatorServer();
//        s2.pushValue(11);
//
//        System.out.println(s1.pop());
//    }


    public static void main(String[] args) throws RemoteException, ExecutionException, InterruptedException {
        CalculatorClientImpl calculatorClient = new CalculatorClientImpl();
        calculatorClient.connectAndRun();
    }


}

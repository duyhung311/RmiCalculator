import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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

    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        int host = 8080;
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            Calculator stub1 = (Calculator) registry.lookup("Hello");
            stub1.pushValue(4);
            stub1.pushValue(16);
            stub1.pushValue(20);
            stub1.pushValue(24);
            stub1.pushValue(66);

            Calculator stub2 = (Calculator) registry.lookup("Hello");
            stub2.pushOperation("lcm");
            System.out.println("response: " + stub1.delayPop(2000));
            System.out.println("response: " + stub1.isEmpty());

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

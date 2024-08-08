import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

public class CalculatorClient {
    private static int id = 0;
    private static Map<Calculator, Integer> clientIds = new HashMap<>();
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
    private static int getNewId(Calculator calculator) {
        if (clientIds.containsKey(calculator)) {
            return clientIds.get(calculator);
        }

        clientIds.put(calculator, id);
        return id++;

    }

    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        int host = 8081;
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            Calculator stub1 = (Calculator) registry.lookup("Hello");
            stub1.pushValue(getNewId(stub1), 4);
            stub1.pushValue(getNewId(stub1),16);
            stub1.pushValue(getNewId(stub1),20);
            stub1.pushValue(getNewId(stub1),24);
            stub1.pushValue(getNewId(stub1),66);

            Calculator stub2 = (Calculator) registry.lookup("Hello");
            stub2.pushOperation(getNewId(stub2),"lcm");
            System.out.println("response: " + stub1.delayPop(getNewId(stub1),2000));
            System.out.println("response: " + stub1.isEmpty(getNewId(stub1)));

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

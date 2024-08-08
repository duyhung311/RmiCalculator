import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static int id = 0;
    private static Map<Calculator, Integer> clientIds = new HashMap<>();
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
            stub1.pushOperation(getNewId(stub1),"lcm");
//            Calculator stub2 = (Calculator) registry.lookup("Hello");
//            stub2.pushOperation(getNewId(stub2),"lcm");
            System.out.println("response: " + stub1.delayPop(getNewId(stub1),2000));
            System.out.println("response: " + stub1.isEmpty(getNewId(stub1)));

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

}
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        int host = 8080;
        try {
            Registry registry = LocateRegistry.getRegistry(8080);
            Calculator stub1 = (Calculator) registry.lookup("Hello");
            stub1.pushValue(4);
            stub1.pushValue(16);
            stub1.pushValue(20);
            stub1.pushValue(24);
            stub1.pushValue(66);

            Calculator stub2 = (Calculator) registry.lookup("Hello");
            stub2.pushOperation("lcm");
            System.out.println("response: " + stub1.pop());
            System.out.println("response: " + stub1.isEmpty());

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

}
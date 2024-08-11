import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultipleClientCalculatorTests {

    private List<CalculatorClientImpl> calculatorClients;
    private static Registry registry;
    private final List<String> testcases = new ArrayList<>();

    private static final StringBuilder inputText = new StringBuilder();

    public MultipleClientCalculatorTests() throws FileNotFoundException {
        calculatorClients = new ArrayList<>();
        File dir = new File("src/target/test/multiple/client");

        if (dir.exists()) {
            if (Objects.nonNull(dir.listFiles())) {
                for (File file1 : Objects.requireNonNull(dir.listFiles())) { //for each file
                    Scanner inFile = new Scanner(file1);
                    System.out.println(file1.getAbsoluteFile());
                    if (file1.getName().endsWith(".txt")) {
                        inputText.delete(0, inputText.length());
                        while (inFile.hasNext()) { // for each line in a file
                            inputText.append(inFile.nextLine());
                            inputText.append("\n");
                        }
                        testcases.add(inputText.toString());
                    }

                }
            }
        }
    }

    @BeforeAll
    public static void setUp() throws RemoteException, NotBoundException {
        CalculatorServer server = new CalculatorServer();
        registry = LocateRegistry.createRegistry(1099);
        registry.rebind("Server", server);
        registry = LocateRegistry.getRegistry();
    }

    @BeforeEach
    public void init() throws RemoteException {
        for (int i = 0; i < 3; i++) {
            calculatorClients.add(new CalculatorClientImpl().connect());
        }
    }


    @Test
    public void runAllMultipleClientTestcaseSuite() throws RemoteException {
        int clientOrder;
        CalculatorClientImpl currentClient;
        for (String testcase : testcases) {
            clientOrder = 0;
            currentClient = calculatorClients.get(clientOrder);

            System.setIn(new ByteArrayInputStream(testcase.getBytes()));
            Scanner keyboard = new Scanner(System.in);

            while (keyboard.hasNext()) {
                String input = keyboard.next();
                if (input.equals("-")) {
                    clientOrder++;
                    currentClient = calculatorClients.get(clientOrder);
                    continue;
                }
                boolean isInputNumeric = isNumeric(input);
                if (!isInputNumeric) { // reading operation
                    currentClient.pushOperation(input);
                    for (CalculatorClientImpl client : calculatorClients) {
                        client.pushOperation(input);
                    }
                    break;
                }
                currentClient.pushValue(Integer.parseInt(input));
            }
            int expected1 = keyboard.nextInt();
            int expected2 = keyboard.nextInt();
            int expected3 = keyboard.nextInt();
            int client1Result = calculatorClients.getFirst().pop();
            int client2Result = calculatorClients.get(1).pop();
            int client3Result = calculatorClients.get(2).pop();

//            System.out.println(expected + " " + res);
            assertEquals(expected1, client1Result);
            assertEquals(expected2, client2Result);
            assertEquals(expected3, client3Result);
        }


    }

    private boolean isNumeric(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}

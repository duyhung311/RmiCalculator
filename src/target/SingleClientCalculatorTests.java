package target;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import uoa.hungluong.*;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class SingleClientCalculatorTests {
    private CalculatorClientImpl calculatorClient;
    private static Registry registry;
    private final List<String> testcases = new ArrayList<>();

    private static final StringBuilder inputText = new StringBuilder();

    public SingleClientCalculatorTests() throws FileNotFoundException {
        File dir = new File("src/target/test/single/client");

        if (dir.exists()) {
            if (Objects.nonNull(dir.listFiles())) {
                for (File file1 : Objects.requireNonNull(dir.listFiles())) { //for each file
                    Scanner inFile = new Scanner(file1);
                    System.out.println(file1.getAbsoluteFile());
                    if (file1.getName().endsWith(".txt")) {
                        inputText.delete(0, inputText.length());
                        while (inFile.hasNext()) { // for each line in a file
                            inputText.append(inFile.next());
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
    }

    @BeforeEach
    public void init() throws RemoteException {
        calculatorClient = new CalculatorClientImpl().connect();
    }

    @AfterEach
    public void destroy() throws RemoteException, NotBoundException {
        registry.unbind("Server");
    }

    @Test
    public void runAllSingleClientTestcaseSuite() throws RemoteException, ExecutionException, InterruptedException {

        for (String testcase : testcases) {
            System.setIn(new ByteArrayInputStream(testcase.getBytes()));
            Scanner keyboard = new Scanner(System.in);

            while (keyboard.hasNext()) {
                String input = keyboard.next();
                boolean isInputNumeric = isNumeric(input);
                if (!isInputNumeric) { // reading operation
                    calculatorClient.pushOperation(input);
                    break;
                }
                calculatorClient.pushValue(Integer.parseInt(input));
            }
            int expected = keyboard.nextInt();
            int res = calculatorClient.pop();
            System.out.println(expected + " " + res);
            assertEquals(expected, res);
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

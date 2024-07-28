public class CalculatorServer extends CalculatorImplementation {
    private static CalculatorServer instance;

    CalculatorServer() {
    }

    public static CalculatorServer connectToServer() {
        if (instance == null) {
            instance = new CalculatorServer();
        }
        return instance;
    }
}

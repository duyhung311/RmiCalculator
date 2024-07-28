public class CalculatorClient {

    public void testSingleClient() {
        CalculatorServer s1 = new CalculatorServer();
        s1.pushValue(7);
        s1.pushValue(20);
        s1.pushValue(16);
        s1.pushValue(12);
        s1.pushValue(8);
        s1.pushValue(4);
        s1.pushOperation("lcm");
        System.out.println(s1.pop());
    }

    public void testMultiClient() {

        CalculatorServer s1 = CalculatorServer.connectToServer();
        s1.pushValue(7);
        s1.pushValue(20);
        s1.pushValue(16);

        CalculatorServer s2 = CalculatorServer.connectToServer();
        s2.pushValue(11);

        System.out.println(s1.pop());
    }
}

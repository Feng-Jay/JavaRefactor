public class FAKECLASS {

    public void displayInner() {
        int a = 1;
        class InnerClass {
            int a = 100000;
            public void printMessage() {
                int a = 1000;
                System.out.println("Hello from the Inner Class!");
            }
        }
    }
}

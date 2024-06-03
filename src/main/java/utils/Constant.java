package utils;

public class Constant {
    public static String beforeFilePath = "";
    public static String transformedFilePath = "";

    public static boolean renameVar = false;
    public static boolean transLoop = false;
    public static boolean switchToIf = false;
    public static boolean insertLog = true;
}

class FAKECLASS {

    public void displayInner() {
        int inner = 1;
        int a = 1;
        int b = 1;
        int c = 2;
        int d = a + b;
        class InnerClass {
            int c = 100000;
            public void printMessage() {
                int c = 111;
                int a = 1000;
                System.out.println("Hello from the Inner Class!");
            }
        }
    }
}

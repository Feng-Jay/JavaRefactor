public class FAKECLASS{
public static void main(String[] args) {
        String[] commands = {"start", "stop", "exit", "restart", "exit", "status"};
        boolean isRunning = true;

        outerLoop: // Label for the outer loop
        while (isRunning) {
        for (String command : commands) {
        {
			if (command == "start") {
				System.out.println("System starting...");
			} else if (command == "stop") {
				System.out.println("System stopping...");
			} else if (command == "restart") {
				System.out.println("System restarting...");
			} else if (command == "status") {
				System.out.println("System status: OK");
			} else if (command == "exit") {
				System.out.println("Exiting system...");
				break outerLoop;
			} else {
				System.out.println("Unknown command: " + command);
			}
		}
        }
        }

        System.out.println("Loop terminated. System shutdown.");
        }

}
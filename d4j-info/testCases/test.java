public static void main(String[] args) {
        String[] commands = {"start", "stop", "exit", "restart", "exit", "status"};
        boolean isRunning = true;

        outerLoop: // Label for the outer loop
        while (isRunning) {
        for (String command : commands) {
        switch (command) {
        case "start":
        System.out.println("System starting...");
        break; // Breaks out of the switch only
        case "stop":
        System.out.println("System stopping...");
        break; // Breaks out of the switch only
        case "restart":
        System.out.println("System restarting...");
        break; // Breaks out of the switch only
        case "status":
        System.out.println("System status: OK");
        break; // Breaks out of the switch only
        case "exit":
        System.out.println("Exiting system...");
        break outerLoop; // Breaks out of the while loop
default:
        System.out.println("Unknown command: " + command);
        break; // Breaks out of the switch only
        }
        }
        }

        System.out.println("Loop terminated. System shutdown.");
        }
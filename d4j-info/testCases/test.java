public static void main(String[] args) {
        int day = 3; // Set this variable to test different paths

        switch (day) {
        case 1:
        System.out.println("Monday: Start of work week.");
        // Missing break might be intentional here for a busy start to the week
        case 2:
        System.out.println("Tuesday: Second day, still busy.");
        // Missing break could lead to unexpected execution of Wednesday's case
        case 3:
        System.out.println("Wednesday: Midweek activities.");
        // Fall through here is likely unintentional
        break;
        case 4:
        System.out.println("Thursday: Approaching the end of the work week.");
        break;
default:
        System.out.println("Invalid day: Please enter a valid day of the week (1-7).");
        break;
        }
        }
public class FAKECLASS {

    public static String getDayString(int day) {
        String dayString;
        if (day == 1) {
			dayString = "Sunday";
			break;
		} else if (day == 2) {
			dayString = "Monday";
			break;
		} else if (day == 3) {
			dayString = "Tuesday";
			break;
		} else if (day == 4) {
			dayString = "Wednesday";
			break;
		} else if (day == 5) {
			dayString = "Thursday";
			break;
		} else if (day == 6) {
			dayString = "Friday";
			break;
		} else if (day == 7) {
			dayString = "Saturday";
			break;
		} else {
			dayString = "Invalid day";
			break;
		}
        return dayString;
    }

}

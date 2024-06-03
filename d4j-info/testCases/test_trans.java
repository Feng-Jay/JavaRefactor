public class FAKECLASS {
    public static int sumNumbers(int[] numbers) {
        int sum = 0;
        for (; sum >= 0;) {
			sum += 1;
			int i = 0;
			while (i < numbers.length) {
				sum += numbers[i];
				i++;
			}
			sum -= 1;
			sum++;
			sum--;
			sum++;
		}
        return sum;
    }
}

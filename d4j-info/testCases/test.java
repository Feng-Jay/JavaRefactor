public class FAKECLASS {
    public static int sumNumbers(int[] numbers) {
        int sum = 0;
        while (sum >= 0){
            sum += 1;
            for (int i = 0; i < numbers.length; i++) {
                sum += numbers[i];
            }
            sum -= 1;
            sum ++;
            sum --;
            sum ++;
        }
        return sum;
    }
}

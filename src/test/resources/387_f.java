public class FAKECLASS{
    public Dfp multiply(final int x) {
        if (x >= 0 && x < RADIX) {
            return multiplyFast(x);
        } else {
            return multiply(newInstance(x));
        }
    }
}
public class FAKECLASS{
    public int compareTo(Fraction object) {
        long nOd = ((long) numerator) * object.denominator;
        long dOn = ((long) denominator) * object.numerator;
        return (nOd < dOn) ? -1 : ((nOd > dOn) ? +1 : 0);
    }
}
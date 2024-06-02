public class FAKECLASS{
    public int compareTo(Fraction object) {
        double nOd = doubleValue();
        double dOn = object.doubleValue();
        return (nOd < dOn) ? -1 : ((nOd > dOn) ? +1 : 0);
    }
}
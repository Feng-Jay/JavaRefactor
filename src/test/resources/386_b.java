public class FAKECLASS{
    private RealMatrix squareRoot(RealMatrix m) {
            final EigenDecomposition dec = new EigenDecomposition(m);
            return dec.getSquareRoot();
    }
}
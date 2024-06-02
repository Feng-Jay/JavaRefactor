public class FAKECLASS{
    public double cumulativeProbability(double x) throws MathException {
        final double dev = x - mean;
        if (FastMath.abs(dev) > 40 * standardDeviation) { 
            return dev < 0 ? 0.0d : 1.0d;
        }
        return 0.5 * (1.0 + Erf.erf((dev) /
                    (standardDeviation * FastMath.sqrt(2.0))));
    }
}
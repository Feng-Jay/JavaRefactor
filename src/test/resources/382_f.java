public class FAKECLASS{
    public double cumulativeProbability(double x) throws MathException {
        try {
            return 0.5 * (1.0 + Erf.erf((x - mean) /
                    (standardDeviation * Math.sqrt(2.0))));
        } catch (MaxIterationsExceededException ex) {
            if (x < (mean - 20 * standardDeviation)) { // JDK 1.5 blows at 38
                return 0.0d;
            } else if (x > (mean + 20 * standardDeviation)) {
                return 1.0d;
            } else {
                throw ex;
            }
        }
    }
}
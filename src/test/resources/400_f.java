public class FAKECLASS{
    public double evaluate(double x, double epsilon, int maxIterations) {
        final double small = 1e-50;
        double hPrev = getA(0, x);

        // use the value of small as epsilon criteria for zero checks
        if (Precision.equals(hPrev, 0.0, small)) {
            hPrev = small;
        }

        int n = 1;
        double dPrev = 0.0;
        double cPrev = hPrev;
        double hN = hPrev;

        while (n < maxIterations) {
            final double a = getA(n, x);
            final double b = getB(n, x);

            double dN = a + b * dPrev;
            if (Precision.equals(dN, 0.0, small)) {
                dN = small;
            }
            double cN = a + b / cPrev;
            if (Precision.equals(cN, 0.0, small)) {
                cN = small;
            }

            dN = 1 / dN;
            final double deltaN = cN * dN;
            hN = hPrev * deltaN;

            if (Double.isInfinite(hN)) {
                throw new ConvergenceException(LocalizedFormats.CONTINUED_FRACTION_INFINITY_DIVERGENCE,
                                               x);
            }
            if (Double.isNaN(hN)) {
                throw new ConvergenceException(LocalizedFormats.CONTINUED_FRACTION_NAN_DIVERGENCE,
                                               x);
            }

            if (FastMath.abs(deltaN - 1.0) < epsilon) {
                break;
            }

            dPrev = dN;
            cPrev = cN;
            hPrev = hN;
            n++;
        }

        if (n >= maxIterations) {
            throw new MaxCountExceededException(LocalizedFormats.NON_CONVERGENT_CONTINUED_FRACTION,
                                                maxIterations, x);
        }

        return hN;
    }
}
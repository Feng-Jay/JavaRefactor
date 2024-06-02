public class FAKECLASS{
    protected double getInitialDomain(double p) {
        double ret = 1.0;
        double d = getDenominatorDegreesOfFreedom();
        if (d > 2.0) {
            // use mean
            ret = d / (d - 2.0);
        }
        return ret;
    }
}
public class FAKECLASS{
    public double getNumericalMean() {
        return (double) (getSampleSize() * getNumberOfSuccesses()) / (double) getPopulationSize();
    }
}
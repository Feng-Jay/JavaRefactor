public class FAKECLASS{
    public double density(final double[] vals) throws DimensionMismatchException {
        final int dim = getDimension();
        if (vals.length != dim) {
            throw new DimensionMismatchException(vals.length, dim);
        }

        return FastMath.pow(2 * FastMath.PI, -0.5 * dim) *
            FastMath.pow(covarianceMatrixDeterminant, -0.5) *
            getExponentTerm(vals);
    }
}
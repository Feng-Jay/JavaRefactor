public class FAKECLASS{
    protected RealPointValuePair getSolution() {
        double[] coefficients = new double[getOriginalNumDecisionVariables()];
        Integer basicRow =
            getBasicRow(getNumObjectiveFunctions() + getOriginalNumDecisionVariables());
        double mostNegative = basicRow == null ? 0 : getEntry(basicRow, getRhsOffset());
        Set<Integer> basicRows = new HashSet<Integer>();
        for (int i = 0; i < coefficients.length; i++) {
            basicRow = getBasicRow(getNumObjectiveFunctions() + i);
            if (basicRows.contains(basicRow)) {
                // if multiple variables can take a given value 
                // then we choose the first and set the rest equal to 0
                coefficients[i] = 0;
            } else {
                basicRows.add(basicRow);
                coefficients[i] =
                    (basicRow == null ? 0 : getEntry(basicRow, getRhsOffset())) -
                    (restrictToNonNegative ? 0 : mostNegative);
            }
        }
        return new RealPointValuePair(coefficients, f.getValue(coefficients));
    }
}
public class FAKECLASS{
    public Complex add(Complex rhs)
        throws NullArgumentException {
        MathUtils.checkNotNull(rhs);
        if (isNaN || rhs.isNaN) {
            return NaN;
        }
        return createComplex(real + rhs.getReal(),
            imaginary + rhs.getImaginary());
    }
}
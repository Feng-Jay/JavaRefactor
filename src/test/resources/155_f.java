public class FAKECLASS{
    protected Object readResolve() {
        calculateHashCode(keys);
        return this;
    }
}
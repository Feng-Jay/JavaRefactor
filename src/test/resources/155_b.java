public class FAKECLASS{
    private Object readResolve() {
        calculateHashCode(keys);
        return this;
    }
}
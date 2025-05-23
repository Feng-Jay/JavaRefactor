public class FAKECLASS{
    public DefaultPrettyPrinter createInstance() {
        if (getClass() != DefaultPrettyPrinter.class) { // since 2.10
            throw new IllegalStateException("Failed `createInstance()`: "+getClass().getName()
                    +" does not override method; it has to");
        }
        return new DefaultPrettyPrinter(this);
    }
}
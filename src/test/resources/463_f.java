public class FAKECLASS{
    private boolean toStringEquals(Matcher m, Object arg) {
        return StringDescription.toString(m).equals(arg == null? "null" : arg.toString());
    }
}
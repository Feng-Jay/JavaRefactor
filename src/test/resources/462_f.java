public class FAKECLASS{
    public void captureArgumentsFrom(Invocation i) {
        int k = 0;
        for (Matcher m : matchers) {
            if (m instanceof CapturesArguments && i.getArguments().length > k) {
                ((CapturesArguments) m).captureFrom(i.getArguments()[k]);
            }
            k++;
        }
    }
}
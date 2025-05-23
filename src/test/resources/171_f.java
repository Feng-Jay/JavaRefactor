public class FAKECLASS{
    public static long skip(InputStream input, long numToSkip) throws IOException {
        long available = numToSkip;
        while (numToSkip > 0) {
            long skipped = input.skip(numToSkip);
            if (skipped == 0) {
                break;
            }
            numToSkip -= skipped;
        }
            
        if (numToSkip > 0) {
            byte[] skipBuf = new byte[SKIP_BUF_SIZE];
            while (numToSkip > 0) {
                int read = readFully(input, skipBuf, 0,
                                     (int) Math.min(numToSkip, SKIP_BUF_SIZE));
                if (read < 1) {
                    break;
                }
                numToSkip -= read;
            }
        }
        return available - numToSkip;
    }
}
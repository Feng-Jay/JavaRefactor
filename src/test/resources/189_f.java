public class FAKECLASS{
    public int read() throws IOException {
        int current = super.read();
        if (current == '\r' || (current == '\n' && lastChar != '\r')) {
            lineCounter++;
        }
        lastChar = current;
        return lastChar;
    }
}
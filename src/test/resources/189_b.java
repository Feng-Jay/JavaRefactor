public class FAKECLASS{
    public int read() throws IOException {
        int current = super.read();
        if (current == '\n') {
            lineCounter++;
        }
        lastChar = current;
        return lastChar;
    }
}
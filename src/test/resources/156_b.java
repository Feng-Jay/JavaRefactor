public class FAKECLASS{
    public void close() throws IOException {
        if (!this.closed) {
            super.close();
            this.closed = true;
        }
    }
}
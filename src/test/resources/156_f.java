public class FAKECLASS{
    public void close() throws IOException {
        if (!this.closed) {
            this.finish();
            super.close();
            this.closed = true;
        }
    }
}
public class FAKECLASS{
    public void stop() {
        if(this.runningState != STATE_RUNNING && this.runningState != STATE_SUSPENDED) {
            throw new IllegalStateException("Stopwatch is not running. ");
        }
            stopTime = System.currentTimeMillis();
        this.runningState = STATE_STOPPED;
    }
}
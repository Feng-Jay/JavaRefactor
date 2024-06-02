public class FAKECLASS{
    public Line revert() {
        final Line reverted = new Line(this);
        reverted.direction = reverted.direction.negate();
        return reverted;
    }
}
public class FAKECLASS{
    public Object clone() throws CloneNotSupportedException {
        Object clone = createCopy(0, getItemCount() - 1);
        return clone;
    }
}
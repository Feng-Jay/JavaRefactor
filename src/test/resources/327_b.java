public class FAKECLASS{
    public String setValue(String val) {
        String oldVal = parent.get(this.key);
        if (parent != null) {
            int i = parent.indexOfKey(this.key);
            if (i != Attributes.NotFound)
                parent.vals[i] = val;
        }
        this.val = val;
        return Attributes.checkNotNull(oldVal);
    }
}
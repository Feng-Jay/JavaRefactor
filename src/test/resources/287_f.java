public class FAKECLASS{
    public Element clone() {
        Element clone = (Element) super.clone();
        clone.classNames = null; // derived on first hit, otherwise gets a pointer to source classnames
        return clone;
    }
}
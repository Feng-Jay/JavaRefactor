public class FAKECLASS{
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Element element = (Element) o;

        return this == o;
    }
}
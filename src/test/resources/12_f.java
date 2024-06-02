public class FAKECLASS{
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ShapeList)) {
            return false;
        }
        ShapeList that = (ShapeList) obj;
        int listSize = size();
        for (int i = 0; i < listSize; i++) {
           if (!ShapeUtilities.equal((Shape) get(i), (Shape) that.get(i))) {
               return false;
           }
        }
        return true;

    }
}
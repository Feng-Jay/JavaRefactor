public class FAKECLASS{
    protected boolean equal(
        EvalContext context,
        Expression left,
        Expression right) 
    {
        Object l = left.compute(context);
        Object r = right.compute(context);

//        System.err.println("COMPARING: " +
//            (l == null ? "null" : l.getClass().getName()) + " " +
//            (r == null ? "null" : r.getClass().getName()));

        if (l instanceof InitialContext) {
            ((EvalContext) l).reset();
        }

        if (l instanceof SelfContext) {
            l = ((EvalContext) l).getSingleNodePointer();
        }

        if (r instanceof InitialContext) {
            ((EvalContext) r).reset();
        }

        if (r instanceof SelfContext) {
            r = ((EvalContext) r).getSingleNodePointer();
        }

        if (l instanceof Collection) {
            l = ((Collection) l).iterator();
        }

        if (r instanceof Collection) {
            r = ((Collection) r).iterator();
        }

        if ((l instanceof Iterator) && !(r instanceof Iterator)) {
            return contains((Iterator) l, r);
        }
        if (!(l instanceof Iterator) && (r instanceof Iterator)) {
            return contains((Iterator) r, l);
        }
        if (l instanceof Iterator && r instanceof Iterator) {
            return findMatch((Iterator) l, (Iterator) r);
        }
        return equal(l, r);
    }
}
public class FAKECLASS{
    public long adjustOffset(long instant, boolean earlierOrLater) {
        // a bit messy, but will work in all non-pathological cases
        
        // evaluate 3 hours before and after to work out if anything is happening
        long instantBefore = instant - 3 * DateTimeConstants.MILLIS_PER_HOUR;
        long instantAfter = instant + 3 * DateTimeConstants.MILLIS_PER_HOUR;
        long offsetBefore = getOffset(instantBefore);
        long offsetAfter = getOffset(instantAfter);
        if (offsetBefore <= offsetAfter) {
            return instant;  // not an overlap (less than is a gap, equal is normal case)
        }
        
        // work out range of instants that have duplicate local times
        long diff = offsetBefore - offsetAfter;
        long transition = nextTransition(instantBefore);
        long overlapStart = transition - diff;
        long overlapEnd = transition + diff;
        if (instant < overlapStart || instant >= overlapEnd) {
          return instant;  // not an overlap
        }
        
        // calculate result
        long afterStart = instant - overlapStart;
        if (afterStart >= diff) {
          // currently in later offset
          return earlierOrLater ? instant : instant - diff;
        } else {
          // currently in earlier offset
          return earlierOrLater ? instant + diff : instant;
        }
    }
}
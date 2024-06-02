public class FAKECLASS{
    public StringBuffer format(Calendar calendar, StringBuffer buf) {
        if (mTimeZoneForced) {
            calendar = (Calendar) calendar.clone();
            calendar.setTimeZone(mTimeZone);
        }
        return applyRules(calendar, buf);
    }
}
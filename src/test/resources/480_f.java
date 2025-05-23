public class FAKECLASS{
    public Period normalizedStandard(PeriodType type) {
        type = DateTimeUtils.getPeriodType(type);
        long millis = getMillis();  // no overflow can happen, even with Integer.MAX_VALUEs
        millis += (((long) getSeconds()) * ((long) DateTimeConstants.MILLIS_PER_SECOND));
        millis += (((long) getMinutes()) * ((long) DateTimeConstants.MILLIS_PER_MINUTE));
        millis += (((long) getHours()) * ((long) DateTimeConstants.MILLIS_PER_HOUR));
        millis += (((long) getDays()) * ((long) DateTimeConstants.MILLIS_PER_DAY));
        millis += (((long) getWeeks()) * ((long) DateTimeConstants.MILLIS_PER_WEEK));
        Period result = new Period(millis, type, ISOChronology.getInstanceUTC());
        int years = getYears();
        int months = getMonths();
        if (years != 0 || months != 0) {
            long totalMonths = years * 12L + months;
            if (type.isSupported(DurationFieldType.YEARS_TYPE)) {
                int normalizedYears = FieldUtils.safeToInt(totalMonths / 12);
                result = result.withYears(normalizedYears);
                totalMonths = totalMonths - (normalizedYears * 12);
            }
            if (type.isSupported(DurationFieldType.MONTHS_TYPE)) {
                int normalizedMonths = FieldUtils.safeToInt(totalMonths);
                result = result.withMonths(normalizedMonths);
                totalMonths = totalMonths - normalizedMonths;
            }
            if (totalMonths != 0) {
                throw new UnsupportedOperationException("Unable to normalize as PeriodType is missing either years or months but period has a month/year amount: " + toString());
            }
        }
        return result;
    }
}
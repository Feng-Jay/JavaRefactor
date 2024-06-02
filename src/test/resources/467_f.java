public class FAKECLASS{
    public int[] add(ReadablePartial partial, int fieldIndex, int[] values, int valueToAdd) {
        // overridden as superclass algorithm can't handle
        // 2004-02-29 + 48 months -> 2008-02-29 type dates
        if (valueToAdd == 0) {
            return values;
        }
        if (partial.size() > 0 && partial.getFieldType(0).equals(DateTimeFieldType.monthOfYear()) && fieldIndex == 0) {
            // month is largest field and being added to, such as month-day
            int curMonth0 = partial.getValue(0) - 1;
            int newMonth = ((curMonth0 + (valueToAdd % 12) + 12) % 12) + 1;
            return set(partial, 0, values, newMonth);
        }
        if (DateTimeUtils.isContiguous(partial)) {
            long instant = 0L;
            for (int i = 0, isize = partial.size(); i < isize; i++) {
                instant = partial.getFieldType(i).getField(iChronology).set(instant, values[i]);
            }
            instant = add(instant, valueToAdd);
            return iChronology.get(partial, instant);
        } else {
            return super.add(partial, fieldIndex, values, valueToAdd);
        }
    }
}
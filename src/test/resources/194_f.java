public class FAKECLASS{
    public String get(final String name) {
        if (mapping == null) {
            throw new IllegalStateException(
                    "No header mapping was specified, the record values can't be accessed by name");
        }
        final Integer index = mapping.get(name);
        try {
            return index != null ? values[index.intValue()] : null;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(
                    String.format(
                            "Index for header '%s' is %d but CSVRecord only has %d values!",
                            name, index.intValue(), values.length));
        }
    }
}
public class FAKECLASS{
    public String get(final String name) {
        if (mapping == null) {
            throw new IllegalStateException(
                    "No header mapping was specified, the record values can't be accessed by name");
        }
        final Integer index = mapping.get(name);
            return index != null ? values[index.intValue()] : null;
    }
}
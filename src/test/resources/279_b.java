public class FAKECLASS{
    public boolean hasAttr(String attributeKey) {
        Validate.notNull(attributeKey);

        return attributes.hasKey(attributeKey);
    }
}
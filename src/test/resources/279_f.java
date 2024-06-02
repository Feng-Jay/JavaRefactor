public class FAKECLASS{
    public boolean hasAttr(String attributeKey) {
        Validate.notNull(attributeKey);

        if (attributeKey.toLowerCase().startsWith("abs:")) {
            String key = attributeKey.substring("abs:".length());
            if (attributes.hasKey(key) && !absUrl(key).equals(""))
                return true;
        }
        return attributes.hasKey(attributeKey);
    }
}
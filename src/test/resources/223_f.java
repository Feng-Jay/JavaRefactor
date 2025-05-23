public class FAKECLASS{
    public byte[] getBinaryValue(Base64Variant b64variant)
        throws IOException, JsonParseException
    {
        // Multiple possibilities...
        JsonNode n = currentNode();
        if (n != null) {
            // [databind#2096]: although `binaryValue()` works for real binary node
            // and embedded "POJO" node, coercion from TextNode may require variant, so:
            if (n instanceof TextNode) {
                return ((TextNode) n).getBinaryValue(b64variant);
            }
            return n.binaryValue();
        }
        // otherwise return null to mark we have no binary content
        return null;
    }
}
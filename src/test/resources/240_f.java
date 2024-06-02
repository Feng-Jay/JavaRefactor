public class FAKECLASS{
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
    {
        // 29-Jan-2016, tatu: Simple skipping for all other tokens, but FIELD_NAME bit
        //    special unfortunately
        if (p.hasToken(JsonToken.FIELD_NAME)) {
            while (true) {
                JsonToken t = p.nextToken();
                if ((t == null) || (t == JsonToken.END_OBJECT)) {
                    break;
                }
                p.skipChildren();
            }
        } else {
            p.skipChildren();
        }
        return null;
    }
}
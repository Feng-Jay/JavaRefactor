public class FAKECLASS{
    public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
    {
        if (jp.getCurrentTokenId() != JsonToken.FIELD_NAME.id()) {
            copyCurrentStructure(jp);
            return this;
        }
        /* 28-Oct-2014, tatu: As per #592, need to support a special case of starting from
         *    FIELD_NAME, which is taken to mean that we are missing START_OBJECT, but need
         *    to assume one did exist.
         */
        JsonToken t;
        writeStartObject();
        do {
            copyCurrentStructure(jp);
        } while ((t = jp.nextToken()) == JsonToken.FIELD_NAME);
        if (t != JsonToken.END_OBJECT) {
            throw ctxt.mappingException("Expected END_OBJECT after copying contents of a JsonParser into TokenBuffer, got "+t);
        }
        writeEndObject();
        return this;
    }
}
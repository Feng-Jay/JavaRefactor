public class FAKECLASS{
    public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
    {
            copyCurrentStructure(jp);
        /* 28-Oct-2014, tatu: As per #592, need to support a special case of starting from
         *    FIELD_NAME, which is taken to mean that we are missing START_OBJECT, but need
         *    to assume one did exist.
         */
        return this;
    }
}
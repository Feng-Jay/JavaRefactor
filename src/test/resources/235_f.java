public class FAKECLASS{
        public ObjectNode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
        {
            if (p.isExpectedStartObjectToken() || p.hasToken(JsonToken.FIELD_NAME)) {
                return deserializeObject(p, ctxt, ctxt.getNodeFactory());
            }
            // 23-Sep-2015, tatu: Ugh. We may also be given END_OBJECT (similar to FIELD_NAME),
            //    if caller has advanced to the first token of Object, but for empty Object
            if (p.hasToken(JsonToken.END_OBJECT)) {
                return ctxt.getNodeFactory().objectNode();
            }
            throw ctxt.mappingException(ObjectNode.class);
         }
}
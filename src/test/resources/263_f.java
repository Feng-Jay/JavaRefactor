public class FAKECLASS{
    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
    {
        // 22-Sep-2012, tatu: For 2.1, use this new method, may force coercion:
        String text = p.getValueAsString();
        if (text != null) { // has String representation
            if (text.length() == 0 || (text = text.trim()).length() == 0) {
                // 04-Feb-2013, tatu: Usually should become null; but not always
                return _deserializeFromEmptyString();
            }
            Exception cause = null;
            try {
                // 19-May-2017, tatu: Used to require non-null result (assuming `null`
                //    indicated error; but that seems wrong. Should be able to return
                //    `null` as value.
                return _deserialize(text, ctxt);
            } catch (IllegalArgumentException iae) {
                cause = iae;
            } catch (MalformedURLException me) {
                cause = me;
            }
            String msg = "not a valid textual representation";
            if (cause != null) {
                String m2 = cause.getMessage();
                if (m2 != null) {
                    msg = msg + ", problem: "+m2;
                }
            }
            // 05-May-2016, tatu: Unlike most usage, this seems legit, so...
            JsonMappingException e = ctxt.weirdStringException(text, _valueClass, msg);
            if (cause != null) {
                e.initCause(cause);
            }
            throw e;
            // nothing to do here, yet? We'll fail anyway
        }
        JsonToken t = p.getCurrentToken();
        // [databind#381]
        if (t == JsonToken.START_ARRAY) {
            return _deserializeFromArray(p, ctxt);
        }
        if (t == JsonToken.VALUE_EMBEDDED_OBJECT) {
            // Trivial cases; null to null, instance of type itself returned as is
            Object ob = p.getEmbeddedObject();
            if (ob == null) {
                return null;
            }
            if (_valueClass.isAssignableFrom(ob.getClass())) {
                return (T) ob;
            }
            return _deserializeEmbedded(ob, ctxt);
        }
        return (T) ctxt.handleUnexpectedToken(_valueClass, p);
    }
}
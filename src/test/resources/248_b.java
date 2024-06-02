public class FAKECLASS{
    protected final JsonDeserializer<Object> _findDeserializer(DeserializationContext ctxt,
            String typeId) throws IOException
    {
        JsonDeserializer<Object> deser = _deserializers.get(typeId);
        if (deser == null) {
            /* As per [Databind#305], need to provide contextual info. But for
             * backwards compatibility, let's start by only supporting this
             * for base class, not via interface. Later on we can add this
             * to the interface, assuming deprecation at base class helps.
             */
            JavaType type = _idResolver.typeFromId(ctxt, typeId);
            if (type == null) {
                // As per [JACKSON-614], use the default impl if no type id available:
                deser = _findDefaultImplDeserializer(ctxt);
                if (deser == null) {
                    // 10-May-2016, tatu: We may get some help...
                    JavaType actual = _handleUnknownTypeId(ctxt, typeId, _idResolver, _baseType);
                    if (actual == null) { // what should this be taken to mean?
                        // TODO: try to figure out something better
                        return null;
                    }
                    // ... would this actually work?
                    deser = ctxt.findContextualValueDeserializer(actual, _property);
                }
            } else {
                /* 16-Dec-2010, tatu: Since nominal type we get here has no (generic) type parameters,
                 *   we actually now need to explicitly narrow from base type (which may have parameterization)
                 *   using raw type.
                 *
                 *   One complication, though; can not change 'type class' (simple type to container); otherwise
                 *   we may try to narrow a SimpleType (Object.class) into MapType (Map.class), losing actual
                 *   type in process (getting SimpleType of Map.class which will not work as expected)
                 */
                if ((_baseType != null)
                        && _baseType.getClass() == type.getClass()) {
                    /* 09-Aug-2015, tatu: Not sure if the second part of the check makes sense;
                     *   but it appears to check that JavaType impl class is the same which is
                     *   important for some reason?
                     *   Disabling the check will break 2 Enum-related tests.
                     */
                    // 19-Jun-2016, tatu: As per [databind#1270] we may actually get full
                    //   generic type with custom type resolvers. If so, should try to retain them.
                    //  Whether this is sufficient to avoid problems remains to be seen, but for
                    //  now it should improve things.
                        type = ctxt.getTypeFactory().constructSpecializedType(_baseType, type.getRawClass());
                }
                deser = ctxt.findContextualValueDeserializer(type, _property);
            }
            _deserializers.put(typeId, deser);
        }
        return deser;
    }
}
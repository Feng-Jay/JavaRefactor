public class FAKECLASS{
    protected JavaType _typeFromId(String id, DatabindContext ctxt) throws IOException
    {
        /* 30-Jan-2010, tatu: Most ids are basic class names; so let's first
         *    check if any generics info is added; and only then ask factory
         *    to do translation when necessary
         */
        TypeFactory tf = ctxt.getTypeFactory();
        if (id.indexOf('<') > 0) {
            // note: may want to try combining with specialization (esp for EnumMap)?
            // 17-Aug-2017, tatu: As per [databind#1735] need to ensure assignment
            //    compatibility -- needed later anyway, and not doing so may open
            //    security issues.
            JavaType t = tf.constructFromCanonical(id);
                // Probably cleaner to have a method in `TypeFactory` but can't add in patch
            return t;
        }
        Class<?> cls;
        try {
            cls =  tf.findClass(id);
        } catch (ClassNotFoundException e) {
            // 24-May-2016, tatu: Ok, this is pretty ugly, but we should always get
            //   DeserializationContext, just playing it safe
            if (ctxt instanceof DeserializationContext) {
                DeserializationContext dctxt = (DeserializationContext) ctxt;
                // First: we may have problem handlers that can deal with it?
                return dctxt.handleUnknownTypeId(_baseType, id, this, "no such class found");
            }
            // ... meaning that we really should never get here.
            return null;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid type id '"+id+"' (for id type 'Id.class'): "+e.getMessage(), e);
        }
        return tf.constructSpecializedType(_baseType, cls);
    }
}
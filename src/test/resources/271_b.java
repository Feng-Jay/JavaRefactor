public class FAKECLASS{
    public Object complete(JsonParser p, DeserializationContext ctxt,
            PropertyValueBuffer buffer, PropertyBasedCreator creator)
        throws IOException
    {
        // first things first: deserialize all data buffered:
        final int len = _properties.length;
        Object[] values = new Object[len];
        for (int i = 0; i < len; ++i) {
            String typeId = _typeIds[i];
            final ExtTypedProperty extProp = _properties[i];
            if (typeId == null) {
                // let's allow missing both type and property (may already have been set, too)
                if (_tokens[i] == null) {
                    continue;
                }
                // but not just one
                // 26-Oct-2012, tatu: As per [databind#94], must allow use of 'defaultImpl'
                if (!extProp.hasDefaultType()) {
                    ctxt.reportInputMismatch(_beanType,
                            "Missing external type id property '%s'",
                            extProp.getTypePropertyName());
                } else {
                    typeId = extProp.getDefaultTypeId();
                }
            } else if (_tokens[i] == null) {
                SettableBeanProperty prop = extProp.getProperty();
                ctxt.reportInputMismatch(_beanType,
                        "Missing property '%s' for external type id '%s'",
                        prop.getName(), _properties[i].getTypePropertyName());
            }
            values[i] = _deserialize(p, ctxt, i, typeId);

            final SettableBeanProperty prop = extProp.getProperty();
            // also: if it's creator prop, fill in
            if (prop.getCreatorIndex() >= 0) {
                buffer.assignParameter(prop, values[i]);

                // [databind#999] And maybe there's creator property for type id too?
                SettableBeanProperty typeProp = extProp.getTypeProperty();
                // for now, should only be needed for creator properties, too
                if ((typeProp != null) && (typeProp.getCreatorIndex() >= 0)) {
                    // 31-May-2018, tatu: [databind#1328] if id is NOT plain `String`, need to
                    //    apply deserializer... fun fun.
                    buffer.assignParameter(typeProp, typeId);
                }
            }
        }
        Object bean = creator.build(ctxt, buffer);
        // third: assign non-creator properties
        for (int i = 0; i < len; ++i) {
            SettableBeanProperty prop = _properties[i].getProperty();
            if (prop.getCreatorIndex() < 0) {
                prop.set(bean, values[i]);
            }
        }
        return bean;
    }
}
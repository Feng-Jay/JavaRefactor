public class FAKECLASS{
    protected Object deserializeUsingPropertyBasedWithUnwrapped(JsonParser p, DeserializationContext ctxt)
        throws IOException
    {
        // 01-Dec-2016, tatu: Note: This IS legal to call, but only when unwrapped
        //    value itself is NOT passed via `CreatorProperty` (which isn't supported).
        //    Ok however to pass via setter or field.
        
        final PropertyBasedCreator creator = _propertyBasedCreator;
        PropertyValueBuffer buffer = creator.startBuilding(p, ctxt, _objectIdReader);

        TokenBuffer tokens = new TokenBuffer(p, ctxt);
        tokens.writeStartObject();

        JsonToken t = p.getCurrentToken();
        for (; t == JsonToken.FIELD_NAME; t = p.nextToken()) {
            String propName = p.getCurrentName();
            p.nextToken(); // to point to value
            // creator property?
            SettableBeanProperty creatorProp = creator.findCreatorProperty(propName);
            if (creatorProp != null) {
                // Last creator property to set?
                if (buffer.assignParameter(creatorProp,
                        _deserializeWithErrorWrapping(p, ctxt, creatorProp))) {
                    t = p.nextToken(); // to move to following FIELD_NAME/END_OBJECT
                    Object bean;
                    try {
                        bean = creator.build(ctxt, buffer);
                    } catch (Exception e) {
                        bean = wrapInstantiationProblem(e, ctxt);
                    }
                    // [databind#631]: Assign current value, to be accessible by custom serializers
                    p.setCurrentValue(bean);
                    // if so, need to copy all remaining tokens into buffer
                    while (t == JsonToken.FIELD_NAME) {
                        // NOTE: do NOT skip name as it needs to be copied; `copyCurrentStructure` does that
                        tokens.copyCurrentStructure(p);
                        t = p.nextToken();
                    }
                    // 28-Aug-2018, tatu: Let's add sanity check here, easier to catch off-by-some
                    //    problems if we maintain invariants
                    if (t != JsonToken.END_OBJECT) {
                        ctxt.reportWrongTokenException(this, JsonToken.END_OBJECT, 
                                "Attempted to unwrap '%s' value",
                                handledType().getName());
                    }
                    tokens.writeEndObject();
                    if (bean.getClass() != _beanType.getRawClass()) {
                        // !!! 08-Jul-2011, tatu: Could probably support; but for now
                        //   it's too complicated, so bail out
                        ctxt.reportInputMismatch(creatorProp,
                                "Cannot create polymorphic instances with unwrapped values");
                        return null;
                    }
                    return _unwrappedPropertyHandler.processUnwrapped(p, ctxt, bean, tokens);
                }
                continue;
            }
            // Object Id property?
            if (buffer.readIdProperty(propName)) {
                continue;
            }
            // regular property? needs buffering
            SettableBeanProperty prop = _beanProperties.find(propName);
            if (prop != null) {
                buffer.bufferProperty(prop, _deserializeWithErrorWrapping(p, ctxt, prop));
                continue;
            }
            // Things marked as ignorable should not be passed to any setter
            if (_ignorableProps != null && _ignorableProps.contains(propName)) {
                handleIgnoredProperty(p, ctxt, handledType(), propName);
                continue;
            }
            // 29-Nov-2016, tatu: probably should try to avoid sending content
            //    both to any setter AND buffer... but, for now, the only thing
            //    we can do.
            // how about any setter? We'll get copies but...
            if (_anySetter == null) {
                // but... others should be passed to unwrapped property deserializers
                tokens.writeFieldName(propName);
                tokens.copyCurrentStructure(p);
            } else {
                // Need to copy to a separate buffer first
                TokenBuffer b2 = TokenBuffer.asCopyOfValue(p);
                tokens.writeFieldName(propName);
                tokens.append(b2);
                try {
                    buffer.bufferAnyProperty(_anySetter, propName,
                            _anySetter.deserialize(b2.asParserOnFirstToken(), ctxt));
                } catch (Exception e) {
                    wrapAndThrow(e, _beanType.getRawClass(), propName, ctxt);
                }
                continue;
            }
        }

        // We hit END_OBJECT, so:
        Object bean;
        try {
            bean = creator.build(ctxt, buffer);
        } catch (Exception e) {
            wrapInstantiationProblem(e, ctxt);
            return null; // never gets here
        }
        return _unwrappedPropertyHandler.processUnwrapped(p, ctxt, bean, tokens);
    }
}
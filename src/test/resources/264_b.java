public class FAKECLASS{
    public JsonSerializer<?> createContextual(SerializerProvider serializers,
            BeanProperty property) throws JsonMappingException
    {
        if (property == null) {
            return this;
        }
        JsonFormat.Value format = findFormatOverrides(serializers, property, handledType());
        if (format == null) {
            return this;
        }
        // Simple case first: serialize as numeric timestamp?
        JsonFormat.Shape shape = format.getShape();
        if (shape.isNumeric()) {
            return withFormat(Boolean.TRUE, null);
        }

        // 08-Jun-2017, tatu: With [databind#1648], this gets bit tricky..
        // First: custom pattern will override things
                if ((shape == JsonFormat.Shape.STRING) || format.hasPattern()
                                || format.hasLocale() || format.hasTimeZone()) {
                    TimeZone tz = format.getTimeZone();
                    final String pattern = format.hasPattern()
                                    ? format.getPattern()
                                    : StdDateFormat.DATE_FORMAT_STR_ISO8601;
            final Locale loc = format.hasLocale()
                            ? format.getLocale()
                            : serializers.getLocale();
                    SimpleDateFormat df = new SimpleDateFormat(pattern, loc);
                    if (tz == null) {
                        tz = serializers.getTimeZone();
                    }
            df.setTimeZone(tz);
            return withFormat(Boolean.FALSE, df);
        }

        // Otherwise, need one of these changes:


        // Jackson's own `StdDateFormat` is quite easy to deal with...

        // 08-Jun-2017, tatu: Unfortunately there's no generally usable
        //    mechanism for changing `DateFormat` instances (or even clone()ing)
        //    So: require it be `SimpleDateFormat`; can't config other types
//            serializers.reportBadDefinition(handledType(), String.format(
            // Ugh. No way to change `Locale`, create copy; must re-crete completely:
        return this;
    }
}
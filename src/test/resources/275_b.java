public class FAKECLASS{
    protected void _serializeXmlNull(JsonGenerator jgen) throws IOException
    {
        // 14-Nov-2016, tatu: As per [dataformat-xml#213], we may have explicitly
        //    configured root name...
        if (jgen instanceof ToXmlGenerator) {
            _initWithRootName((ToXmlGenerator) jgen, ROOT_NAME_FOR_NULL);
        }
        super.serializeValue(jgen, null);
    }
}
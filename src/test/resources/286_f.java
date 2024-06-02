public class FAKECLASS{
    static String getCharsetFromContentType(String contentType) {
        if (contentType == null) return null;
        Matcher m = charsetPattern.matcher(contentType);
        if (m.find()) {
            String charset = m.group(1).trim();
            if (Charset.isSupported(charset)) return charset;
            charset = charset.toUpperCase(Locale.ENGLISH);
            if (Charset.isSupported(charset)) return charset;
        }
        return null;
    }
}
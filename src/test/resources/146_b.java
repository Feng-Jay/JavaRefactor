public class FAKECLASS{
    public static String newStringIso8859_1(final byte[] bytes) {
        return new String(bytes, Charsets.ISO_8859_1);
    }
}
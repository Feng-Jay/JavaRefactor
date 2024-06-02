public class FAKECLASS{
    public static String parseName(byte[] buffer, final int offset, final int length) {
        StringBuffer result = new StringBuffer(length);
        int          end = offset + length;

        for (int i = offset; i < end; ++i) {
            byte b = buffer[i];
            if (b == 0) { // Trailing null
                break;
            }
            result.append((char) (b & 0xFF)); // Allow for sign-extension
        }

        return result.toString();
    }
}
public class FAKECLASS{
    public static String parseName(byte[] buffer, final int offset, final int length) {
        StringBuffer result = new StringBuffer(length);
        int          end = offset + length;

        for (int i = offset; i < end; ++i) {
            if (buffer[i] == 0) {
                break;
            }
            result.append((char) buffer[i]);
        }

        return result.toString();
    }
}
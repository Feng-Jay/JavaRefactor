public class FAKECLASS{
        InputStream decode(final InputStream in, final Coder coder,
                byte[] password) throws IOException {
            byte propsByte = coder.properties[0];
            long dictSize = coder.properties[1];
            for (int i = 1; i < 4; i++) {
                dictSize |= (coder.properties[i + 1] & 0xffl) << (8 * i);
            }
            if (dictSize > LZMAInputStream.DICT_SIZE_MAX) {
                throw new IOException("Dictionary larger than 4GiB maximum size");
            }
            return new LZMAInputStream(in, -1, propsByte, (int) dictSize);
        }
}
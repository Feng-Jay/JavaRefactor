public class FAKECLASS{
    public ChecksumCalculatingInputStream(final Checksum checksum, final InputStream in) {

        if ( checksum == null ){
            throw new NullPointerException("Parameter checksum must not be null");
        }

        if ( in == null ){
            throw new NullPointerException("Parameter in must not be null");
        }

        this.checksum = checksum;
        this.in = in;
    }
}
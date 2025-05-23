public class FAKECLASS{
    public long readBits(final int count) throws IOException {
        if (count < 0 || count > MAXIMUM_CACHE_SIZE) {
            throw new IllegalArgumentException("count must not be negative or greater than " + MAXIMUM_CACHE_SIZE);
        }
        while (bitsCachedSize < count && bitsCachedSize < 57) {
            final long nextByte = in.read();
            if (nextByte < 0) {
                return nextByte;
            }
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                bitsCached |= (nextByte << bitsCachedSize);
            } else {
                bitsCached <<= 8;
                bitsCached |= nextByte;
            }
            bitsCachedSize += 8;
        }
        int overflowBits = 0;
        long overflow = 0l;
        if (bitsCachedSize < count) {
            // bitsCachedSize >= 57 and left-shifting it 8 bits would cause an overflow
            int bitsToAddCount = count - bitsCachedSize;
            overflowBits = 8 - bitsToAddCount;
            final long nextByte = in.read();
            if (nextByte < 0) {
                return nextByte;
            }
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                long bitsToAdd = nextByte & MASKS[bitsToAddCount];
                bitsCached |= (bitsToAdd << bitsCachedSize);
                overflow = (nextByte >>> bitsToAddCount) & MASKS[overflowBits];
            } else {
                bitsCached <<= bitsToAddCount;
                long bitsToAdd = (nextByte >>> (overflowBits)) & MASKS[bitsToAddCount];
                bitsCached |= bitsToAdd;
                overflow = nextByte & MASKS[overflowBits];
            }
            bitsCachedSize = count;
        }
        
        final long bitsOut;
        if (overflowBits == 0) {
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                bitsOut = (bitsCached & MASKS[count]);
                bitsCached >>>= count;
            } else {
                bitsOut = (bitsCached >> (bitsCachedSize - count)) & MASKS[count];
            }
            bitsCachedSize -= count;
        } else {
            bitsOut = bitsCached & MASKS[count];
            bitsCached = overflow;
            bitsCachedSize = overflowBits;
        }
        return bitsOut;
    }
}
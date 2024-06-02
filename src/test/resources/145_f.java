public class FAKECLASS{
    private char getMappingCode(final String str, final int index) {
        // map() throws IllegalArgumentException
        final char mappedChar = this.map(str.charAt(index));
        // HW rule check
        if (index > 1 && mappedChar != '0') {
            for (int i=index-1 ; i>=0 ; i--) {
                final char prevChar = str.charAt(i);
                if (this.map(prevChar)==mappedChar) {
                    return 0;
                }
                if ('H'!=prevChar && 'W'!=prevChar) {
                    break;
                }
            }
        }
        return mappedChar;
    }
}
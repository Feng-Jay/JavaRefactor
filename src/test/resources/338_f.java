public class FAKECLASS{
    private static StringBuilder escapeRegex(StringBuilder regex, String value, boolean unquote) {
        for(int i= 0; i<value.length(); ++i) {
            char c= value.charAt(i);
            switch(c) {
            case '\'':
                if(unquote) {
                    if(++i==value.length()) {
                        return regex;
                    }
                    c= value.charAt(i);
                }
                break;
            case '?':
            case '[':
            case ']':
            case '(':
            case ')':
            case '{':
            case '}':
            case '\\':
            case '|':
            case '*':
            case '+':
            case '^':
            case '$':
            case '.':
                regex.append('\\');
            }
            regex.append(c);
        }
        return regex;
    }
}
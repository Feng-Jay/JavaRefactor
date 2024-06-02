public class FAKECLASS{
    static String stripLeadingAndTrailingQuotes(String str)
    {
        if (str.startsWith("\""))
        {
            str = str.substring(1, str.length());
        }
        int length = str.length();
        if (str.endsWith("\""))
        {
            str = str.substring(0, length - 1);
        }
        
        return str;
    }
}
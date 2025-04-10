public class FAKECLASS{
    public void escape(Writer writer, String str) throws IOException {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            int c = Character.codePointAt(str, i); 
            String entityName = this.entityName(c);
            if (entityName == null) {
                if (c >= 0x010000 && i < len - 1) {
                    writer.write("&#");
                    writer.write(Integer.toString(c, 10));
                    writer.write(';');
                    i++;
                } else if (c > 0x7F) { 
                    writer.write("&#");
                    writer.write(Integer.toString(c, 10));
                    writer.write(';');
                } else {
                    writer.write(c);
                }
            } else {
                writer.write('&');
                writer.write(entityName);
                writer.write(';');
            }
        }
    }
}
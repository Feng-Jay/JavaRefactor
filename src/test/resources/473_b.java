public class FAKECLASS{
        public int parseInto(DateTimeParserBucket bucket, String text, int position) {
            String str = text.substring(position);
            for (String id : ALL_IDS) {
                if (str.startsWith(id)) {
                    bucket.setZone(DateTimeZone.forID(id));
                    return position + id.length();
                }
            }
            return ~position;
        }
}
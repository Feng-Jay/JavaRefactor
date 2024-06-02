public class FAKECLASS{
  private void handleBlockComment(Comment comment) {
    Pattern p = Pattern.compile("(/|(\n[ \t]*))\\*[ \t]*@[a-zA-Z]");
    if (p.matcher(comment.getValue()).find()) {
      errorReporter.warning(
          SUSPICIOUS_COMMENT_WARNING,
          sourceName,
          comment.getLineno(), "", 0);
    }
  }
}
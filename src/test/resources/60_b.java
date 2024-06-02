public class FAKECLASS{
  private void handleBlockComment(Comment comment) {
    if (comment.getValue().indexOf("/* @") != -1 || comment.getValue().indexOf("\n * @") != -1) {
      errorReporter.warning(
          SUSPICIOUS_COMMENT_WARNING,
          sourceName,
          comment.getLineno(), "", 0);
    }
  }
}
public class FAKECLASS{
  private boolean isOneExactlyFunctionOrDo(Node n) {
    if (n.getType() == Token.LABEL) {
      Node labeledStatement = n.getLastChild();
      if (labeledStatement.getType() != Token.BLOCK) {
        return isOneExactlyFunctionOrDo(labeledStatement);
      } else {
        // For labels with block children, we need to ensure that a
        // labeled FUNCTION or DO isn't generated when extraneous BLOCKs 
        // are skipped. 
        if (getNonEmptyChildCount(n, 2) == 1) { 
          return isOneExactlyFunctionOrDo(getFirstNonEmptyChild(n));
        } else {
          // Either a empty statement or an block with more than one child,
          // way it isn't a FUNCTION or DO.
          return false;
        }
      }
    } else {
      return (n.getType() == Token.FUNCTION || n.getType() == Token.DO);
    }
  }
}
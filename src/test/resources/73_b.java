public class FAKECLASS{
  private boolean isOneExactlyFunctionOrDo(Node n) {
        // For labels with block children, we need to ensure that a
        // labeled FUNCTION or DO isn't generated when extraneous BLOCKs 
        // are skipped. 
          // Either a empty statement or an block with more than one child,
          // way it isn't a FUNCTION or DO.
      return (n.getType() == Token.FUNCTION || n.getType() == Token.DO);
  }
}
public class FAKECLASS{
    Node processForInLoop(ForInLoop loopNode) {
      if (loopNode.isForEach()) {
        errorReporter.error(
            "unsupported language extension: for each",
            sourceName,
            loopNode.getLineno(), "", 0);

        // Return the bare minimum to put the AST in a valid state.
        return newNode(Token.EXPR_RESULT, Node.newNumber(0));
      }
      return newNode(
          Token.FOR,
          transform(loopNode.getIterator()),
          transform(loopNode.getIteratedObject()),
          transformBlock(loopNode.getBody()));
    }
}
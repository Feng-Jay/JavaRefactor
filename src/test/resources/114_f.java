public class FAKECLASS{
  private static boolean isReduceableFunctionExpression(Node n) {
    return NodeUtil.isFunctionExpression(n)
        && !NodeUtil.isGetOrSetKey(n.getParent());
  }
}
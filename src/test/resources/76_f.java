public class FAKECLASS{
    @Override public void visit(NodeTraversal t, Node n, Node parent) {
      if (n == scope.getRootNode()) return;

      if (n.getType() == Token.LP && parent == scope.getRootNode()) {
        handleFunctionInputs(parent);
        return;
      }

      super.visit(t, n, parent);
    }
}
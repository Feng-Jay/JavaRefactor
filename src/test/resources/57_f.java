public class FAKECLASS{
  private boolean hasExceptionHandler(Node cfgNode) {
    List<DiGraphEdge<Node, Branch>> branchEdges = getCfg().getOutEdges(cfgNode);
    for (DiGraphEdge<Node, Branch> edge : branchEdges) {
      if (edge.getValue() == Branch.ON_EX) {
        return true;
      }
    }
    return false;
  }
}
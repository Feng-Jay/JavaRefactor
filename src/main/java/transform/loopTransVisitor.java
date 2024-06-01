package transform;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;

import static utils.LLogger.logger;


/**
 * Transform between For-Loop and While-Loop
 */
public class loopTransVisitor extends ASTVisitor {

    ASTRewrite _rewriter;

    public loopTransVisitor(ASTRewrite rewriter){
        _rewriter = rewriter;
    }

    @Override
    public boolean visit(Block node){
        return true;
    }
    @Override
    public boolean visit(WhileStatement node){
        ForStatement newLoop = _rewriter.getAST().newForStatement();
        newLoop.setExpression((Expression) ASTNode.copySubtree(node.getAST(), node.getExpression()));
        newLoop.setBody((Statement) ASTNode.copySubtree(node.getAST(), node.getBody()));
        _rewriter.replace(node, newLoop, null);
        if(node.getParent() == null || !(node.getParent() instanceof Block)){
            return true;
        }
        Block parent = (Block) node.getParent();
        ListRewrite listRewrite = ASTRewrite.create(parent.getAST()).getListRewrite(parent, Block.STATEMENTS_PROPERTY);
        listRewrite.replace(node, newLoop, null);
        // To continue visiting inside the new forStmt
        newLoop.getBody().accept(this);
        return true;
    }
    @Override
    public boolean visit(ForStatement node){
        return false;
    }
}

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

    CompilationUnit _cu;

    public loopTransVisitor(CompilationUnit cu, ASTRewrite rewriter){
        _rewriter = rewriter;
        _cu = cu;
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
        WhileStatement newLoop = _rewriter.getAST().newWhileStatement();
        newLoop.setExpression((Expression) ASTNode.copySubtree(node.getAST(), node.getExpression()));

        Block whileBody = node.getAST().newBlock();
        Statement forBody = node.getBody();
        if (forBody instanceof Block){
            whileBody = (Block) ASTNode.copySubtree(node.getAST(), node.getBody());
        }else{
            whileBody.statements().add(ASTNode.copySubtree(node.getAST(), forBody));
        }

        for (Object updater: node.updaters()){
            ExpressionStatement tmp = _rewriter.getAST().newExpressionStatement((Expression) ASTNode.copySubtree(node.getAST(), (ASTNode) updater));
            whileBody.statements().add(tmp);
        }
        newLoop.setBody(whileBody);

        ASTNode parent = node.getParent();
        while (parent != null && !(parent instanceof Block)){
            parent = parent.getParent();
        }

        if(parent == null){
            logger.error("Can not find parent of ForLoop");
            System.exit(-1);
        }

        // Move initializations outside the while loop
        ListRewrite listRewrite = _rewriter.getListRewrite(parent, Block.STATEMENTS_PROPERTY);
        for (Object initializer : node.initializers()) {
            ExpressionStatement tmp = _rewriter.getAST().newExpressionStatement((Expression) ASTNode.copySubtree(node.getAST(), (ASTNode) initializer));
            listRewrite.insertBefore(tmp, node, null);
        }

        listRewrite.replace(node, newLoop, null);

        // continue visit body
        newLoop.getBody().accept(this);
        return true;
    }
}

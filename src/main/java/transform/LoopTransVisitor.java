package transform;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;

import static utils.LLogger.logger;


/**
 * Transform between For-Loop and While-Loop
 */
public class LoopTransVisitor extends ASTVisitor {

    ASTRewrite _rewriter;

    CompilationUnit _cu;

    public LoopTransVisitor(CompilationUnit cu, ASTRewrite rewriter){
        _rewriter = rewriter;
        _cu = cu;
    }

    @Override
    public boolean visit(Block node){
        return true;
    }
    @Override
    public boolean visit(WhileStatement node){
        logger.info("enter while");
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
        logger.info("enter for");
        logger.info(node.toString());
        WhileStatement newLoop = _rewriter.getAST().newWhileStatement();
        if(node.getExpression() != null)
            newLoop.setExpression((Expression) ASTNode.copySubtree(node.getAST(), node.getExpression()));
        else
            newLoop.setExpression(node.getAST().newBooleanLiteral(true));

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
        while (parent != null && !(parent instanceof Block) && !(parent instanceof SwitchStatement) ){
            parent = parent.getParent();
        }

        if(parent == null){
            logger.error("Can not find parent of ForLoop");
            System.exit(-1);
        }

        // Move initializations outside the while loop
        ListRewrite listRewrite = null;
        if(parent instanceof Block)
            listRewrite = _rewriter.getListRewrite(parent, Block.STATEMENTS_PROPERTY);
        else if(parent instanceof SwitchStatement)
            listRewrite = _rewriter.getListRewrite(parent, SwitchStatement.STATEMENTS_PROPERTY);

        for (Object initializer : node.initializers()) {
            ExpressionStatement tmp = _rewriter.getAST().newExpressionStatement((Expression) ASTNode.copySubtree(node.getAST(), (ASTNode) initializer));
            try {
                listRewrite.insertBefore(tmp, node, null);
            } catch (IllegalArgumentException e) {
                logger.info(listRewrite.getOriginalList().toString());
                logger.info(tmp.toString());
                logger.info(node.toString());
                logger.error("Failed to insert node: " + e.getMessage());
            }
        }

        listRewrite.replace(node, newLoop, null);

        // continue visit body
        newLoop.getBody().accept(this);
        return true;
    }
}

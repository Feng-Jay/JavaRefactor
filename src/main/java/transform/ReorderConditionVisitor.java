package transform;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

import static utils.LLogger.logger;

public class ReorderConditionVisitor extends ASTVisitor {
    CompilationUnit _cu = null;
    ASTRewrite _rewriter = null;

    public ReorderConditionVisitor(CompilationUnit cu, ASTRewrite rewriter){
        _cu = cu;
        _rewriter = rewriter;
    }

    public void parseInfixExpr(Expression node){
        if(!(node instanceof InfixExpression)){
            return;
        }
        InfixExpression infixExpression = (InfixExpression) node;
        InfixExpression.Operator op = infixExpression.getOperator();
        if (op != InfixExpression.Operator.CONDITIONAL_AND && op != InfixExpression.Operator.CONDITIONAL_OR){
            return;
        }
        logger.info(infixExpression.toString());
        logger.info(infixExpression.extendedOperands().toString());
        Expression left = infixExpression.getLeftOperand();
        Expression right = infixExpression.getRightOperand();
        logger.info("left:" + left);
        logger.info("op: " + infixExpression.getOperator());
        logger.info("right: " + right);
    }

    @Override
    public boolean visit(IfStatement node){
        parseInfixExpr(node.getExpression());
        return true;
    }
}

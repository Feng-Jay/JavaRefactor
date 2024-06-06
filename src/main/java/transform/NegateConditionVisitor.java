package transform;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

import static utils.LLogger.logger;

public class NegateConditionVisitor extends ASTVisitor {
    CompilationUnit _cu = null;
    ASTRewrite _rewriter = null;

    public NegateConditionVisitor(CompilationUnit cu, ASTRewrite rewriter) {
        _cu = cu;
        _rewriter = rewriter;
    }

    public Expression makeNegativeExpr(Expression expr, ASTNode oriNode){
        ParenthesizedExpression parenthesizedExpression = oriNode.getAST().newParenthesizedExpression();
        logger.info(oriNode.toString());
        parenthesizedExpression.setExpression((Expression) ASTNode.copySubtree(oriNode.getAST(), expr));

        PrefixExpression notExpr = oriNode.getAST().newPrefixExpression();
        notExpr.setOperand(parenthesizedExpression);
        notExpr.setOperator(PrefixExpression.Operator.NOT);

        ParenthesizedExpression secondParentExpr = oriNode.getAST().newParenthesizedExpression();
        secondParentExpr.setExpression(notExpr);

        PrefixExpression secondNotExpr = oriNode.getAST().newPrefixExpression();
        secondNotExpr.setOperand(secondParentExpr);
        secondNotExpr.setOperator(PrefixExpression.Operator.NOT);
        return secondNotExpr;
    }

    @Override
    public boolean visit(WhileStatement node){
        Expression expr = node.getExpression();
        if(expr == null)
            return true;
        Expression newExpr = makeNegativeExpr(expr, node);
        _rewriter.replace(node.getExpression(), newExpr, null);
        return true;
    }

    @Override
    public boolean visit(IfStatement node){
        Expression expr = node.getExpression();
        if(expr == null)
            return true;
        Expression newExpr = makeNegativeExpr(expr, node);
        logger.info(newExpr.toString());
        _rewriter.replace(node.getExpression(), newExpr, null);
        return true;
    }

    @Override
    public boolean visit(AssertStatement node){
        Expression expr = node.getExpression();
        if(expr == null)
            return true;
        Expression newExpr = makeNegativeExpr(expr, node);
        _rewriter.replace(node.getExpression(), newExpr, null);
        return true;
    }

    @Override
    public boolean visit(ForStatement node){
        Expression expr = node.getExpression();
        if(expr == null)
            return true;
        Expression newExpr = makeNegativeExpr(expr, node);
        _rewriter.replace(node.getExpression(), newExpr, null);
        return true;
    }

    @Override
    public boolean visit(DoStatement node){
        Expression expr = node.getExpression();
        if(expr == null)
            return true;
        Expression newExpr = makeNegativeExpr(expr, node);
        _rewriter.replace(node.getExpression(), newExpr, null);
        return true;
    }

    @Override
    public boolean visit(ExpressionStatement node){
       Expression expr = node.getExpression();
        if(expr == null)
            return true;
       if(expr instanceof ConditionalExpression){
           ConditionalExpression conditionExpr = (ConditionalExpression) expr;
           Expression newExpr = makeNegativeExpr(conditionExpr.getExpression(), node);
           _rewriter.replace(conditionExpr.getExpression(), newExpr, null);
       }
       return true;
    }
}

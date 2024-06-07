package transform;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

import java.util.ArrayList;
import java.util.List;

import static utils.LLogger.logger;

public class ReorderConditionVisitor extends ASTVisitor {
    CompilationUnit _cu = null;
    ASTRewrite _rewriter = null;

    public ReorderConditionVisitor(CompilationUnit cu, ASTRewrite rewriter){
        _cu = cu;
        _rewriter = rewriter;
    }

    public List<Expression> parseInfixExpr(Expression node, List<Expression> exprs){
        if(!(node instanceof InfixExpression)){
            exprs.add(node);
            return exprs;
        }
        InfixExpression infixExpression = (InfixExpression) node;
        InfixExpression.Operator op = infixExpression.getOperator();
        if (op == InfixExpression.Operator.CONDITIONAL_AND){
            exprs.add(node);
            return exprs;
        }
        if (op == InfixExpression.Operator.CONDITIONAL_OR){
            parseInfixExpr(infixExpression.getLeftOperand(), exprs);
            parseInfixExpr(infixExpression.getRightOperand(), exprs);
        }
        return exprs;
    }

    public boolean isExtended(Expression node){
        if(!(node instanceof InfixExpression)){
            return false;
        }
        InfixExpression infixExpression = (InfixExpression) node;
        boolean conditional_op = (infixExpression.getOperator() == InfixExpression.Operator.CONDITIONAL_OR) || (infixExpression.getOperator() == InfixExpression.Operator.CONDITIONAL_AND);
        Expression left = infixExpression.getLeftOperand();
        Expression right = infixExpression.getRightOperand();
        boolean minimal_left = !(left instanceof InfixExpression)
                || ((left instanceof InfixExpression) &&
                (((InfixExpression) left).getOperator() != InfixExpression.Operator.CONDITIONAL_AND && ((InfixExpression) left).getOperator() != InfixExpression.Operator.CONDITIONAL_OR));
        boolean minimal_right = !(right instanceof InfixExpression)
                || ((right instanceof InfixExpression) &&
                (((InfixExpression) right).getOperator() != InfixExpression.Operator.CONDITIONAL_AND && ((InfixExpression) right).getOperator() != InfixExpression.Operator.CONDITIONAL_OR));

        return conditional_op && minimal_left && minimal_right;
    }

    public List<Expression> getExtended(Expression node){
        InfixExpression infixExpression = (InfixExpression) node;
        List<Expression> ret = new ArrayList<>();
        ret.add(infixExpression.getLeftOperand());
        ret.add(infixExpression.getRightOperand());
        ret.addAll(infixExpression.extendedOperands());
        return ret;
    }

    public IfStatement makeNewIf(Expression node, IfStatement oriIf){
        AST ast = oriIf.getAST();
        Statement thenStmt = oriIf.getThenStatement();
        Statement elseStmt = oriIf.getElseStatement();
        if(isExtended(node) && ((InfixExpression) node).getOperator() == InfixExpression.Operator.CONDITIONAL_AND){
            IfStatement newIf = ast.newIfStatement();
            List<Expression> subExprs = getExtended(node);
            newIf.setExpression((Expression) ASTNode.copySubtree(ast, subExprs.get(0)));
            IfStatement currentIf = newIf;
            for(int i = 1; i < subExprs.size(); ++i){
                IfStatement curNewIf = ast.newIfStatement();
                curNewIf.setExpression((Expression) ASTNode.copySubtree(ast, subExprs.get(i)));
                Block block = ast.newBlock();
                block.statements().add(curNewIf);
                currentIf.setThenStatement(block);
                currentIf = curNewIf;
            }
            currentIf.setThenStatement((Statement) ASTNode.copySubtree(ast, thenStmt));
//            logger.info(newIf.toString());
            return newIf;
        }else if(isExtended(node) && ((InfixExpression) node).getOperator() == InfixExpression.Operator.CONDITIONAL_OR){
            IfStatement newIf = ast.newIfStatement();
            List<Expression> subExprs = getExtended(node);
            newIf.setExpression((Expression) ASTNode.copySubtree(node.getAST(), subExprs.get(0)));
            newIf.setThenStatement((Statement) ASTNode.copySubtree(node.getAST(), thenStmt));
            IfStatement currentIf = newIf;
            for(int i = 1; i < subExprs.size(); ++i){
                IfStatement subIf = ast.newIfStatement();
                subIf.setExpression((Expression) ASTNode.copySubtree(node.getAST(), subExprs.get(i)));
                subIf.setThenStatement((Statement) ASTNode.copySubtree(node.getAST(), thenStmt));
                currentIf.setElseStatement(subIf);
                currentIf = subIf;
            }
            if(elseStmt != null){
                currentIf.setElseStatement((Statement) ASTNode.copySubtree(node.getAST(), elseStmt));
            }
//            logger.info(newIf.toString());
            return newIf;
        }
        else if(!isExtended(node)){
            IfStatement newIf = ast.newIfStatement();
            newIf.setExpression((Expression) ASTNode.copySubtree(ast, node));
            newIf.setThenStatement((Statement) ASTNode.copySubtree(ast, thenStmt));
            return newIf;
        }else{
            return null;
        }
    }

    @Override
    public boolean visit(IfStatement node){
        if(!(node.getExpression() instanceof InfixExpression)){
            return true;
        }else{
            IfStatement newIf = reorder(node);
            _rewriter.replace(node, newIf, null);
        }
        return true;
    }

    public IfStatement reorder(IfStatement node){
        Statement thenStmt = node.getThenStatement();
        Statement elseStmt = node.getElseStatement();
        Expression expression = node.getExpression();
        AST ast = node.getAST();
        InfixExpression infixExpression = (InfixExpression) expression;
        List<Expression> subExprs = new ArrayList<>();

        if (isExtended(expression)){
            IfStatement ifStatement = makeNewIf(expression, node);
            ifStatement.setElseStatement((Statement) ASTNode.copySubtree(ast, elseStmt));
            return ifStatement;
        }else{
            parseInfixExpr(expression, subExprs);
            if(subExprs.isEmpty()){
                return node;
            }
            IfStatement newIf = makeNewIf(subExprs.get(0), node);
            newIf.setThenStatement((Statement) ASTNode.copySubtree(ast, thenStmt));
            IfStatement currentIf = newIf;
            for(int i =1; i < subExprs.size(); ++i){
                IfStatement curNewIf = makeNewIf(subExprs.get(i), node);
                currentIf.setElseStatement(curNewIf);
                currentIf = curNewIf;
            }
            currentIf.setElseStatement((Statement) ASTNode.copySubtree(ast, elseStmt));
            return newIf;
//            logger.info(newIf.toString());
        }
//        logger.info("SubExprs: " + subExprs.toString());
    }
}

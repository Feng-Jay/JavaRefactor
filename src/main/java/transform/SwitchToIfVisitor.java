package transform;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

public class SwitchToIfVisitor extends ASTVisitor {

    CompilationUnit _cu = null;
    ASTRewrite _rewriter = null;
    public SwitchToIfVisitor(CompilationUnit cu, ASTRewrite rewriter){
        _cu = cu;
        _rewriter = rewriter;
    }

    @Override
    public boolean visit(SwitchStatement node){
        AST ast = node.getAST();

        IfStatement firstIfStatement = null;
        IfStatement lastIfStatement = null;

        for(Object obj: node.statements()){
            if (obj instanceof SwitchCase){
                SwitchCase switchCase = (SwitchCase) obj;
                if (!switchCase.isDefault()){
                    Expression expression = switchCase.getExpression();
                    InfixExpression condition = ast.newInfixExpression();
                    condition.setLeftOperand((Expression) ASTNode.copySubtree(ast, node.getExpression()));
                    condition.setOperator(InfixExpression.Operator.EQUALS);
                    condition.setRightOperand((Expression) ASTNode.copySubtree(ast, expression));

                    IfStatement ifStatement = ast.newIfStatement();
                    ifStatement.setExpression(condition);

                    if (firstIfStatement == null){
                        firstIfStatement = ifStatement;
                    }else{
                        lastIfStatement.setElseStatement(ifStatement);
                    }
                    lastIfStatement = ifStatement;
                }else {
                    // Handle default case as the else part of the last if statement
                    Block elseBlock = ast.newBlock();
                    lastIfStatement.setElseStatement(elseBlock);
                }
            }else if (lastIfStatement != null){
                Statement statement = (Statement) ASTNode.copySubtree(ast, (ASTNode) obj);
                Block block = null;
                if(lastIfStatement.getElseStatement() != null){
                    block = (Block) lastIfStatement.getElseStatement();
                }else{
                    block = (Block) lastIfStatement.getThenStatement();
                }
                if (block == null) {
                    block = ast.newBlock();
                    lastIfStatement.setThenStatement(block);
                }
                block.statements().add(statement);
            }
        }
        _rewriter.replace(node, firstIfStatement, null);
        return false; // Do not continue to visit children
    }

}

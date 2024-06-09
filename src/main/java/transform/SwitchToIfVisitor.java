package transform;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import static utils.LLogger.logger;
import java.util.List;

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
        if (node.getExpression() instanceof MethodInvocation){
            return true;
        }

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
                        Block oriBlock = (Block) lastIfStatement.getThenStatement();
                        List<Object> oriBlockStmts = oriBlock.statements();
                        boolean add = true;
                        for(Object stmt: oriBlockStmts){
                            if(stmt instanceof BreakStatement || stmt instanceof ReturnStatement){
                                add = false;
                                break;
                            }
                        }
                        if(add){
                            ifStatement.setThenStatement((Statement) ASTNode.copySubtree(lastIfStatement.getAST(), lastIfStatement.getThenStatement()));
                        }
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
                if(statement instanceof Block){
                    Block block1 = (Block) statement;
                    for (Object stmt: block1.statements()){
//                        logger.info(stmt.toString());
                        if(!(stmt instanceof BreakStatement))
                            block.statements().add(ASTNode.copySubtree(ast, (ASTNode) stmt));
                    }
                }
                else if(!(statement instanceof BreakStatement))
                    block.statements().add(statement);
            }
        }
        _rewriter.replace(node, firstIfStatement, null);
        return false; // Do not continue to visit children
    }

}

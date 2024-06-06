package transform;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;

public class InsertLogVisitor extends ASTVisitor {
    /**
     * Insert a System.out.println(str) to each block
     */
    CompilationUnit _cu = null;
    ASTRewrite _rewriter = null;

    public InsertLogVisitor(CompilationUnit cu, ASTRewrite rewriter){
        _cu = cu;
        _rewriter = rewriter;
    }

    private ExpressionStatement getInsertedStmt(Block node){
        MethodInvocation methodInvocation = node.getAST().newMethodInvocation();

        QualifiedName qualifiedName = node.getAST().newQualifiedName(node.getAST().newSimpleName("System"), node.getAST().newSimpleName("out"));
        methodInvocation.setExpression(qualifiedName);
        methodInvocation.setName(node.getAST().newSimpleName("println"));

        StringLiteral outputContent = node.getAST().newStringLiteral();
        outputContent.setLiteralValue("log");

        methodInvocation.arguments().add(outputContent);

        return node.getAST().newExpressionStatement(methodInvocation);
    }

    private IfStatement getUnusedIfStmt(Block node){
        IfStatement ifStatement = node.getAST().newIfStatement();
        ifStatement.setExpression(node.getAST().newBooleanLiteral(false));
        Block block = node.getAST().newBlock();
        ExpressionStatement expressionStatement = getInsertedStmt(node);
        block.statements().add(expressionStatement);
        ifStatement.setThenStatement(block);
        return ifStatement;
    }

    @Override
    public boolean visit(Block node){
        ExpressionStatement insertedStmt = getInsertedStmt(node);
        IfStatement unUsedStmt = getUnusedIfStmt(node);

        ListRewrite rewrite = _rewriter.getListRewrite(node, Block.STATEMENTS_PROPERTY);
        boolean insertFirst = true;
        for(Object stmt: node.statements()){
            if(stmt instanceof SuperConstructorInvocation){
                insertFirst = false;
                break;
            }
        }
        if(insertFirst)
            rewrite.insertFirst(unUsedStmt, null);
        for (Object stmt: node.statements()){
            if(stmt instanceof ReturnStatement)
                return true;
        }
        rewrite.insertLast(insertedStmt, null);
        return true;
    }
}

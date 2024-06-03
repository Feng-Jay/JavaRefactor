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

    @Override
    public boolean visit(Block node){
        ExpressionStatement insertedStmt = getInsertedStmt(node);

        ListRewrite rewrite = _rewriter.getListRewrite(node, Block.STATEMENTS_PROPERTY);
        rewrite.insertLast(insertedStmt, null);
        return true;
    }
}

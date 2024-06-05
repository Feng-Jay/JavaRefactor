package transform;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

public class RenameMethodVisitor extends ASTVisitor{

    public CompilationUnit _cu = null;
    public ASTRewrite _rewriter = null;
    public RenameMethodVisitor(CompilationUnit cu, ASTRewrite rewriter){
        _cu = cu;
        _rewriter = rewriter;
    }

    @Override
    public boolean visit(MethodDeclaration node){
        SimpleName newName = node.getAST().newSimpleName("methodName");
        _rewriter.set(node, MethodDeclaration.NAME_PROPERTY, newName, null);
        return true;
    }
}

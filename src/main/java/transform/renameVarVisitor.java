package transform;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import utils.Scope;

import static utils.LLogger.logger;

public class renameVarVisitor extends ASTVisitor{
    private int _varCounter = 0;
    CompilationUnit _cu = null;
    private final ASTRewrite _rewriter;

    private Scope _rootScope = null;
    private Scope _currentScope = null;

    public renameVarVisitor(CompilationUnit cu, ASTRewrite rewrite){
        _cu = cu;
        _rewriter = rewrite;
    }

    public void addNewScope(int beginPos, int endPos){
        Scope newScope = new Scope(_cu.getLineNumber(beginPos), _cu.getLineNumber(endPos));
        newScope.setParent(_currentScope);
        _currentScope = newScope;
    }

    public void addNewScope(String name, int beginPos, int endPos){
        Scope newScope = new Scope(name, _cu.getLineNumber(beginPos), _cu.getLineNumber(endPos));
        newScope.setParent(_currentScope);
        _currentScope = newScope;
    }

    public void popScope(){
        _currentScope = _currentScope.getParent();
    }

    @Override
    public boolean visit(SingleVariableDeclaration node){
        _currentScope.addVar(node.getName().getIdentifier(), "TRANSVAR"+_varCounter);
        _varCounter ++;
        return true;
    }

    @Override
    public boolean visit(VariableDeclarationFragment node){
        _currentScope.addVar(node.getName().getIdentifier(), "TRANSVAR"+_varCounter);
        _varCounter++;
        return true;
    }
    @Override
    public boolean visit(TypeDeclaration node){
        if(node.getName().toString().equals("FAKECLASS")){
            _rootScope = new Scope("RootScope", _cu.getLineNumber(node.getStartPosition()), _cu.getLineNumber(node.getStartPosition() + node.getLength()));
            _currentScope = _rootScope;
        }
        return true;
    }

    @Override
    public void postVisit(ASTNode node){
        if(node instanceof SimpleName && _currentScope.haveVar(node.toString())){
            AST ast = node.getAST();
            SimpleName newName = ast.newSimpleName(_currentScope.getReplaceName(node.toString()));
            _rewriter.replace(node, newName, null);
        }
        if(node instanceof TypeDeclaration){
            TypeDeclaration typeDecl = (TypeDeclaration) node;
            if(!typeDecl.getName().toString().equals("FAKECLASS")){
                popScope();
            }
            return;
        }
        if (node instanceof Block){
            popScope();
            return;
        }else if (node instanceof EnhancedForStatement){
            popScope();
        }else if (node instanceof ForStatement){
            popScope();
        }else if (node instanceof IfStatement){
            popScope();
        }else if (node instanceof CatchClause){
            popScope();
        }else if (node instanceof WhileStatement){
            popScope();
        }
        if(!(node instanceof Statement)){
            return;
        }
        ASTNode parent = node.getParent();
        if(parent == null){
            return;
        }
        if(parent instanceof IfStatement && ((IfStatement) parent).getThenStatement().equals(node)){
            // if-then block
            popScope();
        }else if(parent instanceof IfStatement && ((IfStatement) parent).getElseStatement().equals(node)){
            // if-else block
            popScope();
        }else if(parent instanceof DoStatement && ((DoStatement) parent).getBody().equals(node)){
            // do-while body
            popScope();
        }else if(parent instanceof EnhancedForStatement && ((EnhancedForStatement) parent).getBody().equals(node)){
            // for-each body
            popScope();
        }else if(parent instanceof ForStatement && ((ForStatement) parent).getBody().equals(node)){
            // for-loop body
            popScope();
        }else if(parent instanceof WhileStatement && ((WhileStatement) parent).getBody().equals(node)){
            popScope();
        }else{
//            logger.info("Skip: " + node.getClass());
        }
    }

    @Override
    public void preVisit(ASTNode node){
        if (node instanceof TypeDeclaration && !((TypeDeclaration) node).getName().toString().equals("FAKECLASS")){
            addNewScope("Class", node.getStartPosition(), node.getStartPosition() + node.getLength());
            return;
        }
        if (node instanceof Block){
            addNewScope("Block", node.getStartPosition(), node.getStartPosition() + node.getLength());
            return;
        }else if (node instanceof EnhancedForStatement){
            addNewScope("ForEach", node.getStartPosition(), node.getStartPosition() + node.getLength());
        }else if (node instanceof ForStatement){
            addNewScope("ForStmt", node.getStartPosition(), node.getStartPosition() + node.getLength());
        }else if (node instanceof IfStatement){
            addNewScope("IfStmt", node.getStartPosition(), node.getStartPosition() + node.getLength());
        }else if (node instanceof CatchClause){
            addNewScope("Catch", node.getStartPosition(), node.getStartPosition() + node.getLength());
        }else if (node instanceof WhileStatement){
            addNewScope("While", node.getStartPosition(), node.getStartPosition() + node.getLength());
        }
        if (!(node instanceof Statement)){
            return;
        }
        // tackle special cases: lack {}
        ASTNode parent = node.getParent();
        if(parent == null){
            return;
        }
        if(parent instanceof IfStatement && ((IfStatement) parent).getThenStatement().equals(node)){
            // if-then block
            addNewScope("If-then", node.getStartPosition(), node.getStartPosition() + node.getLength());
        }else if(parent instanceof IfStatement && ((IfStatement) parent).getElseStatement().equals(node)){
            // if-else block
            addNewScope("If-else", node.getStartPosition(), node.getStartPosition() + node.getLength());
        }else if(parent instanceof DoStatement && ((DoStatement) parent).getBody().equals(node)){
            // do-while body
            addNewScope("Do-while-body", node.getStartPosition(), node.getStartPosition() + node.getLength());
        }else if(parent instanceof EnhancedForStatement && ((EnhancedForStatement) parent).getBody().equals(node)){
            // for-each body
            addNewScope("For-each-body", node.getStartPosition(), node.getStartPosition() + node.getLength());
        }else if(parent instanceof ForStatement && ((ForStatement) parent).getBody().equals(node)){
            // for-loop body
            addNewScope("For-body", node.getStartPosition(), node.getStartPosition() + node.getLength());
        }else if(parent instanceof WhileStatement && ((WhileStatement) parent).getBody().equals(node)){
            addNewScope("While-body", node.getStartPosition(), node.getStartPosition() + node.getLength());
        }else{
//            logger.info("Skip: " + node.getClass());
        }
        return;
    }
}

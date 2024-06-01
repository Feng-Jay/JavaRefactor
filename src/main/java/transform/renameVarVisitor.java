package transform;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static utils.LLogger.logger;

public class renameVarVisitor extends ASTVisitor{
    private Stack<Map<String, String>> varStack;

    private Map<String, String> currentMap = new HashMap<>();
    private int _varCounter = 0;

    private ASTRewrite _rewriter;
    public renameVarVisitor(ASTRewrite rewrite){
        varStack = new Stack<>();
        _rewriter = rewrite;
    }

    public void pushMap(){
        varStack.push(currentMap);
        currentMap = new HashMap<>();
    }

    @Override
    public boolean visit(SingleVariableDeclaration node){
        currentMap.put(node.getName().getIdentifier(), "TRANSVAR"+_varCounter);
        _varCounter ++;
        return true;
    }

    @Override
    public boolean visit(VariableDeclarationFragment node){
        currentMap.put(node.getName().getIdentifier(), "TRANSVAR"+_varCounter);
        _varCounter++;
        return true;
    }
    @Override
    public boolean visit(TypeDeclaration node){
        if(!node.getName().toString().equals("FAKECLASS")){
            logger.info("Skip this class...");
            return false;
        }
        return true;
    }

    @Override
    public void postVisit(ASTNode node){
        if (node instanceof SimpleName && currentMap.containsKey(node.toString())){
            logger.info(node.toString());
            AST ast = node.getAST();
            SimpleName newName = ast.newSimpleName(currentMap.get(node.toString()));
//            ASTRewrite rewriter = ASTRewrite.create(ast);
            _rewriter.replace(node, newName, null);
        }
    }

//    @Override
//    public void preVisit(ASTNode node){
//        if (node instanceof Block){
//            pushMap();
//            return;
//        }
//        ASTNode parent = node.getParent();
//        if(parent == null){
//            return;
//        }
//        if(parent instanceof IfStatement && ((IfStatement) parent).getThenStatement().equals(node)){
//            pushMap();
//        }else if(parent instanceof IfStatement && ((IfStatement) parent).getElseStatement().equals(node)){
//            pushMap();
//        }else if(parent instanceof )
//    }
}

package transform;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jdt.internal.compiler.ast.ForeachStatement;

import static utils.LLogger.logger;

import java.util.ArrayList;
import java.util.List;

public class SwitchToIfVisitor extends ASTVisitor {

    private CompilationUnit _cu = null;
    private ASTRewrite _rewriter = null;
    private AST _ast = null;
    private boolean underSwitchStmt = false;
    private int flagCounter = 0;

    public SwitchToIfVisitor(CompilationUnit cu, ASTRewrite rewriter){
        _cu = cu;
        _rewriter = rewriter;
        _ast = _cu.getAST();
    }

//    @Override
//    public boolean visit(SwitchStatement node){
//        AST ast = node.getAST();
//        if (node.getExpression() instanceof MethodInvocation){
//            return true;
//        }
//
//        IfStatement firstIfStatement = null;
//        IfStatement lastIfStatement = null;
//
//        for(Object obj: node.statements()){
//            if (obj instanceof SwitchCase){
//                SwitchCase switchCase = (SwitchCase) obj;
//                if (!switchCase.isDefault()){
//                    Expression expression = switchCase.getExpression();
//                    InfixExpression condition = ast.newInfixExpression();
//                    condition.setLeftOperand((Expression) ASTNode.copySubtree(ast, node.getExpression()));
//                    condition.setOperator(InfixExpression.Operator.EQUALS);
//                    condition.setRightOperand((Expression) ASTNode.copySubtree(ast, expression));
//
//                    IfStatement ifStatement = ast.newIfStatement();
//                    ifStatement.setExpression(condition);
//
//                    if (firstIfStatement == null){
//                        firstIfStatement = ifStatement;
//                    }else{
//                        Block oriBlock = (Block) lastIfStatement.getThenStatement();
//                        List<Object> oriBlockStmts = oriBlock.statements();
//                        boolean add = true;
//                        for(Object stmt: oriBlockStmts){
//                            if(stmt instanceof BreakStatement || stmt instanceof ReturnStatement){
//                                add = false;
//                                break;
//                            }
//                        }
//                        if(add){
//                            ifStatement.setThenStatement((Statement) ASTNode.copySubtree(lastIfStatement.getAST(), lastIfStatement.getThenStatement()));
//                        }
//                        lastIfStatement.setElseStatement(ifStatement);
//                    }
//                    lastIfStatement = ifStatement;
//                }else {
//                    // Handle default case as the else part of the last if statement
//                    Block elseBlock = ast.newBlock();
//                    lastIfStatement.setElseStatement(elseBlock);
//                }
//            }else if (lastIfStatement != null){
//                Statement statement = (Statement) ASTNode.copySubtree(ast, (ASTNode) obj);
//                Block block = null;
//                if(lastIfStatement.getElseStatement() != null){
//                    block = (Block) lastIfStatement.getElseStatement();
//                }else{
//                    block = (Block) lastIfStatement.getThenStatement();
//                }
//                if (block == null) {
//                    block = ast.newBlock();
//                    lastIfStatement.setThenStatement(block);
//                }
//                if(statement instanceof Block){
//                    Block block1 = (Block) statement;
//                    for (Object stmt: block1.statements()){
////                        logger.info(stmt.toString());
//                        if(!(stmt instanceof BreakStatement))
//                            block.statements().add(ASTNode.copySubtree(ast, (ASTNode) stmt));
//                    }
//                }
//                else if(!(statement instanceof BreakStatement))
//                    block.statements().add(statement);
//            }
//        }
//        _rewriter.replace(node, firstIfStatement, null);
//        return false; // Do not continue to visit children
//    }

    public Expression makeInfixExpr(Expression lhs, Expression rhs, InfixExpression.Operator infixOp, AST ast){
        InfixExpression infixExpression = ast.newInfixExpression();
        infixExpression.setLeftOperand(lhs);
        infixExpression.setRightOperand(rhs);
        infixExpression.setOperator(infixOp);
        return infixExpression;
    }
    public Expression makeIfCondition(List<SwitchCase> switchCases, SwitchStatement switchStatement){
        List<Expression> allExpressions = new ArrayList<>();
        AST ast = switchStatement.getAST();
        Expression expression = switchStatement.getExpression();
        for (SwitchCase switchCase: switchCases){
            allExpressions.add(switchCase.getExpression());
        }
        Expression firstExpression = makeInfixExpr((Expression) ASTNode.copySubtree(ast, expression), (Expression) ASTNode.copySubtree(ast, allExpressions.get(0)), InfixExpression.Operator.EQUALS, ast);
        for(int i = 1; i < allExpressions.size(); ++i){
            Expression tmpRhs = makeInfixExpr((Expression) ASTNode.copySubtree(ast, expression), (Expression) ASTNode.copySubtree(ast, allExpressions.get(i)), InfixExpression.Operator.EQUALS, ast);
            Expression tmp = makeInfixExpr(firstExpression, tmpRhs, InfixExpression.Operator.CONDITIONAL_OR, ast);
            firstExpression = tmp;
        }
        logger.info(firstExpression.toString());
        return firstExpression;
    }
    public Expression makeIfCondition(SwitchCase switchCase, SwitchStatement switchStatement){
        AST ast = switchStatement.getAST();
        Expression oriExpr = makeInfixExpr((Expression) ASTNode.copySubtree(ast, switchStatement.getExpression()), (Expression) ASTNode.copySubtree(ast, switchCase.getExpression()), InfixExpression.Operator.EQUALS, ast);
        ParenthesizedExpression wrapOriExpr = ast.newParenthesizedExpression();
        wrapOriExpr.setExpression(oriExpr);
        Expression andExpr = makeInfixExpr(ast.newSimpleName("TransBreakFlag" + flagCounter), wrapOriExpr, InfixExpression.Operator.CONDITIONAL_AND, ast);
        ParenthesizedExpression wrapAndExpr = ast.newParenthesizedExpression();
        wrapAndExpr.setExpression(andExpr);
        Expression orExpr = makeInfixExpr(ast.newSimpleName("TransFallThroughFlag" + flagCounter), wrapAndExpr, InfixExpression.Operator.CONDITIONAL_OR, ast);
        return orExpr;
    }

    private Statement makeFallThroughFlag(){
        AST ast = _ast;
        SimpleName simpleName = ast.newSimpleName("TransFallThroughFlag" + flagCounter);
        BooleanLiteral initial = ast.newBooleanLiteral(false);
        VariableDeclarationFragment fragment = ast.newVariableDeclarationFragment();
        fragment.setName(simpleName);
        fragment.setInitializer(initial);
        VariableDeclarationStatement ret = ast.newVariableDeclarationStatement(fragment);
        ret.setType(ast.newPrimitiveType(PrimitiveType.BOOLEAN));
        return ret;
    }

    private Statement makeBreakFlag(){
        AST ast = _ast;
        SimpleName simpleName = ast.newSimpleName("TransBreakFlag" + flagCounter);
        BooleanLiteral initial = ast.newBooleanLiteral(true);
        VariableDeclarationFragment fragment = ast.newVariableDeclarationFragment();
        fragment.setName(simpleName);
        fragment.setInitializer(initial);
        VariableDeclarationStatement ret = ast.newVariableDeclarationStatement(fragment);
        ret.setType(ast.newPrimitiveType(PrimitiveType.BOOLEAN));
        return ret;
    }

    private Statement makeAssignFallThroughFlag(){
        Assignment assignment = _ast.newAssignment();
        assignment.setLeftHandSide(_ast.newSimpleName("TransFallThroughFlag" + flagCounter));
        assignment.setRightHandSide(_ast.newBooleanLiteral(true));
        return _ast.newExpressionStatement(assignment);
    }

    private Statement makeAssignBreakFlag(){
        Assignment assignment = _ast.newAssignment();
        assignment.setLeftHandSide(_ast.newSimpleName("TransBreakFlag" + flagCounter));
        assignment.setRightHandSide(_ast.newBooleanLiteral(false));
        return _ast.newExpressionStatement(assignment);
    }

    private boolean isBreakable(ASTNode node){
        return node instanceof ForStatement || node instanceof EnhancedForStatement || node instanceof WhileStatement || node instanceof DoStatement || node instanceof LabeledStatement;
    }

    @Override
    public void postVisit(ASTNode node){
        if (node instanceof SwitchStatement){
            underSwitchStmt = false;
        }
        if (underSwitchStmt && node instanceof BreakStatement && ((BreakStatement) node).getLabel() == null){
//            logger.info(node.toString());
            ASTNode parent = node.getParent();
            while(parent != null){
                if (parent instanceof SwitchStatement){
                    _rewriter.remove(node, null);
                }else if (isBreakable(node)){
                    break;
                }
                parent = parent.getParent();
            }
        }
        return;
    }


    @Override
    public boolean visit(SwitchStatement node){
        underSwitchStmt = true;
        AST ast = node.getAST();
        for(Object object: node.statements()) {
            if (object instanceof SwitchCase) {
                SwitchCase switchCase = (SwitchCase) object;
                Expression expr = switchCase.getExpression();
                if (expr instanceof SimpleName && expr.toString().equals(expr.toString().toUpperCase())) {
                    logger.info("Detect potential Enum used in switchCase, abort transformation...");
                    underSwitchStmt = false;
                    return true;
                }
            }
        }
        ListRewrite rewrite = null;
        ASTNode parent = node.getParent();
        if(parent instanceof Block){
            rewrite = _rewriter.getListRewrite(node.getParent(), Block.STATEMENTS_PROPERTY);
        }else if(parent instanceof SwitchStatement){
            rewrite = _rewriter.getListRewrite(node.getParent(), SwitchStatement.STATEMENTS_PROPERTY);
        }else{
            logger.info(String.valueOf(node.getParent().getClass()));
        }

        Statement fallThroughFlagDecl = makeFallThroughFlag();
        Statement breakFlag = makeBreakFlag();

        rewrite.insertBefore(fallThroughFlagDecl, node, null);
        rewrite.insertBefore(breakFlag, node, null);

        List<Statement> statements = node.statements();
        if (node.statements().size() == 1 && node.statements().get(0) instanceof Block){
            Block tmp = (Block) node.statements().get(0);
            statements = tmp.statements();
        }

        IfStatement lastIfStmt = null;
        boolean meetBreak = false;
        List<Statement> insertedStatements = new ArrayList<>();
        for (Object object: statements){
            if (object instanceof SwitchCase){
                if (!meetBreak && lastIfStmt != null){
                    Block block = (Block) lastIfStmt.getThenStatement();
                    ListRewrite blockWriter = _rewriter.getListRewrite(block, Block.STATEMENTS_PROPERTY);
                    blockWriter.insertFirst(makeAssignFallThroughFlag(), null);
                    insertedStatements.add(lastIfStmt);
                }
                if (meetBreak && lastIfStmt != null){
                    Block block = (Block) lastIfStmt.getThenStatement();
                    ListRewrite blockWriter = _rewriter.getListRewrite(block, Block.STATEMENTS_PROPERTY);
                    blockWriter.insertFirst(makeAssignBreakFlag(), null);
                    insertedStatements.add(lastIfStmt);
                    meetBreak = false;
                }
                SwitchCase switchCase = (SwitchCase) object;
                if (!switchCase.isDefault()){
                    Expression ifCondition = makeIfCondition(switchCase, node);
                    IfStatement newIfStmt = ast.newIfStatement();
                    newIfStmt.setExpression(ifCondition);
                    lastIfStmt = newIfStmt;
                }else{
                    PrefixExpression lhs = ast.newPrefixExpression();
                    lhs.setOperand(ast.newSimpleName("TransFallThroughFlag" + flagCounter));
                    lhs.setOperator(PrefixExpression.Operator.NOT);
                    Expression ifCondition = makeInfixExpr(lhs, ast.newSimpleName("TransBreakFlag" + flagCounter), InfixExpression.Operator.CONDITIONAL_AND, ast);
                    IfStatement newIfstmt = ast.newIfStatement();
                    newIfstmt.setExpression(ifCondition);
                    lastIfStmt = newIfstmt;
                }
            }else if (lastIfStmt != null){
                Statement statement = (Statement) ASTNode.copySubtree(ast, (ASTNode) object);
                Block block = null;
                if (lastIfStmt.getThenStatement() != null){
                    block = (Block) lastIfStmt.getThenStatement();
                }else{
                    block = ast.newBlock();
                    lastIfStmt.setThenStatement(block);
                }
                if(statement instanceof Block){
                    Block block1 = (Block) statement;
                    for (Object stmt: block1.statements()){
                        if(!(stmt instanceof BreakStatement && ((BreakStatement) stmt).getLabel() == null))
                            block.statements().add(ASTNode.copySubtree(ast, (ASTNode) stmt));
                        if(stmt instanceof BreakStatement)
                            meetBreak = true;
                    }
                }else{
                    if(!(statement instanceof BreakStatement && ((BreakStatement) statement).getLabel() == null))
                        block.statements().add(statement);
                    if (statement instanceof BreakStatement)
                        meetBreak = true;
                }
            }
        }
        insertedStatements.add(lastIfStmt);
        Block newBlock = ast.newBlock();
        for(Statement stmt: insertedStatements){
            newBlock.statements().add(stmt);
        }
        flagCounter += 1;
        newBlock.accept(this);
        rewrite.replace(node, newBlock, null);
        return true;

//        List<SwitchCase> candidateCases = new ArrayList<>();
//        List<Statement> insertedStatements = new ArrayList<>();
//        IfStatement firstIfStmt = null;
//        IfStatement lastIfStmt = null;
//        boolean meetBreak = false;
//        for (Object object: node.statements()){
//            if (object instanceof SwitchCase){
//                logger.info(object.toString().replace("\n", "") + ";" + meetBreak + ";" + firstIfStmt);
//                if (!meetBreak && firstIfStmt != null){
//                    insertedStatements.add(firstIfStmt);
//                    firstIfStmt = null;
////                    lastIfStmt = null;
//                }else{
//                    candidateCases.clear();
//                    meetBreak = false;
//                }
//                SwitchCase switchCase = (SwitchCase) object;
//                if (!switchCase.isDefault()){
//                    candidateCases.add((SwitchCase) object);
//                    Expression ifCondition = makeIfCondition(candidateCases, node);
//                    IfStatement newIfStmt = ast.newIfStatement();
//                    newIfStmt.setExpression(ifCondition);
//                    if (firstIfStmt == null){
//                        firstIfStmt = newIfStmt;
//                    }else{
//                        lastIfStmt.setElseStatement(newIfStmt);
//                    }
//                    lastIfStmt = newIfStmt;
//                }else{
//                    Block elseBlock = ast.newBlock();
//                    lastIfStmt.setElseStatement(elseBlock);
//                }
//            }else if (lastIfStmt != null){
//                Statement statement = (Statement) ASTNode.copySubtree(ast, (ASTNode) object);
//                Block block = null;
//                if(lastIfStmt.getElseStatement() != null){
//                    block = (Block) lastIfStmt.getElseStatement();
//                }else{
//                    block = (Block) lastIfStmt.getThenStatement();
//                }
//                if (block == null) {
//                    block = ast.newBlock();
//                    lastIfStmt.setThenStatement(block);
//                }
//                if(statement instanceof Block){
//                    Block block1 = (Block) statement;
//                    for (Object stmt: block1.statements()){
//                        if(!(stmt instanceof BreakStatement && ((BreakStatement) stmt).getLabel() == null))
//                            block.statements().add(ASTNode.copySubtree(ast, (ASTNode) stmt));
//                        else
//                            meetBreak = true;
//                    }
//                }else{
//                    if(!(statement instanceof BreakStatement && ((BreakStatement) statement).getLabel() == null))
//                        block.statements().add(statement);
//                    else
//                        meetBreak = true;
//                }
//
//            }
//        }
//        if (firstIfStmt != null){
//            insertedStatements.add(firstIfStmt);
//        }
//
//        logger.info(insertedStatements.toString());
//        ASTNode parent = node.getParent();
//        ListRewrite rewrite = null;
//        if(parent instanceof Block){
//            rewrite = _rewriter.getListRewrite(node.getParent(), Block.STATEMENTS_PROPERTY);
//        }else if(parent instanceof SwitchStatement){
//            rewrite = _rewriter.getListRewrite(node.getParent(), SwitchStatement.STATEMENTS_PROPERTY);
//        }else{
//            logger.info(String.valueOf(node.getParent().getClass()));
//        }
////        logger.info(rewrite.getOriginalList().toString());
////        for(Statement newStmt: insertedStatements){
////            logger.info(String.valueOf(newStmt));
////            rewrite.insertBefore(newStmt, node, null);
////        }
////        rewrite.remove(node, null);
//        Block newBlock = ast.newBlock();
//        for(Statement stmt: insertedStatements){
//            newBlock.statements().add(stmt);
//        }
//        newBlock.accept(this);
//        rewrite.replace(node, newBlock, null);
//
//        return true;
    }
}

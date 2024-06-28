import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;
import transform.*;
import utils.Constant;
import utils.JavaFile;

import static utils.LLogger.logger;

public class Main {

    private static String wrapJavaCode(String javaCode){
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(javaCode.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        if (!cu.types().isEmpty() && (cu.types().get(0) instanceof TypeDeclaration || cu.types().get(0) instanceof EnumDeclaration)){
            return javaCode;
        }else{
            String prefix = "public class " + Constant.fakeClassName + "{\n";
            String suffix = "\n}";
//            logger.info("wrap java code into class:" + javaCode);
            return prefix + javaCode + suffix;
        }
    }

    public static void main(String[] args){
        if (args.length != 3){
            logger.error("Illegal Parameters!!!");
            System.exit(-1);
        }
        Constant.beforeFilePath = args[0];
        Constant.transformedFilePath = args[1];
        Constant.jsonFilePath = args[2];
        logger.info("InputFilePath: " + Constant.beforeFilePath);
        logger.info("OutputFilePath: " + Constant.transformedFilePath);
        logger.info("Please keep each java file only have one method!!!");
        String oriJavaContent = JavaFile.readFileToString(Constant.beforeFilePath);
        oriJavaContent = wrapJavaCode(oriJavaContent);
        transform(oriJavaContent);
    }
    private static void transform(String javaCode){
        String currentJavaCode = javaCode;
        if(Constant.renameMethod){
            currentJavaCode = transformRenameMethod(currentJavaCode);
        }
        if(Constant.renameVar){
            currentJavaCode = transformRenameVar(currentJavaCode);
        }
        if(Constant.transLoop){
            currentJavaCode = transformLoopTrans(currentJavaCode);
        }
        if(Constant.switchToIf){
            currentJavaCode = transformSwitchIf(currentJavaCode);
        }
        if(Constant.insertLog){
            currentJavaCode = transformInsertLog(currentJavaCode);
        }
        if(Constant.reorderCondition){
            currentJavaCode = transformReorderCondition(currentJavaCode);
        }
        if(Constant.negateCondition){
            currentJavaCode = transformNegateCondition(currentJavaCode);
        }
        JavaFile.writeFile(currentJavaCode, Constant.transformedFilePath);
        logger.info("Transforming Done.");
    }

    public static String applyModification(ASTRewrite rewriter, String javaCode){
        Document document = new Document(javaCode);
        TextEdit edits = rewriter.rewriteAST(document, null);
        try{
            edits.apply(document);
            logger.info(document.get());
        }catch (Exception e){
            logger.error("Unexpected Error when writing modified file to ori path!!!");
            System.exit(-1);
        }
        return document.get();
    }

    public static String transformRenameMethod(String javaCode){
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(javaCode.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        AST ast = cu.getAST();
        ASTRewrite rewriter = ASTRewrite.create(ast);
        cu.recordModifications();
        RenameMethodVisitor visitor = new RenameMethodVisitor(cu, rewriter);
        cu.accept(visitor);
        String transformedJavaCode = applyModification(rewriter, javaCode);
        logger.info("Transforming Done.");
        return transformedJavaCode;
    }

    public static String transformRenameVar(String javaCode){
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(javaCode.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        AST ast = cu.getAST();
        ASTRewrite rewriter = ASTRewrite.create(ast);
        cu.recordModifications();
        RenameVarVisitor visitor = new RenameVarVisitor(cu, rewriter);
        cu.accept(visitor);
        String transformedJavaCode = applyModification(rewriter, javaCode);
        logger.info("Transforming Done.");
        return transformedJavaCode;
    }

    public static String transformLoopTrans(String javaCode){
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(javaCode.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        AST ast = cu.getAST();
        ASTRewrite rewriter = ASTRewrite.create(ast);
        cu.recordModifications();
        LoopTransVisitor visitor = new LoopTransVisitor(cu, rewriter);
        cu.accept(visitor);
        String transformedJavaCode = applyModification(rewriter, javaCode);
        logger.info("Transforming Done.");
        return transformedJavaCode;
    }

    public static String transformSwitchIf(String javaCode){
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(javaCode.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        AST ast = cu.getAST();
        ASTRewrite rewriter = ASTRewrite.create(ast);
        cu.recordModifications();
        SwitchToIfVisitor visitor = new SwitchToIfVisitor(cu, rewriter);
        cu.accept(visitor);
        String transformedJavaCode = applyModification(rewriter, javaCode);
        logger.info("Transforming Done.");
        return transformedJavaCode;
    }

    public static String transformInsertLog(String javaCode){
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(javaCode.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        AST ast = cu.getAST();
        ASTRewrite rewriter = ASTRewrite.create(ast);
        cu.recordModifications();
        InsertLogVisitor visitor = new InsertLogVisitor(cu, rewriter);
        cu.accept(visitor);
        String transformedJavaCode = applyModification(rewriter, javaCode);
        logger.info("Transforming Done.");
        return transformedJavaCode;
    }

    public static String transformReorderCondition(String javaCode){
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(javaCode.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        AST ast = cu.getAST();
        ASTRewrite rewriter = ASTRewrite.create(ast);
        cu.recordModifications();
        ReorderConditionVisitor visitor = new ReorderConditionVisitor(cu, rewriter);
        cu.accept(visitor);
        String transformedJavaCode = applyModification(rewriter, javaCode);
        logger.info("Transforming Done.");
        return transformedJavaCode;
    }

    public static String transformNegateCondition(String javaCode){
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(javaCode.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        AST ast = cu.getAST();
        ASTRewrite rewriter = ASTRewrite.create(ast);
        cu.recordModifications();
        NegateConditionVisitor visitor = new NegateConditionVisitor(cu, rewriter);
        cu.accept(visitor);
        String transformedJavaCode = applyModification(rewriter, javaCode);
        logger.info("Transforming Done.");
        return transformedJavaCode;
    }

}

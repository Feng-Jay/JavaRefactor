import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;
import transform.loopTransVisitor;
import transform.renameVarVisitor;
import utils.Constant;
import utils.JavaFile;

import static utils.LLogger.logger;

public class Main {
    public static void main(String[] args){
        if (args.length != 2){
            logger.error("Illegal Parameters!!!");
            System.exit(-1);
        }

        Constant.beforeFilePath = args[0];
        Constant.transformedFilePath  =args[1];
        logger.info("InputFilePath: " + Constant.beforeFilePath);
        logger.info("OutputFilePath: " + Constant.transformedFilePath);
        logger.info("Please keep each java file only have one method!!!");
        String oriJavaContent = JavaFile.readFileToString(Constant.beforeFilePath);
        transform(oriJavaContent);
    }
    private static void transform(String javaCode){
        String currentJavaCode = javaCode;
        if(Constant.renameVar){
            currentJavaCode = transformRenameVar(currentJavaCode);
        }
        if(Constant.transLoop){
            currentJavaCode = transformLoopTrans(currentJavaCode);
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

    public static String transformRenameVar(String javaCode){
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(javaCode.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        AST ast = cu.getAST();
        ASTRewrite rewriter = ASTRewrite.create(ast);
        cu.recordModifications();
        renameVarVisitor visitor = new renameVarVisitor(cu, rewriter);
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
        loopTransVisitor visitor = new loopTransVisitor(cu, rewriter);
        cu.accept(visitor);
        String transformedJavaCode = applyModification(rewriter, javaCode);
        logger.info("Transforming Done.");
        return transformedJavaCode;
    }

}

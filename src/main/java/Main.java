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

        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(JavaFile.readFileToString(Constant.beforeFilePath).toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);

        transform(cu);
    }
    private static void transform(CompilationUnit cu){
        AST ast = cu.getAST();
        ASTRewrite rewriter = ASTRewrite.create(ast);
        cu.recordModifications();
        if(Constant.renameVar){
            renameVarVisitor visitor = new renameVarVisitor(cu);
            cu.accept(visitor);
        }
//        if(Constant.transLoop){
//            loopTransVisitor visitor = new loopTransVisitor(rewriter);
//            cu.accept(visitor);
//        }
        Document document = new Document(JavaFile.readFileToString(Constant.beforeFilePath));
        TextEdit edits = rewriter.rewriteAST(document, null);
        try{
            edits.apply(document);
            logger.info(document.get());
            JavaFile.writeFile(document.get(), Constant.transformedFilePath);
        }catch (Exception e){
            logger.error("Unexpected Error when writing modified file to ori path!!!");
            System.exit(-1);
        }
        logger.info("Transforming Done.");
    }


}

package transform;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.junit.jupiter.api.Test;
import utils.Constant;
import utils.JavaFile;

import java.net.URL;

import static utils.LLogger.logger;

public class InsertLogVisitorTest {
    private void acceptVisitor(String fileName){
        URL resource = getClass().getClassLoader().getResource(fileName);
        if (resource == null){
            logger.error("File not found: " + fileName);
            throw new IllegalArgumentException("File not found!!!");
        }else{
            ASTParser parser = ASTParser.newParser(AST.JLS8);
            parser.setSource(JavaFile.readFileToString(resource.getFile()).toCharArray());
            parser.setKind(ASTParser.K_COMPILATION_UNIT);
            CompilationUnit cu = (CompilationUnit) parser.createAST(null);
            AST ast = cu.getAST();
            ASTRewrite rewriter = ASTRewrite.create(ast);
            cu.recordModifications();
            if(Constant.renameVar){
                InsertLogVisitor visitor = new InsertLogVisitor(cu, rewriter);
                cu.accept(visitor);
            }
        }
    }

    @Test
    public void testInsertLog(){
        for (int i =0; i < 483; ++i){
            String buggy_file_name = i + "_b.java";
            String fixed_file_name = i + "_f.java";
            acceptVisitor(buggy_file_name);
            acceptVisitor(fixed_file_name);
            logger.info("Current testcases: " + i + "/483 passed!");
        }

    }
}

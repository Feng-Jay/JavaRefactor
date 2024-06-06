import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.junit.jupiter.api.Test;
import utils.Constant;
import utils.JavaFile;

import java.net.URL;

import static utils.LLogger.logger;

public class CombinedTest {
    private void transform(String javaCode){
        String currentJavaCode = javaCode;
        currentJavaCode = Main.transformRenameMethod(currentJavaCode);
        currentJavaCode = Main.transformRenameVar(currentJavaCode);
        currentJavaCode = Main.transformLoopTrans(currentJavaCode);
        currentJavaCode = Main.transformSwitchIf(currentJavaCode);
        currentJavaCode = Main.transformInsertLog(currentJavaCode);
        currentJavaCode = Main.transformReorderCondition(currentJavaCode);
        currentJavaCode = Main.transformNegateCondition(currentJavaCode);
        logger.info("Transforming Done.");
    }

    @Test
    public void combineTest(){
        for (int i =0; i < 483; ++i){
            String buggy_file_name = i + "_b.java";
            String fixed_file_name = i + "_f.java";

            URL resource = getClass().getClassLoader().getResource(buggy_file_name);
            if (resource == null){
                logger.error("File not found: " + buggy_file_name);
                throw new IllegalArgumentException("File not found!!!");
            }else{
                String fileContent = JavaFile.readFileToString(resource.getFile());
                transform(fileContent);
            }

            resource = getClass().getClassLoader().getResource(fixed_file_name);
            if (resource == null){
                logger.error("File not found: " + buggy_file_name);
                throw new IllegalArgumentException("File not found!!!");
            }else{
                String fileContent = JavaFile.readFileToString(resource.getFile());
                transform(fileContent);
            }
            logger.info("Current testcases: " + i + "/483 passed!");
        }
    }
}

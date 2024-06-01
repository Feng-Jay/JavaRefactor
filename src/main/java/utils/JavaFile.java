package utils;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.LLogger.logger;

public class JavaFile {

    private static List<String> ergodic(File dirPath, List<String> filePaths){
        if (dirPath == null){
            logger.error("Ergodic(private) dirPath is NULL!!!");
            System.exit(-1);
        }
        File[] files = dirPath.listFiles();
        if (files == null){
            return filePaths;
        }
        for (File f: files){
            if (f.isDirectory()){
                ergodic(f, filePaths);
            }else if (f.getName().endsWith(".java")){
                filePaths.add(f.getAbsolutePath());
            }
        }
        return filePaths;
    }

    public static List<String> ergodic(String dirPath, List<String> filePaths){
        if (dirPath == null){
            logger.error("Ergodic dirPath is NULL!!!");
            return filePaths;
        }
        File file = new File(dirPath);
        if (!file.exists()){
            logger.error("The dirPath is not exists: " + file.getAbsolutePath());
            System.exit(-1);
        }
        File[] files = file.listFiles();
        if (files == null){
            logger.info("Given Directory is not exists!!!");
            return filePaths;
        }
        for (File f: files){
            if (f.isDirectory()){
                ergodic(f, filePaths);
            } else if(f.getName().endsWith(".java")){
                filePaths.add(f.getAbsolutePath());
            }
        }
        return filePaths;
    }

    public static void copyDir(String ori, String tar){
        File fileOri = new File(ori);
        File fileTar = new File(tar);
        try{
            FileUtils.copyDirectory(fileOri, fileTar);
        } catch (IOException e) {
            logger.error("Copy directory failed, src: " + fileOri.getAbsolutePath() + "; tar: " + fileTar.getAbsolutePath());
            throw new RuntimeException(e);
        }
    }

    public static String readFileToString(String filePath) {
        if (filePath == null) {
            logger.error("Illegal input file : null!!!");
            System.exit(-1);
        }
        File file = new File(filePath);
        StringBuilder builder = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String currentLine;
            while ((currentLine = reader.readLine()) != null){
                builder.append(currentLine).append("\n");
            }
        }catch (FileNotFoundException fe){
            logger.error("File: " + filePath + " not found!!!");
            System.exit(-1);
        }catch (IOException e){
            logger.error("ReadFile: " + filePath + " not found!!!");
            System.exit(-1);
        }
        return builder.toString();
    }

    public static List<String> readFileToStrings(String filePath){
        File file = new File(filePath);
        List<String> result = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line=reader.readLine()) != null){
                result.add(line);
            }
            reader.close();
        }catch (FileNotFoundException fe){
            logger.error("File: " + filePath + " not found!!!");
            System.exit(-1);
        }catch (IOException e){
            logger.error("ReadFile: " + filePath + " not found!!!");
            System.exit(-1);
        }
        return result;
    }

    public static void writeFile(String content, String filePath) {
        File file = new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        } catch (IOException e) {
            logger.error("Wtring to file: " + filePath + " failed!!!");
            System.exit(-1);
        }
    }

    public static String join(char delimiter, String... element) {
        return join(delimiter, Arrays.asList(element));
    }

    public static String join(char delimiter, List<String> elements) {
        StringBuffer buffer = new StringBuffer();
        if (elements.size() > 0) {
            buffer.append(elements.get(0));
        }
        for (int i = 1; i < elements.size(); i++) {
            buffer.append(delimiter);
            buffer.append(elements.get(i));
        }
        return buffer.toString();
    }


    public static void main(String[] args){
        // test ergodic
//        List<String> files = new ArrayList<>();
//        ergodic("/Users/ffengjay/Postgraduate/PLM4APR/tmp/defects4j_buggy/Cloure/Closure_10_buggy/", files);
//        for (String file: files)
//            System.out.println(file);

        // test readPatchInfo
//        List<D4JSubject> subjects = readPatchInfo();
//        for (D4JSubject subject: subjects){
//            logger.info(JavaFile.join(';', subject._d4jHome, subject._src, subject._test, subject._srcBin, subject._testBin));
//            for (String test: subject._testMethods){
//                logger.info(test);
//            }
//        }
    }

    public static class Pair<T1, T2> {

        private T1 first;
        private T2 second;

        public Pair() {
        }

        public Pair(T1 fst, T2 snd){
            this.first = fst;
            this.second = snd;
        }

        public T1 getFirst(){
            return this.first;
        }

        public T2 getSecond(){
            return this.second;
        }

        public void setFirst(T1 fst){
            this.first = fst;
        }

        public void setSecond(T2 snd){
            this.second = snd;
        }

        public static Object[] toArray(Pair<?, ?> pair) {
            return new Object[]{pair.getFirst(), pair.getSecond()};
        }

        @Override
        public int hashCode() {
            int hash = 0;
            if(first != null) {
                hash += first.hashCode();
            }
            if(second != null) {
                hash += second.hashCode();
            }
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == null || !(obj instanceof Pair)) {
                return false;
            }
            Pair pair = (Pair) obj;
            if(first == null) {
                if (pair.getFirst() != null) {
                    return false;
                }
            } else if(!first.equals(pair.getFirst())) {
                return false;
            }
            if(second == null) {
                if(pair.getSecond() != null) {
                    return false;
                }
            } else {
                return second.equals(pair.getSecond());
            }
            return true;
        }

        @Override
        public String toString() {
            return "<" + first.toString() + "," + second.toString() + ">";
        }
    }
}
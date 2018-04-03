package com.worcount;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class WordCountTest {

    private static String testDirectoryPath="C:\\work\\ODS\\Application\\wordcount-app\\wordcount\\src\\main\\java\\com\\worcount\\testFiles";

    public static void main(String[] args) {

        try {
            System.out.println("Running test cases..");
            WordCountTest.testEmptyFiles();
            WordCountTest.testSmallMultipleFilesInput();
            WordCountTest.testOneFile();
            WordCountTest.testOneFileNotFound_processOthers();
            WordCountTest.testFileWithOnlyNonAlphaNumberic();
            WordCountTest.testLargeMultipleFile();
            WordCountTest.testNoFiles();
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
    }

    public static void assertTrue(Map<String, Integer> expected, Map<String, Integer> returned) {
        System.out.println(expected.equals(returned));
    }

    public static void testEmptyFiles() throws Exception {
        WordCountRunner wc = new WordCountRunner();

        ArrayList<String> filePaths = new ArrayList<>();
        filePaths.add(testDirectoryPath+"\\fileEmpty.txt");
        filePaths.add(testDirectoryPath+"\\fileEmpty2.txt");

        Map<String, Integer> wordMap = wc.getMapOfWordCount(filePaths);

        assertTrue(new HashMap<String, Integer>(), wordMap);
    }

    public static void testSmallMultipleFilesInput() throws Exception {
        WordCountRunner wc = new WordCountRunner();

        ArrayList<String> filePaths = new ArrayList<>();
        filePaths.add(testDirectoryPath+"\\file.txt");
        filePaths.add(testDirectoryPath+"\\file2.txt");

        Map<String, Integer> wordMap = wc.getMapOfWordCount(filePaths);

        /*
        if (wordMap != null) {
            for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
        }
        */

        Map<String, Integer>  expectedWordMap = new HashMap<String, Integer>();
        expectedWordMap.put("a", 1);
        expectedWordMap.put("software", 1);
        expectedWordMap.put("like", 2);
        expectedWordMap.put("wife", 1);
        expectedWordMap.put("i", 2);
        expectedWordMap.put("is", 2);
        expectedWordMap.put("cute",1);
        expectedWordMap.put("mayur", 2);
        expectedWordMap.put("cutepooja", 1);
        expectedWordMap.put("kadu", 1);
        expectedWordMap.put("are", 2);
        expectedWordMap.put("too", 1);
        expectedWordMap.put("of", 1);
        expectedWordMap.put("chitrakar", 1);
        expectedWordMap.put("dogs", 3);
        expectedWordMap.put("pooja", 2);
        expectedWordMap.put("developer", 1);
        expectedWordMap.put("dog", 1);
        expectedWordMap.put("dg34saf", 1);

        assertTrue(expectedWordMap, wordMap);

    }

    public static void testOneFile() throws Exception {
        WordCountRunner wc = new WordCountRunner();

        ArrayList<String> filePaths = new ArrayList<>();
        filePaths.add(testDirectoryPath+"\\file.txt");

        Map<String, Integer> wordMap = wc.getMapOfWordCount(filePaths);

        Map<String, Integer>  expectedWordMap = new HashMap<String, Integer>();
        expectedWordMap.put("kadu", 1);
        expectedWordMap.put("like", 1);
        expectedWordMap.put("are", 1);
        expectedWordMap.put("wife", 1);
        expectedWordMap.put("of", 1);
        expectedWordMap.put("chitrakar", 1);
        expectedWordMap.put("dogs", 2);
        expectedWordMap.put("pooja", 2);
        expectedWordMap.put("i", 1);
        expectedWordMap.put("is", 1);
        expectedWordMap.put("mayur", 1);
        expectedWordMap.put("cutepooja", 1);

        assertTrue(expectedWordMap, wordMap);

    }

    public static void testOneFileNotFound_processOthers() throws Exception {
        WordCountRunner wc = new WordCountRunner();

        ArrayList<String> filePaths = new ArrayList<>();
        filePaths.add(testDirectoryPath+"\\file.txt");
        filePaths.add(testDirectoryPath+"\\file4.txt");

        Map<String, Integer> wordMap = wc.getMapOfWordCount(filePaths);

        Map<String, Integer>  expectedWordMap = new HashMap<String, Integer>();
        expectedWordMap.put("kadu", 1);
        expectedWordMap.put("like", 1);
        expectedWordMap.put("are", 1);
        expectedWordMap.put("wife", 1);
        expectedWordMap.put("of", 1);
        expectedWordMap.put("chitrakar", 1);
        expectedWordMap.put("dogs", 2);
        expectedWordMap.put("pooja", 2);
        expectedWordMap.put("i", 1);
        expectedWordMap.put("is", 1);
        expectedWordMap.put("mayur", 1);
        expectedWordMap.put("cutepooja", 1);

        assertTrue(expectedWordMap, wordMap);//should throw exception for file not found

    }

    public static void testFileWithOnlyNonAlphaNumberic() throws Exception {
        WordCountRunner wc = new WordCountRunner();

        ArrayList<String> filePaths = new ArrayList<>();
        filePaths.add(testDirectoryPath+"\\fileNonAlphaNumeric.txt");

        Map<String, Integer> wordMap = wc.getMapOfWordCount(filePaths);
        Map<String, Integer>  expectedWordMap = new HashMap<String, Integer>();

        assertTrue(expectedWordMap, wordMap);
    }

    public static void testLargeMultipleFile() throws Exception {

        WordCountRunner wc = new WordCountRunner();

        ArrayList<String> filePaths = new ArrayList<>();
        filePaths.add(testDirectoryPath+"\\file1.txt");
        filePaths.add(testDirectoryPath+"\\file2.txt");

        Map<String, Integer> wordMap = wc.getMapOfWordCount(filePaths);

        Map<String, Integer>  expectedWordMap = new HashMap<String, Integer>();
        expectedWordMap.put("a", 31181);
        expectedWordMap.put("software", 31306);
        expectedWordMap.put("like", 31306);
        expectedWordMap.put("i", 31306);
        expectedWordMap.put("is", 31306);
        expectedWordMap.put("cute", 17907);
        expectedWordMap.put("dg34saf", 1);
        expectedWordMap.put("mayur", 1);
        expectedWordMap.put("cutepooja", 12609);
        expectedWordMap.put("cutevpooja", 125);
        expectedWordMap.put("cutea", 125);
        expectedWordMap.put("too", 1);
        expectedWordMap.put("are", 31306);
        expectedWordMap.put("cutevv", 208);
        expectedWordMap.put("dogs" ,62611);
        expectedWordMap.put("pooja", 18571);
        expectedWordMap.put("cutev", 332);
        expectedWordMap.put("developer",31306);
        expectedWordMap.put("dog", 1);


        assertTrue(expectedWordMap, wordMap);
    }

    public static void testNoFiles() throws Exception {
        WordCountRunner wc = new WordCountRunner();

        ArrayList<String> filePaths = new ArrayList<>();

        Map<String, Integer> wordMap = wc.getMapOfWordCount(filePaths);

        Map<String, Integer>  expectedWordMap = new HashMap<String, Integer>();

        assertTrue(expectedWordMap, wordMap);
    }


}
package com.worcount;

import sun.plugin2.util.SystemUtil;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by mkadu on 4/2/2018.
 */
public class WordCountRunner {
    private static String testDirectoryPath = "C:\\work\\ODS\\Application\\wordcount-app\\wordcount\\src\\main\\java\\com\\worcount\\testFiles";

    private  int nextFileIndex = 0;

    private List<File> listOfFiles = new ArrayList<>();

    private final int  NO_OF_THREADS=3;

    private Map<String, Integer> globalWordMap = new HashMap<>();

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

        WordCountRunner runner = new WordCountRunner();
        ArrayList<String> filePaths = new ArrayList<>();
        filePaths.add(testDirectoryPath + "\\file.txt");
        filePaths.add(testDirectoryPath + "\\fileEmpty.txt");
        filePaths.add(testDirectoryPath + "\\file2.txt");
        filePaths.add(testDirectoryPath + "\\file1.txt");
        long startTime = System.currentTimeMillis();
        System.out.println("Started At :"+ startTime );

        if(filePaths.size() >0 ){
            runner.getMapOfWordCount(filePaths);
            System.out.println("Finished processing all the files");
            for (Map.Entry<String, Integer> entry : runner.globalWordMap.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
            System.out.println("Total time:" + (System.currentTimeMillis()-startTime));
        } else {
            System.out.println("No files to process");
        }

    }

    /**
     *  Generates the HashMap<word,frequency> from given files
     * @param filePaths
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public Map<String, Integer> getMapOfWordCount(List<String> filePaths) throws InterruptedException, ExecutionException {


        //Read the files from directory instead
//        File folder = new File("C:\\wordcount");
//        listOfFiles = Arrays.asList(folder.listFiles());
//
        if (filePaths == null) {
            return globalWordMap;
        }

        //uncomment below if want to run against file path
        for (String filePath : filePaths) {
            listOfFiles.add(new File(filePath));
        }


        if (listOfFiles.size() > 0) {
            int nthreads = (NO_OF_THREADS % listOfFiles.size() == 0) ? NO_OF_THREADS : (NO_OF_THREADS % listOfFiles.size());
            try{
                if(nthreads < 1){
                    throw new Exception("No threads configured.");
                }
            } catch (Exception e){
                System.out.println("No threads configured:"+ e.getMessage());
            }
            ExecutorService threadPool = Executors.newFixedThreadPool(nthreads);

            List<Future> futureList = new ArrayList<>();
            int i = 0;
            for (; i < nthreads; i++) {
                File file = getNextFile();
                if (file != null) {
                    Runnable fileProcessor = new FileProcessor(file, this);
                    Future mapResult = threadPool.submit(fileProcessor);
                    futureList.add(mapResult);
                }
            }

            //check if all the tasks are done by threads
            boolean allDone = true;

            do {
                for (Future future : futureList) {
                    if (future.get() != null)
                        allDone = (allDone && true);
                }
            } while (!allDone);
            //shutdown all the threads after finishing current task
            threadPool.shutdown();
        }

        return globalWordMap;
    }

    public  File getNextFile() {
        if (listOfFiles.size() > nextFileIndex) {
            return listOfFiles.get(nextFileIndex++);
        }

        return null;

    }

    /**
     *  accepts map from individual processed file and add/update to global map
     * @param partialMap
     */
    synchronized public void writeMapToGlobalMap(Map<String, Integer> partialMap) {
        if (partialMap != null) {
            for (Map.Entry<String, Integer> entrySet : partialMap.entrySet()) {
                String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                if (globalWordMap.containsKey(key)) {
                    globalWordMap.put(key, globalWordMap.get(key) + value);
                } else {
                    globalWordMap.put(key, value);
                }
            }
        }
    }
}


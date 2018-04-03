package com.worcount;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides implementation for processing each file in parallel to generate individual map for given file which
 * then merged to global synchronized hashmap
 * <p>
 * Created by mkadu on 4/2/2018.
 */
public class FileProcessor implements Runnable {

    private Map<String, Integer> map;
    private File file;
    private WordCountRunner wc;

    public FileProcessor(File file, WordCountRunner wc) {
        this.file = file;
        this.wc = wc;
    }

    @Override
    public void run() {
        processFile();
    }

    /**
     * Process each file and generate HashMap based on words and their frequency for given file
     */
    private void processFile() {
        while (file != null) {
            InputStream inputStream = null;
            BufferedReader br = null;
            try {
                inputStream = new FileInputStream(file);
                br = new BufferedReader(new InputStreamReader(inputStream));

                Map<String, Integer> wordMap = new HashMap<>();
                String str;
                while ((str = br.readLine()) != null) {
                    String[] wordArray = str.toLowerCase().trim()
                            .replaceAll("[!?@$%^&*#(){}<>'.,'']", "").replace("\"", "")
                            .split(" +");
                    for (String word : wordArray) {
                        if (!word.isEmpty()) {
                            if (!wordMap.containsKey(word)) {
                                wordMap.put(word, 1);
                            } else {
                                wordMap.put(word, wordMap.get(word) + 1); //update value in map
                            }
                        }
                    }
                }

                wc.writeMapToGlobalMap(wordMap);
            } catch (Exception e) {
                System.out.println(e);

            } finally {
                if(br != null){
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            file = wc.getNextFile();
        }

    }
}
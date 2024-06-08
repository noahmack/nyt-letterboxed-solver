package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WordMap {
    
    public static Map<String, String> getWordMap() {
        File words = new File("res/words.txt");
        Map<String, String> wordMap = new HashMap<>();
        try {
            Scanner scnr = new Scanner(words);
            while(scnr.hasNextLine()) {
                String currWord = scnr.nextLine();
                if(currWord.length() > 2 && !containsIllegalCharacter(currWord)) {
                    wordMap.put(currWord, currWord);
                }
            }
            scnr.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error!!!!! File Not Found.");
        }
        System.out.println(wordMap.size());
        return wordMap;
    }

    private static boolean containsIllegalCharacter(String word) {
        for(int i = 0; i < word.length(); i++) {
            if(isNotALetter(word.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isNotALetter(char c) {
        return c < 'A' || (c > 'Z' && c < 'a') || c > 'z';
    }
}

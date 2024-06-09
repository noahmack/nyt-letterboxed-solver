package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class WordSet {
    
    public static Set<String> getWordSet() {
        File words = new File("res/words.txt");
        Set<String> wordSet = new HashSet<>();
        try {
            Scanner scnr = new Scanner(words);
            while(scnr.hasNextLine()) {
                String currWord = scnr.nextLine();
                if(currWord.length() > 2 && !containsIllegalCharacter(currWord)) {
                    wordSet.add(currWord);
                }
            }
            scnr.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error!!!!! File Not Found.");
        }
        System.out.println(wordSet.size());
        return wordSet;
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

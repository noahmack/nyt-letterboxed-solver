package game;

import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import util.WordSet;

public class BaseGameContext {
    
    private String letters;
    private int currCharIndex;
    private String words;
    private int remainingWordsAllowed;
    private Set<String> wordSet;

    public BaseGameContext() {
        currCharIndex = -1;
        letters = "";
        words = "";
        remainingWordsAllowed = 5;
        wordSet = WordSet.getWordSet();
        Scanner scnr = new Scanner(System.in);
        for(int i = 0; i < 4; i++) {
            String input = "";
            System.out.println("Enter next side of the board:");
            input = scnr.nextLine();
            if(input.length() != 3) {
                System.out.println("Invalid side");
                i--;
                continue;
            }
            letters += "" + input.charAt(0) + input.charAt(1) + input.charAt(2);
        }
    }

    public void gameLoop() {
        Scanner scnr = new Scanner(System.in);
        while(!gameIsWon()) {
            if(remainingWordsAllowed == 0) {
                System.out.println("No more words.");
                scnr.close();
                return;
            }
            System.out.println("(" + remainingWordsAllowed + " or less words left) Enter word:");
            String guess = scnr.nextLine();
            if(isValidWord(guess)) {
                words += guess;
                currCharIndex = letters.indexOf(guess.charAt(guess.length() - 1));
                remainingWordsAllowed--;
            } else {
                System.out.println("Invalid Word. Try Again.");
            }
        }
        System.out.println("Congratulations! You won!");
        scnr.close();
    }

    public boolean isValidWord(String word) {
        char[] charArray = word.toCharArray();
        if(!wordSet.contains(word)) return false;
        if(letters.indexOf(charArray[0]) == -1) return false;   // first char is not on the board
        if(currCharIndex != -1 && charArray[0] != letters.charAt(currCharIndex)) return false;  // first character is illegal
        for(int i = 1; i < charArray.length; i++) {
            if(letters.indexOf(charArray[i]) == -1) return false; // character is not on the board
            if(onSameSide(charArray[i], charArray[i-1])) return false;  // two characters in a row on the same side
        }
        return true;
    }

    public void printAllTwoWordSolutions() {
        Set<String> narrowSet = new HashSet<>();
        char zero = 0;
        for(String word : wordSet) {
            if(letters.indexOf(word.charAt(0)) != -1 && isValidWord(word, zero)) {
                narrowSet.add(word);
            }
        }
        for(String word : narrowSet) {
            Set<String> potentialSet = new HashSet<>();
            for(String potential : narrowSet) {
                if(potential.charAt(0) == word.charAt(word.length()-1) && isValidWord(potential, word.charAt(word.length()-1))) {
                    potentialSet.add(potential);
                }
            }
            for(String potential : potentialSet) {
                String check = potential + word;
                if(isWinning(check)) {
                    System.out.println("Possible Solution: " + word + " - " + potential);
                }
            }
        }
    }

    private boolean isValidWord(String word, char ch) {
        char[] charArray = word.toCharArray();
        if(!wordSet.contains(word)) return false;
        if(letters.indexOf(charArray[0]) == -1) return false;   // first char is not on the board
        if(ch != 0 && charArray[0] != ch) return false;  // first character is illegal
        for(int i = 1; i < charArray.length; i++) {
            if(letters.indexOf(charArray[i]) == -1) return false; // character is not on the board
            if(onSameSide(charArray[i], charArray[i-1])) return false;  // two characters in a row on the same side
        }
        return true;
    }

    private boolean isWinning(String chars) {
        String lettersCopy = letters + "";
        for(int i = 0; i < chars.length(); i++) {
            int index = lettersCopy.indexOf(chars.charAt(i));
            if(index != -1) {
                lettersCopy = lettersCopy.substring(0, index) + lettersCopy.substring(index + 1);
            }
        }
        if(lettersCopy.length() == 0) return true;
        return false;
    }

    private boolean gameIsWon() {
        String lettersCopy = letters + "";
        for(int i = 0; i < words.length(); i++) {
            int index = lettersCopy.indexOf(words.charAt(i));
            if(index != -1) {
                lettersCopy = lettersCopy.substring(0, index) + lettersCopy.substring(index + 1);
            }
        }
        if(lettersCopy.length() == 0) return true;
        return false;
    }

    private boolean onSameSide(char a, char b) {
        return this.letters.indexOf(a) / 3 == this.letters.indexOf(b) / 3;
    }
}

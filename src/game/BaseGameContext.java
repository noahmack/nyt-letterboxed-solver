package game;

import java.util.Scanner;

public class BaseGameContext {
    
    private char[][] sides;
    private char currChar;
    private int currSide;

    public BaseGameContext() {
        currChar = 0;
        currSide = -1;
        Scanner scnr = new Scanner(System.in);
        sides = new char[4][3];
        for(int i = 0; i < 4; i++) {
            String input = "";
            System.out.println("Enter next side of the board:");
            input = scnr.nextLine();
            if(input.length() != 3) {
                System.out.println("Invalid side");
                i--;
                continue;
            }
            sides[i][0] = input.charAt(0);
            sides[i][1] = input.charAt(1);
            sides[i][2] = input.charAt(2);
        }
        scnr.close();
    }

    public boolean isValidWord(String word) {
        char[] charArray = word.toCharArray();

        if(currChar != 0 && charArray[0] != currChar) return false;

        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 3; j++) {
                if(sides[i][j] == charArray[0]) {
                    this.currSide = i;
                    break;
                }
            }
        }
        if(currSide == -1) return false;
        //TODO: continue word logic


        return false;
    }
}

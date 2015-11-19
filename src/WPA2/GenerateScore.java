package WPA2;

/**
 * ******************************
 * Copyright 2015: smanna@cpp.edu
 * CS 241
 * PA2
 * ******************************
 * !!!WARNING!!! You are not allowed
 * to make any changes to this code.
 */

import java.io.File;
import java.util.Scanner;

public class GenerateScore {

  private Dictionary dictionary;
  private int score;

  public GenerateScore(String answerFile, String dictionaryFile) {
    dictionary = new Dictionary(dictionaryFile);
    score = 0;
    grade(answerFile);
  }

  private void grade(String answerFile) {

    try {
      Scanner answers = new Scanner(new File(answerFile));
      String answer = "";
      String attempt = "";
      Boolean grade = false;
      int cases = 0;

      //print header
      System.out.printf("|%-20s|%-20s|%-10s|","ATTEMPT","ANSWER","CORRECT?");

      //grade attempts
      while (answers.hasNextLine()) {
        try{
          answer = answers.next();            //get word
          attempt = dictionary.search(answer);
          answer += answers.nextLine();
          grade = attempt.equals(answer);
          cases++;
          printBorders();
          System.out.printf("|%-20s|%-20s|%-10s|","\"" + attempt + "\"","\"" + answer + "\"",grade);
          if (grade) {
            score++;
          }
        //catch student's exceptions
        } catch (NullPointerException b) {
          printBorders();
          System.out.printf("|%-20s|%-20s|%-10s|","null",answer,"false");
        } catch (Exception c) {
          c.printStackTrace();
        }
      }

      //print final score
      printBorders();
      System.out.println("Score = " + score + "/" + cases);

    } catch (java.io.FileNotFoundException a) {
      System.out.println("Answer file not found");
    }
  }

  private void printBorders() {
    System.out.print("\n|");
    for (int i = 0; i < 52; i++) System.out.print("-");
    System.out.print("|\n");
  }

  public static void main(String[] args) {
    String golden_file1,
           golden_file2;
    if (args.length < 2 || args[0].equals("test")) {
      golden_file1 = "data/test.txt";
      golden_file2 = "data/wordsEn.txt";
    } else if (args[0].equals("eval")) {
      golden_file1 = "data/eval.txt";
      golden_file2 = "data/wordsEn.txt";
    } else {
      System.out.println("Unknown option: " + args[0]);
      System.exit(1);
      return;
    }
    new GenerateScore(golden_file1, golden_file2);
  }
}

package WPA5.java;

/**
 * ******************************
 * Copyright 2015: smanna@cpp.edu
 * CS 241
 * PA5
 * ******************************
 * !!!WARNING!!! You are not allowed
 * to make any changes to this code.
 */

import java.io.File;
import java.util.Scanner;

public class GenerateScore {

  private int score;

  public GenerateScore(String answerFile, String cityFile, String pathFile) {
    score = 0;
    grade(answerFile, cityFile, pathFile);
  }

  private void grade(String answerFile, String cityFile, String pathFile) {
    String start   = "";
    String finish  = "";
    String info    = "";
    int distance   = 0;
    int answer     = 0;
    int attempt    = 0;
    int cases      = 0;
    Boolean grade  = false;

    try {
      Scanner answers = new Scanner(new File(answerFile));
      SPT student = new SPT(cityFile,pathFile);

      //print header
      printBorders();
      System.out.printf("|%-15s|%-12s|%-12s|%-10s|","ENDPOINTS","ATTEMPT","ANSWER","CORRECT?");

      //grade attempts
      while (answers.hasNextLine()) {

        start = answers.next();  //get start or cmd
        grade = false;           //reset for different cases

        //separate the cases
        if (start.equals("delete")) {
          start    = answers.next();                //get 1st city
          finish   = answers.next();                //get 2nd city
          student.deleteEdge(start, finish);        //delete edge
          printBorders();
          System.out.printf("|%-52s|","Deleting Edge...");

        }
        else if (start.equals("insert")) {
          start    = answers.next();                //get 1st city
          finish   = answers.next();                //get 2nd city
          distance = answers.nextInt();             //get weight
          student.addEdge(start, finish, distance); //create edge
          printBorders();
          System.out.printf("|%-52s|","Adding Edge...");

        }
// Other functionality
//        else if (start.equals("print")) {
//          cases++;
//          start   = answers.next();                 //get city
//          finish  = answers.nextLine().trim();      //get answer
//          info = student.getCityData(start);        //get student attempt
//          grade = info.equals(finish);              //grade attempt
//          printBorders();
//          System.out.printf("|%-30s|%-30s|%-10s",start + " to " + info, finish, grade);
//
//        }
        else {
          cases++;
          answers.next();                           //skip "to"
          finish = answers.next();                  //get end city
          answer = answers.nextInt();               //get answer
          attempt = student.findShortestPath(start, finish);
          grade = attempt == answer;                //grade attempt
          printBorders();
          System.out.printf("|%-15s|%-12d|%-12d|%-10s|",start + " to " + finish, attempt, answer, grade);
        }
        if (grade) {
          score++;
        }
      }

      //print final score
      printBorders();
      System.out.println("Score = " + score + "/" + cases);
      answers.close();

    } catch (java.io.FileNotFoundException a) {
      System.out.println("Answer file not found");
    } catch (NullPointerException b) {
      b.printStackTrace();
      printBorders();
      System.out.printf("|%-15s|%-12s|%-12s|%-10s|",start + " to " + finish,"null",answer,"false");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void printBorders() {
    System.out.print("\n|");
    for (int i = 0; i < 52; i++) System.out.print("-");
    System.out.print("|\n");
  }

  public static void main(String[] args) {
   String golden_file1,
          golden_file2,
          golden_file3;
   if (args.length < 3 || args[0].equals("test")) {
     golden_file1 = "data/test.txt";
     golden_file2 = "data/citydat.txt";
     golden_file3 = "data/roaddat.txt";
   } else if (args[0].equals("eval")) {
     golden_file1 = "data/eval.txt";
     golden_file2 = "data/citydat.txt";
     golden_file3 = "data/roaddat.txt";
   } else {
     System.out.println("Unknown option: " + args[0]);
     System.exit(1);
     return;
   }
   new GenerateScore(golden_file1, golden_file2, golden_file3);

  }
}

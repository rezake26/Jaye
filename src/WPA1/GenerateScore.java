package WPA1;

/**
 * Copyright 2015: smanna@cpp.edu
 * CS 241
 *
 * !!!WARNING!!!
 * You are not allowed to make any changes to this code.
 **/

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;

public class GenerateScore {
  
  private int score;
  private BST tree;
  
  public GenerateScore(String fileName) {
    try {
      score = 0;
      tree = new BST();
      File file = new File(fileName);
      Scanner input = new Scanner(file);
      setup(input);
    } catch (java.io.FileNotFoundException e) {
      e.printStackTrace();
    }
  }
    
  /** 
   * Separates the test file into testcases and sends them to be graded.
   * @param input Scanner which uses the test file.
   */
  private void setup(Scanner input) {
    Scanner lineReader;
    ArrayList<String> test = new ArrayList<String>();
    String line = "";
    int testNum = 1;

    //for < n test cases
    while (input.hasNextLine()) {
      line = input.nextLine();
      lineReader = new Scanner(line);
      //if it is a new testcase: grade prior case/and/or reset data
      if (lineReader.next().equals("TESTCASE")) {
        testNum = lineReader.nextInt();
        if (testNum != 1) {
          testCase(test);
          test = new ArrayList<String>();
        }
        System.out.println("TESTCASE: " + testNum);
      }
      //get the line's data added to the testcase 
      else test.add(line);
    }
    //for nth test case
    testCase(test);
    System.out.println("Total Score: " + score + "/" + testNum);
  }
    
  private void testCase(ArrayList<String> test) {
    Scanner answers;
    tree = new BST();
      
    try {
      //for every cmd in the testcase
      for (int i = 0; i < test.size(); i++) {
        answers = new Scanner(test.get(i));
        answers.next();
  
        switch (test.get(i).charAt(0)) {
        case 'i': //insert
          tree.insert(answers.nextInt());
          break;
        case 'd': //delete
          tree.delete(answers.nextInt());
          break;
        case 'a': //inorder
          if (!tree.toString().trim().equals(answers.nextLine().trim())) { 
            System.out.println("Inorder is incorrect");
            answers.close();
            return;
          }
          break;
        case 'b': //preorder
          if (!tree.preOrder().trim().equals(answers.nextLine().trim())) {
            System.out.println("Preorder is incorrect");
            answers.close();
            return;
          }
          break;
        case 'c': //postorder
          if (!tree.postOrder().trim().equals(answers.nextLine().trim())) {
            System.out.println("PostOrder is incorrect");
            answers.close();
            return;
          }
          break;
        case 'e': //contains
          if (!("" + tree.contains(answers.nextInt())).equals(answers.nextLine().trim())) {
            System.out.println("Contains is incorrect");
            answers.close();
            return;
          }
          break;
        case 's': //size
          if (tree.size() != answers.nextInt()) {
            System.out.println("Size is incorrect");
            answers.close();
            return;
          }
          break;
        }
      }
      score++;
      System.out.println("Correct!");
    } catch (java.util.NoSuchElementException a) {
        //ignore
    } catch (NullPointerException b) {
      //System.out.println("Null Pointer Exception");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static void main(String[] args) {
     String golden_file;
     if (args.length == 0 || args[0].equals("test")) {
       golden_file = "data/test.txt";
     } else if (args[0].equals("eval")) {
       golden_file = "data/eval.txt";
     } else {
       System.out.println("Unknown option: " + args[0]);
       System.exit(1);
       return;
     }
     new GenerateScore(golden_file);
  }
}
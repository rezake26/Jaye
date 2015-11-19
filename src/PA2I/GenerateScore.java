package PA2I;

/** Copyright 2014: smanna@csupomona.edu
 *  CS 240 - Fall 2014
 *  !!!WARNING!!! Students should not modify 
 *  this code. 
 **/


import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class GenerateScore {

  private Stack<Integer> stack;
  private int totalScore;

  public GenerateScore(String stackFileName) {
    try {
      File stackFile     = new File(stackFileName);
      Scanner stackInput = new Scanner(stackFile);
      stack      = new Stack<Integer>();
      totalScore = 0;
      reader(stackInput);
      System.out.println("Stack Score: " + totalScore);
    } catch (FileNotFoundException e) {
      System.out.println("File not found!");
    }
  }

  // reading the testcases
  public void reader(Scanner input) {
    Scanner lineReader;
    ArrayList<String> test = new ArrayList<String>();
    String line = "";
    int testNum = 0;
    int[] scores = new int[2];
    //for <n test cases
    while (input.hasNextLine()) {
      line = input.nextLine();
      lineReader = new Scanner(line);
      if (lineReader.next().equals("TESTCASE")) {
        if (testNum != 0) {
          scores = testCase(test);
          System.out.println("TEST: " + testNum + " SCORE: " + scores[0]+"/" +
                             scores[1]);
          totalScore += scores[0];
          test = new ArrayList<String>();
          stack = new Stack<Integer>();
        }
        testNum = lineReader.nextInt();
        lineReader.close();
        scores = new int[2];
      }
      else
        test.add(line);
    }
    //for nth test case
    scores = testCase(test);
    System.out.println("TEST: " + testNum + " SCORE: " + scores[0] + "/" +
                       scores[1]);
    totalScore += scores[0];
  }

  /** Grades test case and returns score.
  *   @param ArrayList<String> ArrayList containing all the
  *   commands of the test case.
  *   @return int int score of test case.
  */
  public int[] testCase(ArrayList<String> test) {
    Scanner lineReader;
    String cmds[] = new String[2];
    String[] studentPrint,
             correctPrint;
    int stackContents = 0,
        possibleScore = 0,
        score = 0,
        value = 0,
        correctValue = 0;
    boolean partial = false;
    int[] scores = new int[2];
    //for every line in the testcase execute command
    for (String line: test) {
      lineReader = new Scanner(line);
      cmds[0] = lineReader.next();
      cmds[1] = lineReader.nextLine().replaceAll("[^0-9]+", "");
      try {
        switch (cmds[0].charAt(1)) {
        case 'u':
        stack.push(Integer.parseInt(cmds[1]));
        break;
        case 'e':
        if (stack.peek() != null) {
          value = stack.peek();
        }
        break;
        case 'o':
        if (stack.peek() != null) {
          value = stack.pop();
        }
        break;
        case 'r':
        possibleScore = possibleScore + 2;
        //if the student's stack's string rep is correct give 2 points
        if (stack.toString() == null) {
          //add no points
        }
        else if (stack.toString().replaceAll("[^0-9]+", "").equals(cmds[1])) {
          score = score + 2;
        }
        //else check to see if it is a partial match
        else {
          studentPrint = stack.toString().split(" ");
          correctPrint = cmds[1].split(" ");
          //check one element from students with correct until end or one match
          //is found
          for (int index = 0; index < correctPrint.length; index++) {
            for (int i = 0; index < studentPrint.length; index++) {
              if (correctPrint[index].equals(studentPrint[i])) {
                //add one point (partial credit)
                score++;
                partial = true;
                break;
              }
            }
            if (partial == true) {
              break;
            }
          }
        }
        break;
        }
      } catch (java.util.EmptyStackException e) {
          //catches exception, but does not add or sub points because this
          //will be evident in the print case. If a student throws this
          //exception wrong, his stack.toString will look different; and he will
          //get less points there.
      } catch (Exception a) {
          //if an unexpected exception is thrown and score is + subtract a point
          if (score > 0) {
            System.out.println("score subtracted");
            System.out.println(a.getMessage());
            score--;
          }
      }
    }
  scores[0]+=score;
  scores[1] = possibleScore;
  return scores;
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
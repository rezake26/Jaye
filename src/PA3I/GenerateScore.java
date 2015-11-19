package PA3I;

/**
 * COPYRIGHT 2014: smanna@csupomona.edu
 * CS 240
 *
 * !!!WARNING!!! You are not allowed to make
 * any change in this code.
 **/

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class GenerateScore {
  private Queue<Integer> queue;
  private int totalScore;

  public GenerateScore(String queueFileName) {
    try {
      File queueFile     = new File(queueFileName);
      Scanner queueInput = new Scanner(queueFile);
      queue      = new Queue<Integer>();
      totalScore = 0;
      reader(queueInput);
      System.out.println("Queue Score: "+ totalScore);
    } catch (FileNotFoundException e) {
      System.out.println("File not found!");
    }
  }

  /** Separates the test file into testcases and sends them to be graded.
  *   @param input Scanner which uses the test file.
  */
  private void reader(Scanner input) {
    Scanner lineReader;
    ArrayList<String> test = new ArrayList<String>();
    String line = "";
    int testNum = 0;
    int[] scores = new int[2];
    //for <n test cases
    while (input.hasNextLine()) {
      line = input.nextLine();
      lineReader = new Scanner(line);
      //if it is a new testcase: grade prior case/and/or reset data
      if (lineReader.next().equals("TESTCASE")) {
        //if it is not the first, grade the prior testcase
        if (testNum != 0) {
          scores = testCase(test);
          System.out.println("TEST: " + testNum + " SCORE: " + scores[0]+"/" +
                             scores[1]);
          totalScore += scores[0];
          test = new ArrayList<String>();
          queue = new Queue<Integer>();
        }
        testNum = lineReader.nextInt();
        lineReader.close();
        scores = new int[2];
      }
      //get the line's data added to the testcase arraylist
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
  int[] scores = new int[2];
  String[] studentPrint,
           correctPrint;
  Integer queueContents = 0,
          possibleScore = 0,
          score = 0,
          value = 0,
          correctValue = 0;
  //for every line in the testcase execute command
  for (String line: test) {
    lineReader = new Scanner(line);
    cmds[0] = lineReader.next();
    cmds[1] = lineReader.nextLine();
    //if the String could contain an "="
    if (cmds[1].length() > 1)
      //replace = with ""
      cmds[1] = cmds[1].replaceAll("=","");
    //trim away front and back white spaces
    cmds[1] = cmds[1].trim();
    try {
      switch (cmds[0].charAt(0)) {
      //enqueue
      case 'e':
        queue.enqueue(Integer.parseInt(cmds[1]));
      break;
      //dequeue
      case 'd':
        if (!cmds[1].equals("")) {
          possibleScore++;
        }
        //this is out of the ^ if statement because I want an exception to be
        //thrown if the queue is empty
	 value = queue.dequeue();
        if (!value.equals(null)) {
          if (Integer.parseInt(cmds[1]) == value) {
            score++;
          }
        }
        break;
      //peek or print
      case 'p':
        //peek
        if (cmds[0].charAt(1) == 'e'){
          if (!cmds[1].equals("")) {
            possibleScore++;
          }
          //this is out of the ^ if statement because I want an exception to be
          //thrown if the queue is empty
          if (queue.peek() != null) {
            value = queue.peek();
            if (Integer.parseInt(cmds[1]) == value) {
              score++;
            }
          }
        }
        //print
        else {
          possibleScore = possibleScore + 2;
          //if it is null give 0 points/prevents null pointer error
          if (queue.toString() == null) {
            //add no points
          }
          //if the student's queue's string rep is correct give 2 points
          else if (queue.toString().equals(cmds[1])) {
            score = score + 2;
          }
          //else check to see if it is a partial match
          else {
            studentPrint = queue.toString().split(" ");
            correctPrint = cmds[1].split(" ");
            //check one element from students with correct until end or 1 match
            //is found and if it is true give them one point.
            if (getPartial(studentPrint,correctPrint)) {
              score++;
            }
          }
        }
        break;
      }
    } catch (Exception a) {
        //if an unexpected exception is thrown and score is + subtract a point
        if (score > 0) {
          System.out.println("Unexpected Exception Thrown: score - 1");
          System.out.println(a.getMessage());
          score--;
        }
      }
    }
  scores[0]+=score;
  scores[1] = possibleScore;
  return scores;
  }

  /** This determines if the toString from a student's queue has a partial match
  *   If there is a partial match, the student gets partial credit.
  *   @param studentPrint String[] containing student's split queue.toString()
  *   @param correctPrint String[] correct values of queue.toString()
  *   @return true if partial credit is deserved, false if not
  */
  private boolean getPartial(String[] studentPrint, String[] correctPrint) {
    //check one element from students with correct until end or one match
    //is found
    boolean partial = false;
    for (int index = 0; index < correctPrint.length; index++) {
      for (int i = 0; index < studentPrint.length; index++) {
        if (correctPrint[index].equals(studentPrint[i])) {
          //add one point (partial credit)
          partial = true;
          break;
        }
      }
      if (partial == true) {
        break;
      }
    }
    return partial;
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

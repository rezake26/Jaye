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

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

public class Dictionary {

  private AvlTree dictionary = new AvlTree();

  public Dictionary(String filename) {
    construct(filename);
  }

  private void construct(String filename) {
    try {
      Scanner input = new Scanner(new File(filename));
      //insert words into dictionary
      while (input.hasNextLine()) {
        dictionary.insert(input.nextLine().trim());
      }
    } catch (java.io.FileNotFoundException e) {
      System.out.println("Dictionary file not found");
    }
  }

  public String search(String word) {
    return dictionary.find(word);
  }

  public static void main(String[] args) {
    new Dictionary(args[0]);
  }

}

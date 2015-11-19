package PA1;

/**
 * Copyright 2014: smanna@csupomona.edu
 * CS 240
 * Students need to write this class. Method signatures are provided.
 * You need write down the method bodies. You can define your own
 * utility private variables and methods to support the existing
 * methods provided.
 *
 * Make sure you check all the boundary conditions while implementing
 * your code here.
 * 
 * Jaye Anne Laguardia
 * jjlaguardia
 * cs240 - PA1 - Linked Lists
 * 10/23/14
 */

public class LinkedList {

  private Node head;
  private Node cursor;
  private int size;

  // constructor
  public LinkedList() {
    head = null;
    cursor = null;
    size = 0;
  }

  //adds element to end of list
  public void append(int elem) {
    if(size == 0) {
      head = new Node(elem);
      cursor = head;
    }
    else {
      cursor = head;
      for(int i = 0; i < size - 1; i++)
        cursor = cursor.next;
      cursor.next = new Node(elem);
    }
    size++;
  }

  //adds element to the beginning of the list
  public void prepend(int elem) {
    head = new Node(elem, head);
    size++;
  }

  // Post insert, element should be at a given index. Index is 0-
  // based. That means, insert(0, 5) is equivalent to prepend(5)
  public void insert(int index, int elem) throws IndexOutOfBoundsException {
    if(size == 0) {
      head = new Node(elem);
    }
    else {
      cursor = head;
      for(int i = 0; i < index - 1; i++) {
        cursor = cursor.next;
      }
      cursor.next = new Node(elem, cursor.next);
    }
    size++;
  }

  //deletes element at index i(0-based)
  public void deleteElemAt(int i) throws IndexOutOfBoundsException {
    cursor = head;
    for(int j = 0; j < i - 1; j++) {
      cursor = cursor.next;
    }
    if(i == 0) {
      head = cursor.next;
      cursor = head;
    }
    else if(i == size - 1) {
      cursor.next = null;
    }
    else
      cursor.next = cursor.next.next;
    size--;
  }

  //returns the index of the element found; -1 if not found
  public int findElem(int elem) {
    cursor = head;
    for(int i = 0; i < size; i++) {
      if(cursor.getData() == elem) {
        return i;
      }
      cursor = cursor.next;
    }
    return -1; // make sure you change this
  }

  //returns element at index i; -1 if not found
  public int readElemAt(int i) {
    if(i < 0 || i >= size){
      return -1;
    }
    cursor = head;
    for(int j  = 0; j < i; j++) {
      cursor = cursor.next;
    }
    return cursor.getData();
   // make sure you change this
  }

  //returns space separated list of elements like "1 3 5 2".
  //For empty list it should return ""
  public String toString() {
    if(size == 0)
      return "";
    else
      cursor = head;
      String answer = "";
      for(int i = 0; i < size; i++) {
        if(i == 0) {
          answer = answer + cursor.getData();
        }
        else {
          answer = answer + " " + cursor.getData();
        }
          cursor = cursor.next;
      }
      return answer;
  }


  // Defining node here
  // begin: node class
  //  WARNING: DO NOT MAKE ANY CHANGE IN THE NODE CLASS
  public class Node{
    private int data;
    private Node next;


    //for elements that are at the tail
    public Node(int data) {
      this.data=data;
      next=null;
    }

    //for everything else
    public Node(int data, Node next) {
      this.data=data;
      this.next=next;
    }

    public int getData() {
      return data;
    }
  } // end: node class
}
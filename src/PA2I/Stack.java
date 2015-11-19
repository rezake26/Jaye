package PA2I;

//Jaye Anne Laguardia
//jjlaguardia
//November 8, 2014

import java.util.*;

public class Stack<E> {

  //instantiating the linked list object
  private LinkedList<E> list;

  //Constructor
  public Stack() {
    list = new LinkedList<E>();
  }

  //this method returns the value of the top of the stack without removing it
  public E peek() throws EmptyStackException {
    return list.readElemAt(0);
  }

  //this method removes the top of the stack and returns it
  public E pop() throws EmptyStackException {
    E object = peek();
    list.deleteElemAt(0);
    return object;
  }

  //this method places a new element on top of the stack
  public void push(E item) {
    list.prepend(item);
  }

  //this method returns a boolean on whether the stack is empty or not
  public boolean empty() {
    if(list.size() == 0)
      return true;
    else
      return false;
  }

  //this method returns a string representation of the stack from top to base
  public String toString() {
    return list.toString();
  }

  //this method returns the size of the stack
  public int size() {
    return list.size();
  }
}

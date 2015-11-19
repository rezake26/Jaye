package PA3I;

/**
 * COPYRIGHT 2014: smanna@csupomona.edu
 * CS 240
 *
 * You need to implement this class. Please feel free to 
 * include your private fields and methods.
*/

//Jaye Anne Laguardia
//jjlaguardia

public class Queue<E> {
  //declaring queue
  private LinkedList<E> queue;

  //constructor
  public Queue() {
    //initializing queue
    queue = new LinkedList<E>();
  }

  /** Puts the parameter into the back of the queue.
  *   @param element The element to be inserted into the queue.
  */
  public void enqueue(E element) {
    queue.append(element);
  }

  /** Returns the first element of queue without removing it; if not found return
  *   null.
  *   @return elem at head of the queue.
  */
  public E peek() {
  if(queue.size() == 0)
    return null;
  else
    return queue.readElemAt(0); 
  }

  /** Takes the first element out of the queue and returns it.
  *   @return elem at head of the queue.
  */
  public E dequeue() {
    if(queue.size() == 0)
      return null;
    else
    {
      E temp = queue.readElemAt(0); 
      queue.deleteElemAt(0);
      return temp;
    } 
  }

  /** Returns a string representation of the queue.
  *   @return The string representation of the queue.
  */
  public String toString() {
    return queue.toString(); 
  }

  /** Returns the size of the queue.
  *   @return The size of the queue.
  */
  public int size() {
    return queue.size();
  }
}

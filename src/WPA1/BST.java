package WPA1;

/**
 * Copyright 2015: smanna@cpp.edu
 * CS 241
 *
 * STUDENTS: You need to implement BST of your own. Please do not change
 * the method signatures. Feel free to include your supporting fields
 * and methods.
 **/

public class BST {


  // TODO (student): Necessary fields
  private TreeNode root;
  private TreeNode cursor;
  /**
   * Constructs default empty tree with a null root.
   */
  public BST() {
    // TODO (student)
	root = null;
	cursor = null;
  }

  /**
   * Returns true if the element passed is in the BST
   * @param data element to be searched for
   * @return true if element is present
   */
  public boolean contains(int data) {
    // TODO (student)
	if(cursor.data == data)
	{
		cursor = root; 
		return true;
	}
	else if(cursor.data > data)
	{
		cursor = cursor.left;
		return contains(data);
	}
	else if(cursor.data < data)
	{
		cursor = cursor.right;
		return contains(data);
	}
	else
	  cursor = root;
      return false; // make sure you change this
  }

  /**
   * Deletes the value from the BST
   * @param data data to be deleted
   * @throws java.util.NoSuchElementException()
   */
  public void delete(int data) {
    // TODO (student)
	while(cursor.data != data)
	{
		if(cursor.data > data)
			cursor = cursor.left;
		else
			cursor = cursor.right;
	}
//	if(cursor.right == null && cursor.left == null)
//	  cursor.data = ;
	
  }

  /**
   * Inserts data into BST
   * @param data data to be inserted
   */
  public void insert(int data) {
	cursor = root;
	boolean added = false;
	if(root == null) {
		root = new TreeNode(data);
		  System.out.println("swag");
	}
	

    // TODO (student)

	else {
	while(!added)
	{
		if(cursor.data > data) {
			if(cursor.left != null)
				cursor = cursor.left;
			else {
				cursor.left = new TreeNode(data);
				added = true;
				  System.out.println("swag");
			}
		}
		else {
			if(cursor.right != null)
				cursor = cursor.right;
			else {
				cursor.right = new TreeNode(data);
				added = true;
				  System.out.println("swag");
			}
		}
	}
	}
  }

  /**
   * Returns a post-order String representation of the tree
   * @return String representation of the tree
   */
  public String postOrder() {
    // TODO (student)
    return postOrder(root); // make sure you change this
  }
  public String postOrder(TreeNode node) {
	  if (node == null) {
		  return "";
	  }
	  String str = "";
	  str += toString(node.right);
	  str += toString(node.left);
	  str += node.data + " ";
	  return str;
  }

  /**
   * Returns a pre-order String representation of the tree
   * @return String representation of tree
   */
  public String preOrder() {
    // TODO (student)
    return preOrder(root);
  }
  public String preOrder(TreeNode node) {
	  if (node == null) {
		  return "";
	  }
	  String str = "";
	  str += node.data + " ";
	  str += toString(node.right);
	  str += toString(node.left);
	  return str;
  }

  /**
   * Returns number of TreeNodes in the BST
   * @return number of TreeNodes
   */
  public int size(TreeNode node) {
    // TODO (student)
	  if (node == null) {
		  return 0;
	  }
	  int siz = 0;
	  siz += size(node.right) + 1;
	  siz += 1;
	  siz += size(node.left) + 1;
	  return siz;
  }
  
  public int size() { 
	return size(root);
  }

  /**
   * Returns an in-order String representation of the tree
   * @return String representation of tree
   */
  public String toString() {
    // TODO (student): in-order traversal
	return toString(root); // make sure you change this
  }

  public String toString(TreeNode node) {
	  if (node == null) {
		  return "";
	  }
	  String str = "";
	  str += toString(node.right);
	  str += node.data + " ";
	  str += toString(node.left);
	  return str;
  }

  // BEGIN -- INNER CLASS
  // DO NOT MAKE ANY CHANGE TO THIS

  class TreeNode {
 
    int data;
    TreeNode left;
    TreeNode right;

    /**
     * Constructs TreeNode containing passed data
     * @param data data to be inserted in TreeNode
     */
    public TreeNode(int data) {
      this.data = data;
      left = null;
      right = null;
    }
  }
  // END -- INNER CLASS
}

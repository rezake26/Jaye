package WPA2;

import java.util.ArrayList;

//Jaye Anne Laguardia
//jjlaguardia
//CS241, PA2
//Feb 11, 2015

/**
 * /**
 * ******************************
 * Copyright 2015: smanna@cpp.edu
 * CS 241
 * PA2
 * ******************************
 * STUDENT: You need to write this class. You MUST 
 * implement the public methods. Feel free to
 * use your own private fields and methods.
 * I have also provided you with suporting
 * private methods to help you to think!
 */

public class AvlTree
{
	// Student: your private
	// fields and methods should 
	// go here

	/** The tree root. */
	private AvlNode root;
	private static ArrayList<AvlNode> path; //used to keep track of ancestors of node

	// **********************************
	/**
	 * Internal method to insert into a subtree.
	 * @param x the item to insert.
	 * @param t the node that roots the tree.
	 * @return the new root.
	 */
	private AvlNode insert( String x, AvlNode t )
	{
		AvlNode s = new AvlNode(x);
		if(x.compareTo(t.element) < 0)	//if less than root, go left
			t.left = s;
		else	//else it is greater than so go right
			t.right = s;
		updateHeight(root);	//ensure all heights of nodes are correct
		return root;
	}

	/**
	 * Internal method to find an item in a subtree.
	 * @param x is item to search for.
	 * @param t the node that roots the tree.
	 * @return node containing the matched item.
	 */
	private AvlNode find( String x, AvlNode t )
	{
		if(t == null) return null;	//if empty, return null
		if(x.compareTo(t.element) < 0)	//go left
			find(x, t.left);
		else if(x.compareTo(t.element) > 0) {	//go right
			find(x, t.right);
		}
		else if(x.compareTo(t.element) == 0) {	//found
			return t;
		}
		return null;	//default return if not found
	}

	//used to find balance factor of a given node
	private int balanceFactor(AvlNode t) {
		if(t == null) return 0;	//if empty
		updateHeight(root);	//ensure all heights are correct

		//calculate balance factor
		int i = (t.right == null ? 0 : t.right.height) - (t.left == null ? 0: t.left.height);
		return i;
	}

	/**
	 * Return the height of node t.
	 */
	private static int height( AvlNode t )
	{
		return t.height;
	}

	//updates height of all nodes within a given tree and return height
	private static int updateHeight(AvlNode k1){
		if(k1 == null) return -1;	//if empty
		int i = updateHeight(k1.left) + 1;	//update left heights of root
		int j = updateHeight(k1.right) + 1;	//update right heights of root
		k1.height = i > j ? i : j;	//return larger height
		return k1.height;
	}

	//jumps to point of insertion
	private static AvlNode fastForward(String x, AvlNode t) {
		int comp = x.compareTo(t.element);	//factor for determining direction of movement in tree
		path.add(t);	//add current node to path to keep track of ancestors

		if(comp < 0) {	//go left
			if(t.left == null) {
				return t;	//arrived at point of insertion
			}
			return fastForward(x, t.left);	//continue left
		}
		else if(comp > 0) {	//go right
			if(t.right == null) {
				return t;	//arrived at point of insertion
			}
			return fastForward(x, t.right);	//continue right
		}
		else
			return t;	//if at end, return point of insertion
	}

	/**
	 * Rotate binary tree node with left child.
	 * For AVL trees, this is a single rotation for case 1.
	 * Update heights, then return new root.
	 */
	private static AvlNode rotateWithLeftChild( AvlNode k2 )
	{
		AvlNode k1 = k2.left;	//make new subtree
		k2.left = k1.right;	//set nodes
		k1.right = k2;
		return k1;
	}




	/**
	 * Rotate binary tree node with right child.
	 * For AVL trees, this is a single rotation for case 4.
	 * Update heights, then return new root.
	 */

	private static AvlNode rotateWithRightChild( AvlNode k2 )
	{

		AvlNode k1 = k2.right;	//create new subtree
		k2.right = k1.left;	//set nodes
		k1.left = k2;
		return k1;
	}



	/**
	 * Double rotate binary tree node: first left child
	 * with its right child; then node k3 with new left child.
	 * For AVL trees, this is a double rotation for case 2.
	 * Update heights, then return new root.
	 */
	private static AvlNode doubleWithLeftChild(AvlNode k3)
	{
		k3.left = rotateWithRightChild(k3.left);	//rotate left
		return rotateWithLeftChild(k3);	//rotate right

	}  

	/**
	 * Double rotate binary tree node: first right child
	 * with its left child; then node k1 with new right child.
	 * For AVL trees, this is a double rotation for case 3.
	 * Update heights, then return new root.
	 */
	private static AvlNode doubleWithRightChild(AvlNode k1)
	{
		k1.right = rotateWithLeftChild(k1.right);	//rotate right
		return rotateWithRightChild(k1);	//rotate left
	}
	//******************************

	// PUBLIC methods 
	/**
	 * Construct the tree.
	 */
	public AvlTree( )
	{
		root = null;
		path = new ArrayList();
	}

	/**
	 * Insert into tree; ignore duplicates, but keep count of occurrences.
	 * @param x the item to insert.
	 */
	public void insert( String x )
	{
		if(root == null) {	//if tree is empty, add node as root
			AvlNode t = new AvlNode(x);
			root = t;
		}
		else if(find(x, root) != null) { //if word is already in tree, update frequency
			AvlNode t = find(x, root);
			t.frequency += 1;
		}
		else if(root.height == 0) { //if only root exists, add as left or right child, update height
			insert(x, root);
			updateHeight(root);
		}
		else { //otherwise substantial tree exists, add to it

			path.clear(); //wipe record of ancestors
			AvlNode t = fastForward(x, root); //go to point of insertion
			insert(x, t); //insert as left or right
			AvlNode u; //parent of pivot point
			if(path.size() <= 3) { //if there isn't a great grandparent, root of tree is parent of pivot
				u = root;
			}
			else { //else there is a great grandparent
				u = path.get(path.size() - 3);
			}

			int AAB = balanceFactor(path.get(path.size() - 2)); //grandparent balance factor
			int AB = balanceFactor(path.get(path.size() - 1)); //parent balance factor
			if(AAB == -2 && AB == -1) {	//left-left
				u.left = rotateWithLeftChild(path.get(path.size() - 2));
			}
			else if(AAB == 2 && AB == 1) {	//right-right
				u.right = rotateWithRightChild(path.get(path.size() - 2));
			}
			else if(AAB == -2 && AB == 1) {	//left-right
				u.left = doubleWithLeftChild(path.get(path.size() - 2));
			}
			else if(AAB == 2 && AB == -1) {	//right-left
				u.right = doubleWithRightChild(path.get(path.size() - 2));
			}
			updateHeight(root);	//update hiehgts of all nodes
		}

	}

	/**
	 * Find an item in the tree.
	 * @param x the item to search for.
	 * @return String x + " " + frequency + " " + (height(right) - height(left))
	 * if not found return String x + " 0 -100"
	 */
	public String find( String x )
	{
		AvlNode s = find(x, root);	//look for object in tree
		if(x.compareTo(s.element) == 0)	//if found object is equal, return
			return x + " " + s.frequency + " " + (height(s.right) - height(s.left)); //change this
		return x + " 0 -100";	//default return
	}

}

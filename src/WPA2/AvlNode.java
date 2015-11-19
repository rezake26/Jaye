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


// Basic node stored in AVL trees
// Note that this class is not accessible outside
// of package

class AvlNode
{
  // Constructors
  AvlNode( String theElement )
  {
    this( theElement, null, null );
  }
  
  AvlNode( String theElement, AvlNode lt, AvlNode rt )
  {
    element  = theElement;
    left     = lt;
    right    = rt;
    height   = 0;
    frequency = 0;
  }
  
  // Friendly data; accessible by other package routines
  String element;      // The data in the node
  AvlNode    left;         // Left child
  AvlNode    right;        // Right child
  int        height;       // Height
  int        frequency;
}

package WPA5.java;

/**
 * ******************************
 * Copyright 2015: smanna@cpp.edu
 * CS 241
 * PA5
 * ******************************
 * !!!WARNING!!! You are not allowed
 * to make any changes to this code.
 */

import java.util.ArrayList;

public class Vertex {
  
  private int label;                   //label is for easy identification
  private int parent;                  //for SPT navigation
  private boolean visited;             //flag to tell if visited
  private ArrayList<Vertex> neighbors; //list of all neighbors
  
  public Vertex(int label) {
    this.label = label;
    this.visited = false;
    neighbors = new ArrayList<Vertex>();
  }

  public void addNeighbor(Vertex v) {
    neighbors.add(v);
  }
  
  public void deleteNeighbor(Vertex v) {
    neighbors.remove(v);
  }
  
  public int getDegree() {
    return neighbors.size();
  }
  
  public int getLabel() {
    return label;
  }

  public Vertex[] getNeighbors() {
    Vertex[] output = new Vertex[neighbors.size()];
    for (int i = 0; i < neighbors.size(); i++) {
      output[i] = neighbors.get(i);
    }
    return output;
  }
  
  public int getParent() {
    return parent;
  }
  
  public void setVisited(boolean visited) {
    this.visited = visited;
  }

  public void setParent(int parent) {
    this.parent = parent;
  }
  
  public String toString() {
    return "[" + label + "]";
  }

  public boolean visited() {
    return visited;
  }

}

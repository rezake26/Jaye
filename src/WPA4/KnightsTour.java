package WPA4;

/**
 * ******************************
 * Copyright 2015: smanna@cpp.edu
 * CS 241
 * PA4
 * ******************************
 * STUDENT: You need to write this class. You MUST 
 * implement the public and private methods as shown. 
 * Feel free to include your own private fields and 
 * methods as well if you find it necessary.
 * 
 * Also make sure to comment your code, otherwise 2 points 
 * will be deducted.
 */

//Jaye Anne Laguardia
//jjlaguardia
//CS241 - PA4
//March 4th, 2015

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class KnightsTour {

	// private fields
	private int size;
	private Graph graph;
	private String path;
	private int[][] mapping;
	private boolean complete;

	// constructor
	public KnightsTour(int size) {
		this.size = size;
		graph = createGraph();
		path = "";
		mapping = mapping(size);
		complete = false;
	}

	/**
	 * Creates a graph representing all of the places on the
	 * board and the possible moves between them.
	 * @return Graph representation of the board.
	 */
	private Graph createGraph() {
		//make new graph
		Graph ans = new Graph(size*size);
		//go through each coordinate
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				//get legal moves from coordinate
				ArrayList<int[]> coords = getLegalMoves(i,j);
				//for each legal move, add edge between it and root position
				for(int k = 0; k < coords.size(); k++) {
					//first the root coordinate is passed to identify which returns its label
					//second the label for the legal move is obtained
					ans.addEdge(identify(i, j), identify(coords.get(k)[0], coords.get(k)[1]));
				}
			}
		}
		return ans;
	}

	/**
	 * Use dfs to traverse the graph until the target is found. This
	 * indicates that we have found a path from v to target. Returns a
	 * String of each label visited (beginning with v, ending with target).
	 * Uses the global variable completed along with the Vertex field,
	 * visited, to determine if it should recurse.
	 * @param v Current Vertex
	 * @param target Vertex that is the destination of the path.
	 * @return String of all vertices (labels) visited
	 */
	private String dfs(Vertex v, Vertex target) {
		//use stack to backtrack
		Stack s = new Stack();
		s.push(v);
		//search for target until it is found
		while(!complete) {
			//get current vertex
			Vertex here = (Vertex) s.peek();
			//if there are unvisited neighbors, visit them
			if(!here.visited()) {
				here.setVisited(true);
				path += " " + here.getLabel();
			}
			//base case; if target is found, exit while loop
			if(here.equals(target)) {
				complete = true;
			}
			else {
				//make a vertex array of current position's neighbors
				Vertex[] neighbors = here.getNeighbors();
				//iterate through neighbors
				for(int i = 0; i < neighbors.length; i++) {
					if(!neighbors[i].visited()) {
						//add first unvisited neighbor to stack
						s.push(neighbors[i]);
						i = neighbors.length;
					}
					//if no unvisited neighbors, backtrack
					else if(i == neighbors.length - 1 && neighbors[i].visited()) {
						s.pop();
					}
				}
			}
		}
	return path;
}

/**
 * Returns the correct label for the xy coordinate parameter.
 * @param x Coordinate x
 * @param y Coordinate y
 * @return int representing the spot on the board.
 */
private int identify(int x, int y) {
	return ((size * x) + y);
}

/**
 * Tests the coordinate to see if it is on the board.
 * @param coordinate
 * @return true if move is legal; false otherwise
 */
private boolean isLegal(int coordinate) {
	return (coordinate >= 0) && (coordinate <= ((size * size) - 1));
}

/**
 * Returns a list of all the possible (legal) moves from the current spot.
 * @param x Current x coordinate
 * @param y Current y coordinate
 * @return List of all possible legal moves from current spot
 */
private ArrayList<int[]> getLegalMoves(int x, int y) {
	ArrayList<int[]> ans = new ArrayList();
	int xnew = x;
	int ynew = y;
	//using the coordinate, add each legal move
	for(int i = 1; i <= 8; i++) {
		switch(i) {
		case 1:
			xnew = x - 2;
			ynew = y + 1;
			if(xnew >= 0 && xnew < size && ynew >= 0 && ynew < size && isLegal(identify(xnew, ynew))) {
				int[] pair = {xnew, ynew};
				ans.add(pair);
			}
			break;
		case 2:
			xnew = x - 1;
			ynew = y + 2;
			if(xnew >= 0 && xnew < size && ynew >= 0 && ynew < size && isLegal(identify(xnew, ynew))) {
				int[] pair = {xnew, ynew};
				ans.add(pair);
			}
			break;
		case 3:
			xnew = x + 1;
			ynew = y + 2;
			if(xnew >= 0 && xnew < size && ynew >= 0 && ynew < size && isLegal(identify(xnew, ynew))) {
				int[] pair = {xnew, ynew};
				ans.add(pair);
			}
			break;
		case 4:
			xnew = x + 2;
			ynew = y + 1;
			if(xnew >= 0 && xnew < size && ynew >= 0 && ynew < size && isLegal(identify(xnew, ynew))) {
				int[] pair = {xnew, ynew};
				ans.add(pair);
			}
			break;
		case 5:
			xnew = x + 2;
			ynew = y - 1;
			if(xnew >= 0 && xnew < size && ynew >= 0 && ynew < size && isLegal(identify(xnew, ynew))) {
				int[] pair = {xnew, ynew};
				ans.add(pair);
			}
			break;
		case 6:
			xnew = x + 1;
			ynew = y - 2;
			if(xnew >= 0 && xnew < size && ynew >= 0 && ynew < size && isLegal(identify(xnew, ynew))) {
				int[] pair = {xnew, ynew};
				ans.add(pair);
			}
			break;
		case 7:
			xnew = x - 1;
			ynew = y - 2;
			if(xnew >= 0 && xnew < size && ynew >= 0 && ynew < size && isLegal(identify(xnew, ynew))) {
				int[] pair = {xnew, ynew};
				ans.add(pair);
			}
			break;
		case 8:
			xnew = x - 2;
			ynew = y - 1;
			if(xnew >= 0 && xnew < size && ynew >= 0 && ynew < size && isLegal(identify(xnew, ynew))) {
				int[] pair = {xnew, ynew};
				ans.add(pair);
			}
			break;
		}
	}
	return ans;
}

/**
 * Returns a mapping of (x,y) coordinate to vertex label
 * @param size the size of one side of the square board.
 * @return
 */
private int[][] mapping(int size) {
	int[][] ans = new int[size][size];
	for(int i = 0; i < size; i++) {
		for(int j = 0; j < size; j++) {
			ans[i][j] = identify(i,j);
		}
	}
	return ans;
}

/**
 * Finds path from starting label to target label using graph.
 * @param starting label of first Vertex
 * @param target label of target Vertex
 * @return String of every Vertex visited on path from start to target.
 */
public String findPath(int starting, int target) {
	path = "";
	path = " [" + dfs(graph.getVertex(starting), graph.getVertex(target)).trim() + "]"; 
	graph.resetVisit();
	String ans = complete + path;
	complete = false;
	return ans;
}
}

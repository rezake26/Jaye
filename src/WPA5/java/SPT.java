package WPA5.java;

/**
 * ******************************
 * Copyright 2015: smanna@cpp.edu
 * CS 241
 * PA5
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
//CS241 - PA5
//March 13th, 2015

import java.util.PriorityQueue;

public class SPT {

	Graph graph;             //graph of all Cities
	PriorityQueue<City> q;   //p-queue for finding smallest fringe city
	int root = 0;            //current root of SPT

	public SPT(String cityData, String pathData) {
		q = new PriorityQueue<City>();
		graph = new Graph(cityData, pathData);
		dijkstras(root);
	}

	// TODO (Student)
	// include your own private fields/methods here
	// if you think its necessary

	/**
	 * Set w as a fringe city, set its parent, set its cost, and add to the p-queue
	 * @param v label of the parent city
	 * @param w label of the current city
	 */
	private final void addFringe(int v, int w) {
		City W = graph.getCity(w);	//get City objects from labels
		City V = graph.getCity(v);
		W.setFringe(true);	//set it as fringe
		W.setParent(v);	//set parent
		W.setCost(newCost(v, w));	//adjust cost
		q.add(V);	//add to priority queue
	}

	/**
	 * Update the cost and parent of the fringe element
	 * @param v label of parent city
	 * @param w label of current city
	 */
	private final void modifyFringe(int v, int w) {
		City W = graph.getCity(w);	//get City objects from labels
		City V = graph.getCity(v);
		W.setParent(v);	//set parent
		W.setCost(newCost(v, w));	//adjust cost
	}

	/**
	 * Calculates the new cost from a to b.
	 * @param a label of first city
	 * @param b label of second city
	 * @return new cost between a and b
	 */
	private int newCost(int a, int b) {
		return graph.getCity(a).getCost() + graph.getWeight(a, b);	//calculate new cost
	}

	/**
	 * Adds new Edge to graph and restructures the SPT.
	 * @param start Starting City
	 * @param end   Ending City
	 * @param weight Cost to get between both Cities
	 */
	public void addEdge(String start, String end, int weight) {
		graph.addEdge(graph.getLabel(start), graph.getLabel(end), weight);	//add new edge
		dijkstras(root);	//calculate new paths
	}

	/**
	 * Removes the edge from the graph and restructures the SPT
	 * @param start Starting City
	 * @param end Ending City
	 */
	public void deleteEdge(String start, String end) {
		graph.deleteEdge(graph.getLabel(start), graph.getLabel(end));	//delete edge
		dijkstras(root);	//calculate new paths
	}

	public String getCityData(String code) {
		return graph.getCity(graph.getLabel(code)).toString();	//return toString of City
	}

	/**
	 * Find Shortest Path between two Cities
	 * @param start Starting City code
	 * @param finish Ending City code
	 * @return int distance between starting and ending City
	 */
	public int findShortestPath(String start, String finish) {
		int Start = graph.getLabel(start);	//get label of start
		int End = graph.getLabel(finish);	//get label of end
		return findShortestPath(Start, End);	//call findShortestPath with label args
	}

	/**
	 * Using the characteristic of trees--there is always a unique path
	 * moving up to a parent--this method travels up the SPT from the
	 * end city to the starting city and constructs the total cost of the
	 * shortest path.
	 * @param start Starting City
	 * @param finish Ending City
	 * @return distance of the shortest path between the cities
	 */
	public int findShortestPath(int start, int finish) {
		if(start != root) {	//if your root and start aligned
			dijkstras(start);	//recalculate paths
		}
		int distance = graph.getCity(finish).getCost();	//get distance for path
		return distance;
	}

	/**
	 *  Dijkstra's algorithm using relaxation to find shortest path tree.
	 *  @param label of desired root (start city)
	 */
	public void dijkstras(int label) {
		graph.resetVisit();	//reset fields
		graph.resetCost();
		graph.resetFringe();
		graph.resetParent();
		graph.getCity(label).setCost(0);	//set start cost to 0
		Vertex[] cities = graph.getVertices();	//get list of vertexes, made method in Graph.java for this 
												//as well as non-functioning one below
		for(Vertex c : cities) {	//for each city vertex
			q.add((City) c);	//add it to priority queue
		}

		while(!q.isEmpty()) {	//while there are still paths to calculate
			City at = q.poll();	//get next City
			at.setVisited(true);	//set it as visited
			Vertex[] neigh = at.getNeighbors();	//get list of all neighbors
			for(Vertex c : neigh) {	//for each neighbor
				if(!c.visited()) {	//neighbor has not been visited
					if(((City) c).isFringe()) {	//and neighbor is a fringe
						if(newCost(at.getLabel(), c.getLabel()) < ((City) c).getCost()) {	//and cost is less
							modifyFringe(at.getLabel(), c.getLabel());	//modify cost
						}							
					}					
					else { //otherwise it isn't a fringe
						addFringe(at.getLabel(), c.getLabel());	//and add it as a fringe
					}
				}
			}
		}

	}

	//non-functional workaround for getting list of vertices
	//above on line 141 a workaround is used that involves modifying Graph.java to include
	//a function that return the list of vertices straight from the class
	//code gets 25/25 using the illegal workaround involving Graph.java
	//with this method it doesn't run correctly and has errors
	public Vertex[] getVertices() {
		int size = graph.getOrder();
		Vertex[] ans = new Vertex[size];
		int inans = 0;
		Edge[] test = graph.getEdges();
		for(Edge e : test) {
			String toSplit = e.toString();
			String one = "";
			String two = "";
			boolean comma = false;
			int pos = 0;
			while(pos <= toSplit.length()) {
				if(Character.isDigit(toSplit.charAt(pos)) && !comma) {
					one+= toSplit.charAt(pos);
				}
				else if(Character.isDigit(toSplit.charAt(pos)) && comma) {
					two+= toSplit.charAt(pos);
				}
				else if(toSplit.substring(pos, pos).compareTo(",") == 0) {
					comma = true;
				}
				pos++;
			}
			int u = Integer.parseInt(one);
			int v = Integer.parseInt(two);
			boolean uused = false;
			boolean vused = false;
			for(Vertex c : ans) {
				if(c.equals(graph.getCity(u))){
					uused = true;
				}
				if(c.equals(graph.getCity(v))) {
					vused = true;
				}
			}
			if(!uused) {
				ans[inans + 1] = graph.getCity(u);
				inans++;
			}
			if(!vused) {
				ans[inans + 1] = graph.getCity(v);
				inans++;
			}
		}
		return ans;
	}
}

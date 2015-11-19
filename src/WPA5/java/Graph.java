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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph {

	private int order;                 //number of cities in graph
	private Vertex[] vertices;         //array of all cities
	private TreeMap<Edge, Edge> edges; //all paths

	/**
	 * Builds graph with desired amount of vertices.
	 */
	public Graph(String cityData, String pathData) {
		edges = new TreeMap<Edge, Edge>();
		initialize(cityData, pathData);
	}

	private void initialize(String cityData, String pathData) {

		//get order
		try {
			Scanner input = new Scanner(new File(cityData));
			this.order = Integer.parseInt(input.nextLine().trim());

			vertices = new City[order];
			createVertices();             //create cities
			setCityValues(input);         //set city values
			addEdges(pathData);           //create paths between cities
		} catch (FileNotFoundException a) {
			a.printStackTrace();
		}
	}

	/**
	 * Adds the edges from the file into the graph
	 * @param filename name of edge file
	 */
	protected void addEdges(String filename) {
		Scanner input;
		try {
			input = new Scanner(new File(filename));

			while(input.hasNextLine()) {
				String line = input.nextLine();
				String[] values = (line.trim()).split("\\s+");
				int u = Integer.parseInt(values[0]);          //1st city
				int v = Integer.parseInt(values[1]);          //2nd city
				int weight = Integer.parseInt(values[2]);     //weight
				addEdge(u,v,weight);
			} 
			input.close(); //close Scanner
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the attributes specified in the file to the cities.
	 * @param filename name of city file
	 */
	protected void setCityValues(Scanner input) {
		City city;
		//modify each city
		for (int i = 0;i < order; i++) {
			String line = input.nextLine();
			String[] values = (line.trim()).split("\\s+");
			int cityNum = Integer.parseInt(values[0]); //get label for city
			city = (City)vertices[cityNum];
			city.setCode(values[1]);                   //city code
			city.setName(values[2]);                   //city name
			city.setPop(Integer.parseInt(values[3]));  //city pop
			city.setElev(Integer.parseInt(values[4])); //city elev
		} 
		input.close(); //close Scanner
	}  

	/**
	 *  Add weighted edge
	 *  @param u vertex
	 *  @param v vertex
	 *  @param weight edge weight
	 */
	public void addEdge(int u, int v, int weight) {
		//add both to each other's list of neighbors (undirected)
		vertices[u].addNeighbor(vertices[v]);
		vertices[v].addNeighbor(vertices[u]);
		//create edge and put in edges map
		Edge edge = new Edge(u,v,weight);
		edges.put(edge,edge);
	}

	/**
	 * Deletes edge
	 * @param u vertex
	 * @param v vertex
	 */
	public void deleteEdge(int u, int v) {
		//delete both to each other's list of neighbors (undirected)
		vertices[u].deleteNeighbor(vertices[v]);
		vertices[v].deleteNeighbor(vertices[u]);
		//remove from map
		Edge edge = new Edge(u,v,0);
		edges.remove(edge);
	}

	/**
	 * Fills graph with vertices up to the desired amount. Also
	 * labels vertices from 0 to n-1;
	 */
	public void createVertices() {
		for (int i = 0; i < order; i++) {
			vertices[i] = new City(i);
		}
	}

	public City getCity(int label) {
		return (City)vertices[label];
	}

	/**
	 *  Returns the maximum degree in the graph
	 *  @return degree of Vertex with most neighbors
	 */
	public int getDegree() {
		int max = 0;
		int current;
		for (Vertex vertex: vertices) {
			current = vertex.getDegree();
			if (current > max) max = current;
		}
		return max;
	}

	/**
	 *  Return degree of vertex
	 *  @param int label of vertex
	 *  @return number of neighbors of v
	 */
	public int getDegree(int label) {
		return vertices[label].getDegree();
	}

	/**
	 *  Return information for edge e
	 *  @return Edge
	 */
	public Edge getEdge(Edge e) {
		return edges.get(e);
	}

	/**
	 *  Return list of all edges
	 *  @return array of edges
	 */
	public Edge[] getEdges() {
		return edges.keySet().toArray(new Edge[0]);
	}

	public int getLabel(String code) {
		for (City c: (City[])vertices) {
			if (c.getCode().equals(code)) return c.getLabel();
		}
		return -1;
	}

	/**
	 *  Return list of neighbors of vertex
	 *  @param int label corresponding to vertex
	 *  @return array of neighbors of vertex
	 */
	public Vertex[] getNeighbors(int label) {
		return vertices[label].getNeighbors();
	}

	/**
	 *  Return number of vertices in the graph
	 *  @return number of vertices
	 */
	public int getOrder() {
		return order;
	}

	/**
	 *  Return vertex of specified label
	 *  @return vertex
	 */
	public Vertex getVertex(int label) {
		return vertices[label];
	}

	public int getWeight(Edge edge) {
		return edges.get(edge).getWeight();
	}

	public int getWeight(int vertex1, int vertex2) {
		return edges.get(new Edge(vertex1,vertex2, 0)).getWeight();
	}

	/**
	 * Unmark all vertices in the graph
	 */
	public void resetVisit() {
		for (int i = 0; i < order; i++) {
			vertices[i].setVisited(false);
		}
	}

	public void resetParent() {
		for (int i = 0; i < order; i++) {
			vertices[i].setParent(0);
		}
	}

	public void resetCost() {
		for (int i = 0; i < order; i++) {
			((City)vertices[i]).setCost(Integer.MAX_VALUE);
		}
	}

	public void resetFringe() {
		for (int i = 0; i < order; i++) {
			((City)vertices[i]).setFringe(false);
		}
	}

	//simple workaround method to get list of vertices/cities in SPT
	public Vertex[] getVertices() {
		return vertices;
	}

}

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

public class City extends Vertex implements Comparable<City>{

  private boolean fringe;    //flag to tell if fringe
  private String  name,      //name of city
                  code;      //2-letter code for city
  private int     pop,       //pop of city
                  elev,      //elev of city
                  cost;      //distance to city
  
  public City(int label) {
    super(label);
  }
  
  /**
   * Compares cities based on cost. If both are equal, go by label
   * order.
   * @param that city to compare to.
   * @return 1 if greater, 0 if equal, -1 if less than
   */
  public int compareTo(City that) {
    //if both are equal distance, compare label
    if (this.cost == that.cost) {
      if (this.getLabel() < that.getLabel()) return -1;
      else return 1;
    }
    else if (this.cost < that.cost) return -1;
    else return 1;
  }
  
  /**
   * Sets the fringe attribute of the City.
   * @param fringe
   */
  public void setFringe(boolean fringe) {
    this.fringe = fringe;
  }
  
  /**
   * Returns if a City is a fringe vertex.
   * @return
   */
  public boolean isFringe() {
    return fringe;
  }
  
  /**
   * Sets the cost for comparison of cities.
   * @param cost new cost for city
   */
  public void setCost(int cost) {
    this.cost = cost;
  }
  
  /**
   * Returns the cost of the City.
   * @return cost of the city
   */
  public int getCost() {
    return cost;
  }
  
  public String getCode() {
    return code;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public void setCode(String code) {
    this.code = code;
  }
  
  public void setPop(int pop) {
    this.pop = pop;
  }
  
  public void setElev(int elev) {
    this.elev = elev;
  }
  
  public String toString() {
    return this.getLabel() + " " + code + " " + name + " " + 
           pop + " " + elev;
  }
}

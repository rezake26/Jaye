package PA2II;

/** Copyright 2014: smanna@csupomona.edu
 *  CS 240
 * 
 * This code is based on equations in infix notation using the method by as discussed in
 * class.
*  
*  Students need to implement a Calculator using the framework provided. Feel
*  free to add your own fields and methods besides ones already provided. Please do not make
*  any changes to the provided method signatures.
*
*  ** YOUR RESULTS SHOULD BE ROUNDED TO THREE DECIMAL PLACES. IF YOU FAIL TO DO
*  SO, YOU WILL BE PENALIZED BY 1 POINT.
*  ** YOU SHOULD PROPOERLY COMMENT YOUR CODE, OTHERWISE YOU WILL BE PENALIZED
*  BY 5 POINTS.
*
**/

//Jaye Anne Laguardia
//jjlaguardia

import java.io.*;
import java.text.DecimalFormat;

public class Calculator {

	//initialize private stacks, one for operands and one for operators
	private Stack<Double> operands;
	private Stack<Character> operators;

  //Constructor
  public Calculator() {
	  operands = new Stack<Double>();
	  operators = new Stack<Character>();
  }

  /** Returns true if the new character has precedence to be pushed on to the
  *   stack. Returns false if not.
  *   @param previous operator already on the stack
  *   @param newChar operator being added to the stack
  *   @return boolean of whether the operator has precedence
  */
  private boolean hasPrecedence(Character previous, Character newChar) {
    //if the previous has top precedence then the new doesn't
    if (previous == '*' || previous == '/')
      return false;
    //subtract previous first then new
    else if (previous == '-' && newChar == '-')
      return false;
    //subtract previous then add new
    else if (previous == '-' && newChar == '+')
      return false;
    else {
      return true;
    }
  }

  /** Pops two operands and one operator and performs the operation.
  *   @param operands stack of the operands
  *   @param operators stack of the operators
  *   @return answer to the operation
  */
  private double popStackAndSolve(Stack<Double> operands,Stack<Character>
                                  operators) {
    double operand1,operand2,answer;
    char operator;
    operand2 = operands.pop(); //pop the second first because stack ordering
    operand1 = operands.pop();
    operator = operators.pop();
    answer = calc(operand1,operand2,operator);
    return answer;
  }

  /** @param operand1 the first operand in the equation
  *   @param operand2 the second operand in the equation
  *   @param operator the operator to perform on the two operands
  *   @return the answer
  */
//this method performs the operation called between two operands
  public double calc(double operand1, double operand2, char operator) {
	if(operator == '*') return operand1*operand2;
    else if(operator == '+') return operand1+operand2;
    else if(operator == '-') return operand1-operand2;
    else if(operator == '/') return operand1/operand2;
    return 0;
  }

  /** Solves an infix equation and returns the answer as a Double. Limits to 3
  *   decimal places.
  *   @param equation infix String equation to be solved.
  *   @return double answer to equation
  */
  public Double solve(String equation) {
      //reads each character and stores it in their respective stack
	  String num = "";
	  for(int i = 0; i < equation.length(); i++)
	  {
		  Character c = equation.charAt(i);
		  //if its a number, add to operand stack
		  if(Character.isDigit(c) || c == '.')
		  {
			  num+= c;
			  if(i == equation.length() - 1 || !Character.isDigit(equation.charAt(i + 1)) && equation.charAt(i + 1) != '.'){
				  operands.push(Double.parseDouble(num));
				  num = "";
			  }
		  }
		  //if its an operator and operator stack is empty, add to stack
		  else if(operators.size() == 0)
		  {
			  operators.push(c);
		  }
		  //if its an operator and operator stack is not empty, add to stack as follows
		  //checks for parentheses since they have precedence first
		  else  if (c == '(') {
			  operators.push(c);
		  }
		  else if(c.equals(')')){
			  while(!operators.peek().equals('('))
			  operands.push(popStackAndSolve(operands, operators));
			  operators.pop();
		  }
		  //otherwise, it's an operator and add to operator stack accordingly
		  else
		  {
			  //first check if the new char has precedence to be pushed onto the stack
			  //if it doesn't have precedence, pop and add new value back onto operands stack
			  if(!hasPrecedence(operators.peek(), c))
			  {
				  operands.push(popStackAndSolve(operands, operators));
			  }
			  //otherwise, the operator will always be pushed onto the operator stack
			  operators.push(c);
		  }
	  }
	  //pop until operator stack is empty
	  while(operators.size() != 0)
		  operands.push(popStackAndSolve(operands, operators));
	  //terminating the answer to 3 decimal places
	  double n = operands.pop();
	  DecimalFormat formatter = new DecimalFormat("#.###");
	  return Double.parseDouble(formatter.format(n));
  }  
}

package N_Queen;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Jaye Anne Laguardia
 */
public class Main {

	public static void main(String[] args) {
		int mode = 1; // determines which method to use
		int n = 17; // number of queens, size of board
		int runs = 1000; // amount of runs to complete
		double passed = 0; // amount of successful runs
		double failed = 0; // amount of failed runs
		long timeTot = 0; // amount of time taken to attempt all solutions
		int costTot = 0; // amount of moves taken for all runs

		for (int run = 0; run < runs; run++) { // repeat until enough runs are completed
			long startTime = System.currentTimeMillis();
			Grid board = new Grid(n);
			System.out.println("Initial grid");
			board.printGrid();

			switch (mode) {

			// Local Search Hill Climbing Approach 1
			case 1:
				boolean bricked1 = false; // true if brick wall encountered
				while (!bricked1) { // while still solvable
					int[] xs = board.getQueenX();
					int[] ys = board.getQueenY();

					int heuristicCurrent = board.getH1(); // heuristic of current board
					int heuristicBest = heuristicCurrent; // heuristic of best candidate
					Grid newBoard = board; // board of best candidate

					for (int a = 0; a < n; a++) { // consider all queens on board
						int queenX = xs[a];
						int queenY = ys[a];
						int[] moveX = board.getQMoveX(queenX, queenY);
						int[] moveY = board.getQMoveY(queenX, queenY);

						for (int xy = 0; xy < moveX.length; xy++) { // consider all moves from selected queen
							Grid test = new Grid(n, board.getBoard());
							test.swap(queenX, queenY, moveX[xy], moveY[xy]);
							if (test.getH1() < heuristicBest) {
								heuristicBest = test.getH1(); // choose this queen
								newBoard = test;
							}
						}
					}

					if (heuristicCurrent > heuristicBest) { // if better solution found
						board = newBoard;
						board.printGrid();
						costTot++;
					} else { // else brick walled
						bricked1 = true;
					}
				}
				break;

			// Local Search Hill Climbing Approach 2
			case 2:
				boolean bricked2 = false; // true if brick wall encountered
				while (!bricked2) { // while still solvable
					int[] xs = board.getQueenX();
					int[] ys = board.getQueenY();

					int heuristicCurrent = board.getH1(); // heuristic of current board
					int heuristicBest = n * (n - 1); // heuristic of best candidate
					Grid newBoard = board; // board of best candidate

					for (int a = 0; a < n; a++) { // consider all queens on board
						int queenX = xs[a];
						int queenY = ys[a];
						int[] moveX = board.getQMoveX(queenX, queenY);
						int[] moveY = board.getQMoveY(queenX, queenY);

						for (int xy = 0; xy < moveX.length; xy++) { // consider all moves from selected queen
							Grid test = new Grid(n, board.getBoard());
							test.swap(queenX, queenY, moveX[xy], moveY[xy]);
							if (heuristicBest == n * (n - 1)) { // if no candidate selected yet
								if (test.getH1() < heuristicCurrent) { // if test heuristic is better than current board
									heuristicBest = test.getH1(); // choose this queen
									newBoard = test;
								}
							} else { // else existing candidate
								if (test.getH1() < heuristicCurrent) { // if test heuristic is better than current board
									if (test.getH1() > heuristicBest) { // if test heuristic is worse than best board
										heuristicBest = test.getH1(); // choose this queen
										newBoard = test;
									}
								}
							}
						}
					}

					if (heuristicCurrent > heuristicBest) { // if better solution found
						board = newBoard;
						board.printGrid();
						costTot++;
					} else { // else brick walled
						bricked2 = true;
					}
				}
				break;

			// CSP Min Conflicts Approach
			case 3:
				int moves = 1;
				int cap = 50; // solvability cap
				boolean solved = false; // true if solved
				while (moves < cap && !solved) { // while still solvable and moves cap hasn't been reached
					int[] xs = board.getQueenX();
					int[] ys = board.getQueenY();
					
					boolean found = false; // true if better solution found

					ArrayList<Integer> attempt = new ArrayList<Integer>(); // randomize order of queens visited
					for (int i = 0; i < n; i++) {
						attempt.add(i);
					}
					Collections.shuffle(attempt);

					while (!found && attempt.size() > 0) { // while better solution not found and queens still left to look at
						int index = attempt.remove(0);
						int xTest = xs[index];
						int yTest = ys[index];
						int heuristic = board.getH2(xTest, yTest); // heuristic of current board
						int[] xMoves = board.getQMoveX(xTest, yTest);
						int[] yMoves = board.getQMoveY(xTest, yTest);

						int hBest = heuristic; // heuristic of best candidate
						Grid newBoard = board; // boar of best candidate

						for (int i = 0; i < xMoves.length; i++) { // for each queen to look at
							Grid test = new Grid(n, board.getBoard());
							test.swap(xTest, yTest, xMoves[i], yMoves[i]);

							int hTest = test.getH2(xMoves[i], yMoves[i]);
							if (hTest < heuristic) { // if better than current board
								if (hTest < hBest) { // if better than best board
									hBest = hTest;
									newBoard = test;
								}
							}
						}

						if (hBest < heuristic) { // if best board is better than current board
							found = true;
							board = newBoard;
							moves++;
						}
					}
					if (board.getH1() == 0) { // if solution found
						solved = true;
					} else if (attempt.size() == 0) { // else if brick wall hit
						moves = cap;
					}
					if (moves != cap) { // if cap not hit
						board.printGrid();
						costTot++;
					}
				}
				break;

			default:
				System.out.println("Invalid input. Try again.");
				System.exit(0);
				break;
			}
			timeTot += System.currentTimeMillis() - startTime;
			if (board.getH1() == 0) { // if board was solved
				passed++;
			}
		}

		failed = runs - passed;
		int avgCost = costTot / runs;
		long avgTime = timeTot / runs;
		double pass = (passed / runs) * 100;
		System.out.println("Passed: " + passed + " Failed: " + failed);
		System.out.println("Average Run Time: " + avgTime + " ms");
		System.out.println("Average Cost: " + avgCost + " moves");
		System.out.println("Percent pass: " + pass + "%");
	}
}
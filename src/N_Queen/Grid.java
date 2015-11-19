package N_Queen;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Jaye Anne Laguardia
 */
public class Grid {

	private int[][] board;
	private final int n;

	/**
	 * Constructs a new (nxn) Grid object with n randomly placed Queens on Grid.
	 *
	 * @param n length, width, and number of Queens
	 */
	public Grid(int n) {
		board = new int[n][n];
		this.n = n;
		for (int y = 0; y < n; y++) {
			for (int x = 0; x < n; x++) {
				board[x][y] = 0;
			}
		}
		placeQueens();
	}

	/**
	 * Constructs a new (nxn) Grid object with a predetermined layout of Queens.
	 *
	 * @param n length, width, number of Queens
	 * @param board predetermined layout of Queens as a 2D Array
	 */
	public Grid(int n, int[][] board) {
		this.board = board;
		this.n = n;
	}

	/**
	 * Returns the Grid as a 2D Array.
	 *
	 * @return the Grid as a 2D Array
	 */
	public int[][] getBoard() {
		int[][] fin = new int[n][n];
		for (int y = 0; y < n; y++) {
			for (int x = 0; x < n; x++) {
				fin[x][y] = board[x][y];
			}
		}
		return fin;
	}

	/**
	 * Calculates heuristic value for the Grid.
	 * Counts total number of Queens opposing other Queens.
	 *
	 * @return heuristic value for the Grid
	 */
	public int getH1() {
		int[] xs = getQueenX();
		int[] ys = getQueenY();
		int h = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int x1, y1;
				int x2, y2;
				x1 = xs[i];
				y1 = ys[i];
				x2 = xs[j];
				y2 = ys[j];
				if (i != j) {
					if (x1 == x2) {
						h++;
					} else if (y1 == y2) {
						h++;
					} else if (Math.abs(x2 - x1) == Math.abs(y2 - y1)) {
						h++;
					}
				}
			}
		}
		return h;
	}

	/**
	 * Calculates the heuristic value for a specified coordinate space.
	 * Counts total number of Queens opposing given coordinates.
	 *
	 * @param x x coordinate of space
	 * @param y y coordinate of space
	 * @return heuristic value of space
	 */
	public int getH2(int x, int y) {
		int opposing = 0;

		// go north
		for (int a = y - 1; a >= 0; a--) {
			if (board[x][a] == 1) {
				opposing++;
			}
		}
		// go northeast
		for (int c = y - 1; c >= 0; c--) {
			for (int b = x + 1; b < n; b++) {
				if (Math.abs(b - x) == Math.abs(c - y)) {
					if (board[b][c] == 1) {
						opposing++;
					}
				}
			}
		}
		// go east
		for (int d = x + 1; d < n; d++) {
			if (board[d][y] == 1) {
				opposing++;
			}
		}
		// go southeast
		for (int f = y + 1; f < n; f++) {
			for (int e = x + 1; e < n; e++) {
				if (Math.abs(e - x) == Math.abs(f - y)) {
					if (board[e][f] == 1) {
						opposing++;
					}
				}
			}
		}
		// go south
		for (int g = y + 1; g < n; g++) {
			if (board[x][g] == 1) {
				opposing++;
			}
		}
		// go southwest
		for (int i = y + 1; i < n; i++) {
			for (int h = x - 1; h >= 0; h--) {
				if (Math.abs(h - x) == Math.abs(i - y)) {
					if (board[h][i] == 1) {
						opposing++;
					}
				}
			}
		}
		// go west
		for (int j = x - 1; j >= 0; j--) {
			if (board[j][y] == 1) {
				opposing++;
			}
		}
		// go northwest
		for (int l = y - 1; l >= 0; l--) {
			for (int k = x - 1; k >= 0; k--) {
				if (Math.abs(k - x) == Math.abs(l - y)) {
					if (board[k][l] == 1) {
						opposing++;
					}
				}
			}
		}

		return opposing;
	}

	/**
	 * Returns x coordinates of possible moves for a Queen at given position.
	 * Indexes are synchronized with getQMoveY.
	 *
	 * @param x x coordinate of Queen
	 * @param y y coordinate of Queen
	 * @return Array of possible x coordinates
	 */
	public int[] getQMoveX(int x, int y) {
		ArrayList<Integer> listOfMoves = new ArrayList<Integer>();

		// north of Queen
		for (int a = y - 1; a >= 0; a--) {
			if (board[x][a] == 1) {
				a = -1;
			} else {
				listOfMoves.add(x);
			}
		}

		// northeast of Queen
		for (int c = y - 1; c >= 0; c--) {
			for (int b = x + 1; b < n; b++) {
				if (Math.abs(b - x) == Math.abs(c - y)) {
					if (board[b][c] == 1) {
						b = n;
						c = -1;
					} else {
						listOfMoves.add(b);
					}
				}
			}
		}

		// east of Queen
		for (int d = x + 1; d < n; d++) {
			if (board[d][y] == 1) {
				d = n;
			} else {
				listOfMoves.add(d);
			}
		}

		// southeast of Queen
		for (int f = y + 1; f < n; f++) {
			for (int e = x + 1; e < n; e++) {
				if (Math.abs(e - x) == Math.abs(f - y)) {
					if (board[e][f] == 1) {
						e = n;
						f = n;
					} else {
						listOfMoves.add(e);
					}
				}
			}
		}

		// south of Queen
		for (int g = y + 1; g < n; g++) {
			if (board[x][g] == 1) {
				g = n;
			} else {
				listOfMoves.add(x);
			}
		}

		// southwest of Queen
		for (int i = y + 1; i < n; i++) {
			for (int h = x - 1; h >= 0; h--) {
				if (Math.abs(h - x) == Math.abs(i - y)) {
					if (board[h][i] == 1) {
						h = -1;
						i = n;
					} else {
						listOfMoves.add(h);
					}
				}
			}
		}

		// west of Queen
		for (int j = x - 1; j >= 0; j--) {
			if (board[j][y] == 1) {
				j = -1;
			} else {
				listOfMoves.add(j);
			}
		}

		// northwest of Queen
		for (int l = y - 1; l >= 0; l--) {
			for (int k = x - 1; k >= 0; k--) {
				if (Math.abs(k - x) == Math.abs(l - y)) {
					if (board[k][l] == 1) {
						k = -1;
						l = -1;
					} else {
						listOfMoves.add(k);
					}
				}
			}
		}

		int[] fin = new int[listOfMoves.size()];
		for (int z = 0; z < fin.length; z++) {
			fin[z] = listOfMoves.get(z);
		}

		return fin;
	}

	/**
	 * Returns y coordinates of possible moves for a Queen at given position.
	 * Indexes are synchronized with getQMoveX.
	 *
	 * @param x x coordinate of Queen
	 * @param y y coordinate of Queen
	 * @return Array of possible x coordinates
	 */
	public int[] getQMoveY(int x, int y) {
		ArrayList<Integer> listOfMoves = new ArrayList<Integer>();

		// north of Queen
		for (int a = y - 1; a >= 0; a--) {
			if (board[x][a] == 1) {
				a = -1;
			} else {
				listOfMoves.add(a);
			}
		}

		// northeast of Queen
		for (int c = y - 1; c >= 0; c--) {
			for (int b = x + 1; b < n; b++) {
				if (Math.abs(b - x) == Math.abs(c - y)) {
					if (board[b][c] == 1) {
						b = n;
						c = -1;
					} else {
						listOfMoves.add(c);
					}
				}
			}
		}

		// east of Queen
		for (int d = x + 1; d < n; d++) {
			if (board[d][y] == 1) {
				d = n;
			} else {
				listOfMoves.add(y);
			}
		}

		// southeast of Queen
		for (int f = y + 1; f < n; f++) {
			for (int e = x + 1; e < n; e++) {
				if (Math.abs(e - x) == Math.abs(f - y)) {
					if (board[e][f] == 1) {
						e = n;
						f = n;
					} else {
						listOfMoves.add(f);
					}
				}
			}
		}

		// south of Queen
		for (int g = y + 1; g < n; g++) {
			if (board[x][g] == 1) {
				g = n;
			} else {
				listOfMoves.add(g);
			}
		}

		// southwest of Queen
		for (int i = y + 1; i < n; i++) {
			for (int h = x - 1; h >= 0; h--) {
				if (Math.abs(h - x) == Math.abs(i - y)) {
					if (board[h][i] == 1) {
						h = -1;
						i = n;
					} else {
						listOfMoves.add(i);
					}
				}
			}
		}

		// west of Queen
		for (int j = x - 1; j >= 0; j--) {
			if (board[j][y] == 1) {
				j = -1;
			} else {
				listOfMoves.add(y);
			}
		}

		// northwest of Queen
		for (int l = y - 1; l >= 0; l--) {
			for (int k = x - 1; k >= 0; k--) {
				if (Math.abs(k - x) == Math.abs(l - y)) {
					if (board[k][l] == 1) {
						k = -1;
						l = -1;
					} else {
						listOfMoves.add(l);
					}
				}
			}
		}

		int[] fin = new int[listOfMoves.size()];
		for (int z = 0; z < fin.length; z++) {
			fin[z] = listOfMoves.get(z);
		}

		return fin;
	}

	/**
	 * Returns x coordinates of Queens in Grid. Indexes are synchronized with getQueenY.
	 *
	 * @return Array of x coordinates of Queens
	 */
	public int[] getQueenX() {
		int[] xCoord = new int[n];
		int iterator = 0;
		for (int y = 0; y < n; y++) {
			for (int x = 0; x < n; x++) {
				if (board[x][y] == 1) {
					xCoord[iterator] = x;
					iterator++;
				}
			}
		}
		return xCoord;
	}

	/**
	 * Returns y coordinates of Queens in Grid. Indexes are synchronized with getQueenY.
	 *
	 * @return Array of y coordinates of Queens
	 */
	public int[] getQueenY() {
		int[] yCoord = new int[n];
		int iterator = 0;
		for (int y = 0; y < n; y++) {
			for (int x = 0; x < n; x++) {
				if (board[x][y] == 1) {
					yCoord[iterator] = y;
					iterator++;
				}
			}
		}
		return yCoord;
	}

	/**
	 * Randomly places Queens on the board.
	 */
	private void placeQueens() {
		Random random = new Random();
		int queens = 0;
		while (queens < n) {
			int x = random.nextInt(n);
			int y = random.nextInt(n);
			if (board[x][y] != 1) {
				board[x][y] = 1;
				queens++;
			}
		}
	}

	/**
	 * Prints out String representation of Grid as well as the heuristic value of the Grid.
	 */
	public void printGrid() {
		for (int j = 0; j < n; j++) {
			for (int k = 0; k < n; k++) {
				if (board[k][j] == 1) {
					System.out.print("Q ");
				} else {
					System.out.print("_ ");
				}
			}
			System.out.print("\n");
		}
		System.out.println("HEURISTIC: " + getH1() + "\n");
	}

	/**
	 * Swaps two different objects on Grid.
	 *
	 * @param x1 x coordinate of 1st object
	 * @param y1 y coordinate of 1st object
	 * @param x2 x coordinate of 2nd object
	 * @param y2 y coordinate of 2nd object
	 */
	public void swap(int x1, int y1, int x2, int y2) {
		int temp = board[x1][y1];
		board[x1][y1] = board[x2][y2];
		board[x2][y2] = temp;
	}
}
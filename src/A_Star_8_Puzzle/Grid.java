package A_Star_8_Puzzle;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Jaye Anne Laguardia
 *
 *         An object representing a Grid for use in an 8-Puzzle.
 */
public class Grid {
	private String[][] grid;
	private final int MAX_X = 3;
	private final int MAX_Y = 3;

	/**
	 * Default Grid constructor. Generates a new Grid with tiles ranging from
	 * non-repeating tiles ranging from 0-8. Tiles are randomly ordered in Grid.
	 */
	public Grid() {
		grid = new String[MAX_X][MAX_Y];
		ArrayList<Integer> notUsed = new ArrayList<Integer>();

		Random rand = new Random();
		while (notUsed.size() < 9) {
			int num = rand.nextInt(9);
			if (!notUsed.contains(num)) {
				notUsed.add(num);
			}
		}

		for (int j = 0; j < 3; j++) {
			for (int k = 0; k < 3; k++) {
				String tile = "";
				tile += notUsed.remove(0);
				grid[j][k] = tile;
			}
		}
	}

	/**
	 * @param grid
	 *            Array of Strings to be turned into a Grid.
	 *
	 *            This method can be used to generate a new Grid from an
	 *            existing Grid or a 2-D array of Strings.
	 */
	public Grid(String[][] grid) {
		this.grid = new String[MAX_X][MAX_Y];
		for (int m = 0; m < 3; m++) {
			for (int n = 0; n < 3; n++) {
				String tile = grid[n][m];
				this.grid[n][m] = tile;
			}
		}
	}

	/**
	 * @return A copy of this Grid.
	 */
	public Grid copyGrid() {
		return new Grid(grid);
	}

	/**
	 * @return A list of Tiles neighboring the 0 Tile.
	 */
	public ArrayList<String> getNeighbors() {
		return getNeighbors("0");
	}

	/**
	 * @param t
	 *            The Tile to find the neighbors of.
	 * @return A list of Tiles neighboring the indicated Tile.
	 *
	 *         Returns all adjacent neighbors above, below, to the left, or to
	 *         the right of the indicated Tile.
	 */
	public ArrayList<String> getNeighbors(String t) {
		String s = locate(t);
		int x = Integer.parseInt(s.substring(0, 1));
		int y = Integer.parseInt(s.substring(1, 2));

		ArrayList<String> validLoc = new ArrayList<>();
		if ((x - 1) >= 0) { // check to the left
			String build = "";
			build += (x - 1);
			build += y;
			validLoc.add(build);
		}
		if ((x + 1) <= 2) { // check to the right
			String build = "";
			build += (x + 1);
			build += y;
			validLoc.add(build);
		}
		if ((y - 1) >= 0) { // check above
			String build = "";
			build += x;
			build += (y - 1);
			validLoc.add(build);
		}
		if ((y + 1) <= 2) { // check below
			String build = "";
			build += x;
			build += (y + 1);
			validLoc.add(build);
		}

		return validLoc;
	}

	/**
	 * @param coord
	 *            The coordinates of the Tile in form "xy".
	 * @return The Tile at given coordinates.
	 */
	public String getTile(String coord) {
		int x = Integer.parseInt(coord.substring(0, 1));
		int y = Integer.parseInt(coord.substring(1, 2));
		return grid[x][y];
	}

	/**
	 * @return The 2-D array representing the Grid.
	 */
	public String[][] getTileGrid() {
		return grid;
	}

	/**
	 * @param tile
	 *            Tile to locate in Grid.
	 * @return Coordinates of the tile in form "xy". If not found return " ".
	 */
	public String locate(String tile) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (grid[i][j].toString().equals(tile.toString())) {
					String coord = "";
					coord += i;
					coord += j;
					return coord;
				}
			}
		}
		return " ";
	}

	/**
	 * @return true if Grid is solvable by the A* algorithm, false if not.
	 *
	 *         Grid is solvable if the number of inversions in the Grid is even.
	 */
	public boolean solvable() {
		int inversions = 0;
		String[] tiles = new String[8];
		int iterator = 0;
		for (int r = 0; r <= 2; r++) {
			for (int c = 0; c <= 2; c++) {
				if (!grid[c][r].toString().equals("0")) {
					tiles[iterator] = grid[c][r].toString();
					iterator++;
				}
			}
		}

		for (int i = 1; i < 8; i++) {
			if (tiles[i - 1].compareTo(tiles[i]) >= 1) {
				inversions++;
			}
		}
		if (inversions % 2 == 1)
			return false;
		else
			return true;
	}

	/**
	 * @return true if Grid is solved, false if not.
	 */
	public boolean solved() {
		int i = 0;
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				String check = "";
				check += i;
				if (!check.equals(grid[c][r].toString()))
					return false;
				i++;
			}
		}
		return true;
	}

	/**
	 * @param tile
	 *            The Tile to swap with the zero Tile.
	 *
	 *            Swaps the zero Tile with tile. If tile isn't a neighbor of
	 *            zero the do nothing.
	 */
	public void swap(String tile) {
		String loc = locate(tile);
		int x = Integer.parseInt(loc.substring(0, 1));
		int y = Integer.parseInt(loc.substring(1, 2));
		ArrayList<String> validLoc = getNeighbors(tile);

		boolean swapped = false;
		for (String s : validLoc) {
			int a = Integer.parseInt(s.substring(0, 1));
			int b = Integer.parseInt(s.substring(1, 2));
			if (grid[a][b].toString().equals("0")) {
				grid[a][b] = grid[x][y];
				grid[x][y] = "0";
				swapped = true;
			}
		}

		if (swapped == false) {
			System.out.println("Fail swap.");
		}
	}

	@Override
	public String toString() {
		String stringRep = "";
		for (int m = 0; m < 3; m++) {
			for (int n = 0; n < 3; n++) {
				stringRep += grid[n][m] + " ";
			}
			stringRep += "\n";
		}
		return stringRep;
	}
}
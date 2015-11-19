package A_Star_8_Puzzle;

/**
 * @author Jaye Anne Laguardia
 *
 *         A Node object that contains a Grid object, a parent, and three
 *         different weights.
 */
public class Node implements Comparable<Node> {

	private int f, g, h;
	private Grid grid;
	private Node parent;

	/**
	 * @param tiles
	 *            Grid to put in Node.
	 */
	public Node(Grid tiles) {
		grid = tiles.copyGrid();
		g = 0;
		h = 0;
		f = g + h;
		parent = null;
	}

	@Override
	public int compareTo(Node n) {
		if (f > n.getF())
			return 1;
		else if (f < n.getF())
			return -1;
		else
			return 0;
	}

	/**
	 * @param n
	 *            Node to compare.
	 * @return true if both nodes have identical Grids, false if not.
	 */
	public boolean equalsTo(Node n) {
		String[][] nTiles = n.getGrid().getTileGrid();
		String[][] tiles = grid.getTileGrid();
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				if (nTiles[c][r].toString().compareTo(tiles[c][r].toString()) != 0)
					return false;
			}
		}
		return true;
	}

	/**
	 * @return f weight.
	 */
	public int getF() {
		return f;
	}

	/**
	 * @return g weight.
	 */
	public int getG() {
		return g;
	}

	/**
	 * @return Grid object.
	 */
	public Grid getGrid() {
		return grid;
	}

	/**
	 * @return h weight.
	 */
	public int getH() {
		return h;
	}

	/**
	 * @return parent Node.
	 */
	public Node getParent() {
		return parent;
	}

	/**
	 * @param g
	 *            New g value.
	 */
	public void setG(int g) {
		this.g = g;
	}

	/**
	 * @param h
	 *            New h value.
	 */
	public void setH(int h) {
		this.h = h;
	}

	/**
	 * @param n
	 *            Parent Node to set.
	 */
	public void setParent(Node n) {
		parent = n;
	}

	@Override
	public String toString() {
		return grid.toString();
	}

	/**
	 * @return New value of f. f will be the sum of the current values g and h
	 *         weights.
	 */
	public int updateF() {
		f = g + h;
		return f;
	}
}
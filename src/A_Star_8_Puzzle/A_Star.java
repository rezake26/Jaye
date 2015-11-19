package A_Star_8_Puzzle;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * @author Jaye Anne Laguardia
 *
 *         Runs the A* algorithm
 */
public class A_Star {

	private ArrayList<Node> closedSet;
	private int depth;
	private long endTime;
	private Grid grid;
	private int heuristic;
	private PriorityQueue<Node> openSet;
	private Stack<Node> stacked;
	private long startTime;
	private long totalTime;

	/**
	 * @param grid
	 *            Grid to analyze.
	 * @param heuristic
	 *            Determining integer for heuristic.
	 *
	 *            Sets up a new A* object. Heuristic should be "1" or "2".
	 */
	public A_Star(Grid grid, int heuristic) {
		this.grid = grid;
		openSet = new PriorityQueue<Node>();
		closedSet = new ArrayList<Node>();
		this.heuristic = heuristic;
		stacked = new Stack<Node>();
		depth = -1;
	}

	/**
	 * @param grid
	 *            Grid to analyze.
	 * @return Weight of heuristic.
	 */
	public int h1(Grid grid) {
		int weight = 0;
		int i = 0;
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				String coord = "";
				coord += c;
				coord += r;
				String tile = "";
				tile += i;
				if (!grid.getTile(coord).toString().equals(tile)) {
					weight++;
				}
				i++;
			}
		}
		return weight;
	}

	/**
	 * @param grid
	 *            Grid to analyze.
	 * @return Weight of heuristic.
	 */
	public int h2(Grid grid) {
		int weight = 0;
		int i = 0;
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				String coord = "";
				coord += c;
				coord += r;
				String tile = "";
				tile += i;
				if (!grid.getTile(coord).toString().equals(tile)) {
					String tile2 = grid.locate(tile);
					int x = Integer.parseInt(tile2.substring(0, 1));
					int y = Integer.parseInt(tile2.substring(1, 2));
					x = Math.abs(x - c);
					y = Math.abs(y - r);
					weight += (x + y);
				}
				i++;
			}
		}
		return weight;
	}

	/**
	 * Solves the Grid.
	 */
	public void solve() {
		startTime = System.currentTimeMillis();
		Node start = new Node(grid);
		if (heuristic == 1) { // run heuristic 1
			start.setH(h1(grid));
			start.updateF();
		} else { // run heuristic 2
			start.setH(h2(grid));
			start.updateF();
		}
		openSet.add(start);

		while (!openSet.isEmpty()) {
			Node node = openSet.peek();
			if (node.getGrid().solved()) { // if solution has been found
				endTime = System.currentTimeMillis();
				totalTime = endTime - startTime;
				System.out.println("Solution found!\n");
				stackit(node);
				while (!stacked.isEmpty()) {
					System.out.println(stacked.pop().getGrid());
					depth++;
				}
				System.out.println("Depth = " + depth + "\n");
				System.out.println("Run Time (ms) = " + totalTime);
				return;
			}
			closedSet.add(openSet.poll());
			Grid g = node.getGrid();
			ArrayList<String> neigh = g.getNeighbors();
			ArrayList<Node> neighTiles = new ArrayList<Node>();
			for (String s : neigh) {
				Grid temp = g.copyGrid();
				String sw = temp.getTile(s);
				temp.swap(sw);
				Node child = new Node(temp);
				child.setParent(node);
				neighTiles.add(child);
			}
			while (!neighTiles.isEmpty()) {
				Node child = neighTiles.remove(0);
				child.setParent(node);
				child.setG(child.getParent().getG() + 1);
				if (heuristic == 1) {
					child.setH(h1(child.getGrid()));
					child.updateF();
				} else {
					child.setH(h2(child.getGrid()));
					child.updateF();
				}
				if (!closedSet.contains(child)) {
					openSet.add(child);
				}
			}
		}
	}

	/**
	 * @param solution
	 *            Node to push onto stack.
	 *
	 *            Builds stack for printing solution.
	 */
	private void stackit(Node solution) {
		stacked.push(solution);
		if (solution.getParent() != null) {
			stackit(solution.getParent());
		}
	}
}
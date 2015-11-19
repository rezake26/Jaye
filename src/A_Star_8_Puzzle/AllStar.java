package A_Star_8_Puzzle;

/**
 * @author Jaye Anne Laguardia
 */

public class AllStar {

	public static void main(String[] args) {
		Grid muhStar = new Grid();
		System.out.println(muhStar); // print Grid to solve

		if (!muhStar.solvable()) { // if Grid is unsolvable
			System.out.println("Puzzle not solvable.");
		} else { // else solve the Grid
			System.out.println("Heuristic 1 run through\n");
			A_Star solve1 = new A_Star(muhStar, 1); // solve using heuristic one
			solve1.solve();
			System.out.println("\nHeuristic 2 run through\n");
			A_Star solve2 = new A_Star(muhStar, 2); // solve using heuristic two
			solve2.solve();
		}

//		code for solving by hand
//		boolean done = false;
//				Scanner kb = new Scanner(System.in);
//				while(done == false) {
//					System.out.println("exit or swap - x or symbol");
//					String answer = kb.next();
//					if(answer.equals("x")) {
//						done = true;
//					}
//					else {
//						String hcaz = answer;
//						muhStar.swap(hcaz);
//						System.out.println(muhStar);
//					}
//
//				}
	}
}
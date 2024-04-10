import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {
    
	public static void main(String[] args) {
		System.out.println("-----------PROBLHMA KANNIBALWN KAI IERAPOSTOLWN-------------------");
		Scanner myObj = new Scanner(System.in);
		int[] inputs = new int[3];
		System.out.println("Give number of Missionaires or number of cannibals(N)");
		inputs[0]=myObj.nextInt();//Ierapostoloi h cannibaloi
		
			
		System.out.println("Give number of limit in the boat(M)");
		while(true){
			inputs[1] = myObj.nextInt();//Orio barkas
			if(inputs[1]<2){
				System.out.println("Give a bigger limit");
				continue;
			}
			break;
		}
		System.out.println("Give maximum number of crosses(K)");
		while(true){
			inputs[2]=myObj.nextInt();//arithmos diasxisewn
			if(inputs[2]<=0){
				System.out.println("Give a bigger number");
				continue;
			}
			break;
		}
		long start = System.currentTimeMillis();//ypologismos xronou
		State initialState = new State (inputs[0], inputs[0], "LEFT",inputs[1], 0, 0);//arxikh katastash NxN
		execute_algorithm_a(initialState,inputs[2]);
		long end=System.currentTimeMillis();//ypologismos xronou
		System.out.println("Search time: "+(double)(end-start)/1000+" sec.");
	}

	private static void execute_algorithm_a(State initialState,int K) {
		Algorithm_a search = new Algorithm_a();
		State solution = search.exec(initialState);
		printSolution(solution,K);
	}


	private static void printSolution(State solution,int K) {//printing results connected with toString of State
		if (null == solution) {
			System.out.print("\nNo solution found.");
		} else {
			
			List<State> path = new ArrayList<State>();
			State state = solution;
			while(null!=state) {
				path.add(state);
				state = state.getFather();
			}

			int depth = path.size() - 1;
			if (depth>K){
				System.out.println("\nThere is not a solution with this limit of "+K+ " crosses");
			}else{
				System.out.println("\nSolution (cannibalLeft,missionaryLeft,boat,cannibalRight,missionaryRight): ");
				for (int i = depth; i >= 0; i--) {
					state = path.get(i);
					if (state.isFinal()) {
						System.out.print(state.toString());
					} else {
						System.out.print(state.toString() + " -> ");
					}
				}
				System.out.println("\nDepth: " + depth);
			}
		}
	}
}
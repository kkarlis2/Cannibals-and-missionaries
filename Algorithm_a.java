import java.util.*;

//-----------Algorithm a*--------------
public class Algorithm_a {

	public State exec(State initialState) {
		if (initialState.isFinal()) {
			return initialState;
		}
		HashMap <State,Integer> frontier = new HashMap<State,Integer>();
		ArrayList<State> explored = new ArrayList<State>();
		frontier.put(initialState,initialState.heuristic());//bale thn arxikh katastash kai to apotelesma tiw eyretikhs ston frontier.
		boolean a=true;
		while (a) {
			if (frontier.size()==0) {
				return null;	// apotyxia
			}
			State state=frontier.keySet().stream().findFirst().get();//pare thn prwth katastash pou  brisketai ston frontier
			frontier.remove(state);//sbhse thn prwth katastash ston frontier.
			explored.add(state);
			List<State> success = state.Success_Movements();
			for (State child : success) {
				if (!explored.contains(child) && frontier.containsKey(child)==false) {//elegxoi gia kleisto synolo
					if (child.isFinal()) {
						a=false;
						return child;
					}
					frontier.put(child,child.heuristic());
					
				}
			}
			frontier=sortByValue(frontier);//ta3inomei ton frontier me bash thn eyretikh mas synarthsh

		}
		return null;
	}
	//---------- kwdikas tajionmhshs me bash to value se hashmap-----------
	public static HashMap<State, Integer> sortByValue(HashMap<State, Integer> hm)
    {
        List<Map.Entry<State, Integer> > list =
               new LinkedList<Map.Entry<State, Integer> >(hm.entrySet());
 
        Collections.sort(list, new Comparator<Map.Entry<State, Integer> >() {
            public int compare(Map.Entry<State, Integer> o1,
                               Map.Entry<State, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
         
        HashMap<State, Integer> temp = new LinkedHashMap<State, Integer>();
        for (Map.Entry<State, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
package basicmodel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import edu.uci.ics.jung.visualization.HasGraphLayout;
import mgr.Agent;
import mgr.DynamicsFunctions;
import mgr.NetCayley;

public class UnitTests {

	public static void main(String[] args) {
		
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> map_edges = new HashMap<Integer, Integer>();
		NetCayley net = new NetCayley(3);
		
		
		
		
		Collection<Integer> edges = net.net.getEdges();
		for (Integer edge : edges){
			edu.uci.ics.jung.graph.util.Pair<Integer> pair = net.net.getEndpoints(edge);
			
		}
		//System.out.println(edges.size());
		for (int i = 0; i< 100000; i++){
			Random r = new Random();
			int ii = r.nextInt(edges.size());
			if(map_edges.containsKey(ii)){
				map_edges.replace(ii,((Integer) map_edges.get(ii)+1));
			}
			else {
				map_edges.put(ii, 1);
			}
			edu.uci.ics.jung.graph.util.Pair<Integer> pair = net.net.getEndpoints((int) edges.toArray()[ii]);
		if(map.containsKey(pair.getFirst())){
			map.replace(pair.getFirst(),((Integer) map.get(pair.getFirst()))+1);
		}
		else {
			map.put(pair.getFirst(), 1);
		}
		
		if(map.containsKey(pair.getSecond())){
			map.replace(pair.getSecond(),(Integer) map.get(pair.getSecond())+1);
		}
		else {
			map.put(pair.getSecond(), 1);
		}
		}
		
		
		for (Entry<?, ?> entry : map.entrySet()) {
		    System.out.println(entry.getKey() + " " + entry.getValue());
		}
		

		for (Entry<?, ?> entry : map_edges.entrySet()) {
		   // System.out.println(entry.getKey() + " " + entry.getValue());
		}

	}

}

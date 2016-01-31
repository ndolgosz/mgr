package mgr;

import java.util.HashMap;
import java.util.Map.Entry;

public class CreateLotsOfNets {

	public static int ITER = 1;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int j = 0; j < ITER; j++) {
			NetBA net = new NetBA(100000);
			net.setBMtoCenter();
			
			for (int i = 1; i <= net.numVertices; i++) {
				int deg = net.net.degree(i);
				if (map.containsKey(deg)) {
					map.replace(deg, map.get(deg), map.get(deg) + 1);
				} else {
					map.put(deg, 1);
				}

			}

		}
		
		for (Entry<Integer,Integer> entry : map.entrySet()) {
			  Integer key = entry.getKey();
			  Double value = 1.0*entry.getValue()/ITER;
			 System.out.println(key+" "+value);
			}


	}

}

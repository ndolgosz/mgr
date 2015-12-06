package basicmodel;

import java.rmi.UnexpectedException;
import java.util.HashSet;
import java.util.Set;

import mgr.DynamicsFunctions;
import mgr.NetCayley;

public class T_steepness_prob_NEW {

	public static void main(String[] args) {
		
		
		final int ITER = 1000;
		int dist = 3;
		double lambda = 20.0;
		double steep = 10;
		double prob = 0;
		DynamicsFunctions dyn = new DynamicsFunctions();
		//iterate over dist
		
		for (double steep_i = 0; steep_i <= steep ; steep_i++){
			
			int T = 0;
			//average
			for(int j = 0; j < ITER; j++){
				NetCayley net = new NetCayley(dist);
				net.configureInformationModel(prob, steep_i);
				
			    
				//System.out.println("Distance: "+i+"   distance in net: "+net.distanceBM.get(net.TI));
				int t = 0;
				double synch = lambda + 1;
				while(synch > lambda){
					dyn.updateOpinions_InformationModel(net, dyn.takeRandomNeighbors(net));
					synch = dyn.countTotalSynchrony(net);
					t=t+1;
				}
				T=T+t;
			}
			double steps = T/ITER;
			System.out.println("Steps: "+ steps + " steep: "+ steep_i);
				
		}
		
		
		
	}
	


}


package basicmodel;

import java.rmi.UnexpectedException;
import java.util.HashSet;
import java.util.Set;

import mgr.DynamicsFunctions;
import mgr.NetBA;
import mgr.NetCayley;

public class Test {

	public static void main(String[] args) {
		
		
		final int ITER = 1000;
		int N = 5;
		double lambda = 5.0;
		double k = 5;
		DynamicsFunctions dyn = new DynamicsFunctions();
		//iterate over dist
		int n=30;
		double avK=0.0;
		
		for (int i = 1; i <= ITER ; i++){
			
			NetBA net = new NetBA(n);
			 
			double tmpK = dyn.averageK(net);
			avK = avK + tmpK;
			System.out.println(tmpK);
		}
		System.out.println(1.0*avK/ITER);
		
		
	}
	


}

package informationmodel;

import mgr.DynamicsFunctions;
import tables.T_steep_k_table;

public class Optimal_k_steep_chart {

	static int lambda = 50;
	static double tau = 0.0002;
	static double kappa = 0.0001;
	
	public static void main(String[] args) {
		DynamicsFunctions dyn = new DynamicsFunctions();
		T_steep_k_table Tsk = new T_steep_k_table(0);
		
		Tsk.readTnkFromFile(lambda, 0.0);
		double[] d = dyn.optimalGroup_steep_k(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1]);

		Tsk.readTnkFromFile(lambda, 0.25);
		d = dyn.optimalGroup_steep_k(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1]);
		
		Tsk.readTnkFromFile(lambda, 0.5);
		d = dyn.optimalGroup_steep_k(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1]);
		
		Tsk.readTnkFromFile(lambda, 0.75);
		d = dyn.optimalGroup_steep_k(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1]);
		
		Tsk.readTnkFromFile(lambda, 1.0);
		d = dyn.optimalGroup_steep_k(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1]);

	}

}

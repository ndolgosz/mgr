package informationmodel;

import mgr.DynamicsFunctions;
import tables.T_steep_k_table;

public class Optimal_k_steep_chart {

	static int lambda = 15;
	static double tau = 0.01;
	static double kappa = 0.01;
	
	public static void main(String[] args) {
		DynamicsFunctions dyn = new DynamicsFunctions();
		T_steep_k_table Tsk = new T_steep_k_table(0);
		
		Tsk.readTnkFromFile(lambda, 0.0);
		double[] d = dyn.optimalGroup_steep_k(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1]);

		Tsk.readTnkFromFile(lambda, 0.1);
		d = dyn.optimalGroup_steep_k(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1]);
		
		Tsk.readTnkFromFile(lambda, 0.2);
		d = dyn.optimalGroup_steep_k(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1]);
		
		Tsk.readTnkFromFile(lambda, 0.3);
		d = dyn.optimalGroup_steep_k(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1]);
		
		Tsk.readTnkFromFile(lambda, 0.4);
		d = dyn.optimalGroup_steep_k(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1]);
		Tsk.readTnkFromFile(lambda, 0.5);
		d = dyn.optimalGroup_steep_k(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1]);
		Tsk.readTnkFromFile(lambda, 0.6);
		d = dyn.optimalGroup_steep_k(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1]);
		Tsk.readTnkFromFile(lambda, 0.7);
		d = dyn.optimalGroup_steep_k(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1]);
		Tsk.readTnkFromFile(lambda, 0.8);
		d = dyn.optimalGroup_steep_k(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1]);
		Tsk.readTnkFromFile(lambda, 0.9);
		d = dyn.optimalGroup_steep_k(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1]);
		Tsk.readTnkFromFile(lambda, 1.0);
		d = dyn.optimalGroup_steep_k(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1]);
		

	}

}

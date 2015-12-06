package informationmodel;

import mgr.DynamicsFunctions;
import tables.T_steep_k_table;
import tables_BA.T_steep_n_table;
import tables_Cayley.T_steep_depth_k_table;

public class Optimal_steep_dk_chart {

	static int lambda = 10;
	static double tau = 0.0001;
	static double kappa = 0.002;
	
	
	
	public static void main(String[] args) {
		DynamicsFunctions dyn = new DynamicsFunctions();
		T_steep_depth_k_table Tsk = new T_steep_depth_k_table(0);
		System.out.println("STEEP" + " " + "D" + " " +"K");
		Tsk.readTnkFromFile(lambda, 0);
		double[] d = dyn.optimalGroup_steep_dk(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1] + " " + d[2]);

		Tsk.readTnkFromFile(lambda, 0.1);
		d = dyn.optimalGroup_steep_dk(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1] + " " + d[2]);
		
		Tsk.readTnkFromFile(lambda, 0.2);
		d = dyn.optimalGroup_steep_dk(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1] + " " + d[2]);
		
		Tsk.readTnkFromFile(lambda, 0.3);
		d = dyn.optimalGroup_steep_dk(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1] + " " + d[2]);
		
		Tsk.readTnkFromFile(lambda, 0.4);
		d = dyn.optimalGroup_steep_dk(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1] + " " + d[2]);
		Tsk.readTnkFromFile(lambda, 0.5);
		d = dyn.optimalGroup_steep_dk(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1] + " " + d[2]);
		Tsk.readTnkFromFile(lambda, 0.6);
		d = dyn.optimalGroup_steep_dk(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1] + " " + d[2]);
		Tsk.readTnkFromFile(lambda, 0.7);
		d = dyn.optimalGroup_steep_dk(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1] + " " + d[2]);
		Tsk.readTnkFromFile(lambda, 0.8);
		d = dyn.optimalGroup_steep_dk(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1] + " " + d[2]);
		Tsk.readTnkFromFile(lambda, 0.9);
		d = dyn.optimalGroup_steep_dk(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1] + " " + d[2]);
		Tsk.readTnkFromFile(lambda, 1);
		d = dyn.optimalGroup_steep_dk(Tsk, kappa, tau);
		System.out.println(d[0] + " " + d[1] + " " + d[2]);
		

	}

}

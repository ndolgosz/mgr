package informationmodel;

import java.util.ArrayList;

import mgr.DynamicsFunctions;
import tables.T_steep_k_table;
import tables_BA.T_steep_n_table;

public class Optimal_n_steep_chart {

	static int lambda = 10;
	static double tau = 0.001;
	static double kappa = 0.0001;
	
	
	static String netName = "BA";
	
	
	public static void main(String[] args) {
		
		
		DynamicsFunctions dyn = new DynamicsFunctions();
		T_steep_n_table Tsk = new T_steep_n_table(0);
		System.out.println("STEEP" + " " + "N");
		double[] wektor = {0 , 0.2, 0.4, 0.6, 0.8, 1};
		for (double l : wektor){
			Tsk.readTnkFromFile(lambda, l,"n");
			double[] d = dyn.optimalGroup_steep_n(Tsk, kappa, tau, netName);
			System.out.println(d[0] + " " + d[1]);
		}

/*		Tsk.readTnkFromFile(lambda, 0.1,"n");
		d = dyn.optimalGroup_steep_n(Tsk, kappa, tau, netName);
		System.out.println(d[0] + " " + d[1]);
		
		Tsk.readTnkFromFile(lambda, 0.2);
		d = dyn.optimalGroup_steep_n(Tsk, kappa, tau, netName);
		System.out.println(d[0] + " " + d[1]);
		
		Tsk.readTnkFromFile(lambda, 0.3);
		d = dyn.optimalGroup_steep_n(Tsk, kappa, tau, netName);
		System.out.println(d[0] + " " + d[1]);
		
		Tsk.readTnkFromFile(lambda, 0.4);
		d = dyn.optimalGroup_steep_n(Tsk, kappa, tau, netName);
		System.out.println(d[0] + " " + d[1]);
		Tsk.readTnkFromFile(lambda, 0.5);
		d = dyn.optimalGroup_steep_n(Tsk, kappa, tau, netName);
		System.out.println(d[0] + " " + d[1]);
		Tsk.readTnkFromFile(lambda, 0.6);
		d = dyn.optimalGroup_steep_n(Tsk, kappa, tau, netName);
		System.out.println(d[0] + " " + d[1]);
		Tsk.readTnkFromFile(lambda, 0.7);
		d = dyn.optimalGroup_steep_n(Tsk, kappa, tau, netName);
		System.out.println(d[0] + " " + d[1]);
		Tsk.readTnkFromFile(lambda, 0.8);
		d = dyn.optimalGroup_steep_n(Tsk, kappa, tau, netName);
		System.out.println(d[0] + " " + d[1]);
		Tsk.readTnkFromFile(lambda, 0.9);
		d = dyn.optimalGroup_steep_n(Tsk, kappa, tau, netName);
		System.out.println(d[0] + " " + d[1]);
		Tsk.readTnkFromFile(lambda, 1.0);
		d = dyn.optimalGroup_steep_n(Tsk, kappa, tau, netName);
		System.out.println(d[0] + " " + d[1]);*/
		

	}

}

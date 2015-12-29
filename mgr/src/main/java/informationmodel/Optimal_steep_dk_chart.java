package informationmodel;

import mgr.DynamicsFunctions;
import tables.T_steep_k_table;
import tables_BA.T_steep_n_table;
import tables_Cayley.T_steep_depth_k_table;

public class Optimal_steep_dk_chart {

	static int lambda = 10;
	static double tau = 0.00003; //koszt nawi¹zania po³¹czenia - komunikacji
	static double kappa = 0.0001;//koszt utrzymania znajomosci

	public static void main(String[] args) {
		DynamicsFunctions dyn = new DynamicsFunctions();
		T_steep_depth_k_table Tsk = new T_steep_depth_k_table(0);
		System.out.println("steep" + " " + "depth" + " k");
		double[] wektor = { 0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1 };
		for (double l : wektor) {
			Tsk.readTnkFromFile(lambda, l);
			double[] d = dyn.optimalGroup_steep_dk(Tsk, kappa, tau);
			System.out.println("prob: "+l + " " + d[0] + " " + d[1]+ " " + d[2]);
		}
	}

}

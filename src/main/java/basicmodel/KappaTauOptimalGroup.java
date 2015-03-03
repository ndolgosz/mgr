package basicmodel;

import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;

import mgr.DynamicsFunctions;

import org.math.plot.Plot2DPanel;

import tables.T_n_k_table;

public class KappaTauOptimalGroup {

	static int ITER = 1000;
	static int TIME = 200;
	static double lambda = 70;

	public static void main(String[] args) {

		
		T_n_k_table T = new T_n_k_table(2);
		try {
			T.readTnkFromFile(60);
		} catch (IOException e) {	
			e.printStackTrace();
		}
		
		JFrame frame = new JFrame("a plot panel");
		frame.setLayout(new GridLayout(1, 2));
		Plot2DPanel[] plots =
		optimalGroup_kappa_tau(T);

		frame.add(plots[0]);
		frame.add(plots[1]);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(1000, 500);
		

	}
	
	private static Plot2DPanel[] optimalGroup_kappa_tau(T_n_k_table table) {
		
		double[][] Tnk = table.T_fun;
		
		DynamicsFunctions dyn = new DynamicsFunctions();
		double[] n1 = new double[100];
		double[] k1 = new double[100];
		double[] kappa_vec = new double[100];
		int i = 0;
		for (double kappa = 0; kappa <= 0.0005; kappa = kappa + 0.000005) {
			int[] pair = dyn.optimalGroup_n_k(table, kappa,
					0.00001);
			n1[i] = (double) pair[0];
			k1[i] = (double) pair[1];
			kappa_vec[i] = kappa;
			i++;
		}
		double[] n2 = new double[100];
		double[] k2 = new double[100];
		double[] tau_vec = new double[100];
		i = 0;
		for (double tau = 0; tau <= 0.0005; tau = tau + 0.000005) {
			int[] pair = dyn.optimalGroup_n_k(table, 0.0001, tau);
			n2[i] = (double) pair[0];
			k2[i] = (double) pair[1];
			tau_vec[i] = tau;
			i++;
		}

		Plot2DPanel plot1 = new Plot2DPanel();
		plot1.addLinePlot("n*(kappa)", kappa_vec, n1);
		plot1.addLinePlot("k*(kappa)", kappa_vec, k1);

		plot1.setAxisLabels("kappa", "n*k*(kappa) for tau = 0.00001");

		Plot2DPanel plot2 = new Plot2DPanel();
		plot2.addLinePlot("n*(tau)", tau_vec, n2);
		plot2.addLinePlot("k*(tau)", tau_vec, k2);

		plot2.setAxisLabels("tau", "n*k*(tau) for kappa = 0.0001");

		return new Plot2DPanel[] { plot1, plot2 };
	}
	
}
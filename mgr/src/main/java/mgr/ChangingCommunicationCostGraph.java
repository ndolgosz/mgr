package mgr;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;
import org.math.plot.Plot3DPanel;
import org.math.plot.plots.GridPlot3D;

import tables.T_steep_k_table;

public class ChangingCommunicationCostGraph {

	static int lambda = 15;
	static double tau = 0.001;
	static double kappa =  0.001;
	static int probSize = 11;

	public static void main(String[] args) {

		DynamicsFunctions dyn = new DynamicsFunctions();

		
		double[] probs = new double[probSize];
		double[] costs = new double[20];
		double[][] optSteep = new double[probSize][20];
		
		double prob = 0.0;
			
		for (int i = 0; i < probSize; i++){
			T_steep_k_table Tsk = new T_steep_k_table(0);
			Tsk.readTnkFromFile(lambda, prob);
			for (int cost = 1; cost <= 20; cost++) {
				optSteep[i][cost-1] = dyn.optimalGroup_steep_cost(Tsk, kappa, tau, cost);
				costs[cost-1] = cost;
			}
			
			probs[i] = prob;
			prob += 0.1;
		}
		Plot3DPanel plot = new Plot3DPanel();
		plot.addGridPlot("communication cost", costs,probs, optSteep);
		plot.setAxisLabels("cost", "inequality", "optimal steepness");
		plot.setFixedBounds(2, 0, 3);
		plot.setFixedBounds(0, 0, 20);
		plot.setFixedBounds(1, 0, 1);
		
		JFrame frame = new JFrame("a plot panel");
		frame.getContentPane().add(plot);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(1000, 500);

	}
}
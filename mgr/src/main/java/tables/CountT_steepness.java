package tables;

import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;



import org.math.plot.Plot3DPanel;

import mgr.DynamicsFunctions;
import mgr.Net;
import mgr.NetBA;

public class CountT_steepness {

	private final int k;
	private final int n_opt = 20;
	private final double begSteep = 0.0;
	private final double endSteep = 5.0;
	private final double diff = 0.1;
	private final int ITER = 5000;
	public double[] Ts = new double[(int) (endSteep / diff) + 1];
	private double[] steep_axis = new double[(int) (endSteep / diff) + 1];

	public CountT_steepness(int k) {
		this.k = k;
	}

	public void countTsteepkMatrix(int lambda, double prob) {
		DynamicsFunctions dynamics = new DynamicsFunctions();

		System.out.println("Building T(steep,k) plot: ITER=" + ITER
				+ " lambda: " + lambda);

		double steep = begSteep;
		int steep_iter = 0;
		while (steep_iter <= (int) endSteep / diff) {
			
			
			int notSync = 0;
			double T = 0;
			// usrednianie
			for (int run = 1; run <= ITER; run++) {
				
				NetBA net = new NetBA(n_opt);				
				net.configureInformationModel(prob, steep);
				net.setBMtoCenter();
				
				double ct = lambda + 1;
				notSync = 0;
				int i = 0;
				while (ct > lambda) {
					dynamics.updateOpinions_InformationModel(net,
							dynamics.takeRandomNeighbors(net));
					ct = dynamics.countTotalSynchrony(net);
					i++;
				}
			
				T = T + i - 1;

			}
			//System.out.println("Not synchronized = "+ notSync);
			steep_axis[steep_iter] = steep;
			Ts[steep_iter] = T / (ITER);
			steep = steep + diff;
			steep_iter++;
		}

	}

	public Plot2DPanel createPlot() {

		Plot2DPanel plot = new Plot2DPanel();
		plot.addLinePlot("T(s)", steep_axis, Ts);

		plot.addLinePlot(
				"interpolation",
				steep_axis,
				TableInterpolation.getValues(
						TableInterpolation.fitPolynomial1D(steep_axis, Ts), steep_axis));

		return plot;
	}

	public static void main(String[] args) {
		CountT_steepness T = new CountT_steepness(6);
		T.countTsteepkMatrix(10, 0.99);
		
		JFrame frame = new JFrame("a plot panel");
		frame.getContentPane().add(T.createPlot());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(1000, 500);

	}

}

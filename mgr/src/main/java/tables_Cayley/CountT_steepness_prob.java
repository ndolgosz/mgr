package tables_Cayley;

import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;



import org.math.plot.Plot3DPanel;

import tables.TableInterpolation;
import mgr.DynamicsFunctions;
import mgr.Net;
import mgr.NetBA;
import mgr.NetCayley;

public class CountT_steepness_prob {

	private final int k;
	private final int n_opt = 20;
	private final double begSteep = 0.0;
	private final double endSteep = 5.0;
	private final double diff = 0.1;
	public final int ITER = 100;
	public double[] Ts = new double[(int) (endSteep / diff) + 1];
	private double[] steep_axis = new double[(int) (endSteep / diff) + 1];

	public CountT_steepness_prob(int k) {
		this.k = k;
	}

	public void countTsteepkMatrix(int lambda, double prob) {
		DynamicsFunctions dynamics = new DynamicsFunctions();

		//System.out.println("Building T(steep,k) plot: ITER=" + ITER
				//+ " lambda: " + lambda);

		double steep = begSteep;
		int steep_iter = 0;
		while (steep_iter <= (int) endSteep / diff) {
			
			//System.out.println("   Counting for steep: "+steep_iter);
			int notSync = 0;
			double T = 0;
			// usrednianie
			for (int run = 1; run <= ITER; run++) {
				
				//NetCayley net = new NetCayley(4);				
				NetBA net = new NetBA(25);
				net.configureInformationModel(prob, steep);
				
				double ct = lambda + 1;
				notSync = 0;
				int i = 0;
				while (ct > lambda){ //&& i < 10000) {
					dynamics.updateOpinions_InformationModel(net,
							dynamics.takeRandomNeighbors(net));
					ct = dynamics.countTotalSynchrony(net);
					i++;
				}
				T=T+i;
				/*if(i < 10000){
				T = T + i - 1;
				}
				else
					notSync++;*/
			}
			//System.out.println("Not synchronized = "+ notSync);
			steep_axis[steep_iter] = steep;
			Ts[steep_iter] = T / (ITER -notSync);
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
		
		
		
		double[][] matrix = new double[5][201];
		int i = 0;
		for(double prob = 0.0; prob <= 1.0; prob = prob + 0.25){ 
			System.out.println("Counting for prob: "+prob);
			CountT_steepness_prob T = new CountT_steepness_prob(6);
			T.countTsteepkMatrix(20, prob);
			matrix[i] = T.Ts;
			i++;
		}
		
		for(int ii = 0; ii < matrix.length; ii++){
			
			for (int j = 0 ; j < matrix[0].length; j++){
				System.out.print(matrix[ii][j]+"\t");
			}
			System.out.println();
		}

		
		
	
	}

}

package basicmodel;

import javax.swing.JFrame;

import mgr.DynamicsFunctions;
import mgr.Net;
import mgr.NetBA;

import org.math.plot.Plot2DPanel;

public class PlotCt_N {

	int ITER;
	int TIME;

	int endN = 20;
	int begN = 6;
	int k = 2;

	public PlotCt_N(int iter, int time) {
		this.ITER = iter;
		this.TIME = time;
	}

	public Plot2DPanel createPlot() {
		DynamicsFunctions dynamics = new DynamicsFunctions();
		int n = begN;
		Plot2DPanel plot = new Plot2DPanel();
		// DYNAMICS
		double[] t = new double[TIME];
		while (n <= endN) {
			double[] ct = new double[TIME];
			ct[0] = 0;
			System.out.println("for n = "+n);
			for (int run = 1; run <= ITER; run++) {
				NetBA net = new NetBA(n);
				
				//Net net = new Net(n, k);
				int i = 0;
				
				while (i < TIME) {

					dynamics.updateOpinions_BasicModel(dynamics
							.takeRandomNeighbors(net));
					ct[i] = ct[i] + dynamics.countBasicTotalSynchrony(net)
							/ ITER;
					
					if (run == 1)
						t[i] = i;
					i++;
				}
			}
			plot.addLinePlot("c(t) for n = " + n + ", k = " + k, t, ct);
			n=n+2;
		}
		return plot;
	}
	public static void main(String[] args) {
		
		PlotCt_N plot = new PlotCt_N(1000, 1000);
		Plot2DPanel p = plot.createPlot();
		
		JFrame frame = new JFrame("a plot panel");
		//frame.setLayout(new GridLayout(1, 2));	
		frame.add(p);
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(1000, 500);
		
		
		
	}

}

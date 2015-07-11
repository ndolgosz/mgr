package basicmodel;

import mgr.DynamicsFunctions;
import mgr.Net;

import org.math.plot.Plot2DPanel;

public class PlotCt_K {

    int ITER;
    int TIME;

    int endK = 19;
    int begK = 3;
    int n = 20; 
    
    /**
     * Function plots synchrony parameter with time
     * @param iter - number of runs
     * @param time
     */
    public PlotCt_K(int iter, int time) {
        this.ITER = iter;
        this.TIME = time;
    }

    public Plot2DPanel createPlot() {
    	DynamicsFunctions dynamics = new DynamicsFunctions();
        System.out.println("Building ct(k) plot: ITER="+ITER+" TIME="+TIME);
        int k = begK;
        Plot2DPanel plot = new Plot2DPanel();
        // DYNAMICS
        double[] t = new double[TIME];
        while (k <= endK) {
            System.out.println("For k = "+k);
            double[] ct = new double[TIME];
            ct[0] = 0;
            for (int run = 1; run <= ITER; run++) {
                Net net = new Net(n, k);
                int i = 0;

                while (i < TIME) {

                	dynamics.updateOpinions_BasicModel(dynamics.takeRandomNeighbors(net));
                    ct[i] = ct[i] + dynamics.countBasicTotalSynchrony(net) / ITER;
                    if (run == 1)
                        t[i] = i;
                    i++;
                }
            }
            plot.addLinePlot("c(t) for n = " + n + ", k = " + k, t, ct);
            k++;
        }
        return plot;
    }

}

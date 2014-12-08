package mgr;

import org.math.plot.Plot2DPanel;

public class PlotCt_K {

    int ITER;
    int TIME;

    int endK = 19;
    int begK = 3;
    int n = 20;

    public PlotCt_K() {
        this.ITER = Main.ITER;
        this.TIME = Main.TIME;
    }

    public PlotCt_K(int iter, int time) {
        this.ITER = iter;
        this.TIME = time;
    }

    public Plot2DPanel createPlot() {

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

                    Main.updateOpinions(Main.takeRandomNeighbors(net));
                    ct[i] = ct[i] + Main.countBasicTotalSynchrony(net) / ITER;
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

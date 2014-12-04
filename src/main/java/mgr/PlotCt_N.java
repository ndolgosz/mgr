package mgr;

import org.math.plot.Plot2DPanel;

public class PlotCt_N {
    
    int ITER;
    int TIME;
    
    int endN = 20;
    int begN = 6;
    int k = 3;
      
    public PlotCt_N(){
        this.ITER = Main.ITER;
        this.TIME = Main.TIME;
    }
    
    public PlotCt_N(int iter, int time){
        this.ITER = iter;
        this.TIME = time;
    }
    
    public Plot2DPanel createPlot(){
        
        int n = begN;
        Plot2DPanel plot = new Plot2DPanel();      
        //DYNAMICS 
        double[] t = new double[TIME];
        while (n <= endN) {
            double [] ct = new double[TIME];
            ct[0] = 0;
            for (int run = 1; run <= ITER; run++) {
                Net net = new Net(n, k);
                int i = 0;

                while (i < TIME) {

                    Main.updateOpinions(Main.takeRandomNeighbors(net));
                    ct[i] = ct[i] + Main.countBasicTotalSynchrony(net) / ITER;
                    if(run == 1) t[i] = i;
                    i++;
                }
            }
            plot.addLinePlot("c(t) for n = "+n+", k = "+k, t, ct);
            n = n + 2;
        }
        return plot;
    }

}

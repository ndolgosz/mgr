package mgr;

import org.math.plot.Plot2DPanel;
import org.math.plot.Plot3DPanel;

public class SynchronyParameterT {

    int ITER;
    int TIME;
    double lambda;

    int begN = 6;
    int endN = 10;
    int endK = 8;
    int begK = 3;
    
    double[] n_vec = new double[endN-begN];
    double[] k_vec = new double[endK-begK];
    double[][] T_fun = new double[endN-begN][endK-begK];

    public SynchronyParameterT() {
        this.ITER = Main.ITER;
        this.TIME = Main.TIME;
        this.lambda = Main.lambda;
    }

    public SynchronyParameterT(int iter, int time) {
        this.ITER = iter;
        this.TIME = time;
    }

    public SynchronyParameterT(double l) {
        this.lambda = l;
        this.ITER = Main.ITER;
        this.TIME = Main.TIME;
    }

    public SynchronyParameterT(int iter, int time, double l) {
        this.lambda = l;
        this.ITER = iter;
        this.TIME = time;
    }

    private void processDynamics(){
        System.out.println("Building ct(k) plot: ITER=" + ITER + " TIME="
                + TIME);
        int k = begK;
        int n = begN;
        
        // DYNAMICS
 
        while (n <= endN) {
            while (k <= endK) {
                System.out.println("For k = " + k);
                double T = 0;
                double ct = 0;
                for (int run = 1; run <= ITER; run++) {
                    
                    Net net = new Net(n, k);
                    int i = 0;
                    while (i < TIME) {

                        Main.updateOpinions(Main.takeRandomNeighbors(net));
                        ct = Main.countBasicTotalSynchrony(net);
                        if (ct <= lambda) {
                            T = T + i/ITER;
                            break;
                        }
                        i++;
                    }
                    T_fun[n-begN][k-begK] = T;
                }
                k_vec[k-begK] = k;
                k++;
            }
            n_vec[n-begN] = n;
            n++;
        }
    }

    
    public Plot3DPanel createPlot() {
        
        processDynamics();
        Plot3DPanel plot = new Plot3DPanel();
        plot.addGridPlot("T(n,k)", n_vec, k_vec, T_fun);
        return plot;
    }

}

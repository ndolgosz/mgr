package mgr;

import java.util.HashMap;

import org.math.plot.Plot3DPanel;


public class SynchronyParameterT {

    int ITER;
    int TIME;
    double lambda;

    int begN = 10;
    int endN = 25;
    int begK = 2;
    int endK;
    
    
    double[] n_vec = new double[(endN-begN)/2 ];  
    double[][] T_fun = new double[(endN-begN)/2 + 1][(endN-begK)];

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

    public HashMap<Integer, HashMap<Integer, Double>> processDynamics(){
    	DynamicsFunctions dynamics = new DynamicsFunctions();
        HashMap<Integer,HashMap<Integer,Double>> T_fun_map = new HashMap<>();
        System.out.println("Building ct(k) plot: ITER=" + ITER + " TIME="
                + TIME);
        int k = begK;
        int n = begN;
        
        // DYNAMICS
        while (n <= endN) {
            System.out.println("For n = " + n);
            endK = n - 5;
            
            k = begK;
            HashMap<Integer, Double> kMap = new HashMap<>();
            while (k <= endK && k < n) {
                System.out.println("For k = " + k);
                double T = 0;
                double ct = 0;
               
                for (int run = 1; run <= ITER; run++) {
                    
                    Net net = new Net(n, k);
                    int i = 0;
                    while (i < TIME) {

                        dynamics.updateOpinions(dynamics.takeRandomNeighbors(net));
                        ct = dynamics.countBasicTotalSynchrony(net);
                        if (ct <= lambda) {
                            T = T + i/ITER;
                            break;
                        }
                        i++;
                    }
                   // T_fun[n_iter][k-begK] = T;
                }
              kMap.put(k, T);
                k++;
            }
            T_fun_map.put(n, kMap); 
            n=n+2;
           
        }
        
        System.out.println(T_fun_map);
        return T_fun_map;
    }

    
    public Plot3DPanel createPlot() {
        System.out.println(n_vec);
       // System.out.println(k_vec);
        processDynamics();
        Plot3DPanel plot = new Plot3DPanel();
        plot.addGridPlot("T(n,k)", T_fun);
        return plot;
    }

}

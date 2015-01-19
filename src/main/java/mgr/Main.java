package mgr;

import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;

public class Main {

    static int ITER = 2000;
    static int TIME = 200;
    static double lambda = 50;

    public static void main(String[] args) {

        System.out.println("Start! ct(t,n)");
        //DynamicsFunctions dyn = new DynamicsFunctions();
        for (double prob = 0.0; prob <= 1.0; prob = prob + 0.2) {
            T_steep_k_table T = new T_steep_k_table(prob);
            T.countTsteepkMatrix();
            try {
                T.saveTMatrixToFile();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        /*
         * int[] pair = dyn.optimalGroup_n_k(T.T_fun, 1000, T.n_axis, T.k_axis,
         * 0.0001, 0.00001); System.out.println("n*: " + pair[0] + " k*: " +
         * pair[1]);
         * 
         * int[] pair2 = dyn.optimalGroup_steep_k(T2.T_fun, 1000, T2.steep_axis,
         * T2.k_axis, 0.0001, 0.00001); System.out.println("steep*: " + pair2[0]
         * + " k*: " + pair2[1]);
         */

        /*
         * // BUILDING JFRAME
         * 
         * JFrame frame = new JFrame("a plot panel");
         * 
         * frame.setLayout(new GridLayout(1, 2)); Plot2DPanel[] plots =
         * dyn.optimalGroup_kappa_tau(T.T_fun, 300, T.n_axis, T.k_axis);
         * 
         * frame.add(plots[0]); frame.add(plots[1]);
         * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         * frame.setVisible(true); frame.setSize(1000, 500);
         * System.out.println("End!");
         */
    }
}

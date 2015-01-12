package mgr;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;

public class Main {

    static int ITER = 1000;
    static int TIME = 200;
    static double lambda = 40;

    public static void main(String[] args) {

        System.out.println("Start! ct(t,n)");

        DynamicsFunctions dyn = new DynamicsFunctions();

        SynchronyParameterT T = new SynchronyParameterT();
        try {
            T.readTnkFromFile(50);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // int[] pair = dyn.optimalGroup(Tnk, 150, T.n_axis, T.k_axis, 0.0001,
        // 0.00001);
        // System.out.println("n*: " + pair[0] + " k*: " + pair[1]);

        // BUILDING JFRAME

     /*   JFrame frame = new JFrame("a plot panel");

        frame.setLayout(new GridLayout(1, 2));
        Plot2DPanel[] plots = dyn.optimalGroup_kappa_tau(T.T_fun, 300,
                T.n_axis, T.k_axis);    
        frame.add(plots[0]);
        frame.add(plots[1]);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(1000, 500);
        System.out.println("End!");*/

    }
}

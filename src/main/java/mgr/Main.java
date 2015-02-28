package mgr;

import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;

public class Main {

	static int ITER = 1000;
	static int TIME = 200;
	static double lambda = 70;

	public static void main(String[] args) {

		System.out.println("Start! ct(t,n)");
		

		
		for (double prob = 0.0; prob <= 1.0; prob += 1.0) {
			T_steep_k_table T = new T_steep_k_table(prob);
			T.countTsteepkMatrix();
			try {
				T.saveTMatrixToFile();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		

		/*	double[] pair2 = dyn.optimalGroup_steep_k(T.T_fun, T.steep_axis,
					T.k_axis, 0.1, 0.1);
			System.out.println("prob: " + prob + " steep*: " + pair2[0]
					+ " k*: " + pair2[1]);
			i++;
		}
*/
	/*	T_steep_k_table T1 = new T_steep_k_table();
		
			try {
				T1.readTnkFromFile(70, 1.0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			T_steep_k_table T0 = new T_steep_k_table();
			
			try {
				T0.readTnkFromFile(50, 0.0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		JFrame frame = new JFrame("a plot panel");
		//frame.setContentPane(T.createPlot());
		frame.setLayout(new GridLayout(1, 2));
		//Plot2DPanel[] plots =
		// dyn.optimalGroup_kappa_tau(T.T_fun, T.n_axis, T.k_axis);

		frame.add(T1.createPlot());
		frame.add(T0.createPlot());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(1000, 500);
		System.out.println("End!");
*/
	}
}
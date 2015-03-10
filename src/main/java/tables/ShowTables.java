package tables;

import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;
import org.math.plot.Plot3DPanel;
import org.math.plot.plots.GridPlot3D;

public class ShowTables {

	static int lambda = 10;

	public static void main(String[] args) {

		T_steep_k_table T1 = new T_steep_k_table(1.0);
		T1.readTnkFromFile(lambda, 1.0);
		
		
		T_steep_k_table T0 = new T_steep_k_table(0.0);
		T0.readTnkFromFile(lambda, 0.0);

		JFrame frame = new JFrame("a plot panel");
		frame.setLayout(new GridLayout(1, 2));	
		frame.add(T1.createPlot());
		frame.add(T0.createPlot());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(1000, 500);

	}
}
package mgr;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;

import org.math.plot.Plot3DPanel;

public class T_steep_k_table {

	private int ITER;
	private double prob;
	private double lambda;
	private final static int n_opt = 19;
	private int begSteep = 1;
	private int endSteep = 10;
	private int begK = 2;
	private int diffEndN = 1;
	private int endK = n_opt - diffEndN;

	double[] steep_axis = new double[10];
	double[] k_axis = new double[n_opt - begK - diffEndN + 1];
	double[][] T_fun = new double[endSteep - begSteep +1][endK - begK
			 + 1];

	public T_steep_k_table() {
		this.ITER = Main.ITER;
		this.lambda = Main.lambda;
		setNKaxes();
	}


	

	public T_steep_k_table(double prob) {
	    this.ITER = Main.ITER;
		this.lambda = Main.lambda;
		this.prob = prob;
		setNKaxes();
	}

	public void countTsteepkMatrix() {
		DynamicsFunctions dynamics = new DynamicsFunctions();

		System.out.println("Building T(steep,k) plot: ITER=" + ITER
				+ " lambda: " + lambda);
		int k = begK;
		int steep = begSteep;

		// DYNAMICS
		while (steep <= endSteep) {
			System.out.println("For steepness = " + steep);

			k = begK;

			while (k <= endK) {
				System.out.println("\tFor k = " + k);
				double T = 0;

				for (int run = 1; run <= ITER; run++) {
					double ct = lambda + 1;
					Net net = new Net(n_opt, k, steep, prob);				

					int i = 0;
					while (ct > lambda && i < 300) {
						dynamics.updateOpinions_InformationModel(net,
								dynamics.takeRandomNeighbors(net));
						ct = dynamics.countTotalSynchrony(net);
						i++;
					}
					T = T + i - 1;
				}
				T_fun[steep - begSteep][k - begK] = T / ITER;
				k++;
			}
			steep++;

		}
	}

	public Plot3DPanel createPlot() {

	    Plot3DPanel plot = new Plot3DPanel();
        plot.addGridPlot("T(n,k)", k_axis, steep_axis, T_fun);
        plot.setAxisLabels("k", "steepness", "T(steepness,k)");
		return plot;
	}

	public void printMatrix(double[][] connections) {
		for (int i = 0; i < connections.length; i++) {
			System.out.println();
			System.out.print(i + "    ");
			for (int j = 0; j < connections[i].length; j++) {
				System.out.print(connections[i][j] + " ");
			}
		}
		System.out.println();
	}

	private void setNKaxes() {

		for (int i = 0; i < steep_axis.length; i++) {
			steep_axis[i] = begSteep + i;
		}
		for (int i = 0; i < k_axis.length; i++) {
			k_axis[i] = begK + i;
		}
	}

	public void saveTMatrixToFile() throws FileNotFoundException {

		PrintWriter writer = null;

		writer = new PrintWriter(
				"/home/n.dolgoszyja/mgr/src/main/resources/mgr/Tsteepk_L"
						+ String.valueOf((int) lambda) + "_"
						+ String.valueOf(prob) + ".txt");

		for (int i = 0; i < steep_axis.length; i++) {
			if (i != steep_axis.length - 1) {
				writer.print((int) steep_axis[i] + " ");
			} else {
				writer.print((int) steep_axis[i] + "\n");
			}
		}
		for (int i = 0; i < k_axis.length; i++) {
			if (i != k_axis.length - 1) {
				writer.print((int) k_axis[i] + " ");
			} else {
				writer.print((int) k_axis[i] + "\n");
			}
		}
		writer.println();

		for (int i = 0; i < T_fun.length; i++) {
			for (int j = 0; j < T_fun[0].length; j++) {
				if (j != T_fun[0].length - 1) {
					writer.print(T_fun[i][j] + " ");
				} else {
					writer.print(T_fun[i][j]);
				}
			}
			if (i < T_fun.length - 1) {
				writer.println();
			}
		}
		writer.close();

	}

	public void readTnkFromFile(int lambda, double prob) throws IOException {
		InputStream in = getClass().getResourceAsStream(
				"Tsteepk_L" + String.valueOf(lambda) + "_" + String.valueOf(prob) + ".txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		int i = 0;
		String[] naa = reader.readLine().split(" ");
		begSteep = Integer.valueOf(naa[0]);
		endSteep = Integer.valueOf(naa[naa.length - 1]);
		steep_axis = new double[endSteep - begSteep + 1];
		for (String na : naa) {
			steep_axis[i] = Double.valueOf(na);
			i++;
		}
		i = 0;
		String[] kaa = reader.readLine().split(" ");
		begK = Integer.valueOf(kaa[0]);
		endK = Integer.valueOf(kaa[kaa.length - 1]);
		k_axis = new double[endK - begK + 1];
		T_fun = new double[endSteep - begSteep + 1][endK - begK + 1];

		for (String na : kaa) {
			k_axis[i] = Double.valueOf(na);
			i++;
		}
		reader.readLine();
		int steep = 0;

		String lineS;
		while ((lineS = reader.readLine()) != null) {
			int k = 0;
			String[] line = lineS.split(" ");
			if (line.length < 2)
				break;
			for (String num : line) {
				T_fun[steep][k] = Double.valueOf(num);
				k++;
			}
			steep++;
		}

	}

	public void deleteFirstColumn() {
		begK = begK - 1;
		double[] k_axisTemp = new double[endSteep - begK - diffEndN + 1];
		for (int i = 1; i < k_axis.length; i++) {
			k_axisTemp[i - 1] = k_axis[i];
		}
		k_axis = k_axisTemp;

		double[][] temp = new double[T_fun.length][T_fun[0].length - 1];
		for (int i = 0; i < T_fun.length; i++) {
			for (int j = 1; j < T_fun[0].length; j++) {
				temp[i][j - 1] = T_fun[i][j];
			}
		}
		T_fun = temp;
	}
}

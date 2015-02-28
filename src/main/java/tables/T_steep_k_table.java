package tables;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import mgr.DynamicsFunctions;
import mgr.Net;

import org.math.plot.Plot3DPanel;

public class T_steep_k_table {

	private static final int ITER = 500;
	private double prob;
	private double lambda;
	private final static int n_opt = 20;
	private double begSteep = 0.0;
	private double endSteep = 3.0;
	private int begK = 2;
	private double diff = 0.2;
	private int endK = n_opt - 1;

	double[] steep_axis = new double[(int) ((endSteep - begSteep) / diff + 1)];
	double[] k_axis = new double[endK - begK + 1];
	double[][] T_fun = new double[endK - begK + 1][(int) ((endSteep - begSteep)
			/ diff + 1)];

	public T_steep_k_table(double prob, int lambda) {
		this.lambda = lambda;
		this.prob = prob;
		setNKaxes();
	}

	public T_steep_k_table(double prob) {
		this.lambda = 60;
		this.prob = prob;
		setNKaxes();
	}

	public void countTsteepkMatrix() {
		DynamicsFunctions dynamics = new DynamicsFunctions();

		System.out.println("Building T(steep,k) plot: ITER=" + ITER
				+ " lambda: " + lambda);
		int k = begK;
		double steep = begSteep;
		int steep_iter = 0;

		// DYNAMICS
		while (steep <= endSteep + diff / 2) {
			System.out.println("For steepness = " + steep);

			k = begK;
			while (k <= endK) {
				// System.out.println("\tFor k = " + k);
				double T = 0;

				for (int run = 1; run <= ITER; run++) {
					double ct = lambda + 1;
					Net net = new Net(n_opt, k, steep, prob);

					int i = 0;
					while (ct > lambda && i < 800) {
						dynamics.updateOpinions_InformationModel(net,
								dynamics.takeRandomNeighbors(net));
						ct = dynamics.countTotalSynchrony(net);
						// System.out.println(i + " : " +ct);

						i++;
					}
					T = T + i - 1;
				}
				T_fun[k - begK][steep_iter] = T / ITER;
				k++;
			}
			steep = steep + diff;
			steep_iter++;

		}
	}

	public Plot3DPanel createPlot() {

		Plot3DPanel plot = new Plot3DPanel();
		plot.addGridPlot("T(n,k)", steep_axis, k_axis, T_fun);
		plot.setAxisLabels("steepness", "k", "T(steepness,k)");
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
		steep_axis[0] = begSteep;
		for (int i = 1; i < steep_axis.length; i++) {
			steep_axis[i] = steep_axis[i - 1] + diff;
		}
		for (int i = 0; i < k_axis.length; i++) {
			k_axis[i] = begK + i;
		}
	}

	public void saveTMatrixToFile() throws FileNotFoundException {

		PrintWriter writer = null;

		writer = new PrintWriter(
				"/home/natalia/mgr/mgr/src/main/resources/mgr/Tsteepk_L"
						+ String.valueOf((int) lambda) + "_"
						+ String.valueOf(prob) + ".txt");

		for (int i = 0; i < steep_axis.length; i++) {
			if (i != steep_axis.length - 1) {
				writer.print(steep_axis[i] + " ");
			} else {
				writer.print(steep_axis[i] + "\n");
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

	public void readTnkFromFile(int lambda, double prob) {
		InputStream in = getClass().getResourceAsStream(
				"Tsteepk_L" + String.valueOf(lambda) + "_"
						+ String.valueOf(prob) + ".txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		int i = 0;
		try {
			String[] naa = reader.readLine().split(" ");
			begSteep = Double.valueOf(naa[0]);
			endSteep = Double.valueOf(naa[naa.length - 1]);
			steep_axis = new double[(int) ((endSteep - begSteep) / diff + 1)];
			for (String na : naa) {
				steep_axis[i] = Double.valueOf(na);
				i++;
			}
			i = 0;
			String[] kaa = reader.readLine().split(" ");
			begK = Integer.valueOf(kaa[0]);
			endK = Integer.valueOf(kaa[kaa.length - 1]);
			k_axis = new double[endK - begK + 1];
			T_fun = new double[endK - begK + 1][(int) ((endSteep - begSteep)
					/ diff + 1)];

			for (String na : kaa) {
				k_axis[i] = Double.valueOf(na);
				i++;
			}
			reader.readLine();
			int k = 0;

			String lineS;
			while ((lineS = reader.readLine()) != null) {
				int steep = 0;
				String[] line = lineS.split(" ");
				if (line.length < 2)
					break;
				for (String num : line) {
					T_fun[k][steep] = Double.valueOf(num);
					steep++;
				}
				k++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

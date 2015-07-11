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

public class T_n_k_table {

	private static final int ITER = 500;
	private double lambda;

	private int begN = 10;
	private int endN = 25;
	private int begK = 2;
	private int endK;
	private int diffEndN = 1;

	public double[] n_axis = new double[endN - begN + 1];
	public double[] k_axis = new double[endN - begK - diffEndN + 1];
	public double[][] T_fun = new double[endN - begN + 1][endN - begK - diffEndN + 1];

	public T_n_k_table(double l) {
		this.lambda = l;	
		setNKaxes();
	}

	public void countTnkMatrix() {
		DynamicsFunctions dynamics = new DynamicsFunctions();

		System.out.println("Building T(n,k) plot: ITER=" + ITER+ " lambda: " + lambda);
		int k = begK;
		int n = begN;

		// DYNAMICS
		while (n <= endN) {
			System.out.println("For n = " + n);
			endK = n - diffEndN;

			k = begK;

			while (k <= endK && k < n) {
				System.out.println("\tFor k = " + k);
				double T = 0;
				

				for (int run = 1; run <= ITER; run++) {
					double ct = lambda + 1;
					Net net = new Net(n, k);
					int i = 0;
					while (ct > lambda && i < 300) {
						dynamics.updateOpinions_BasicModel(dynamics
								.takeRandomNeighbors(net));
						ct = dynamics.countBasicTotalSynchrony(net);
						i++;
					}
					T = T + i - 1;
				}
				T_fun[n - begN][k - begK] = T / ITER;
				k++;
			}
			n++;

		}
	}

	public Plot3DPanel createPlot() {
		
		Plot3DPanel plot = new Plot3DPanel();
		plot.addGridPlot("T(n,k)", k_axis, n_axis, T_fun);
		plot.setAxisLabels("k", "n", "T(n,k)");
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
	
		for (int i = 0; i < n_axis.length; i++) {
			n_axis[i] = begN + i;
		}
		for (int i = 0; i < k_axis.length; i++) {
			k_axis[i] = begK + i;
		}
	}

	public void saveTMatrixToFile() {
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(
					"/home/n.dolgoszyja/mgr/src/main/resources/mgr/Tnk_L"
							+ String.valueOf((int) lambda) + ".txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < n_axis.length; i++) {
			if (i != n_axis.length - 1) {
				writer.print((int) n_axis[i] + " ");
			} else {
				writer.print((int) n_axis[i] + "\n");
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

	public void readTnkFromFile(int lambda) throws IOException {
		InputStream in = getClass().getResourceAsStream(
				"Tnk_L" + String.valueOf(lambda) + ".txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		int i = 0;
		String[] naa = reader.readLine().split(" ");
		begN = Integer.valueOf(naa[0]);
		endN = Integer.valueOf(naa[naa.length - 1]);
		n_axis = new double[endN - begN + 1];
		for (String na : naa) {
			n_axis[i] = Double.valueOf(na);
			i++;
		}
		i = 0;
		String[] kaa = reader.readLine().split(" ");
		begK = Integer.valueOf(kaa[0]);
		diffEndN = endN - Integer.valueOf(kaa[kaa.length - 1]);
		k_axis = new double[endN - begK - diffEndN + 1];
		T_fun = new double[endN - begN + 1][endN - begK - diffEndN + 1];

		for (String na : kaa) {
			k_axis[i] = Double.valueOf(na);
			i++;
		}
		reader.readLine();
		int n = 0;

		String lineS;
		while ((lineS = reader.readLine()) != null) {
			int k = 0;
			String[] line = lineS.split(" ");
			if (line.length < 2)
				break;
			for (String num : line) {
				T_fun[n][k] = Double.valueOf(num);
				k++;
			}
			n++;
		}

	}

	public void deleteFirstColumn() {
		begK = begK - 1;
		double[] k_axisTemp = new double[endN - begK - diffEndN + 1];
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

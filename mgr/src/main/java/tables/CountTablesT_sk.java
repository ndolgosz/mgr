package tables;
import java.io.FileNotFoundException;

public class CountTablesT_sk {

	public static void main(String[] args) {

		System.out.println("Started counting T(steep,k) tables:");
			
		for (double prob = 0.0; prob <= 1.0; prob += 0.1) {
			System.out.println("\tprobability = "+prob);
			
			T_steep_k_table T = new T_steep_k_table(prob,10);
			T.countTsteepkMatrix();
			try {
				T.saveTMatrixToFile();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
			
		System.out.println("End!");

	}
}
package tables_BA;
import java.io.FileNotFoundException;

public class CountTablesT_steep_n {

	public static void main(String[] args) {

		System.out.println("Started counting T(steep,n) tables:");
			
		for (double prob = 0.0; prob <= 1.0; prob += 0.1) {
			System.out.println("\tprobability = "+prob);
			
			T_steep_n_table T = new T_steep_n_table(prob,10);
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
package mgr;

public class Main {

	static int ITER = 1000;
	static int TIME = 200;
	static double lambda = 40;
	

	public static void main(String[] args) {

		System.out.println("Start! ct(t,n)");
		
		//DynamicsFunctions dyn = new DynamicsFunctions();
		
		Net net = new Net(20,4);
		net.setWeightsToEveryAgent();
		net.chooseTI(0.5);
		System.out.println("BM: "+net.BM+" TI: "+net.TI);
		
		//int[] pair = dyn.optimalGroup(Tnk, 150, T.n_axis, T.k_axis, 0.0001, 0.00001);
		//System.out.println("n*: " + pair[0] + " k*: " + pair[1]);

		// BUILDING JFRAME
		
		// JFrame frame = new JFrame("a plot panel");
		 
		 
		 //frame.setContentPane(plots[1]);
		
		//frame.setVisible(true); frame.setSize(1000, 500);
		 System.out.println("End!");
		 

	}
}

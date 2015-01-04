package mgr;


import javax.swing.JFrame;


public class Main {

    static int ITER = 2000;
    static int TIME = 200;
    static double lambda = 20;

    public static void main(String[] args) {

        System.out.println("Start! ct(t,n)");
        
        // BUILDING JFRAME
       JFrame frame = new JFrame("a plot panel");

        frame.setContentPane(new PlotCt_N().createPlot());
        //frame.add(new PlotCt_K().createPlot());
        frame.setVisible(true);
        frame.setSize(1000, 500);
        System.out.println("End!");

    }
}

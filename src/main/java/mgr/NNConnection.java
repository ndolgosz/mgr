package mgr;

import java.util.List;

public class NNConnection {

    List<Integer> bonds;

    public NNConnection(List<Integer> intList) {
        this.bonds = intList;
    }

    public void printNNConnection() {
        
        System.out.print("\n");
        for (Integer item : bonds) {
            System.out.print(item + " ");
        }
    }
}

package mgr;

import org.junit.*;

import org.junit.Test;

public class DynamicsTest {

    @Test
    public void setOpinion_InformationModelTest() {
        DynamicsFunctions dyn = new DynamicsFunctions();
        Net net = new Net(20, 4, 2, 0);
        for (int i = 0; i < 200; i++) {
            Agent[] agents = dyn.takeRandomNeighbors(net);
            dyn.updateOpinions_InformationModel(net,
                    agents);
            Assert.assertTrue(agents[0].getOpinion() == agents[1].getOpinion());
            Assert.assertTrue(agents[0].getOpinion() >= 0);
            Assert.assertTrue(agents[1].getOpinion() >= 0);
            Assert.assertTrue(agents[0].getOpinion() < 360);
            Assert.assertTrue(agents[1].getOpinion() < 360);
        }

    }

}

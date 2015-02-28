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
            dyn.updateOpinions_InformationModel(net, agents);
            Assert.assertTrue(agents[0].getOpinion() == agents[1].getOpinion());
            Assert.assertTrue(agents[0].getOpinion() >= 0);
            Assert.assertTrue(agents[1].getOpinion() >= 0);
            Assert.assertTrue(agents[0].getOpinion() < 360);
            Assert.assertTrue(agents[1].getOpinion() < 360);
        }

    }
    
    @Test
    public void setOpinion_BasicModelTest() {
        DynamicsFunctions dyn = new DynamicsFunctions();
        Net net = new Net(20, 4);
        for (int i = 0; i < 500; i++) {        
            Agent[] agents = dyn.takeRandomNeighbors(net);
            dyn.updateOpinions_BasicModel(agents);
            Assert.assertTrue(agents[0].getOpinion() == agents[1].getOpinion());
            Assert.assertTrue(agents[0].getOpinion() >= 0);
            Assert.assertTrue(agents[1].getOpinion() >= 0);
            Assert.assertTrue(agents[0].getOpinion() < 360);
            Assert.assertTrue(agents[1].getOpinion() < 360);
        }

    }

    @Test
    public void addingVectorsTest3() {
        DynamicsFunctions dyn = new DynamicsFunctions();
        for (int i = 0; i < 200; i++) {
            Net net = new Net(20, 4, 2, 0);
            Agent[] agents = dyn.takeRandomNeighbors(net);
            agents[0].setOpinion(350);
            agents[1].setOpinion(30);
            dyn.updateOpinions_InformationModel(net, agents);
            Assert.assertTrue(agents[0].getOpinion() == agents[1].getOpinion());
            Assert.assertTrue((agents[0].getOpinion() > 340 && agents[1]
                    .getOpinion() < 360)
                    || (agents[0].getOpinion() >= 0 && agents[0].getOpinion() <= 30));

        }

    }
    
    @Test
    public void addingVectorsTest2() {
        DynamicsFunctions dyn = new DynamicsFunctions();
        for (int i = 0; i < 200; i++) {
            Net net = new Net(20, 4, 2, 0);
            Agent[] agents = dyn.takeRandomNeighbors(net);
            agents[0].setOpinion(280);
            agents[1].setOpinion(105);
            dyn.updateOpinions_InformationModel(net, agents);
            Assert.assertTrue(agents[0].getOpinion() == agents[1].getOpinion());
            Assert.assertTrue((agents[0].getOpinion() <= 280 && agents[1]
                    .getOpinion() >= 105));

        }

    }
    
    @Test
    public void addingVectorsTest4() {
        DynamicsFunctions dyn = new DynamicsFunctions();
        for (int i = 0; i < 200; i++) {
            Net net = new Net(20, 4, 2, 0);
            Agent[] agents = dyn.takeRandomNeighbors(net);
            agents[0].setOpinion(0);
            agents[1].setOpinion(175);
            dyn.updateOpinions_InformationModel(net, agents);
            Assert.assertTrue(agents[0].getOpinion() == agents[1].getOpinion());
            Assert.assertTrue((agents[0].getOpinion() <= 175 && agents[1]
                    .getOpinion() >= 0));

        }

    }
    
    @Test
    public void addingVectorsTest5() {
        DynamicsFunctions dyn = new DynamicsFunctions();
        for (int i = 0; i < 200; i++) {
            Net net = new Net(20, 4, 2, 0);
            Agent[] agents = dyn.takeRandomNeighbors(net);
            agents[0].setOpinion(10);
            agents[1].setOpinion(340);
            dyn.updateOpinions_InformationModel(net, agents);
            Assert.assertTrue(agents[0].getOpinion() == agents[1].getOpinion());       
            Assert.assertTrue((agents[0].getOpinion() <= 10 && agents[1]
                    .getOpinion() >= 0) || (agents[0].getOpinion() >= 340 && agents[1]
                            .getOpinion() < 360));

        }

    }
}

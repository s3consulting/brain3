package mains;

import model.Brain3SimulatorALGORITMO2;
import model.Graph;
import util.FileSystemUtil;
import util.GraphUtil;

import java.io.IOException;

public class TestBrain3ALGORITMO2 {
    public static void main(String[] args) throws IOException {
        String dir = "/Users/cristianocimino/NetBeansProjects/generic-graph/GRAPHS";
        //String graphName = "PraksGasGraph";
        //String graphName = "PraksGasGraph_MAX_CAP";
        //String graphName = "PRAKS_GRAPH_CASE_A";
        //String graphName = "PRAKS_GRAPH_CASE_B";
        //String graphName = "PRAKS_GRAPH_CASE_C";
        //String graphName = "PRAKS_GRAPH_CASE_D";
        //String graphName = "PRAKS_GRAPH_CASE_E";
        //String graphName = "PRAKS_GRAPH_CASE_F";
        String graphName = "PRAKS_GRAPH_CASE_G";

        //String graphName = "CasoParticolare_ALGORITMO2";
        //String graphName = "PraksGasGraph_MAX_SOURCE_DEMAND";
        //String graphName = "graphBuilderCase_G_BIGGER_CAPACITIES";

        Graph graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);
        graph.setName(graphName);

        Brain3SimulatorALGORITMO2 brain3SimulatorALGORITMO2 = new Brain3SimulatorALGORITMO2(graph);
        brain3SimulatorALGORITMO2.execute();

        GraphUtil.showSourceStatistics(brain3SimulatorALGORITMO2.getAugmentedGraph());
        GraphUtil.showSinksStatistics(brain3SimulatorALGORITMO2.getAugmentedGraph());

        GraphUtil.checkEquilibriumOnNodes(brain3SimulatorALGORITMO2.getAugmentedGraph());

        System.out.println("------------- SORTED SINKS STATISTICS -----------------");
        GraphUtil.showRealDestinationsOrdered(graph);
    }
}

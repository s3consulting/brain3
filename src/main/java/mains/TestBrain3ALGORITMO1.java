package mains;

import model.Brain3SimulatorALGORITMO1;
import model.Graph;
import util.FileSystemUtil;
import util.GraphUtil;

import java.io.IOException;

public class TestBrain3ALGORITMO1 {
    public static void main(String[] args) throws IOException {
        //Graph graph = GasGraphBuilder.createPraksGasGraph();

        String dir = "/Users/cristianocimino/NetBeansProjects/generic-graph/GRAPHS";
        //String graphName = "PraksGasGraph";
        //String graphName = "PraksGasGraph_MAX_CAP";
        //String graphName = "PRAKS_GRAPH_CASE_A";
        //String graphName = "PRAKS_GRAPH_CASE_B";
        //String graphName = "PRAKS_GRAPH_CASE_C";
        //String graphName = "PRAKS_GRAPH_CASE_D";
        //String graphName = "PRAKS_GRAPH_CASE_E";
        String graphName = "PRAKS_GRAPH_CASE_F";
        //String graphName = "PRAKS_GRAPH_CASE_G";

        //String graphName = "graphBuilderCase_G_BIGGER_CAPACITIES";

        Graph graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);
        graph.setName(graphName);
        Brain3SimulatorALGORITMO1 brain3SimulatorALGORITMO1 = new Brain3SimulatorALGORITMO1();
        brain3SimulatorALGORITMO1.execute(graph);
        System.out.println("END");

        System.out.println("------------- SORTED SINKS STATISTICS -----------------");
        GraphUtil.showRealDestinationsOrdered(graph);
    }
}

package mains;

import model.Brain3SimulatorALGORITMO3;
import model.Graph;
import util.FileSystemUtil;
import util.GraphUtil;

import java.io.IOException;

public class TestBrain3ALGORITMO3 {
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

        Graph graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);
        Brain3SimulatorALGORITMO3 brain3SimulatorALGORITMO3 = new Brain3SimulatorALGORITMO3(graph);
        brain3SimulatorALGORITMO3.execute();

        GraphUtil.showRealDestinationsOrdered(graph);
    }
}

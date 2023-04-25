package mains;

import model.Graph;
import model.MaxFlowResponse;
import util.FileSystemUtil;
import util.FordFulkersonUtil;
import util.GraphUtil;

import java.io.IOException;

public class TestFileSystem {
    public static void main(String[] args) throws IOException {

        Integer idSource=0;
        Integer idSink=50;
        Graph graph = GasGraphBuilder.createPraksGasGraph();
        String dir = "/Users/cristianocimino/NetBeansProjects/generic-graph/GRAPHS";
        String graphName = "PraksGasGraph";

        //FileSystemUtil.writeLengthAndCapacityToFile(graph, graphName, dir);
        FileSystemUtil.writeGraphToFile(graph, graphName, dir);

        Graph augmentedGraph = GraphUtil.addVirtualSourceAndSink(graph);
        String augmentedGraphName = "AUGMENTED_"+graphName;
        FileSystemUtil.writeLengthAndCapacityToFile(augmentedGraph, augmentedGraphName, dir);
        FileSystemUtil.writeGraphToFile(augmentedGraph, augmentedGraphName, dir);



        graphName = "PraksGasGraph_MAX_CAP";
        graph = GasGraphBuilder.createPraksGraphWithIncreasedCapacities();
        graph.setName(graphName);
        FileSystemUtil.writeGraphToFile(graph, graphName, dir);

        graphName = "PraksGasGraph_MAX_SOURCE_DEMAND";
        graph = GasGraphBuilder.createPraksGraphWithIncreasedSourceDemand();
        graph.setName(graphName);
        FileSystemUtil.writeGraphToFile(graph, graphName, dir);


        System.out.println("END");


    }
}
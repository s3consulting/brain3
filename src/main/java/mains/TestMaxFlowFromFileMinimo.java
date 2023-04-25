package mains;

import model.Graph;
import model.MaxFlowResponse;
import util.FileSystemUtil;
import util.FordFulkersonUtil;

import java.io.IOException;

public class TestMaxFlowFromFileMinimo {
    public static void main(String[] args) throws IOException {

        String dir = "/Users/cristianocimino/NetBeansProjects/generic-graph/GRAPHS";
        Graph graph = GraphBuilder.createGraphMaxFlow4X4__Esercizio_capacity();
        String graphName = "GraphMaxFlow4X4__Esercizio_capacity";


        MaxFlowResponse maxFlowResponse = FordFulkersonUtil.maxFlow(graph, 0, 3);
        System.out.println("Max Flow: "+maxFlowResponse.getMaxFlow());


        FileSystemUtil.writeLengthAndCapacityToFile(graph, graphName, dir);
        FileSystemUtil.writeGraphToFile(graph, graphName, dir);

        Graph g1 = FileSystemUtil.loadGraphFromFile1(graphName, dir);
        g1.setName(graphName);


        MaxFlowResponse maxFlowResponse1 = FordFulkersonUtil.maxFlow(g1, 0, 3);
        System.out.println("Max Flow: "+maxFlowResponse1.getMaxFlow());

        System.out.println("END");
    }
}

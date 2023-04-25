package mains;

import model.DijkstraResponse;
import model.Graph;
import model.MaxFlowResponse;
import model.Vertex;
import util.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestGas {

    public static void main(String[] args) throws IOException {
        String dir = "/Users/cristianocimino/NetBeansProjects/generic-graph/GRAPHS";

        Graph graph = GasGraphBuilder.createPraksGasGraph();

        Graph augmentedGraph = GraphUtil.addVirtualSourceAndSink(graph);
        double[][] L = augmentedGraph.getLengthMatrix();
        //MatrixUtil.print(L);

        double[][] C = augmentedGraph.getCapacityMatrix();
        //MatrixUtil.print(C);


        FileSystemUtil.writeGraphToFile(augmentedGraph, augmentedGraph.getName(), dir);


        MaxFlowResponse maxFlowResponse = FordFulkersonUtil.maxFlow(augmentedGraph, augmentedGraph.getVirtualSource().getId(), augmentedGraph.getVirtualSink().getId());
        System.out.println("Max Flow: " + maxFlowResponse.getMaxFlow());

        Map<String, DijkstraResponse> distances = new HashMap<>();
        for (Vertex v : graph.getVertexes()) {
            DijkstraResponse dr = DijkstraUtil.findMultipleShortestPathsWithEdges(augmentedGraph, augmentedGraph.getVirtualSource().getId(), v.getId());
            distances.put(augmentedGraph.getVirtualSource().getId() + "_" + v.getId(), dr);
        }
        System.out.println("END");

        Map<String, Double> dist = new HashMap<>();
        for (String key : distances.keySet()) {
            dist.put(key, distances.get(key).getLength());
        }

        Map<String, Double> sorted = GenericUtil.sortByValue(dist);

        System.out.println("---------");


        maxFlowResponse = FordFulkersonUtil.maxFlow(augmentedGraph, 53, 54);
        System.out.println("Max Flow: " + maxFlowResponse.getMaxFlow());
    }


}

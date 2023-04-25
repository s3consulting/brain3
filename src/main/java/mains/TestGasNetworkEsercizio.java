package mains;

import constant.VertexConstant;
import model.DijkstraResponse;
import model.Graph;
import model.MaxFlowResponse;
import model.Vertex;
import util.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestGasNetworkEsercizio {
    public static void main(String[] args) throws IOException {
        Graph graph = GasGraphBuilder.GasNetworkEsercizio();
        Graph augmentedGraph = GraphUtil.addVirtualSourceAndSink(graph);
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
        System.out.println("END");


    }
}

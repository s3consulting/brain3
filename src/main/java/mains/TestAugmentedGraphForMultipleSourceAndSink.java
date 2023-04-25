package mains;

import model.DijkstraResponse;
import model.Graph;
import model.MaxFlowResponse;
import model.Vertex;
import util.DijkstraUtil;
import util.FordFulkersonUtil;
import util.GraphUtil;

import java.util.HashMap;
import java.util.Map;

public class TestAugmentedGraphForMultipleSourceAndSink {

    public static void main(String[] args) {

        //Graph graph = GraphBuilder.createGraphMaxFlow4X4__Esercizio_capacity();
        Graph graph = GraphBuilder.createGraphMaxFlow6X6__YouTube();
        Graph augmentedGraph = GraphUtil.addVirtualSourceAndSink(graph);

        MaxFlowResponse maxFlowResponse = FordFulkersonUtil.maxFlow(augmentedGraph, augmentedGraph.getVirtualSource().getId(), augmentedGraph.getVirtualSink().getId());
        System.out.println("Max Flow: "+maxFlowResponse.getMaxFlow());


        DijkstraResponse dijkstraResponse =DijkstraUtil.findMultipleShortestPathsWithEdges(augmentedGraph, augmentedGraph.getVirtualSource().getId(), augmentedGraph.getVirtualSink().getId());

        Map<String, DijkstraResponse> distances = new HashMap<>();

        for(Vertex v: graph.getVertexes()){
            DijkstraResponse dr = DijkstraUtil.findMultipleShortestPathsWithEdges(augmentedGraph, augmentedGraph.getVirtualSource().getId(), v.getId());
            distances.put(augmentedGraph.getVirtualSource().getId()+"_"+v.getId(), dr);
        }
        System.out.println("END");
    }
}

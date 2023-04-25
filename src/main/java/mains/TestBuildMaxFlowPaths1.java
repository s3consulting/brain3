package mains;

import model.Edge;
import model.Graph;
import model.MaxFlowResponse;
import model.Vertex;
import util.FordFulkersonUtil;
import util.GraphUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestBuildMaxFlowPaths1 {

    public static void main(String[] args) {
        //Graph graph = GasGraphBuilder.GasNetworkEsercizio1();
        //Graph graph = GasGraphBuilder.GasNetworkEsercizio();
        Graph graph = GasGraphBuilder.createPraksGasGraph();

        Graph augmentedGraph = GraphUtil.addVirtualSourceAndSink(graph);

        Vertex realSink = augmentedGraph.getVertexMap().get(19);
        Vertex virtualSource = augmentedGraph.getVirtualSource();

        //MaxFlowResponse maxFlowResponse = FordFulkersonUtil.maxFlow(augmentedGraph, augmentedGraph.getVirtualSource().getId(), realSink.getId());

        //GraphUtil.buildMaxFlowPaths1(virtualSource, maxFlowResponse, realSink);


        System.out.println("---------");
/*
        List<Edge> list = new ArrayList<>();
        List<String> visited = new ArrayList<>();
        String key = "";


        GraphUtil.buildMaxFlowPaths(maxFlowResponse, virtualSource, realSink, list, visited, key);

        Map<String, Double> sorted = new HashMap<>();
        sorted.put("53_19", 7.0);


        GraphUtil.showResult(augmentedGraph, sorted);

 */

        Integer[] sinkIds = {3,4,5,13,15,16,19,25,26,27,28,30,33,34,36,37,39,41,42,43,44,47,48,51,52};
        //Integer[] sinkIds = {3,4,5,13,15,16,28,33,34,37,39,41,42,43,44,47,48,51,52};
        for(int i=0; i<sinkIds.length; i++){
            System.out.println("Sink Id: "+sinkIds[i]);
            MaxFlowResponse maxFlowResponse = FordFulkersonUtil.maxFlow(augmentedGraph, augmentedGraph.getVirtualSource().getId(), sinkIds[i]);
            realSink = augmentedGraph.getVertexMap().get(sinkIds[i]);
            GraphUtil.buildMaxFlowPaths1(virtualSource, maxFlowResponse, realSink);
        }
    }
}

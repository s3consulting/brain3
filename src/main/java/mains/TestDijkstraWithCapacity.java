package mains;

import constant.VertexConstant;
import model.DijkstraResponse;
import model.Graph;
import model.MaxFlowResponse;
import model.Vertex;
import util.DijkstraUtil;
import util.FordFulkersonUtil;
import util.GenericUtil;
import util.GraphUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDijkstraWithCapacity {

    public static void main(String[] args) {
        Graph graph = GasGraphBuilder.createPraksGasGraph();

        Graph augmentedGraph = GraphUtil.addVirtualSourceAndSink(graph);

        Vertex virtualSource = augmentedGraph.getVirtualSource();
        Vertex virtualSink = augmentedGraph.getVirtualSink();

        System.out.println("Virtual Source: " + virtualSource);
        System.out.println("Virtual Sink: " + virtualSink);

        DijkstraUtil.findMultipleShortestPathsWithEdgesConsideringCapacity(augmentedGraph, virtualSource.getId(), virtualSink.getId());

        Map<String, DijkstraResponse> distances = new HashMap<>();

        List<Integer> visitedSinks = new ArrayList<>();
        List<Integer> realSinkIds = new ArrayList<>();
        for (Vertex v : graph.getVertexes()) {
            if(v.getWeight(VertexConstant.VERTEX_DEMAND)!=null && v.getWeight(VertexConstant.VERTEX_DEMAND)>0.0) {
                realSinkIds.add(v.getId());
            }
        }

        Map<String, Double> sortedFinal = new HashMap<>();

        while(visitedSinks.size()< realSinkIds.size()) {
            distances = new HashMap<>();
            System.out.println("Distanze dei percorsi minimi dal virtual source verso ogni real sink");
            for (Vertex v : graph.getVertexes()) {
                if (v.getWeight(VertexConstant.VERTEX_DEMAND) != null && v.getWeight(VertexConstant.VERTEX_DEMAND) > 0.0 && !visitedSinks.contains(v.getId())) {
                    DijkstraResponse dr = DijkstraUtil.findMultipleShortestPathsWithEdgesConsideringCapacity(augmentedGraph, augmentedGraph.getVirtualSource().getId(), v.getId());
                    distances.put(augmentedGraph.getVirtualSource().getId() + "_" + v.getId(), dr);
                    dr.showAllPaths();
                }
            }
            System.out.println("END");

            Map<String, Double> dist = new HashMap<>();

            for (String key : distances.keySet()) {
                dist.put(key, distances.get(key).getLength());
            }

            Map<String, Double> sorted = GenericUtil.sortByValue(dist);
            if(visitedSinks.size()==0){
                sortedFinal = sorted;
            }

            System.out.println("SORTED DISTANCES LIST");
            for (String key : sorted.keySet()) {
                System.out.println(key + " distanza: " + sorted.get(key));
            }

            String key = sorted.keySet().stream().findFirst().get();

            Integer realsinkKey = Integer.valueOf(key.split("_")[1]);
            Vertex realSink = augmentedGraph.getVertexMap().get(realsinkKey);

            MaxFlowResponse maxFlowResponse = FordFulkersonUtil.maxFlow(augmentedGraph, augmentedGraph.getVirtualSource().getId(), realSink.getId());
            System.out.println("MaxFlow da " + augmentedGraph.getVirtualSource().getId() + " a " + realsinkKey + ": " + maxFlowResponse.getMaxFlow());
            GraphUtil.updateFlowsInMaxFlow(augmentedGraph, maxFlowResponse, realSink);

            System.out.println("---");
            visitedSinks.add(realsinkKey);
        }

        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        GraphUtil.showResult(augmentedGraph, sortedFinal);


    }
}

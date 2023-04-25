package model;

import constant.ValueConstant;
import constant.VertexConstant;
import constant.WeightConstant;
import lombok.Getter;
import util.DijkstraUtil;
import util.FordFulkersonUtil;
import util.GenericUtil;
import util.GraphUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter

public class Brain3SimulatorALGORITMO4 {
    private Graph graph;
    private Graph augmentedGraph;
    private Map<String, Double> sorted;

    public Brain3SimulatorALGORITMO4(Graph graph) {
        this.graph = graph;
        augmentedGraph = GraphUtil.addVirtualSourceAndSink(graph);
    }

    public void execute(){

        Vertex virtualSource = augmentedGraph.getVirtualSource();
        Vertex virtualSink = augmentedGraph.getVirtualSink();
        List<Edge> saturatedArcs = new ArrayList<>();

        List<Vertex> satisfiedNodes = new ArrayList<>();
        Boolean canProceed = true;
        while (canProceed) {
            Map<String, DijkstraResponse> distances = new HashMap<>();

            //System.out.println("Distanze dei percorsi minimi dal virtual source verso ogni real sink escludendo i real sink già soddisfatti");
            for (Vertex v : graph.getVertexes()) {
                if(!satisfiedNodes.contains(v)) {
                    if (v.getWeight(VertexConstant.VERTEX_DEMAND) != null && v.getWeight(VertexConstant.VERTEX_DEMAND) > 0.0) {
                        DijkstraResponse dr = DijkstraUtil.findMultipleShortestPathsWithEdgesConsideringCapacity(augmentedGraph, augmentedGraph.getVirtualSource().getId(), v.getId());
                        distances.put(augmentedGraph.getVirtualSource().getId() + "_" + v.getId(), dr);
                        //dr.showAllPaths();
                    }
                }
            }

            Map<String, Double> dist = new HashMap<>();

            for (String key : distances.keySet()) {
                dist.put(key, distances.get(key).getLength());
            }

            Map<String, Double> sorted = GenericUtil.sortByValue(dist);
            System.out.println("--------------- SORTED ------------------");
            for(String key: sorted.keySet()){
                System.out.println(key+": "+sorted.get(key));
            }
            if(sorted.keySet().stream().findFirst().isPresent()) {
                String firstKey = sorted.keySet().stream().findFirst().get();
                Integer firstSinkKey = Integer.valueOf(firstKey.split("_")[1]);

                Vertex closestRealSink = graph.getVertexMap().get(firstSinkKey);

                DijkstraResponse dijkstraResponse = distances.get(firstKey);

                if (dijkstraResponse.getEdgePaths().size() == 0) {
                    System.out.println("ALT!");
                    canProceed = false;
                } else {
                    GraphUtil.updateCapacitiesOnArcsToVirtualSink(augmentedGraph, closestRealSink);
                    MaxFlowResponse maxFlowResponse = FordFulkersonUtil.maxFlow(augmentedGraph, virtualSource.getId(), virtualSink.getId());
                    updateFlowsOnMaxFlowPaths(maxFlowResponse);
                    System.out.println("HHHHHH");
                }
                if (distances.size() == 0) {
                    canProceed = false;
                }
                Double flowNet = closestRealSink.getFlowNet();
                Double bb = Math.abs(closestRealSink.getFlowNet() - closestRealSink.getWeight(VertexConstant.VERTEX_DEMAND));
                if (bb < 0.001) {
                    satisfiedNodes.add(closestRealSink);
                }
            }
            else{
                canProceed=false;
            }
        }
        GraphUtil.finalFlowsUpdate(augmentedGraph);
        for(Edge e: saturatedArcs){
            System.out.println("ARCO SATURATO: "+e);
        }
    }

    private void updateFlowsOnMaxFlowPaths(MaxFlowResponse maxFlowResponse){
        for(Edge e: maxFlowResponse.getEdgesMaxFlow()){
            if(e.getValue(ValueConstant.CUMULATIVE_FLOW)==null){
                e.setValue(ValueConstant.CUMULATIVE_FLOW,0.0);
            }
            e.setValue(ValueConstant.CUMULATIVE_FLOW, e.getValue(ValueConstant.CUMULATIVE_FLOW)+e.getValue(ValueConstant.FLOW));
            e.setWeight(WeightConstant.CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY)-e.getValue(ValueConstant.CUMULATIVE_FLOW));
        }
    }
}

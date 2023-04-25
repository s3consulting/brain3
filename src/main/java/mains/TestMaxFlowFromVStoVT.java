package mains;

import constant.ValueConstant;
import constant.VertexConstant;
import constant.WeightConstant;
import model.*;
import util.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestMaxFlowFromVStoVT {
    public static void main(String[] args) throws IOException {
        //Graph graph = GasGraphBuilder.createPraksGasGraph();

        String dir = "/Users/cristianocimino/NetBeansProjects/generic-graph/GRAPHS";
        //String graphName = "PraksGasGraph";
        //String graphName = "PraksGasGraph_MAX_CAP";
        //String graphName = "PRAKS_GRAPH_CASE_A";
        //String graphName = "PRAKS_GRAPH_CASE_B";
        //String graphName = "PRAKS_GRAPH_CASE_C";
        String graphName = "PRAKS_GRAPH_CASE_D";
        //String graphName = "PRAKS_GRAPH_CASE_E";
        //String graphName = "PRAKS_GRAPH_CASE_F";
        //String graphName = "PRAKS_GRAPH_CASE_G";

        Graph graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);
        graph.setName(graphName);
        Graph augmentedGraph = GraphUtil.addVirtualSourceAndSink(graph);

        Vertex virtualSource = augmentedGraph.getVirtualSource();
        Vertex virtualSink = augmentedGraph.getVirtualSink();

        MaxFlowResponse maxFlowResponse = FordFulkersonUtil.maxFlow(augmentedGraph, virtualSource.getId(), virtualSink.getId());

        GraphUtil.finalFlowsUpdate(augmentedGraph);
        GraphUtil.checkEquilibriumOnNodes(augmentedGraph);
        //GraphUtil.showSinksStatistics(augmentedGraph);

        System.out.println("-------");
        System.out.println("MaxFlow: "+maxFlowResponse.getMaxFlow());
        maxFlowResponse.getEdgesMaxFlow().forEach(x-> System.out.println(x.getSource().getId()+"_"+x.getDestination().getId()+", Flow: "+x.getValue(ValueConstant.FLOW)));

        for(Vertex vertex: augmentedGraph.getVertexes()){
            Double flowIn = 0.0;
            Double flowOut=0.0;
            if(vertex.getWeight(VertexConstant.VERTEX_DEMAND)!=null && vertex.getWeight(VertexConstant.VERTEX_DEMAND)>0){
                for(Edge edge: vertex.getInEdges()){
                    if(edge.getValue(ValueConstant.FLOW)!=null) {

                        flowIn += edge.getValue(ValueConstant.FLOW);
                    }
                }
                for(Edge edge: vertex.getOutEdges()){
                    if(edge.getValue(ValueConstant.FLOW)!=null) {
                        if(edge.getDestination().getId()!=augmentedGraph.getVirtualSink().getId()) {
                            flowOut += edge.getValue(ValueConstant.FLOW);
                        }
                    }
                }
                Double flowNet = flowIn-flowOut;
                String status = "Soddisfatto";
                if(vertex.getWeight(VertexConstant.VERTEX_DEMAND)-flowNet>.001){
                    status="NON SODDISFATTO";
                }
                System.out.println("Sink "+vertex.getName()+" flowIn: "+flowIn+", flowOut: "+flowOut+", Demand: "+vertex.getWeight(VertexConstant.VERTEX_DEMAND)+", status: "+status);
            }
        }



        Map<String, DijkstraResponse> distances = new HashMap<>();
        System.out.println("Distanze dei percorsi minimi dal virtual source verso ogni real sink");
        for (Vertex v : graph.getVertexes()) {
            if(v.getWeight(VertexConstant.VERTEX_DEMAND)!=null && v.getWeight(VertexConstant.VERTEX_DEMAND)>0.0) {
                DijkstraResponse dr = DijkstraUtil.findMultipleShortestPathsWithEdges(augmentedGraph, augmentedGraph.getVirtualSource().getId(), v.getId());
                distances.put(augmentedGraph.getVirtualSource().getId() + "_" + v.getId(), dr);
                //System.out.println("\t "+augmentedGraph.getVirtualSource().getId() + "_" + v.getId()+": "+dr.getLength());
                dr.showAllPaths();
            }
        }
        System.out.println("END");

        Map<String, Double> dist = new HashMap<>();
        for (String key : distances.keySet()) {
            dist.put(key, distances.get(key).getLength());
        }

        Map<String, Double> sorted = GenericUtil.sortByValue(dist);
        System.out.println("Sorted distances");
        for(String key: sorted.keySet()){
            System.out.println(key+" -> "+sorted.get(key));
        }

        for(Edge e: augmentedGraph.getEdges()){
            e.setValue(ValueConstant.CUMULATIVE_FLOW, e.getValue(ValueConstant.FLOW));
            //e.setWeight(WeightConstant.CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY)-e.getValue(ValueConstant.FLOW));
        }
        GraphUtil.showResult(augmentedGraph, sorted);

        GraphUtil.checkEquilibriumOnNodes(augmentedGraph);
        GraphUtil.showSourceStatistics(augmentedGraph);
        GraphUtil.showSinksStatistics(augmentedGraph, sorted);

        GraphUtil.showRealDestinationsOrdered(graph);
    }
}

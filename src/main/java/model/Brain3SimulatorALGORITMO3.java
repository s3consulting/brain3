package model;

import constant.ValueConstant;
import constant.VertexConstant;
import lombok.Getter;
import model.*;
import util.DijkstraUtil;
import util.FordFulkersonUtil;
import util.GenericUtil;
import util.GraphUtil;

import java.util.HashMap;
import java.util.Map;

@Getter
/**
 * Implementazione dell'algoritmo 3. In questo caso si calcola il massimo flusso senza prendere in considerazione le distanze dei real sink. In
 * questo caso avremo quindi la determinazione del massimo flusso (Ford-Fulkerson) tra il nodo virtual VIRTUAL_SOURCE e il nodo virtuale VIRTUAL_SINK
 *
 */
public class Brain3SimulatorALGORITMO3 {

    private Graph graph;
    private Graph augmentedGraph;



    public Brain3SimulatorALGORITMO3(Graph graph){
        this.graph = graph;
        augmentedGraph = GraphUtil.addVirtualSourceAndSink(graph);
    }

    public void execute(){
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



    }
}

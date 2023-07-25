package model;

import constant.ValueConstant;
import constant.VertexConstant;
import constant.WeightConstant;
import lombok.Getter;
import lombok.Setter;
import util.DijkstraUtil;
import util.FordFulkersonUtil;
import util.GenericUtil;
import util.GraphUtil;

import java.util.*;

@Getter
@Setter
/**
 * Algoritmo 2 rappresenta l'implementazione del second algoritmo implementato. In questo caso le distanze vengono calcolate ad ogni passo
 * e si cerca di utilizzare solo il path più breve. Nel caso in cui alcuni archi appartenenti al path individuato si dovessero saturare, questi non
 * sono più considerati nel calcolo dei percosri minimi (Dijkstra) e quindi le priorità dei sink reali sono riviste.
 */
public class Brain3SimulatorALGORITMO2 {

    private Graph graph;
    private Graph augmentedGraph;
    private Map<String, Double> sorted;

    public Brain3SimulatorALGORITMO2(Graph graph) {
        this.graph = graph;
        augmentedGraph = GraphUtil.addVirtualSourceAndSink(graph);
    }

    public void execute() {


        MinimalPathFlows minimalPathFlows = new MinimalPathFlows();
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

            //Map<String, Double> sorted = reverse(dist);

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
                    //MaxFlowResponse maxFlowResponse = FordFulkersonUtil.maxFlow(augmentedGraph, augmentedGraph.getVirtualSource().getId(), closestRealSink.getId());
                    //updateFlowsOnMinimalPathToClosestRealSink(dijkstraResponse, closestRealSink, maxFlowResponse, saturatedArcs, minimalPathFlows);
                    updateFlowsOnMinimalPathToClosestRealSink(dijkstraResponse, closestRealSink, saturatedArcs, minimalPathFlows);

                    System.out.println("HHHHHH");
                }
                if (distances.size() == 0) {
                    canProceed = false;
                }
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


    //private void updateFlowsOnMinimalPathToClosestRealSink(DijkstraResponse dijkstraResponse, Vertex closestRealSink, MaxFlowResponse maxFlowResponse, List<Edge> saturatedArcs, MinimalPathFlows minimalPathFlows) {
    private void updateFlowsOnMinimalPathToClosestRealSink(DijkstraResponse dijkstraResponse, Vertex closestRealSink, List<Edge> saturatedArcs, MinimalPathFlows minimalPathFlows) {
        //check how much flow the closestRealSink has already received
        //net flow on closest sink
        Double netFlowOnSink = closestRealSink.getFlowNet();
        //how much flow is missing to satisfy closest sink demand
        Double diffFlow = closestRealSink.getWeight(VertexConstant.VERTEX_DEMAND) - netFlowOnSink;
        System.out.println("diffFlow on Sink " + closestRealSink.getName() + ": " + diffFlow);
        Double minCapacityOnShortestPath = Double.MAX_VALUE;
        String arcKeyWithMinumimCapacity="";
        if (dijkstraResponse.getEdgePaths().size() == 0) {
            System.out.println("ALT!");
        }
        for (Edge edge : dijkstraResponse.getEdgePaths().get(0)) {
            //min capacity on arcs belonging to shortest path

            //if (maxFlowResponse.getEdgesMaxFlow().contains(edge)) {

                System.out.println((edge));
                if (edge.getWeight(WeightConstant.CAPACITY) < minCapacityOnShortestPath) {
                    minCapacityOnShortestPath = edge.getWeight(WeightConstant.CAPACITY);
                    arcKeyWithMinumimCapacity = edge.getSource().getId()+"_"+edge.getDestination().getId();
                }
            //}
        }
        System.out.println("minCapacity: " + minCapacityOnShortestPath+" on arc "+arcKeyWithMinumimCapacity) ;
        Double flowAdjust = 0.0;
        //determine how much flow on arcs belonging to shortest path has to be added/removed
        if (diffFlow < minCapacityOnShortestPath) {
            flowAdjust = diffFlow;
        } else {
            flowAdjust = minCapacityOnShortestPath;
        }
        //update flow and cumulative flow on all arcs of the shortest path
        for (Edge edge : dijkstraResponse.getEdgePaths().get(0)) {
            //if (maxFlowResponse.getEdgesMaxFlow().contains(edge)) {
                if (edge.getValue(ValueConstant.FLOW) == null) {
                    edge.setValue(ValueConstant.FLOW, 0.0);
                }
                if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) == null) {
                    edge.setValue(ValueConstant.CUMULATIVE_FLOW, 0.0);
                }
                edge.setValue(ValueConstant.FLOW, flowAdjust);
                edge.setValue(ValueConstant.CUMULATIVE_FLOW, edge.getValue(ValueConstant.CUMULATIVE_FLOW) + edge.getValue(ValueConstant.FLOW));
                edge.setWeight(WeightConstant.CAPACITY, edge.getWeight(WeightConstant.ORIGINAL_CAPACITY) - edge.getValue(ValueConstant.CUMULATIVE_FLOW));
                if(edge.getWeight(WeightConstant.CAPACITY)==0.0){
                    saturatedArcs.add(edge);
                }
            //}

        }
        minimalPathFlows.addPath(dijkstraResponse.getEdgePaths().get(0));
        minimalPathFlows.addPathForClosestSink(""+closestRealSink.getId(), dijkstraResponse.getEdgePaths().get(0));
        System.out.println("£££££");

    }

    public static Map<String, Double> reverse(Map<String, Double> sorted){
        Map<String, Double> reverse = new HashMap<>();
        List<String> reverseKeys=new ArrayList<>();
        for(String key: sorted.keySet()){
            reverseKeys.add(key);
        }
        for(int i= reverseKeys.size()-1; i>=0; i--){
            reverse.put(reverseKeys.get(i), sorted.get(reverseKeys.get(i)));
        }
        return reverse;
    }
/*
    public void execute_OLD() {
        Integer cnt = 0;
        Integer maxRepetition = 10;
        Integer size0 = 0;
        Integer size1 = Integer.MAX_VALUE;
        List<Vertex> blockingNodes = new ArrayList<>();
        Boolean canProceed = true;
        while (canProceed) {
            Map<String, DijkstraResponse> distances = new HashMap<>();

            //System.out.println("Distanze dei percorsi minimi dal virtual source verso ogni real sink");
            for (Vertex v : graph.getVertexes()) {

                if(!blockingNodes.contains(v)) {
                    Double bb = Math.abs(v.getFlowNet() - v.getWeight(VertexConstant.VERTEX_DEMAND));
                    if (v.getWeight(VertexConstant.VERTEX_DEMAND) != null && v.getWeight(VertexConstant.VERTEX_DEMAND) > 0.0 && bb > 0.001) {
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
            String firstKey = sorted.keySet().stream().findFirst().get();
            Integer firstSinkKey = Integer.valueOf(firstKey.split("_")[1]);
            size0 = sorted.size();

            Vertex closestRealSink = graph.getVertexMap().get(firstSinkKey);
            if(closestRealSink.getId()==41){
                System.out.println("W");
            }
            DijkstraResponse dijkstraResponse = distances.get(firstKey);
            if (size0 == size1) {
                cnt++;
            } else {
                cnt = 0;
            }
            if (cnt == maxRepetition) {
                cnt = 0;
                blockingNodes.add(closestRealSink);
            }
            if (dijkstraResponse.getEdgePaths().size() == 0) {
                System.out.println("ALT!");
                canProceed = false;
            }
            else {
                MaxFlowResponse maxFlowResponse = FordFulkersonUtil.maxFlow(augmentedGraph, augmentedGraph.getVirtualSource().getId(), closestRealSink.getId());
                updateFlowsOnMinimalPathToClosestRealSink(dijkstraResponse, closestRealSink, maxFlowResponse, s);
                System.out.println("HHHHHH");
                size1 = size0;
            }
            if (distances.size() == 0) {
                canProceed = false;
            }
        }
        GraphUtil.finalFlowsUpdate(augmentedGraph);
    }

    private void updateFlowsOnMinimalPathToClosestRealSink_OLD(DijkstraResponse dijkstraResponse, Vertex closestRealSink, MaxFlowResponse maxFlowResponse) {
        //check how much flow the closestRealSink has already received
        Double flowIn = 0.0;
        Double flowOut = 0.0;
        for (Edge edgeIn : closestRealSink.getInEdges()) {
            if (edgeIn.getValue(ValueConstant.CUMULATIVE_FLOW) != null) {
                flowIn = edgeIn.getValue(ValueConstant.CUMULATIVE_FLOW);
            }
        }
        for (Edge edgeOut : closestRealSink.getOutEdges()) {
            if (edgeOut.getValue(ValueConstant.CUMULATIVE_FLOW) != null) {
                flowOut = edgeOut.getValue(ValueConstant.CUMULATIVE_FLOW);
            }
        }
        //net flow on closest sink
        Double netFlowOnSink = flowIn - flowOut;
        //how much flow is missing to satisfy closest sink demand
        Double diffFlow = closestRealSink.getWeight(VertexConstant.VERTEX_DEMAND) - netFlowOnSink;
        System.out.println("diffFlow on Sink " + closestRealSink.getName() + ": " + diffFlow);
        Double minCapacityOnShortestPath = Double.MAX_VALUE;
        if (dijkstraResponse.getEdgePaths().size() == 0) {
            System.out.println("ALT!");
        }
        for (Edge edge : dijkstraResponse.getEdgePaths().get(0)) {
            //min capacity on arcs belonging to shortest path

            if (maxFlowResponse.getEdgesMaxFlow().contains(edge)) {
                System.out.println((edge));
                if (edge.getWeight(WeightConstant.CAPACITY) < minCapacityOnShortestPath) {
                    minCapacityOnShortestPath = edge.getWeight(WeightConstant.CAPACITY);
                }
            }
        }
        System.out.println("minCapacity: " + minCapacityOnShortestPath);
        Double flowAdjust = 0.0;
        //determine how much flow on arcs belonging to shortest path has to be added/removed
        if (diffFlow < minCapacityOnShortestPath) {
            flowAdjust = diffFlow;
        } else {
            flowAdjust = minCapacityOnShortestPath;
        }
        //update flow and cumulative flow on all arcs of the shortest path
        for (Edge edge : dijkstraResponse.getEdgePaths().get(0)) {
            if (maxFlowResponse.getEdgesMaxFlow().contains(edge)) {
                if (edge.getValue(ValueConstant.FLOW) == null) {
                    edge.setValue(ValueConstant.FLOW, 0.0);
                }
                if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) == null) {
                    edge.setValue(ValueConstant.CUMULATIVE_FLOW, 0.0);
                }
                edge.setValue(ValueConstant.FLOW, flowAdjust);
                edge.setValue(ValueConstant.CUMULATIVE_FLOW, edge.getValue(ValueConstant.CUMULATIVE_FLOW) + edge.getValue(ValueConstant.FLOW));
                edge.setWeight(WeightConstant.CAPACITY, edge.getWeight(WeightConstant.ORIGINAL_CAPACITY) - edge.getValue(ValueConstant.CUMULATIVE_FLOW));
            }
        }
        System.out.println("£££££");

    }

 */
}

package model;

import constant.VertexConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import util.DijkstraUtil;
import util.FordFulkersonUtil;
import util.GenericUtil;
import util.GraphUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
/**
 * Algoritmo 1 si riferisce al primo algoritmo implementato: Dijkstra viene calcolato solo una volta quindi le distanze, e le relative priorità
 * Una volta individuate le priorità ogni real sink deve essere soddisfatto a prescindere dal percorso individuato
 */
public class Brain3SimulatorALGORITMO1 {

    private Graph augmentedGraph;



    public void execute(Graph graph) throws IOException {
        //GraphUtil.checkArcsInArcsOutForVertices(graph);
        augmentedGraph = GraphUtil.addVirtualSourceAndSink(graph);
        //calcolo dei percorsi minimi dal Virtual Source verso ogni Sink Reale (nodi con demand>0)
        Map<String, DijkstraResponse> distances = new HashMap<>();

        //System.out.println("Distanze dei percorsi minimi dal virtual source verso ogni real sink");
        for (Vertex v : graph.getVertexes()) {
            if(v.getWeight(VertexConstant.VERTEX_DEMAND)!=null && v.getWeight(VertexConstant.VERTEX_DEMAND)>0.0) {
                DijkstraResponse dr = DijkstraUtil.findMultipleShortestPathsWithEdges(augmentedGraph, augmentedGraph.getVirtualSource().getId(), v.getId());
                distances.put(augmentedGraph.getVirtualSource().getId() + "_" + v.getId(), dr);
                //dr.showAllPaths();
            }
        }
        //System.out.println("END");

        Map<String, Double> dist = new HashMap<>();
        for (String key : distances.keySet()) {
            dist.put(key, distances.get(key).getLength());
        }

        Map<String, Double> sorted = GenericUtil.sortByValue(dist);

        System.out.println("SORTED DISTANCES LIST");
        for(String key: sorted.keySet()){
            System.out.println(key+" distanza: "+sorted.get(key));
        }

        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

        Map<String, MaxFlowReduced> maxFlowsReduced = new HashMap<>();
        for(String key: sorted.keySet()){
            Integer realsinkKey = Integer.valueOf(key.split("_")[1]);
            Vertex realSink = augmentedGraph.getVertexMap().get(realsinkKey);

            MaxFlowResponse maxFlowResponse = FordFulkersonUtil.maxFlow(augmentedGraph, augmentedGraph.getVirtualSource().getId(), realSink.getId());
            //System.out.println("MaxFlow da "+ augmentedGraph.getVirtualSource().getId()+" a "+realsinkKey+": "+maxFlowResponse.getMaxFlow());
            GraphUtil.updateFlowsInMaxFlow(augmentedGraph, maxFlowResponse, realSink);

            //System.out.println("---");
            MaxFlowReduced maxFlowReduced = new MaxFlowReduced();
            maxFlowReduced.build(maxFlowResponse);
            maxFlowReduced.setKey(key);
            maxFlowsReduced.put(key, maxFlowReduced);
        }

        GraphUtil.finalFlowsUpdate(augmentedGraph);
        //GraphUtil.showResult(augmentedGraph, sorted);

        String graphName = augmentedGraph.getName();
        String resultDir = "RESULT_DIR";
        GraphUtil.writeResultToFile(augmentedGraph, sorted, graphName, resultDir);


/*

        System.out.println("--------------- ARCHI USATI --------------------");
        GraphUtil.showUsedArcsInFlow(augmentedGraph);


        System.out.println("---------------  Flussi sugli archi per ogni Sink reale -----------------");

        for(String key: maxFlowsReduced.keySet()){
            MaxFlowReduced maxFlowReduced = maxFlowsReduced.get(key);
            if(maxFlowReduced.getEdges()!=null) {
                String data = maxFlowReduced.getEdges().stream().map(x -> "\t["+x.getSource().getId()+"_"+x.getDestination().getId() + "], flow: " + x.getValue(ValueConstant.FLOW) + ", capacity: " + x.getWeight(WeightConstant.CAPACITY)).collect(Collectors.joining("\n"));
                System.out.println(maxFlowReduced.getKey() + "\n" + data);
            }
        }

 */
        GraphUtil.checkEquilibriumOnNodes(augmentedGraph);
        GraphUtil.showSourceStatistics(augmentedGraph);
        //GraphUtil.showSinksStatistics(augmentedGraph);
        GraphUtil.showSinksStatistics(augmentedGraph, sorted);
    }
}

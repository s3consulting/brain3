package mains;

import constant.VertexConstant;
import model.DijkstraResponse;
import model.Graph;
import model.Vertex;
import util.DijkstraUtil;
import util.GenericUtil;
import util.GraphUtil;

import java.util.HashMap;
import java.util.Map;

public class TestDijikstraOnPrakGraph {
    public static void main(String[] args){
        Graph graph = GasGraphBuilder.createPraksGasGraph();

        Graph augmentedGraph = GraphUtil.addVirtualSourceAndSink(graph);

        Vertex virtualSource = augmentedGraph.getVirtualSource();
        Vertex virtualSink = augmentedGraph.getVirtualSink();

        System.out.println("Virtual Source: "+virtualSource);
        System.out.println("Virtual Sink: "+virtualSink);

        //calcolo dei percorsi minimi dal Virtual Source verso ogni Sink Reale (nodi con demand>0)
        Map<String, DijkstraResponse> distances = new HashMap<>();
        for (Vertex v : augmentedGraph.getVertexes()) {
            if(v!= virtualSource && v.getWeight(VertexConstant.VERTEX_DEMAND)!=null && v.getWeight(VertexConstant.VERTEX_DEMAND)>0) {
                DijkstraResponse dr = DijkstraUtil.findMultipleShortestPathsWithEdges(augmentedGraph, augmentedGraph.getVirtualSource().getId(), v.getId());
                distances.put(augmentedGraph.getVirtualSource().getId() + "_" + v.getId(), dr);
            }
        }
        System.out.println("END");

        Map<String, Double> dist = new HashMap<>();
        for (String key : distances.keySet()) {
            dist.put(key, distances.get(key).getLength());
        }

        Map<String, Double> sorted = GenericUtil.sortByValue(dist);

        System.out.println("SORTED DISTANCES LIST");
        for(String key: sorted.keySet()){
            System.out.println(key+" distanza: "+sorted.get(key));
        }
    }
}

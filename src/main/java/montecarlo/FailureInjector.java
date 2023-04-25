package montecarlo;

import constant.VertexConstant;
import constant.WeightConstant;
import model.Edge;
import model.Graph;
import model.Vertex;

import java.util.List;

public class FailureInjector {

    public static void uniformFailureOnArcs(Graph graph, Double percentageLoss){
        for(Edge edge: graph.getEdges()){
            if(edge.getWeight(WeightConstant.ORIGINAL_CAPACITY)!=null){
                edge.setWeight(WeightConstant.ORIGINAL_CAPACITY, edge.getWeight(WeightConstant.ORIGINAL_CAPACITY)*percentageLoss);
                edge.setWeight(WeightConstant.CAPACITY, edge.getWeight(WeightConstant.ORIGINAL_CAPACITY));
            }
        }
    }

    public static void uniformFailureOnSources(Graph graph, Double percentageLoss){
        for(Vertex vertex: graph.getVertexes()){
            if(vertex.getWeight(VertexConstant.VERTEX_DEMAND)!=null && vertex.getWeight(VertexConstant.VERTEX_DEMAND)<0.0){
                vertex.setWeight(VertexConstant.VERTEX_DEMAND, vertex.getWeight(VertexConstant.VERTEX_DEMAND)*percentageLoss);
            }
        }
    }

    public static void uniformFailureOnSinks(Graph graph, Double percentageLoss){
        for(Vertex vertex: graph.getVertexes()){
            if(vertex.getWeight(VertexConstant.VERTEX_DEMAND)!=null && vertex.getWeight(VertexConstant.VERTEX_DEMAND)>0.0){
                vertex.setWeight(VertexConstant.VERTEX_DEMAND, vertex.getWeight(VertexConstant.VERTEX_DEMAND)*percentageLoss);
            }
        }
    }

}

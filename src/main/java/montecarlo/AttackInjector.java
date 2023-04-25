package montecarlo;

import constant.VertexConstant;
import constant.WeightConstant;
import model.Edge;
import model.Graph;
import model.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AttackInjector {

    public static void injectRandomAttack(Graph graph, Double percentageDamage){
        List<Vertex> attackedVertices = new ArrayList<>();
        List<Edge> attackedEdges = new ArrayList<>();
        Random r = new Random();
        int numberOfAttackedVertices = r.nextInt(graph.getVertexes().size());
        int numberOfAttackedEdges = r.nextInt(graph.getEdges().size());

        while(attackedEdges.size()<numberOfAttackedEdges){
            Integer randomEdgeId = r.nextInt(graph.getEdges().size()-1);
            Edge randomEdge = graph.getEdge(randomEdgeId);
            if(randomEdge!=null) {
                if (!attackedEdges.contains(randomEdge)) {
                    attackedEdges.add(randomEdge);
                    randomEdge.setWeight(WeightConstant.ORIGINAL_CAPACITY, randomEdge.getWeight(WeightConstant.ORIGINAL_CAPACITY) * percentageDamage);
                    randomEdge.setWeight(WeightConstant.CAPACITY, randomEdge.getWeight(WeightConstant.ORIGINAL_CAPACITY));

                }
            }
        }
        while(attackedVertices.size()<numberOfAttackedVertices){
            Integer randomVertexId = r.nextInt(graph.getVertexes().size()-1);
            Vertex randomVertex = graph.getVertex(randomVertexId);
            if(randomVertex!=null) {
                if (!attackedVertices.contains(randomVertex)) {
                    attackedVertices.add(randomVertex);
                    if (randomVertex.getWeight(VertexConstant.VERTEX_DEMAND) != null && randomVertex.getWeight(VertexConstant.VERTEX_DEMAND) != 0) {
                        randomVertex.setWeight(VertexConstant.VERTEX_DEMAND, randomVertex.getWeight(VertexConstant.VERTEX_DEMAND) * percentageDamage);
                    } else {
                        for (Edge inEdge : randomVertex.getInEdges()) {
                            inEdge.setWeight(WeightConstant.ORIGINAL_CAPACITY, 0.0);
                            inEdge.setWeight(WeightConstant.CAPACITY, 0.0);
                        }
                        for (Edge outEdge : randomVertex.getOutEdges()) {
                            outEdge.setWeight(WeightConstant.ORIGINAL_CAPACITY, 0.0);
                            outEdge.setWeight(WeightConstant.CAPACITY, 0.0);
                        }
                    }
                }
            }
        }

        System.out.println( "----------------- Vertices attacked --------------------");
        for(Vertex v: attackedVertices){
            System.out.println("Vertex "+v.toLine());
        }

        System.out.println("------------------- Edges attacked -----------------------");
        for(Edge e: attackedEdges){
            System.out.println("Edge "+e.toLine());
        }
    }

    public static void injectAimedAttack(Graph graph, List<Integer> attackedVertexIds, List<Integer> attackedEdgeIds, Double percentageDamage){
        for(Integer id: attackedVertexIds){
            Vertex vertex= graph.getVertex(id);
            if(vertex!=null){
                if (vertex.getWeight(VertexConstant.VERTEX_DEMAND) != null && vertex.getWeight(VertexConstant.VERTEX_DEMAND) != 0) {
                    vertex.setWeight(VertexConstant.VERTEX_DEMAND, vertex.getWeight(VertexConstant.VERTEX_DEMAND) * percentageDamage);
                } else {
                    for (Edge inEdge : vertex.getInEdges()) {
                        inEdge.setWeight(WeightConstant.ORIGINAL_CAPACITY, 0.0);
                        inEdge.setWeight(WeightConstant.CAPACITY, 0.0);
                    }
                    for (Edge outEdge : vertex.getOutEdges()) {
                        outEdge.setWeight(WeightConstant.ORIGINAL_CAPACITY, 0.0);
                        outEdge.setWeight(WeightConstant.CAPACITY, 0.0);
                    }
                }
            }
        }
        for(Integer id: attackedEdgeIds){
            Edge edge = graph.getEdge(id);
            if(id!=null){
                edge.setWeight(WeightConstant.ORIGINAL_CAPACITY, edge.getWeight(WeightConstant.ORIGINAL_CAPACITY) * percentageDamage);
                edge.setWeight(WeightConstant.CAPACITY, edge.getWeight(WeightConstant.ORIGINAL_CAPACITY));
            }
        }
        System.out.println("%%%");
    }
}

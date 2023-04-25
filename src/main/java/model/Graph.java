package model;

import constant.WeightConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import util.MatrixUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Graph {
    private String name;
    private List<Vertex> vertexes;
    private List<Edge> edges;

    private Map<String, Edge> edgeMap;
    private Map<Integer, Vertex> vertexMap;

    private double[][] adjacency;

    private double[][] capacityMatrix;
    private double[][] lengthMatrix;

    private Vertex virtualSource;
    private Vertex virtualSink;




    public void build(List<Vertex> vertexes, List<Edge> edges) {
        this.vertexes = vertexes;
        this.edges = edges;

        //MatrixUtil.checkWeights(edges);
        adjacency = new double[vertexes.size()][vertexes.size()];
        for (Vertex v : vertexes) {
            v.clearEdges();
        }

        for (Edge e : edges) {
            if(e.getId()==null){
                System.out.println("AAAA");
            }
            Vertex source = e.getSource();
            Vertex destination = e.getDestination();
            if(source==null || destination==null){
                System.out.println("GGGGG");
            }
            if (source.getEnabled() && destination.getEnabled() && adjacency[source.getId()][destination.getId()] == 0D) {
                e.setEnabled(true);
                source.addOutEdge(e);
                destination.addInEdge(e);
                adjacency[source.getId()][destination.getId()] = 1.0;
                if (e.getBidirectional() && adjacency[destination.getId()][source.getId()] == 0D) {
                    destination.addOutEdge(e);
                    source.addInEdge(e);
                    adjacency[destination.getId()][source.getId()] = 1.0;
                }
            } else {
                e.setEnabled(false);
            }

        }
        buildEdgeMap();
        buildVertexMap();

        capacityMatrix = buildCapacityMatrix();
        lengthMatrix = buildLengthMatrix();
    }

    public void build() {
        build(vertexes, edges);
    }

    public void buildEdgeMap() {
        edgeMap = new HashMap<>();
        for (Edge e : edges) {
            String key = e.getSource().getId() + "_" + e.getDestination().getId();
            edgeMap.put(key, e);
        }
    }

    public void buildVertexMap() {
        vertexMap = new HashMap<>();
        for (Vertex v : vertexes) {
            vertexMap.put(v.getId(), v);
        }
    }


    public void showAdjacencyList() {
        if (vertexes == null) {
            System.out.println("Empty graph");
        } else {
            for (Vertex v : vertexes) {
                System.out.println(v.toString());
            }
        }
    }

    public Vertex getVertex(Integer id) {
        for (Vertex v : vertexes) {
            if (v.getId() == id) {
                return v;
            }
        }
        return null;
    }

    public Edge getEdge(Integer id) {
        for (Edge e : edges) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public void showCentralities() {
        for (Vertex v : vertexes) {
            System.out.println(v.toCentralities());
        }
        for (Edge e : edges) {
            System.out.println(e.toCentralities());
        }
    }


    public double[][] buildCapacityMatrix() {

        double[][] C = new double[vertexes.size()][vertexes.size()];
        for (Edge e : edges) {
            Vertex source = e.getSource();
            Vertex destination = e.getDestination();
            if (e.getWeight(WeightConstant.CAPACITY) != null && e.getWeight(WeightConstant.CAPACITY)>0.0) {
                C[source.getId()][destination.getId()] = e.getWeight(WeightConstant.CAPACITY);
            }
        }
        return C;
    }

    public double[][] buildLengthMatrix() {

        double[][] L = new double[vertexes.size()][vertexes.size()];
        for (Edge e : edges) {
            Vertex source = e.getSource();
            Vertex destination = e.getDestination();
            if (e.getWeight(WeightConstant.LENGTH) != null) {
                L[source.getId()][destination.getId()] = e.getWeight(WeightConstant.LENGTH);
            }
        }
        return L;
    }


}

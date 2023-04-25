package model;

import constant.ValueConstant;
import constant.VertexConstant;
import exception.GraphException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vertex {
    private Integer id;
    private String code;
    private String name;
    private Boolean enabled = true;
    private List<Edge> inEdges;
    private List<Edge> outEdges;

    private Map<String, Double> weights = new HashMap<>();
    private Map<String, Double> values = new HashMap<>();

    private String type;


    public void addInEdge(Edge edge) {
        if (inEdges == null) {
            inEdges = new ArrayList<>();
        }
        inEdges.add(edge);
    }

    public void removeInEdge(Edge edge) throws GraphException {
        if (inEdges != null) {
            inEdges.remove(edge);
        } else {
            throw new GraphException("cannot remove edge from an empty list");
        }
    }

    public void addOutEdge(Edge edge) {
        if (outEdges == null) {
            outEdges = new ArrayList<>();
        }
        outEdges.add(edge);
    }

    public void removeOutEdge(Edge edge) throws GraphException {
        if (outEdges != null) {
            outEdges.remove(edge);
        } else {
            throw new GraphException("cannot remove edge from an empty list");
        }
    }

    @Override
    public String toString() {
        String str = "ID: " + id + ", CODE: " + code + ", NOME: " + name + ", ENABLED: " + enabled + "\n InEdges: " + inEdges + "\n OutEdges: " + outEdges;
        return str;
    }

    public String toCentralities() {
        String str = "ID: " + id + ", CODE: " + code + ", NOME: " + name + ", ENABLED: " + enabled + ", DegreeCentrality: " + values.get(ValueConstant.DEGREE_CENTRALITY) + ", ClosenessCentrality: " + values.get(ValueConstant.CLOSENESS_CENTRALITY) + ", BetweeneessCentrality: " + values.get(ValueConstant.BETWEENNESS_CENTRALITY) + ", LaplacianCentrality: " + values.get(ValueConstant.LAPLACIAN_CENTRALITY);
        return str;
    }

    public String toLine() {
        Double demand = weights.get(VertexConstant.VERTEX_DEMAND)!=null ? weights.get(VertexConstant.VERTEX_DEMAND) : 0.0;
        String tipo = type!=null ? type: "";
        String str = id + "\t" + code + "\t" + name+"\t"+demand+"\t"+tipo;
        return str;
    }

    public void clearEdges() {
        if (inEdges != null) {
            inEdges.clear();
        }
        if (outEdges != null) {
            outEdges.clear();
        }
    }

    public void setValue(String name, Double value) {
        values.put(name, value);
    }


    public Double getValue(String name) {
        return values.get(name);
    }

    public void setWeight(String name, Double value) {
        weights.put(name, value);
    }

    public Double getWeight(String name) {
        return weights.get(name);
    }

    public Double getFlowNet(){
        return getFlowIn()-getFlowOut();
    }

    public Double getFlowIn(){
        Double flowIn=0.0;
        if(inEdges!=null) {
            for (Edge edge : inEdges) {
                if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) == null) {
                    edge.setValue(ValueConstant.CUMULATIVE_FLOW, 0.0);
                }
                flowIn += edge.getValue(ValueConstant.CUMULATIVE_FLOW);
            }
        }
        return flowIn;
    }

    public Double getFlowOut(){
        Double flowOut=0.0;
        if(outEdges!=null) {
            for (Edge edge : outEdges) {
                Vertex destination = edge.getDestination();
                if(!destination.getName().equalsIgnoreCase("Virtual_Sink")) {
                    if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) == null) {
                        edge.setValue(ValueConstant.CUMULATIVE_FLOW, 0.0);
                    }
                    flowOut += edge.getValue(ValueConstant.CUMULATIVE_FLOW);
                }
            }
        }
        return flowOut;
    }
}

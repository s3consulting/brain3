package model;

import constant.ValueConstant;
import constant.WeightConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Edge {

    private Integer id;
    private String name;
    private Boolean bidirectional = false;
    private Boolean enabled = true;
    private Vertex source;
    private Vertex destination;
    private Map<String, Double> weights = new HashMap<>();
    private Map<String, Double> values = new HashMap<>();
    private List<String> incomingPaths = new ArrayList<>();

    private String type;

    @Override
    public String toString() {
        String str = "{ID: " + id + ", NOME: " + name + ", ENABLED: " + enabled + ", SOURCE: " + source.getId() + ", DESTINATION: " + destination.getId() + "}";
        return str;
    }

    public String toCentralities() {
        String str = "{ ID: " + id + ", NOME: " + name + ", ENABLED: " + enabled + ", SOURCE: " + source.getId() + ", DESTINATION: " + destination.getId() + ", BetweeneessCentrality: " + values.get(ValueConstant.BETWEENNESS_CENTRALITY) + "}";
        return str;
    }

    public String toMaxFlow() {
        String str = "{ ID: " + id + ", NOME: " + name + ", SOURCE: " + source.getId() + ", DESTINATION: " + destination.getId() + ", CAPACITY: " + weights.get(WeightConstant.CAPACITY) + ", FLOW: " + values.get(ValueConstant.FLOW) + ", LENGTH: " + weights.get(WeightConstant.LENGTH) + "}";
        return str;
    }

    public String toLine() {
        Double ll = weights.get(WeightConstant.LENGTH) != null ? weights.get(WeightConstant.LENGTH) : 0.0;
        Double cc = weights.get(WeightConstant.CAPACITY) != null ? weights.get(WeightConstant.CAPACITY) : 0.0;
        String tipo = type!=null ? type : "";
        String str = id + "\t" + name + "\t" + source.getId() + "\t" + destination.getId() + "\t" + ll + "\t" + cc+"\t"+tipo;
        //String str=id+"\t"+name+"\t"+ source.getId()+"\t"+ destination.getId()+"\t"+length+"\t"+capacity;
        return str;
    }

    public void setWeight(String name, Double value) {
        weights.put(name, value);
    }

    public void setValue(String name, Double value) {
        values.put(name, value);
    }

    public Double getWeight(String name) {
        return weights.get(name);
    }

    public Double getValue(String name) {
        return values.get(name);
    }
}

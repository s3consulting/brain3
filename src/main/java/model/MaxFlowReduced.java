package model;

import constant.ValueConstant;
import constant.WeightConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MaxFlowReduced {

    private String key;
    private List<Edge> edges;

    public void addEdge(Edge edge) {
        if (edge.getValue(ValueConstant.FLOW) != null && edge.getValue(ValueConstant.FLOW) > 1.0E-10) {
            if (edges == null) {
                edges = new ArrayList<>();
            }
            Edge e = new Edge();
            e.setId(edge.getId());
            e.setSource(edge.getSource());
            e.setDestination(edge.getDestination());
            e.setWeight(WeightConstant.ORIGINAL_CAPACITY, edge.getWeight(WeightConstant.ORIGINAL_CAPACITY));
            e.setWeight(WeightConstant.CAPACITY, edge.getWeight(WeightConstant.CAPACITY));
            e.setWeight(WeightConstant.LENGTH, edge.getWeight(WeightConstant.LENGTH));
            e.setValue(ValueConstant.FLOW, edge.getValue(ValueConstant.FLOW));
            e.setValue(ValueConstant.CUMULATIVE_FLOW, edge.getValue(ValueConstant.CUMULATIVE_FLOW));
            edges.add(e);
        }
    }

    public void build(MaxFlowResponse maxFlowResponse){
        for(Edge e: maxFlowResponse.getEdgesMaxFlow()){
            addEdge(e);
        }
    }

}

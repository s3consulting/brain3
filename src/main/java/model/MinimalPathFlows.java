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

@AllArgsConstructor

@Setter
@Getter
public class MinimalPathFlows {
    private Integer index;
    Map<Integer, List<Edge>> edgePaths = new HashMap<>();

    Map<String, List<List<Edge>>> edgePathsForClosestSink = new HashMap<>();

    public MinimalPathFlows(){
        index=0;
    }

    public void addPath(List<Edge> edges){
        //duplicate edge
        List<Edge> edges1 = new ArrayList<>();

        for(Edge edge: edges){
            Edge e = new Edge();
            e.setId(edge.getId());
            e.setName(edge.getName());
            e.setBidirectional(false);
            e.setDestination(edge.getDestination());
            e.setSource(edge.getSource());

            e.setWeight(WeightConstant.ORIGINAL_CAPACITY, edge.getWeight(WeightConstant.ORIGINAL_CAPACITY));
            e.setWeight(WeightConstant.CAPACITY, edge.getWeight(WeightConstant.CAPACITY));
            e.setWeight(WeightConstant.LENGTH, edge.getWeight(WeightConstant.LENGTH));

            e.setValue(ValueConstant.FLOW, edge.getValue(ValueConstant.FLOW));
            e.setValue(ValueConstant.CUMULATIVE_FLOW, edge.getValue(ValueConstant.CUMULATIVE_FLOW));
            edges1.add(e);
        }
        edgePaths.put(index, edges1);
        index++;
    }

    public void addPathForClosestSink(String key, List<Edge> edges){
        //duplicate edge
        List<Edge> edges1 = new ArrayList<>();

        for(Edge edge: edges){
            Edge e = new Edge();
            e.setId(edge.getId());
            e.setName(edge.getName());
            e.setBidirectional(false);
            e.setDestination(edge.getDestination());
            e.setSource(edge.getSource());

            e.setWeight(WeightConstant.ORIGINAL_CAPACITY, edge.getWeight(WeightConstant.ORIGINAL_CAPACITY));
            e.setWeight(WeightConstant.CAPACITY, edge.getWeight(WeightConstant.CAPACITY));
            e.setWeight(WeightConstant.LENGTH, edge.getWeight(WeightConstant.LENGTH));

            e.setValue(ValueConstant.FLOW, edge.getValue(ValueConstant.FLOW));
            e.setValue(ValueConstant.CUMULATIVE_FLOW, edge.getValue(ValueConstant.CUMULATIVE_FLOW));
            edges1.add(e);
        }
        if(edgePathsForClosestSink.get(key)==null){
            List<List<Edge>> paths = new ArrayList<>();
            edgePathsForClosestSink.put(key, paths);
        }
        edgePathsForClosestSink.get(key).add(edges1);
    }
}

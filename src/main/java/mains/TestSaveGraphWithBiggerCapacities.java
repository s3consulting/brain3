package mains;

import constant.WeightConstant;
import model.Graph;
import util.FileSystemUtil;

import java.io.IOException;

public class TestSaveGraphWithBiggerCapacities {

    public static void main(String[] args) throws IOException {

        String dir = "/Users/cristianocimino/NetBeansProjects/generic-graph/GRAPHS";

        Graph graph = PraksCaseStudyGraphBuilder.graphBuilderCase_G();
        String graphName = graph.getName()+"_BIGGER_CAPACITIES";
        graph.setName(graphName);
        graph.getEdgeMap().get("8_7").setWeight(WeightConstant.CAPACITY, 10.0);
        graph.getEdgeMap().get("7_8").setWeight(WeightConstant.CAPACITY, 10.0);
        graph.getEdgeMap().get("7_6").setWeight(WeightConstant.CAPACITY, 10.0);
        graph.getEdgeMap().get("6_7").setWeight(WeightConstant.CAPACITY, 10.0);
        graph.getEdgeMap().get("8_7").setWeight(WeightConstant.ORIGINAL_CAPACITY, 10.0);
        graph.getEdgeMap().get("7_8").setWeight(WeightConstant.ORIGINAL_CAPACITY, 10.0);
        graph.getEdgeMap().get("7_6").setWeight(WeightConstant.ORIGINAL_CAPACITY, 10.0);
        graph.getEdgeMap().get("6_7").setWeight(WeightConstant.ORIGINAL_CAPACITY, 10.0);

        FileSystemUtil.writeGraphToFile(graph, graphName, dir);
    }
}

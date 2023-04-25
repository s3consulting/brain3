package mains;

import constant.ValueConstant;
import constant.VertexConstant;
import constant.WeightConstant;
import model.*;
import util.DijkstraUtil;
import util.FordFulkersonUtil;
import util.GenericUtil;
import util.GraphUtil;

import java.util.HashMap;
import java.util.Map;

public class TestGasNetworkWithCapacitiesAndFlowsUpadates {

    public static void main(String[] args){

        Graph graph = GasGraphBuilder.GasNetworkEsercizio1();
        Graph augmentedGraph = GraphUtil.addVirtualSourceAndSink(graph);


    }
}

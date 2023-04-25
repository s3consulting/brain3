package mains;

import model.Graph;
import model.MaxFlowResponse;
import util.FordFulkersonUtil;
import util.MatrixUtil;

public class TestMaxFlow {
    public static void main(String[] args){
        //Graph graph = GraphBuilder.createGraphMaxFlow6X6();
        //Graph graph = GraphBuilder.createGraphMaxFlow6X6__YouTube();
        Graph graph = GraphBuilder.createGraphMaxFlow4X4__Esercizio_capacity();
        double[][] L = graph.getLengthMatrix();
        MatrixUtil.print(L);

        double[][] C = graph.getCapacityMatrix();
        MatrixUtil.print(C);



        MaxFlowResponse maxFlowResponse = FordFulkersonUtil.maxFlow(graph, 0, 3);
        System.out.println("Max Flow: "+maxFlowResponse.getMaxFlow());
    }
}

package mains;

import constant.WeightConstant;
import model.Graph;
import model.MaxFlowResponse;
import model.Vertex;
import util.DijkstraUtil;
import util.FordFulkersonUtil;
import util.GraphUtil;
import util.MatrixUtil;

public class TestDijkstra {
    public static void main(String[] args){
        //Graph graph = GraphBuilder.createGraphMST_7X7();

        //Graph graph = GraphBuilder.createGraph5X5();

        //Graph graph = GasGraphBuilder.GasNetworkEsercizio();

        Graph graph = CasiParticolariGraphBuilder.casoParticolareAlgoritmo2();

        Graph ag = GraphUtil.addVirtualSourceAndSink(graph);

        //DijkstraUtil.findMultipleShortestPaths(MatrixUtil.graph2double(graph, WeightConstant.LENGTH), 0, 4);

        //DijkstraUtil.findMultipleShortestPaths(MatrixUtil.graph2double(graph, WeightConstant.LENGTH), 0, 3);

        //DijkstraUtil.findMultipleShortestPaths(MatrixUtil.graph2double(graph, WeightConstant.CAPACITY), 0, 4);

        //DijkstraUtil.findMultipleShortestPaths(MatrixUtil.graph2double(graph, WeightConstant.CAPACITY), 0, 3);


        DijkstraUtil.findMultipleShortestPaths(graph, 0, 6);

        //DijkstraUtil.findMultipleShortestPathsWithEdges(ag, ag.getVirtualSource().getId(), ag.getVirtualSink().getId());

        //MaxFlowResponse maxFlowResponse = FordFulkersonUtil.maxFlow(graph, 0, 3);
        //System.out.println("Max Flow: "+maxFlowResponse.getMaxFlow());


        for(Vertex v: graph.getVertexes()){
            DijkstraUtil.findMultipleShortestPathsWithEdges(ag, ag.getVirtualSource().getId(), v.getId());
        }
        System.out.println("END");
    }
}

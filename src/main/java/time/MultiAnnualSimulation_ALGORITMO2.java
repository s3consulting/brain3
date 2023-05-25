package time;

import model.*;
import util.FileSystemUtil;
import util.GraphUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MultiAnnualSimulation_ALGORITMO2 {
    public static void main(String[] args) throws IOException {
        //String dir = args[0];
        //String outDir = args[1];
        //Integer nIterazioni = Integer.valueOf(args[2]);
        //String graphName = args[3];

        Integer numberOfDays = 365;
        String algoritmo="ALGORITMO_2";

        String outDir = "/Users/cristianocimino/NetBeansProjects/generic-graph/MULTI_ANNUAL";
        Integer nIterazioni =10;
        String dir = "/Users/cristianocimino/NetBeansProjects/generic-graph/GRAPHS";
        String graphName = "PRAKS_GRAPH_CASE_G";

        Graph graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);;

        Map<Integer, List<Double>> sourceFailurePerYear = AnnualSimulationUtil.generateFailureOnSourcePerYear(graph);
        Map<String, List<Double>> pipelineFailurePerYear = AnnualSimulationUtil.generateFailureOnPipelinePerYear(graph);

        List<Vertex> sourcesWithFailure = new ArrayList<>();
        List<Edge> failedEdges = new ArrayList<>();
        List<Edge> arcsWithReducedCapacity = new ArrayList<>();
        Brain3SimulatorALGORITMO2 brain3SimulatorALGORITMO2 = new Brain3SimulatorALGORITMO2(graph);
        Graph augmentedGraph = brain3SimulatorALGORITMO2.getAugmentedGraph();
        CumulativeAdjacencyMatrix cumulativeAdjacencyMatrix = new CumulativeAdjacencyMatrix(augmentedGraph.getVertexes().size());
        String mask = GraphUtil.createFileMask();
        String adjacencyOutDir = outDir+"/"+graphName+"_"+algoritmo+"_ADJACENCY_"+mask;
        for(int index=0; index<nIterazioni; index++) {
            sourcesWithFailure = new ArrayList<>();
            failedEdges = new ArrayList<>();
            arcsWithReducedCapacity = new ArrayList<>();

            sourceFailurePerYear = AnnualSimulationUtil.generateFailureOnSourcePerYear(graph);
            pipelineFailurePerYear = AnnualSimulationUtil.generateFailureOnPipelinePerYear(graph);
            for(int day=0; day<numberOfDays; day++){
                graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);
                graph.setName(graphName);
                brain3SimulatorALGORITMO2 = new Brain3SimulatorALGORITMO2(graph);

                augmentedGraph = brain3SimulatorALGORITMO2.getAugmentedGraph();

                String updateFixedElementsTxt = AnnualSimulationUtil.updateFixedElements(augmentedGraph, day, sourcesWithFailure, failedEdges, arcsWithReducedCapacity);


                String propagateFailuresTxt = AnnualSimulationUtil.propagateFailures(augmentedGraph, day, sourcesWithFailure, arcsWithReducedCapacity, failedEdges);


                String injectFailureToSourceTxt = AnnualSimulationUtil.injectFailureToSource(augmentedGraph, day, sourceFailurePerYear, sourcesWithFailure, arcsWithReducedCapacity, failedEdges);


                String injectFailureToPipelineTxt = AnnualSimulationUtil.injectFailureToPipeline(augmentedGraph, day, pipelineFailurePerYear, failedEdges);


                brain3SimulatorALGORITMO2.execute();


                Double totalDemand = GraphUtil.getTotalDemand(augmentedGraph);
                Double netSinkFlow = GraphUtil.getNetFlowOnSinks(augmentedGraph);

                cumulativeAdjacencyMatrix.updateMatrix(augmentedGraph.buildCumulativeFlowAdjacencyMatrix(), day);
                System.out.println("DAY "+day+", ITERAZIONE "+index);
                //cumulativeAdjacencyMatrix.showMatrixAtDay(day);

            }
            System.out.println("END ANNUAL");
        }
        double[][][] averageAjacencyMatrix = cumulativeAdjacencyMatrix.arithmeticAverage(nIterazioni);
        System.out.println("END");
        OutputFormatterUtil.writeAverageAdjacencyMatrix(adjacencyOutDir, averageAjacencyMatrix, numberOfDays);
    }

}

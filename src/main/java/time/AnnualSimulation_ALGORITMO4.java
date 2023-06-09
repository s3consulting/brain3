package time;

import exception.GraphException;
import model.Brain3SimulatorALGORITMO4;
import model.*;
import util.FileObject;
import util.FileSystemUtil;
import util.GraphUtil;

import java.io.IOException;
import java.util.*;

public class AnnualSimulation_ALGORITMO4 {

    public static void main(String[] args) throws IOException, GraphException {

        AnnualSimulationUtil.checkParametersAnnualSimulation(args, 3);

        String dir = args[0];
        String outDir = args[1];
        String graphName = args[2];


        String algoritmo="ALGORITMO_4";

        int numberOfSamples = 365;
        int summation = 1;

        //String dir = "/Users/cristianocimino/NetBeansProjects/generic-graph/GRAPHS";
        //String graphName = "PraksGasGraph";
        //String graphName = "PraksGasGraph_MAX_CAP";
        //String graphName = "PRAKS_GRAPH_CASE_A";
        //String graphName = "PRAKS_GRAPH_CASE_B";
        //String graphName = "PRAKS_GRAPH_CASE_C";
        //String graphName = "PRAKS_GRAPH_CASE_D";
        //String graphName = "PRAKS_GRAPH_CASE_E";
        //String graphName = "PRAKS_GRAPH_CASE_F";
        //String graphName = "PRAKS_GRAPH_CASE_G";

        Graph graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);

        Map<String, Map<String, Double>> CDF = new HashMap<>();

        Map<Integer, List<Double>> sourceFailurePerYear = IntrinsicFaultInjector.generateFailureOnSourcePerYear(graph);
        Map<String, List<Double>> pipelineFailurePerYear = IntrinsicFaultInjector.generateFailureOnPipelinePerYear(graph);

        List<Vertex> sourcesWithFailure = new ArrayList<>();
        List<Edge> failedEdges = new ArrayList<>();
        List<Edge> arcsWithReducedCapacity = new ArrayList<>();


        Graph augmentedGraph;
        //String outDir = "/Users/cristianocimino/NetBeansProjects/generic-graph/SIMULATION_ANNUAL";
        String mask = GraphUtil.createFileMask();
        String cdfOutDir = outDir+"/"+graphName+"_"+algoritmo+"_CDF_"+mask;
        String adjacencyOutputDir = outDir+"/"+graphName+"_"+algoritmo+"_ADJACENCY_"+mask;

        List<Double> gasReceivedPercentage = new ArrayList<>();
        List<Double> satisfiedSinksPercentage = new ArrayList<>();

        FileObject fileObject = FileSystemUtil.openFileObject(outDir, graphName+"_"+algoritmo);
        for (int day = 0; day < numberOfSamples; day++) {
            System.out.println("DAY: " + day);
            graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);
            graph.setName(graphName);
            Brain3SimulatorALGORITMO4 brain3SimulatorALGORITMO4 = new Brain3SimulatorALGORITMO4(graph);

            augmentedGraph = brain3SimulatorALGORITMO4.getAugmentedGraph();
            fileObject.getBw().write("\n---------------------------------- DAY " + day + "\n");


            String updateFixedElementsTxt = IntrinsicFaultInjector.updateFixedElements(augmentedGraph, day, sourcesWithFailure, failedEdges, arcsWithReducedCapacity);
            fileObject.getBw().write(updateFixedElementsTxt);

            String propagateFailuresTxt = IntrinsicFaultInjector.propagateFailures(augmentedGraph, day, sourcesWithFailure, arcsWithReducedCapacity, failedEdges);
            fileObject.getBw().write(propagateFailuresTxt);

            String injectFailureToSourceTxt = IntrinsicFaultInjector.injectFailureToSource(augmentedGraph, day, sourceFailurePerYear, sourcesWithFailure, arcsWithReducedCapacity, failedEdges);
            fileObject.getBw().write(injectFailureToSourceTxt);

            String injectFailureToPipelineTxt = IntrinsicFaultInjector.injectFailureToPipeline(augmentedGraph, day, pipelineFailurePerYear, failedEdges, arcsWithReducedCapacity);
            fileObject.getBw().write(injectFailureToPipelineTxt);


            brain3SimulatorALGORITMO4.execute();
            GraphUtil.showRealDestinationsOrdered(graph);


            fileObject.getBw().write(GraphUtil.getResultsAsString(brain3SimulatorALGORITMO4.getAugmentedGraph()));

            Double totalDemand = GraphUtil.getTotalDemand(augmentedGraph);
            Double netSinkFlow = GraphUtil.getNetFlowOnSinks(augmentedGraph);
            Double numberOfSatisfiedSink = GraphUtil.getNumberOfSatisfiedSinks(augmentedGraph);
            Double numberOfSink = GraphUtil.getNumberOfSinks(augmentedGraph);

            GraphUtil.createCumulativeDistribution(CDF, augmentedGraph, totalDemand, netSinkFlow);

            gasReceivedPercentage.add(netSinkFlow/totalDemand);
            satisfiedSinksPercentage.add(numberOfSatisfiedSink/numberOfSink);
            System.out.println("-------");
            GraphUtil.updateFlowOnArcsToVirtualDestination(augmentedGraph);
            OutputFormatterUtil.writeAdjacencyMatrix(adjacencyOutputDir,day, augmentedGraph);


        }
        FileSystemUtil.closeFileObject(fileObject);
        GraphUtil.writeCDFonFile(CDF, cdfOutDir);
        GraphUtil.writeStatisticOnFile(gasReceivedPercentage, satisfiedSinksPercentage, cdfOutDir);

        System.out.println("END");
    }
}

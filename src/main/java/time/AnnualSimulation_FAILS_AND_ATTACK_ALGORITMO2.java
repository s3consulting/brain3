package time;

import exception.GraphException;
import model.Brain3SimulatorALGORITMO2;
import model.Edge;
import model.Graph;
import model.Vertex;
import util.FileObject;
import util.FileSystemUtil;
import util.GraphUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnualSimulation_FAILS_AND_ATTACK_ALGORITMO2 {
    public static void main(String[] args) throws GraphException, IOException {
        //AnnualSimulationUtil.checkParametersAnnualSimulation(args, 3);
        //String dir = args[0];
        //String outDir = args[1];
        //String graphName = args[2];


        String algoritmo="ALGORITMO_2";

        int numberOfSamples = 365;
        int summation = 1;

        String dir = "/Users/cristianocimino/NetBeansProjects/generic-graph/GRAPHS";
        String graphName = "PRAKS_GRAPH_CASE_G";
        String outDir = "/Users/cristianocimino/NetBeansProjects/generic-graph/SIMULATION_ANNUAL_FAILS_ATTACK";

        Graph graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);

        Map<String, Map<String, Double>> CDF = new HashMap<>();

        Map<Integer, List<Double>> sourceFailurePerYear = AnnualSimulationUtil.generateFailureOnSourcePerYear(graph);
        Map<String, List<Double>> pipelineFailurePerYear = AnnualSimulationUtil.generateFailureOnPipelinePerYear(graph);

        List<Vertex> sourcesWithFailure = new ArrayList<>();
        List<Edge> failedEdges = new ArrayList<>();
        List<Edge> arcsWithReducedCapacity = new ArrayList<>();

        List<Vertex> sourceUnderAttack = new ArrayList<>();
        List<Edge> pipelinesWithReducedCapacityDueToAttack = new ArrayList<>();
        List<Edge> pipelinesUnderAttack = new ArrayList<>();

        Graph augmentedGraph;

        String mask = GraphUtil.createFileMask();
        String cdfOutDir = outDir+"/"+graphName+"_"+algoritmo+"_CDF_"+mask;
        String adjacencyOutputDir = outDir+"/"+graphName+"_"+algoritmo+"_ADJACENCY_"+mask;

        List<Double> gasReceivedPercentage = new ArrayList<>();
        List<Double> satisfiedSinksPercentage = new ArrayList<>();
        FileObject fileObject = FileSystemUtil.openFileObject(outDir, graphName+"_"+algoritmo);

        String attackDir = "/Users/cristianocimino/NetBeansProjects/generic-graph/TELEGRAM/";
        String attackFile = "attacks.csv";
        ExternalAttackKeeper externalAttackKeeper= new ExternalAttackKeeper(2);
        externalAttackKeeper.loadAttackFromFile(attackDir, attackFile);

        for (int day = 0; day < numberOfSamples; day++) {
            System.out.println("DAY: " + day);
            graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);
            graph.setName(graphName);
            Brain3SimulatorALGORITMO2 brain3SimulatorALGORITMO2 = new Brain3SimulatorALGORITMO2(graph);

            augmentedGraph = brain3SimulatorALGORITMO2.getAugmentedGraph();
            fileObject.getBw().write("\n---------------------------------- DAY " + day + "\n");

            String updateFixedElementFromAttackTxt = ExternalAttackInjector.updateFixedElements(augmentedGraph, day, sourceUnderAttack, pipelinesUnderAttack, pipelinesWithReducedCapacityDueToAttack);
            fileObject.getBw().write(updateFixedElementFromAttackTxt);

            String propagateAttackTxt = ExternalAttackInjector.propagateAttacks(augmentedGraph, day, sourceUnderAttack, pipelinesUnderAttack, pipelinesWithReducedCapacityDueToAttack);
            fileObject.getBw().write(propagateAttackTxt);

            String updateFixedElementsTxt = AnnualSimulationUtil.updateFixedElements(augmentedGraph, day, sourcesWithFailure, failedEdges, arcsWithReducedCapacity);
            fileObject.getBw().write(updateFixedElementsTxt);

            String propagateFailuresTxt = AnnualSimulationUtil.propagateFailures(augmentedGraph, day, sourcesWithFailure, arcsWithReducedCapacity, failedEdges);
            fileObject.getBw().write(propagateFailuresTxt);

            String injectFailureToSourceTxt = AnnualSimulationUtil.injectFailureToSource(augmentedGraph, day, sourceFailurePerYear, sourcesWithFailure, arcsWithReducedCapacity, failedEdges);
            fileObject.getBw().write(injectFailureToSourceTxt);

            String injectFailureToPipelineTxt = AnnualSimulationUtil.injectFailureToPipeline(augmentedGraph, day, pipelineFailurePerYear, failedEdges);
            fileObject.getBw().write(injectFailureToPipelineTxt);

            String injectAttackToSourcesTxt = ExternalAttackInjector.injectAttackToSource(augmentedGraph, day, externalAttackKeeper, sourceUnderAttack, sourcesWithFailure, pipelinesUnderAttack, pipelinesWithReducedCapacityDueToAttack, arcsWithReducedCapacity, failedEdges);
            fileObject.getBw().write(injectAttackToSourcesTxt);

            String injectAttackToPipelineTxt = ExternalAttackInjector.injectAttackToPipeline(augmentedGraph, day, externalAttackKeeper, pipelinesUnderAttack, pipelinesWithReducedCapacityDueToAttack, arcsWithReducedCapacity, failedEdges);
            fileObject.getBw().write(injectAttackToPipelineTxt);


            brain3SimulatorALGORITMO2.execute();
            GraphUtil.showRealDestinationsOrdered(graph);


            fileObject.getBw().write(GraphUtil.getResultsAsString(brain3SimulatorALGORITMO2.getAugmentedGraph()));

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

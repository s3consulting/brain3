package time;

import exception.GraphException;
import model.*;
import util.FileObject;
import util.FileSystemUtil;
import util.GraphUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnualSimulation_INTRINSICFAULTS_AND_ATTACKS_ALGORITMO4 {

    public static void main(String[] args) throws GraphException, IOException {
        AnnualSimulationUtil.checkParametersAnnualSimulation(args, 4);
        String dir = args[0];
        String outDir = args[1];
        String graphName = args[2];
        String attackDir = args[3];


        String algoritmo="ALGORITMO_4";

        int numberOfSamples = 365;
        int summation = 1;

        //String dir = "/Users/cristianocimino/NetBeansProjects/generic-graph/GRAPHS";
        //String graphName = "PRAKS_GRAPH_CASE_G";
        //String outDir = "/Users/cristianocimino/NetBeansProjects/generic-graph/SIMULATION_ANNUAL_FAILS_ATTACK";
        //String attackDir = "/Users/cristianocimino/NetBeansProjects/generic-graph/TELEGRAM/";

        String attackFile = "attacks.csv";

        Graph graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);

        Map<String, Map<String, Double>> CDF = new HashMap<>();

        Map<Integer, List<Double>> sourceFailurePerYear = IntrinsicFaultInjector.generateFailureOnSourcePerYear(graph);
        Map<String, List<Double>> pipelineFailurePerYear = IntrinsicFaultInjector.generateFailureOnPipelinePerYear(graph);

        List<Vertex> sourcesWithIntrinsicFailure = new ArrayList<>();
        List<Edge> arcsWithIntrinsicFailure = new ArrayList<>();
        List<Edge> arcsWithReducedCapacityDueToIntrinsicFailure = new ArrayList<>();

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



        ExternalAttackKeeper externalAttackKeeper= new ExternalAttackKeeper(2);
        externalAttackKeeper.loadAttackFromFile(attackDir, attackFile);

        for (int day = 0; day < numberOfSamples; day++) {
            System.out.println("DAY: " + day);
            graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);
            graph.setName(graphName);
            Brain3SimulatorALGORITMO4 brain3SimulatorALGORITMO4 = new Brain3SimulatorALGORITMO4(graph);

            augmentedGraph = brain3SimulatorALGORITMO4.getAugmentedGraph();
            fileObject.getBw().write("\n---------------------------------- DAY " + day + "\n");

            String updateFixedElementFromAttackTxt = ExternalAttackInjector.updateFixedElements(augmentedGraph, day, sourceUnderAttack, pipelinesUnderAttack, pipelinesWithReducedCapacityDueToAttack);
            fileObject.getBw().write(updateFixedElementFromAttackTxt);

            String propagateAttackTxt = ExternalAttackInjector.propagateAttacks(augmentedGraph, day, sourceUnderAttack, pipelinesUnderAttack, pipelinesWithReducedCapacityDueToAttack);
            fileObject.getBw().write(propagateAttackTxt);

            String updateFixedElementsTxt = IntrinsicFaultInjector.updateFixedElements(augmentedGraph, day, sourcesWithIntrinsicFailure, arcsWithIntrinsicFailure, arcsWithReducedCapacityDueToIntrinsicFailure);
            fileObject.getBw().write(updateFixedElementsTxt);

            String propagateFailuresTxt = IntrinsicFaultInjector.propagateFailures(augmentedGraph, day, sourcesWithIntrinsicFailure, arcsWithReducedCapacityDueToIntrinsicFailure, arcsWithIntrinsicFailure);
            fileObject.getBw().write(propagateFailuresTxt);

            String injectFailureToSourceTxt = IntrinsicFaultInjector.injectFailureToSource(augmentedGraph, day, sourceFailurePerYear, sourcesWithIntrinsicFailure, arcsWithReducedCapacityDueToIntrinsicFailure, arcsWithIntrinsicFailure);
            fileObject.getBw().write(injectFailureToSourceTxt);

            String injectFailureToPipelineTxt = IntrinsicFaultInjector.injectFailureToPipeline(augmentedGraph, day, pipelineFailurePerYear, arcsWithIntrinsicFailure, arcsWithReducedCapacityDueToIntrinsicFailure);
            fileObject.getBw().write(injectFailureToPipelineTxt);

            String injectAttackToSourcesTxt = ExternalAttackInjector.injectAttackToSource(augmentedGraph, day, externalAttackKeeper, sourceUnderAttack, sourcesWithIntrinsicFailure, pipelinesUnderAttack, pipelinesWithReducedCapacityDueToAttack, arcsWithReducedCapacityDueToIntrinsicFailure, arcsWithIntrinsicFailure);
            fileObject.getBw().write(injectAttackToSourcesTxt);

            String injectAttackToPipelineTxt = ExternalAttackInjector.injectAttackToPipeline(augmentedGraph, day, externalAttackKeeper, pipelinesUnderAttack, pipelinesWithReducedCapacityDueToAttack, arcsWithReducedCapacityDueToIntrinsicFailure, arcsWithIntrinsicFailure);
            fileObject.getBw().write(injectAttackToPipelineTxt);


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

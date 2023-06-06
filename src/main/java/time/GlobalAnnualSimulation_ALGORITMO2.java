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

public class GlobalAnnualSimulation_ALGORITMO2 {

    public static void main(String[] args) throws GraphException, IOException {
        //AnnualSimulationUtil.checkParametersAnnualSimulation(args, 4);
        //String dir = args[0];
        //String outDir = args[1];
        //String graphName = args[2];
        //String attackDir = args[3];


        String algoritmo="ALGORITMO_2";

        int numberOfSamples = 365;
        int summation = 1;

        String dir = "/Users/cristianocimino/NetBeansProjects/generic-graph/GRAPHS";
        String graphName = "PRAKS_GRAPH_CASE_G";
        String outDir = "/Users/cristianocimino/NetBeansProjects/generic-graph/SIMULATION_ANNUAL_GLOBAL";
        String attackDir = "/Users/cristianocimino/NetBeansProjects/generic-graph/TELEGRAM/";
        String heavyFaultDir = "/Users/cristianocimino/NetBeansProjects/generic-graph/HEAVY_FAULT/";

        String attackFile = "attacks.csv";
        String heavyFaultFile = "heavyFaults.csv";

        Graph graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);

        Map<String, Map<String, Double>> CDF = new HashMap<>();

        Map<Integer, List<Double>> sourceFailurePerYear = IntrinsicFaultInjector.generateFailureOnSourcePerYear(graph);
        Map<String, List<Double>> pipelineFailurePerYear = IntrinsicFaultInjector.generateFailureOnPipelinePerYear(graph);

        List<Vertex> sourcesWithIntrinsicFault = new ArrayList<>();
        List<Edge> pipelinesWithIntrinsicFault = new ArrayList<>();
        List<Edge> arcsWithReducedCapacityDueToIntrinsicFault = new ArrayList<>();

        List<Vertex> sourcesWithHeavyFault = new ArrayList<>();
        List<Edge> pipelinesWithHeavyFault = new ArrayList<>();
        List<Edge> arcsWithReducedCapacityDueToHeavyFault = new ArrayList<>();

        List<Vertex> sourceUnderAttack = new ArrayList<>();
        List<Edge> arcsWithReducedCapacityDueToAttack = new ArrayList<>();
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

        HeavyFaultKeeper heavyFaultKeeper = new HeavyFaultKeeper();
        heavyFaultKeeper.loadHeavyFaultsFromFile(heavyFaultDir, heavyFaultFile);


        String updateFixedElementFromAttackTxt;
        String propagateAttackTxt;
        String injectAttackToSourcesTxt;
        String injectAttackToPipelinesTxt;

        String updateFixedElementsTxtFromIntrinsicFaultTxt;
        String propagateIntrinsicFaultTxt;
        String injectIntrinsicFaultToSourcesTxt;
        String injectIntrinsicFaultToPipelinesTxt;

        String updateFixedElementsTxtFromHeavyFaultTxt;
        String propagateHeavyFaultTxt;
        String injectHeavyFaultToSourcesTxt;
        String injectHeavyFaultToPipelinesTxt;

        String statistics;


        for (int day = 0; day < numberOfSamples; day++) {
            System.out.println("DAY: " + day);
            graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);
            graph.setName(graphName);
            Brain3SimulatorALGORITMO2 brain3SimulatorALGORITMO2 = new Brain3SimulatorALGORITMO2(graph);

            augmentedGraph = brain3SimulatorALGORITMO2.getAugmentedGraph();
            fileObject.getBw().write("\n---------------------------------- DAY " + day + "\n");

            updateFixedElementsTxtFromIntrinsicFaultTxt = IntrinsicFaultGenerator.updateFixedElement(augmentedGraph, day, sourcesWithIntrinsicFault, arcsWithReducedCapacityDueToIntrinsicFault, pipelinesWithIntrinsicFault);
            fileObject.getBw().write(updateFixedElementsTxtFromIntrinsicFaultTxt);

            updateFixedElementsTxtFromHeavyFaultTxt = HeavyFaultGenerator.updateFixedElement(augmentedGraph, day, sourcesWithHeavyFault, arcsWithReducedCapacityDueToHeavyFault, pipelinesWithHeavyFault);
            fileObject.getBw().write(updateFixedElementsTxtFromHeavyFaultTxt);

            updateFixedElementFromAttackTxt = ExternalAttackGenerator.updateFixedElement(augmentedGraph, day, sourceUnderAttack, arcsWithReducedCapacityDueToAttack, pipelinesUnderAttack);
            fileObject.getBw().write(updateFixedElementFromAttackTxt);

            propagateIntrinsicFaultTxt = IntrinsicFaultGenerator.propagateIntrinsicFaults(augmentedGraph, sourcesWithIntrinsicFault, arcsWithReducedCapacityDueToIntrinsicFault, pipelinesWithIntrinsicFault);
            fileObject.getBw().write(propagateIntrinsicFaultTxt);

            propagateHeavyFaultTxt = HeavyFaultGenerator.propagateHeavyFaults(augmentedGraph, sourcesWithHeavyFault, arcsWithReducedCapacityDueToHeavyFault, pipelinesWithHeavyFault);
            fileObject.getBw().write(propagateHeavyFaultTxt);

            propagateAttackTxt = ExternalAttackGenerator.propagateAttacks(augmentedGraph, sourceUnderAttack, arcsWithReducedCapacityDueToAttack, pipelinesUnderAttack);
            fileObject.getBw().write(propagateAttackTxt);

            injectIntrinsicFaultToSourcesTxt = IntrinsicFaultGenerator.injectIntrinsicFaultToSource(augmentedGraph, day, sourceFailurePerYear, sourcesWithIntrinsicFault, arcsWithReducedCapacityDueToIntrinsicFault);
            fileObject.getBw().write(injectIntrinsicFaultToSourcesTxt);

            injectHeavyFaultToSourcesTxt = HeavyFaultGenerator.injectHeavyFailureToSource(augmentedGraph, day, heavyFaultKeeper, sourcesWithHeavyFault, arcsWithReducedCapacityDueToHeavyFault);
            fileObject.getBw().write(injectHeavyFaultToSourcesTxt);

            injectAttackToSourcesTxt = ExternalAttackGenerator.injectAttackToSource(augmentedGraph, day, externalAttackKeeper, sourceUnderAttack, arcsWithReducedCapacityDueToAttack);
            fileObject.getBw().write(injectAttackToSourcesTxt);

            injectIntrinsicFaultToPipelinesTxt = IntrinsicFaultGenerator.injectIntrinsicFaultToPipeline(augmentedGraph, day, pipelineFailurePerYear, pipelinesWithIntrinsicFault);
            fileObject.getBw().write(injectIntrinsicFaultToPipelinesTxt);

            injectHeavyFaultToPipelinesTxt = HeavyFaultGenerator.injectHeavyFaultToPipeline(augmentedGraph, day, heavyFaultKeeper, pipelinesWithHeavyFault);
            fileObject.getBw().write(injectHeavyFaultToPipelinesTxt);

            injectAttackToPipelinesTxt = ExternalAttackGenerator.injectAttackToPipeline(augmentedGraph, day, externalAttackKeeper, pipelinesUnderAttack);
            fileObject.getBw().write(injectAttackToPipelinesTxt);

            statistics = AnnualSimulationUtil.getStatistics(sourceUnderAttack, sourcesWithHeavyFault, sourcesWithIntrinsicFault, arcsWithReducedCapacityDueToAttack, pipelinesUnderAttack, arcsWithReducedCapacityDueToHeavyFault, pipelinesWithHeavyFault, arcsWithReducedCapacityDueToIntrinsicFault, pipelinesWithIntrinsicFault);
            fileObject.getBw().write(statistics);

            /*
            String updateFixedElementFromAttackTxt = ExternalAttackInjector.updateFixedElements(augmentedGraph, day, sourceUnderAttack, pipelinesUnderAttack, arcsWithReducedCapacityDueToAttack);
            fileObject.getBw().write(updateFixedElementFromAttackTxt);

            String propagateAttackTxt = ExternalAttackInjector.propagateAttacks(augmentedGraph, day, sourceUnderAttack, pipelinesUnderAttack, arcsWithReducedCapacityDueToAttack);
            fileObject.getBw().write(propagateAttackTxt);

            String updateFixedElementsTxt = IntrinsicFaultInjector.updateFixedElements(augmentedGraph, day, sourcesWithHeavyFault, pipelinesWithIntrinsicFault, arcsWithReducedCapacityDueToIntrinsicFault);
            fileObject.getBw().write(updateFixedElementsTxt);

            String propagateFailuresTxt = IntrinsicFaultInjector.propagateFailures(augmentedGraph, day, sourcesWithHeavyFault, arcsWithReducedCapacityDueToIntrinsicFailure, arcsWithIntrinsicFailure);
            fileObject.getBw().write(propagateFailuresTxt);

            String injectFailureToSourceTxt = IntrinsicFaultInjector.injectFailureToSource(augmentedGraph, day, sourceFailurePerYear, sourcesWithHeavyFault, arcsWithReducedCapacityDueToIntrinsicFailure, arcsWithIntrinsicFailure);
            fileObject.getBw().write(injectFailureToSourceTxt);

            String injectFailureToPipelineTxt = IntrinsicFaultInjector.injectFailureToPipeline(augmentedGraph, day, pipelineFailurePerYear, arcsWithIntrinsicFailure, arcsWithReducedCapacityDueToIntrinsicFailure);
            fileObject.getBw().write(injectFailureToPipelineTxt);

            String injectAttackToSourcesTxt = ExternalAttackInjector.injectAttackToSource(augmentedGraph, day, externalAttackKeeper, sourceUnderAttack, sourcesWithHeavyFault, pipelinesUnderAttack, arcsWithReducedCapacityDueToAttack, arcsWithReducedCapacityDueToIntrinsicFailure, arcsWithIntrinsicFailure);
            fileObject.getBw().write(injectAttackToSourcesTxt);

            String injectAttackToPipelineTxt = ExternalAttackInjector.injectAttackToPipeline(augmentedGraph, day, externalAttackKeeper, pipelinesUnderAttack, arcsWithReducedCapacityDueToAttack, arcsWithReducedCapacityDueToIntrinsicFailure, arcsWithIntrinsicFailure);
            fileObject.getBw().write(injectAttackToPipelineTxt);
            */

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

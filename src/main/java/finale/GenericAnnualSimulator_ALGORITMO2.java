package finale;

import model.Brain3SimulatorALGORITMO2;
import model.Edge;
import model.Graph;
import model.Vertex;
import time.*;
import util.FileObject;
import util.FileSystemUtil;
import util.GraphUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericAnnualSimulator_ALGORITMO2 {

    private String dir = "/Users/cristianocimino/NetBeansProjects/generic-graph/GRAPHS";
    private String graphName = "PRAKS_GRAPH_CASE_G";
    private String outDir = "/Users/cristianocimino/NetBeansProjects/generic-graph/SIMULATION_ANNUAL_GLOBAL";
    private String attackDir = "/Users/cristianocimino/NetBeansProjects/generic-graph/TELEGRAM/";
    private String heavyFaultDir = "/Users/cristianocimino/NetBeansProjects/generic-graph/HEAVY_FAULT/";

    private String attackFile = "attacks.csv";
    private String heavyFaultFile = "heavyFaults.csv";
    private CumulativeAdjacencyMatrix cumulativeAdjacencyMatrix;


    public GenericAnnualSimulator_ALGORITMO2(String dir,
                                             String graphName,
                                             String outDir,
                                             String attackDir,
                                             String heavyFaultDir,
                                             String attackFile,
                                             String heavyFaultFile,
                                             CumulativeAdjacencyMatrix cumulativeAdjacencyMatrix) {
        this.dir = dir;
        this.graphName = graphName;
        this.outDir = outDir;
        this.attackDir = attackDir;
        this.heavyFaultDir = heavyFaultDir;
        this.attackFile = attackFile;
        this.heavyFaultFile = heavyFaultFile;
        this.cumulativeAdjacencyMatrix = cumulativeAdjacencyMatrix;

    }

    public void executeAnnual(Map<Integer, List<Double>> sourceFailurePerYear,
                              Map<String, List<Double>> pipelineFailurePerYear,
                              int iter) throws IOException {
        Graph graph;

        Map<String, Map<String, Double>> CDF = new HashMap<>();


        List<Vertex> sourcesWithIntrinsicFault = new ArrayList<>();
        List<Edge> pipelinesWithIntrinsicFault = new ArrayList<>();
        List<Edge> arcsWithReducedCapacityDueToIntrinsicFault = new ArrayList<>();

        List<Vertex> sourcesWithHeavyFault = new ArrayList<>();
        List<Edge> pipelinesWithHeavyFault = new ArrayList<>();
        List<Edge> arcsWithReducedCapacityDueToHeavyFault = new ArrayList<>();

        List<Vertex> sourceUnderAttack = new ArrayList<>();
        List<Edge> arcsWithReducedCapacityDueToAttack = new ArrayList<>();
        List<Edge> pipelinesUnderAttack = new ArrayList<>();

        String algoritmo = "ALGORITMO_2";

        int numberOfDays = 365;

        Graph augmentedGraph;

        String mask = GraphUtil.createFileMask();
        //String cdfOutDir = outDir + "/" + graphName + "_" + algoritmo + "_CDF_" + type + "_" + mask;
        //String averageAdjacencyOutputDir = outDir + "/" + graphName + "_" + algoritmo + "_ADJACENCY_AVERAGE_" + type + "_" + mask;

        List<Double> gasReceivedPercentage = new ArrayList<>();
        List<Double> satisfiedSinksPercentage = new ArrayList<>();


        ExternalAttackKeeper externalAttackKeeper = new ExternalAttackKeeper(2);
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

        graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);
        Brain3SimulatorALGORITMO2 brain3SimulatorALGORITMO2 = new Brain3SimulatorALGORITMO2(graph);
        augmentedGraph = brain3SimulatorALGORITMO2.getAugmentedGraph();
        //


        FileObject fileObject = FileSystemUtil.openFileObject(outDir, graphName + "_" + algoritmo, iter);

        for (int day = 0; day < numberOfDays; day++) {
            System.out.println("DAY: " + day);
            graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);
            graph.setName(graphName);
            brain3SimulatorALGORITMO2 = new Brain3SimulatorALGORITMO2(graph);

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

            brain3SimulatorALGORITMO2.execute();
            GraphUtil.showRealDestinationsOrdered(graph);


            fileObject.getBw().write(GraphUtil.getResultsAsString(brain3SimulatorALGORITMO2.getAugmentedGraph()));

            Double totalDemand = GraphUtil.getTotalDemand(augmentedGraph);
            Double netSinkFlow = GraphUtil.getNetFlowOnSinks(augmentedGraph);
            Double numberOfSatisfiedSink = GraphUtil.getNumberOfSatisfiedSinks(augmentedGraph);
            Double numberOfSink = GraphUtil.getNumberOfSinks(augmentedGraph);
            GraphUtil.createCumulativeDistribution(CDF, augmentedGraph, totalDemand, netSinkFlow);

            gasReceivedPercentage.add(netSinkFlow / totalDemand);
            satisfiedSinksPercentage.add(numberOfSatisfiedSink / numberOfSink);
            System.out.println("-------");
            GraphUtil.updateFlowOnArcsToVirtualDestination(augmentedGraph);
            //OutputFormatterUtil.writeAdjacencyMatrix(adjacencyOutputDir, day, augmentedGraph);
            cumulativeAdjacencyMatrix.updateMatrix(augmentedGraph.buildCumulativeFlowAdjacencyMatrix(), day);

        }
        FileSystemUtil.closeFileObject(fileObject);
        //GraphUtil.writeCDFonFile(CDF, cdfOutDir);
        //GraphUtil.writeStatisticOnFile(gasReceivedPercentage, satisfiedSinksPercentage, cdfOutDir);
        System.out.println("END");

    }



}

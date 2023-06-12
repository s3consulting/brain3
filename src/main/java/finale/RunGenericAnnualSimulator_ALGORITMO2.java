package finale;

import model.Graph;
import time.CumulativeAdjacencyMatrix;
import time.IntrinsicFaultInjector;
import util.FileSystemUtil;
import util.GraphUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RunGenericAnnualSimulator_ALGORITMO2 {
    public static void main(String[] args) throws IOException {

        String dir = args[0];
        String graphName = args[1];
        String outDir = args[2]+"___"+ GraphUtil.createFileMask();
        String attackDir = args[3];
        String heavyFaultDir = args[4];
        Integer iterations = Integer.parseInt(args[5]);
/*

        String dir = "/Users/cristianocimino/NetBeansProjects/generic-graph/GRAPHS";
        String graphName = "PRAKS_GRAPH_CASE_G";
        String outDir = "/Users/cristianocimino/NetBeansProjects/generic-graph/FINAL_SIMULATION";
        String attackDir = "/Users/cristianocimino/NetBeansProjects/generic-graph/TELEGRAM/";
        String heavyFaultDir = "/Users/cristianocimino/NetBeansProjects/generic-graph/HEAVY_FAULT/";
        Integer iterations = 10;
*/


        String attackFile = "attacks.csv";
        String heavyFaultFile = "heavyFaults.csv";

        String noAttacksFile = "noAttacks.csv";
        String noHeavyFaultsFile = "noHeavyFaults.csv";

        String mildAttackFile = "mildAttacks.csv";
        String mediumAttackFile = "mediumAttacks.csv";
        String heavyAttackFile = "heavyAttacks.csv";

        Graph graph0 = FileSystemUtil.loadGraphFromFile1(graphName, dir);
        int numberOfNodes = graph0.getVertexes().size();

        CumulativeAdjacencyMatrix cumulativeAdjacencyMatrixNoIntrinsicFaultsNoHeavyFaultsNoAttacks = new CumulativeAdjacencyMatrix(numberOfNodes);
        CumulativeAdjacencyMatrix cumulativeAdjacencyMatrixIntrinsicFaultsNoHeavyFaultsNoAttacks = new CumulativeAdjacencyMatrix(numberOfNodes);
        CumulativeAdjacencyMatrix cumulativeAdjacencyMatrixIntrinsicFaultsHeavyFaultsNoAttacks = new CumulativeAdjacencyMatrix(numberOfNodes);
        CumulativeAdjacencyMatrix cumulativeAdjacencyMatrixIntrinsicFaultsHeavyFaultsAttacks = new CumulativeAdjacencyMatrix(numberOfNodes);

        CumulativeAdjacencyMatrix cumulativeAdjacencyMatrixIntrinsicFaultsMildAttacks = new CumulativeAdjacencyMatrix(numberOfNodes);
        CumulativeAdjacencyMatrix cumulativeAdjacencyMatrixIntrinsicFaultsMediumAttacks = new CumulativeAdjacencyMatrix(numberOfNodes);
        CumulativeAdjacencyMatrix cumulativeAdjacencyMatrixIntrinsicFaultsHeavyAttacks = new CumulativeAdjacencyMatrix(numberOfNodes);

        String typeNoIntrinsicFaultsNoHeavyFaultsNoAttacks = "NO_INTRINSIC_FAULTS_NO_HEAVY_FAULTS_NO_ATTACK";
        String typeIntrinsicFaultsNoHeavyFaultsNoAttacks = "INTRINSIC_FAULTS_NO_HEAVY_FAULTS_NO_ATTACKS";
        String typeIntrinsicFaultsHeavyFaultsNoAttacks = "INTRINSIC_FAULTS_HEAVY_FAULTS_NO_ATTACKS";
        String typeIntrinsicFaultsHeavyFaultsAttacks = "INTRINSIC_FAULTS_HEAVY_FAULTS_ATTACKS";

        String typeIntrinsicFaultsMildAttacks = "INTRINSIC_FAULTS_MILD_ATTACKS";
        String typeIntrinsicFaultsMediumAttacks = "INTRINSIC_FAULTS_MEDIUM_ATTACKS";
        String typeIntrinsicFaultsHeavyAttacks = "INTRINSIC_FAULTS_HEAVY_ATTACKS";

        String outDirTypeNoIntrinsicFaultsNoHeavyFaultsNoAttacks = outDir+"/"+typeNoIntrinsicFaultsNoHeavyFaultsNoAttacks;
        String outDirTypeIntrinsicFaultsNoHeavyFaultsNoAttacks = outDir+"/"+typeIntrinsicFaultsNoHeavyFaultsNoAttacks;
        String outDirTypeIntrinsicFaultsHeavyFaultsNoAttacks = outDir+"/"+typeIntrinsicFaultsHeavyFaultsNoAttacks;
        String outDirTypeIntrinsicFaultsHeavyFaultsAttacks = outDir+"/"+typeIntrinsicFaultsHeavyFaultsAttacks;

        String outDirTypeIntrinsicFaultsMildAttacks = outDir+"/"+typeIntrinsicFaultsMildAttacks;
        String outDirTypeIntrinsicFaultsMediumAttacks = outDir+"/"+typeIntrinsicFaultsMediumAttacks;
        String outDirTypeIntrinsicFaultsHeavyAttacks = outDir+"/"+typeIntrinsicFaultsHeavyAttacks;

        for (int iter = 0; iter < iterations; iter++) {
            Graph graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);
            graph.setName(graphName);
            Map<Integer, List<Double>> sourceFailurePerYear = IntrinsicFaultInjector.generateFailureOnSourcePerYear(graph);
            Map<String, List<Double>> pipelineFailurePerYear = IntrinsicFaultInjector.generateFailureOnPipelinePerYear(graph);





            GenericAnnualSimulator_ALGORITMO2 simulator_NoIntrinsic_NoHeavy_NoAttacks = new GenericAnnualSimulator_ALGORITMO2(dir,
                    graphName,
                    outDirTypeNoIntrinsicFaultsNoHeavyFaultsNoAttacks,
                    attackDir,
                    heavyFaultDir,
                    noAttacksFile,
                    noHeavyFaultsFile,
                    cumulativeAdjacencyMatrixNoIntrinsicFaultsNoHeavyFaultsNoAttacks);

            simulator_NoIntrinsic_NoHeavy_NoAttacks.executeAnnual(null,
                    null, iter);

            GenericAnnualSimulator_ALGORITMO2 simulator_Intrinsic_NoHeavy_NoAttacks = new GenericAnnualSimulator_ALGORITMO2(dir,
                    graphName,
                    outDirTypeIntrinsicFaultsNoHeavyFaultsNoAttacks,
                    attackDir,
                    heavyFaultDir,
                    noAttacksFile,
                    noHeavyFaultsFile,
                    cumulativeAdjacencyMatrixIntrinsicFaultsNoHeavyFaultsNoAttacks);

            simulator_Intrinsic_NoHeavy_NoAttacks.executeAnnual(sourceFailurePerYear,
                    pipelineFailurePerYear, iter);

            GenericAnnualSimulator_ALGORITMO2 simulator_Intrinsic_Mild_Attacks = new GenericAnnualSimulator_ALGORITMO2(dir,
                    graphName,
                    outDirTypeIntrinsicFaultsMildAttacks,
                    attackDir,
                    heavyFaultDir,
                    mildAttackFile,
                    noHeavyFaultsFile,
                    cumulativeAdjacencyMatrixIntrinsicFaultsMildAttacks);

            simulator_Intrinsic_Mild_Attacks.executeAnnual(sourceFailurePerYear,
                    pipelineFailurePerYear, iter);

            GenericAnnualSimulator_ALGORITMO2 simulator_Intrinsic_Medium_Attacks = new GenericAnnualSimulator_ALGORITMO2(dir,
                    graphName,
                    outDirTypeIntrinsicFaultsMediumAttacks,
                    attackDir,
                    heavyFaultDir,
                    mediumAttackFile,
                    noHeavyFaultsFile,
                    cumulativeAdjacencyMatrixIntrinsicFaultsMediumAttacks);

            simulator_Intrinsic_Medium_Attacks.executeAnnual(sourceFailurePerYear,
                    pipelineFailurePerYear, iter);


            GenericAnnualSimulator_ALGORITMO2 simulator_Intrinsic_Heavy_Attacks = new GenericAnnualSimulator_ALGORITMO2(dir,
                    graphName,
                    outDirTypeIntrinsicFaultsHeavyAttacks,
                    attackDir,
                    heavyFaultDir,
                    mediumAttackFile,
                    noHeavyFaultsFile,
                    cumulativeAdjacencyMatrixIntrinsicFaultsHeavyAttacks);

            simulator_Intrinsic_Heavy_Attacks.executeAnnual(sourceFailurePerYear,
                    pipelineFailurePerYear, iter);

/*
            GenericAnnualSimulator_ALGORITMO2 simulator_Intrinsic_Heavy_NoAttacks = new GenericAnnualSimulator_ALGORITMO2(dir,
                    graphName,
                    outDirTypeIntrinsicFaultsHeavyFaultsNoAttacks,
                    attackDir,
                    heavyFaultDir,
                    noAttacksFile,
                    heavyFaultFile,
                    cumulativeAdjacencyMatrixIntrinsicFaultsHeavyFaultsNoAttacks);

            simulator_Intrinsic_Heavy_NoAttacks.executeAnnual(sourceFailurePerYear,
                    pipelineFailurePerYear, iter);


            GenericAnnualSimulator_ALGORITMO2 simulator_Intrinsic_Heavy_Attacks = new GenericAnnualSimulator_ALGORITMO2(dir,
                    graphName,
                    outDirTypeIntrinsicFaultsHeavyFaultsAttacks,
                    attackDir,
                    heavyFaultDir,
                    attackFile,
                    heavyFaultFile,
                    cumulativeAdjacencyMatrixIntrinsicFaultsHeavyFaultsAttacks);

            simulator_Intrinsic_Heavy_Attacks.executeAnnual(sourceFailurePerYear,
                    pipelineFailurePerYear, iter);

 */
        }

        /*

         String averageAdjacencyOutputDirMatrixIntrinsicFaultsHeavyFaultsNoAttacks = outDir+"/"+typeIntrinsicFaultsHeavyFaultsNoAttacks+"/ADJACENCY_AVERAGE";
        cumulativeAdjacencyMatrixIntrinsicFaultsHeavyFaultsNoAttacks.writeAverageAdjacencyMatrix(averageAdjacencyOutputDirMatrixIntrinsicFaultsHeavyFaultsNoAttacks, iterations, 365);

        String averageAdjacencyOutputDirMatrixIntrinsicFaultsHeavyFaultsAttacks = outDir+"/"+typeIntrinsicFaultsHeavyFaultsAttacks+"/ADJACENCY_AVERAGE";
        cumulativeAdjacencyMatrixIntrinsicFaultsHeavyFaultsAttacks.writeAverageAdjacencyMatrix(averageAdjacencyOutputDirMatrixIntrinsicFaultsHeavyFaultsAttacks, iterations, 365);

        */

        String averageAdjacencyOutputDirTypeNoIntrinsicFaultsNoHeavyNoFaultsAttacks = outDir+"/"+typeNoIntrinsicFaultsNoHeavyFaultsNoAttacks+"/ADJACENCY_AVERAGE";
        cumulativeAdjacencyMatrixNoIntrinsicFaultsNoHeavyFaultsNoAttacks.writeAverageAdjacencyMatrix(averageAdjacencyOutputDirTypeNoIntrinsicFaultsNoHeavyNoFaultsAttacks, iterations, 365);


        String averageAdjacencyOutputDirMatrixIntrinsicFaultsNoHeavyFaultsNoAttacks = outDir+"/"+typeIntrinsicFaultsNoHeavyFaultsNoAttacks+"/ADJACENCY_AVERAGE";
        cumulativeAdjacencyMatrixIntrinsicFaultsNoHeavyFaultsNoAttacks.writeAverageAdjacencyMatrix(averageAdjacencyOutputDirMatrixIntrinsicFaultsNoHeavyFaultsNoAttacks, iterations, 365);


        String averageAdjacencyOutputDirMatrixIntrinsicFaultsMildAttacks = outDir+"/"+typeIntrinsicFaultsMildAttacks+"/ADJACENCY_AVERAGE";
        cumulativeAdjacencyMatrixIntrinsicFaultsMildAttacks.writeAverageAdjacencyMatrix(averageAdjacencyOutputDirMatrixIntrinsicFaultsMildAttacks, iterations, 365);

        String averageAdjacencyOutputDirMatrixIntrinsicFaultsMediumAttacks = outDir+"/"+typeIntrinsicFaultsMediumAttacks+"/ADJACENCY_AVERAGE";
        cumulativeAdjacencyMatrixIntrinsicFaultsMediumAttacks.writeAverageAdjacencyMatrix(averageAdjacencyOutputDirMatrixIntrinsicFaultsMediumAttacks, iterations, 365);

        String averageAdjacencyOutputDirMatrixIntrinsicFaultsHeavyAttacks = outDir+"/"+typeIntrinsicFaultsHeavyAttacks+"/ADJACENCY_AVERAGE";
        cumulativeAdjacencyMatrixIntrinsicFaultsHeavyAttacks.writeAverageAdjacencyMatrix(averageAdjacencyOutputDirMatrixIntrinsicFaultsHeavyAttacks, iterations, 365);


    }
}

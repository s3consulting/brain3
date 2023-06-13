package finale;

import model.Graph;
import time.CumulativeAdjacencyMatrix;
import time.IntrinsicFaultInjector;
import util.FileSystemUtil;
import util.GraphUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RunGenericAnnualSimulator_ALGORITMO4 {
    public static void main(String[] args) throws IOException {

        String dir = args[0];
        String graphName = args[1];
        String outDir = args[2] + "___" + GraphUtil.createFileMask();
        String attackDir = args[3];
        String heavyFaultDir = args[4];
        Integer iterations = Integer.parseInt(args[5]);
        String confs = args[6];
        String[] configurations = confs.split(",");

/*
        String dir = "/Users/cristianocimino/NetBeansProjects/generic-graph/GRAPHS";
        String graphName = "PRAKS_GRAPH_CASE_G";
        String outDir = "/Users/cristianocimino/NetBeansProjects/generic-graph/FINAL_SIMULATION";
        String attackDir = "/Users/cristianocimino/NetBeansProjects/generic-graph/TELEGRAM/";
        String heavyFaultDir = "/Users/cristianocimino/NetBeansProjects/generic-graph/HEAVY_FAULT/";
        Integer iterations = 5;
        String confs = "INTRINSIC_FAULTS,HEAVY_FAULTS,MILD_ATTACKS,MEDIUM_ATTACKS,HEAVY_ATTACKS";
        String[] configurations = confs.split(",");
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

        CumulativeAdjacencyMatrix cumulativeAdjacencyMatrixIntrinsicFaultsMildAttacks = new CumulativeAdjacencyMatrix(numberOfNodes);
        CumulativeAdjacencyMatrix cumulativeAdjacencyMatrixIntrinsicFaultsMediumAttacks = new CumulativeAdjacencyMatrix(numberOfNodes);
        CumulativeAdjacencyMatrix cumulativeAdjacencyMatrixIntrinsicFaultsHeavyAttacks = new CumulativeAdjacencyMatrix(numberOfNodes);

        CumulativeAdjacencyMatrix cumulativeAdjacencyMatrixIntrinsicFaultsHeavyFaultsMildAttacks = new CumulativeAdjacencyMatrix(numberOfNodes);
        CumulativeAdjacencyMatrix cumulativeAdjacencyMatrixIntrinsicFaultsHeavyFaultsMediumAttacks = new CumulativeAdjacencyMatrix(numberOfNodes);
        CumulativeAdjacencyMatrix cumulativeAdjacencyMatrixIntrinsicFaultsHeavyFaultsHeavyAttacks = new CumulativeAdjacencyMatrix(numberOfNodes);

        String typeNoIntrinsicFaultsNoHeavyFaultsNoAttacks = "NO_INTRINSIC_FAULTS_NO_HEAVY_FAULTS_NO_ATTACK";
        String typeIntrinsicFaultsNoHeavyFaultsNoAttacks = "INTRINSIC_FAULTS_NO_HEAVY_FAULTS_NO_ATTACKS";

        String typeIntrinsicFaultsMildAttacks = "INTRINSIC_FAULTS_MILD_ATTACKS";
        String typeIntrinsicFaultsMediumAttacks = "INTRINSIC_FAULTS_MEDIUM_ATTACKS";
        String typeIntrinsicFaultsHeavyAttacks = "INTRINSIC_FAULTS_HEAVY_ATTACKS";

        String typeIntrinsicFaultsHeavyFaultsMildAttacks = "INTRINSIC_FAULTS_HEAVY_FAULTS_MILD_ATTACKS";
        String typeIntrinsicFaultsHeavyFaultsMediumAttacks = "INTRINSIC_FAULTS_HEAVY_FAULTS_MEDIUM_ATTACKS";
        String typeIntrinsicFaultsHeavyFaultsHeavyAttacks = "INTRINSIC_FAULTS_HEAVY_FAULTS_HEAVY_ATTACKS";

        String outDirTypeNoIntrinsicFaultsNoHeavyFaultsNoAttacks = outDir + "/" + typeNoIntrinsicFaultsNoHeavyFaultsNoAttacks;
        String outDirTypeIntrinsicFaultsNoHeavyFaultsNoAttacks = outDir + "/" + typeIntrinsicFaultsNoHeavyFaultsNoAttacks;


        String outDirTypeIntrinsicFaultsMildAttacks = outDir + "/" + typeIntrinsicFaultsMildAttacks;
        String outDirTypeIntrinsicFaultsMediumAttacks = outDir + "/" + typeIntrinsicFaultsMediumAttacks;
        String outDirTypeIntrinsicFaultsHeavyAttacks = outDir + "/" + typeIntrinsicFaultsHeavyAttacks;

        String outDirTypeIntrinsicFaultsHeavyFaultsMildAttacks = outDir + "/" + typeIntrinsicFaultsHeavyFaultsMildAttacks;
        String outDirTypeIntrinsicFaultsHeavyFaultsMediumAttacks = outDir + "/" + typeIntrinsicFaultsHeavyFaultsMediumAttacks;
        String outDirTypeIntrinsicFaultsHeavyFaultsHeavyAttacks = outDir + "/" + typeIntrinsicFaultsHeavyFaultsHeavyAttacks;

        for (int iter = 0; iter < iterations; iter++) {
            Graph graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);
            graph.setName(graphName);
            Map<Integer, List<Double>> sourceFailurePerYear = IntrinsicFaultInjector.generateFailureOnSourcePerYear(graph);
            Map<String, List<Double>> pipelineFailurePerYear = IntrinsicFaultInjector.generateFailureOnPipelinePerYear(graph);


            //Situazione ottimale, no intrinsic faults, no heavy faults, no attacks
            if (confs.contains(ConfigurationConstant.OPTIMUM)) {
                GenericAnnualSimulator_ALGORITMO4 simulator_NoIntrinsic_NoHeavy_NoAttacks = new GenericAnnualSimulator_ALGORITMO4(dir,
                        graphName,
                        outDirTypeNoIntrinsicFaultsNoHeavyFaultsNoAttacks,
                        attackDir,
                        heavyFaultDir,
                        noAttacksFile,
                        noHeavyFaultsFile,
                        cumulativeAdjacencyMatrixNoIntrinsicFaultsNoHeavyFaultsNoAttacks);

                simulator_NoIntrinsic_NoHeavy_NoAttacks.executeAnnual(null,
                        null, iter);
            }


            //Situazione con il solo rumore di fondo. Intrinsic faults, no heavy faults, no attacks
            if (confs.contains(ConfigurationConstant.INTRINSIC_FAULTS)) {
                GenericAnnualSimulator_ALGORITMO4 simulator_Intrinsic_NoHeavy_NoAttacks = new GenericAnnualSimulator_ALGORITMO4(dir,
                        graphName,
                        outDirTypeIntrinsicFaultsNoHeavyFaultsNoAttacks,
                        attackDir,
                        heavyFaultDir,
                        noAttacksFile,
                        noHeavyFaultsFile,
                        cumulativeAdjacencyMatrixIntrinsicFaultsNoHeavyFaultsNoAttacks);

                simulator_Intrinsic_NoHeavy_NoAttacks.executeAnnual(sourceFailurePerYear,
                        pipelineFailurePerYear, iter);
            }


            //situazione con rumore di fondo, no heavy faults e mild attacks
            if (!confs.contains(ConfigurationConstant.HEAVY_FAULTS) && confs.contains(ConfigurationConstant.MILD_ATTACKS)) {
                GenericAnnualSimulator_ALGORITMO4 simulator_Intrinsic_Mild_Attacks = new GenericAnnualSimulator_ALGORITMO4(dir,
                        graphName,
                        outDirTypeIntrinsicFaultsMildAttacks,
                        attackDir,
                        heavyFaultDir,
                        mildAttackFile,
                        noHeavyFaultsFile,
                        cumulativeAdjacencyMatrixIntrinsicFaultsMildAttacks);

                simulator_Intrinsic_Mild_Attacks.executeAnnual(sourceFailurePerYear,
                        pipelineFailurePerYear, iter);
            }

            //situazione con rumore di fondo, no heavy faults e medium attacks
            if (!confs.contains(ConfigurationConstant.HEAVY_FAULTS) && confs.contains(ConfigurationConstant.MEDIUM_ATTACKS)) {
                GenericAnnualSimulator_ALGORITMO4 simulator_Intrinsic_Medium_Attacks = new GenericAnnualSimulator_ALGORITMO4(dir,
                        graphName,
                        outDirTypeIntrinsicFaultsMediumAttacks,
                        attackDir,
                        heavyFaultDir,
                        mediumAttackFile,
                        noHeavyFaultsFile,
                        cumulativeAdjacencyMatrixIntrinsicFaultsMediumAttacks);

                simulator_Intrinsic_Medium_Attacks.executeAnnual(sourceFailurePerYear,
                        pipelineFailurePerYear, iter);
            }

            //situazione con rumore di fondo, no heavy faults e heavy attacks
            if (!confs.contains(ConfigurationConstant.HEAVY_FAULTS) && confs.contains(ConfigurationConstant.HEAVY_ATTACKS)) {
                GenericAnnualSimulator_ALGORITMO4 simulator_Intrinsic_Heavy_Attacks = new GenericAnnualSimulator_ALGORITMO4(dir,
                        graphName,
                        outDirTypeIntrinsicFaultsHeavyAttacks,
                        attackDir,
                        heavyFaultDir,
                        heavyAttackFile,
                        noHeavyFaultsFile,
                        cumulativeAdjacencyMatrixIntrinsicFaultsHeavyAttacks);

                simulator_Intrinsic_Heavy_Attacks.executeAnnual(sourceFailurePerYear,
                        pipelineFailurePerYear, iter);
            }


            //situazione con rumore di fondo, heavy faults e mild attacks
            if (confs.contains(ConfigurationConstant.HEAVY_FAULTS) && confs.contains(ConfigurationConstant.MILD_ATTACKS)) {
                GenericAnnualSimulator_ALGORITMO4 simulator_Intrinsic_Heavy_Faults_Mild_Attacks = new GenericAnnualSimulator_ALGORITMO4(dir,
                        graphName,
                        outDirTypeIntrinsicFaultsHeavyFaultsMildAttacks,
                        attackDir,
                        heavyFaultDir,
                        mildAttackFile,
                        heavyFaultFile,
                        cumulativeAdjacencyMatrixIntrinsicFaultsHeavyFaultsMildAttacks);

                simulator_Intrinsic_Heavy_Faults_Mild_Attacks.executeAnnual(sourceFailurePerYear,
                        pipelineFailurePerYear, iter);
            }


            //situazione con rumore di fondo, heavy faults e medium attacks
            if (confs.contains(ConfigurationConstant.HEAVY_FAULTS) && confs.contains(ConfigurationConstant.MEDIUM_ATTACKS)) {

                GenericAnnualSimulator_ALGORITMO4 simulator_Intrinsic_Heavy_Faults_Medium_Attacks = new GenericAnnualSimulator_ALGORITMO4(dir,
                        graphName,
                        outDirTypeIntrinsicFaultsHeavyFaultsMediumAttacks,
                        attackDir,
                        heavyFaultDir,
                        mediumAttackFile,
                        heavyFaultFile,
                        cumulativeAdjacencyMatrixIntrinsicFaultsHeavyFaultsMediumAttacks);

                simulator_Intrinsic_Heavy_Faults_Medium_Attacks.executeAnnual(sourceFailurePerYear,
                        pipelineFailurePerYear, iter);
            }


            //situazione con rumore di fondo, heavy faults e heavy attacks
            if (confs.contains(ConfigurationConstant.HEAVY_FAULTS) && confs.contains(ConfigurationConstant.HEAVY_ATTACKS)) {

                GenericAnnualSimulator_ALGORITMO4 simulator_Intrinsic_Heavy_Faults_Heavy_Attacks = new GenericAnnualSimulator_ALGORITMO4(dir,
                        graphName,
                        outDirTypeIntrinsicFaultsHeavyFaultsHeavyAttacks,
                        attackDir,
                        heavyFaultDir,
                        heavyAttackFile,
                        heavyFaultFile,
                        cumulativeAdjacencyMatrixIntrinsicFaultsHeavyFaultsHeavyAttacks);

                simulator_Intrinsic_Heavy_Faults_Heavy_Attacks.executeAnnual(sourceFailurePerYear,
                        pipelineFailurePerYear, iter);
            }

        }



        if (confs.contains(ConfigurationConstant.OPTIMUM)) {
            String averageAdjacencyOutputDirTypeNoIntrinsicFaultsNoHeavyFaultsNoAttacks = outDir + "/" + typeNoIntrinsicFaultsNoHeavyFaultsNoAttacks + "/ADJACENCY_AVERAGE";
            cumulativeAdjacencyMatrixNoIntrinsicFaultsNoHeavyFaultsNoAttacks.writeAverageAdjacencyMatrix(averageAdjacencyOutputDirTypeNoIntrinsicFaultsNoHeavyFaultsNoAttacks, iterations, 365);
        }


        if(confs.contains(ConfigurationConstant.INTRINSIC_FAULTS)) {
            String averageAdjacencyOutputDirMatrixIntrinsicFaultsNoHeavyFaultsNoAttacks = outDir + "/" + typeIntrinsicFaultsNoHeavyFaultsNoAttacks + "/ADJACENCY_AVERAGE";
            cumulativeAdjacencyMatrixIntrinsicFaultsNoHeavyFaultsNoAttacks.writeAverageAdjacencyMatrix(averageAdjacencyOutputDirMatrixIntrinsicFaultsNoHeavyFaultsNoAttacks, iterations, 365);
        }

        if (!confs.contains(ConfigurationConstant.HEAVY_FAULTS) && confs.contains(ConfigurationConstant.MILD_ATTACKS)) {
            String averageAdjacencyOutputDirMatrixIntrinsicFaultsMildAttacks = outDir + "/" + typeIntrinsicFaultsMildAttacks + "/ADJACENCY_AVERAGE";
            cumulativeAdjacencyMatrixIntrinsicFaultsMildAttacks.writeAverageAdjacencyMatrix(averageAdjacencyOutputDirMatrixIntrinsicFaultsMildAttacks, iterations, 365);
        }

        if (!confs.contains(ConfigurationConstant.HEAVY_FAULTS) && confs.contains(ConfigurationConstant.MEDIUM_ATTACKS)) {
            String averageAdjacencyOutputDirMatrixIntrinsicFaultsMediumAttacks = outDir + "/" + typeIntrinsicFaultsMediumAttacks + "/ADJACENCY_AVERAGE";
            cumulativeAdjacencyMatrixIntrinsicFaultsMediumAttacks.writeAverageAdjacencyMatrix(averageAdjacencyOutputDirMatrixIntrinsicFaultsMediumAttacks, iterations, 365);
        }

        if (!confs.contains(ConfigurationConstant.HEAVY_FAULTS) && confs.contains(ConfigurationConstant.HEAVY_ATTACKS)) {
            String averageAdjacencyOutputDirMatrixIntrinsicFaultsHeavyAttacks = outDir + "/" + typeIntrinsicFaultsHeavyAttacks + "/ADJACENCY_AVERAGE";
            cumulativeAdjacencyMatrixIntrinsicFaultsHeavyAttacks.writeAverageAdjacencyMatrix(averageAdjacencyOutputDirMatrixIntrinsicFaultsHeavyAttacks, iterations, 365);
        }

        if (confs.contains(ConfigurationConstant.HEAVY_FAULTS) && confs.contains(ConfigurationConstant.MILD_ATTACKS)) {
            String averageAdjacencyOutputDirMatrixIntrinsicFaultsHeavyFaultsMildAttacks = outDir + "/" + typeIntrinsicFaultsHeavyFaultsMildAttacks + "/ADJACENCY_AVERAGE";
            cumulativeAdjacencyMatrixIntrinsicFaultsHeavyFaultsMildAttacks.writeAverageAdjacencyMatrix(averageAdjacencyOutputDirMatrixIntrinsicFaultsHeavyFaultsMildAttacks, iterations, 365);
        }

        if (confs.contains(ConfigurationConstant.HEAVY_FAULTS) && confs.contains(ConfigurationConstant.MEDIUM_ATTACKS)) {
            String averageAdjacencyOutputDirMatrixIntrinsicFaultsHeavyFaultsMediumAttacks = outDir + "/" + typeIntrinsicFaultsHeavyFaultsMediumAttacks + "/ADJACENCY_AVERAGE";
            cumulativeAdjacencyMatrixIntrinsicFaultsHeavyFaultsMediumAttacks.writeAverageAdjacencyMatrix(averageAdjacencyOutputDirMatrixIntrinsicFaultsHeavyFaultsMediumAttacks, iterations, 365);
        }

        if (confs.contains(ConfigurationConstant.HEAVY_FAULTS) && confs.contains(ConfigurationConstant.HEAVY_ATTACKS)) {
            String averageAdjacencyOutputDirMatrixIntrinsicFaultsHeavyFaultsHeavyAttacks = outDir + "/" + typeIntrinsicFaultsHeavyFaultsHeavyAttacks + "/ADJACENCY_AVERAGE";
            cumulativeAdjacencyMatrixIntrinsicFaultsHeavyFaultsHeavyAttacks.writeAverageAdjacencyMatrix(averageAdjacencyOutputDirMatrixIntrinsicFaultsHeavyFaultsHeavyAttacks, iterations, 365);
        }
    }
}

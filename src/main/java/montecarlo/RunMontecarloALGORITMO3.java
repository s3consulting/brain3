package montecarlo;

import model.Brain3SimulatorALGORITMO2;
import model.Brain3SimulatorALGORITMO3;
import model.Graph;
import util.FileObject;
import util.FileSystemUtil;
import util.GraphUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RunMontecarloALGORITMO3 {

    public static void main(String[] args) throws IOException {
        int nSteps = 10000;
        String dir = "/Users/cristianocimino/NetBeansProjects/generic-graph/GRAPHS";
        String outDir = "/Users/cristianocimino/NetBeansProjects/generic-graph/SIMULATION_ALGORITMO_TRE_"+nSteps;
        //String graphName = "PraksGasGraph";
        //String graphName = "PraksGasGraph_MAX_CAP";
        String graphName_A = "PRAKS_GRAPH_CASE_A";
        String graphName_B = "PRAKS_GRAPH_CASE_B";
        String graphName_C = "PRAKS_GRAPH_CASE_C";
        String graphName_D = "PRAKS_GRAPH_CASE_D";
        String graphName_E = "PRAKS_GRAPH_CASE_E";
        String graphName_F = "PRAKS_GRAPH_CASE_F";
        String graphName_G = "PRAKS_GRAPH_CASE_G";

        List<String> cases = new ArrayList<>();
        cases.add(graphName_A);
        cases.add(graphName_B);
        cases.add(graphName_C);
        cases.add(graphName_D);
        cases.add(graphName_E);
        cases.add(graphName_F);
        cases.add(graphName_G);

        Map<String, Map<String, Double>> CDF = new HashMap<>();

        for(String graphName: cases) {

            FileObject fileObject = FileSystemUtil.openFileObject(outDir, graphName);



            for (int step = 0; step < nSteps; step++) {

                Graph graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);
                graph.setName(graphName);
                Double totalDemand = GraphUtil.getTotalDemand(graph);
                FailedElements failedElements = FailureRandomGenerator.setFailures(graph);
                Brain3SimulatorALGORITMO3 brain3Simulator = new Brain3SimulatorALGORITMO3(graph);
                brain3Simulator.execute();
                fileObject.getBw().write("\n---------------------------------- ITERAZIONE " + step + "\n");
                fileObject.getBw().write(failedElements.toLine());
                fileObject.getBw().write(GraphUtil.getResultsAsString(brain3Simulator.getAugmentedGraph()));
                Double netSinkFlow = GraphUtil.getNetFlowOnSinks(brain3Simulator.getAugmentedGraph());
                GraphUtil.createCumulativeDistribution(CDF, brain3Simulator.getAugmentedGraph(), totalDemand, netSinkFlow);
                System.out.println("END CASE "+graphName);
            }

            FileSystemUtil.closeFileObject(fileObject);
        }
        GraphUtil.writeCDFonFile(CDF, outDir);
        System.out.println("END");

    }
}

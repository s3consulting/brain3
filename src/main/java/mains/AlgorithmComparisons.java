package mains;

import model.*;
import util.FileObject;
import util.FileSystemUtil;
import util.GraphUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlgorithmComparisons {
    public static void main(String[] args) throws IOException {
        String dir = "/Users/cristianocimino/NetBeansProjects/generic-graph/GRAPHS";
        String outDir = "/Users/cristianocimino/NetBeansProjects/generic-graph/COMPARISONS";
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

        for(String graphName: cases){

            Graph graph ;
            String fileName = "COMPARISONS_"+graphName+".txt";
            FileObject fileObject = FileSystemUtil.openFileObject(outDir,fileName);




            System.out.println("--------------------------- "+graphName+" ------------------------------");

            //BRAIN3 ALGORITMO UNO
            graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);
            graph.setName(graphName);
            fileObject.getBw().write("************ ALGORITMO UNO ***************");
            Brain3SimulatorALGORITMO1 brain3SimulatorALGORITMO1 = new Brain3SimulatorALGORITMO1();
            brain3SimulatorALGORITMO1.execute(graph);
            System.out.println("END");
            fileObject.getBw().write(GraphUtil.getResultsAsString(brain3SimulatorALGORITMO1.getAugmentedGraph()));

            System.out.println("------------- SORTED SINKS STATISTICS -----------------");
            GraphUtil.showRealDestinationsOrdered(graph);

            //BRAIN3 WITH DISTANCES ALGORITMO DUE
            graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);
            graph.setName(graphName);
            fileObject.getBw().write("************ ALGORITMO DUE ***************");
            Brain3SimulatorALGORITMO2 brain3SimulatorALGORITMO2 = new Brain3SimulatorALGORITMO2(graph);
            brain3SimulatorALGORITMO2.execute();
            fileObject.getBw().write(GraphUtil.getResultsAsString(brain3SimulatorALGORITMO2.getAugmentedGraph()));
            GraphUtil.showSourceStatistics(brain3SimulatorALGORITMO2.getAugmentedGraph());
            GraphUtil.showSinksStatistics(brain3SimulatorALGORITMO2.getAugmentedGraph());

            GraphUtil.checkEquilibriumOnNodes(brain3SimulatorALGORITMO2.getAugmentedGraph());

            System.out.println("------------- SORTED SINKS STATISTICS -----------------");
            GraphUtil.showRealDestinationsOrdered(graph);

            //BRAIN3 ALGORITMO TRE
            graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);
            graph.setName(graphName);
            fileObject.getBw().write("************ ALGORITMO TRE ***************");

            Brain3SimulatorALGORITMO3 brain3SimulatorALGORITMO3 = new Brain3SimulatorALGORITMO3(graph);
            brain3SimulatorALGORITMO3.execute();
            fileObject.getBw().write(GraphUtil.getResultsAsString(brain3SimulatorALGORITMO3.getAugmentedGraph()));

            GraphUtil.showRealDestinationsOrdered(graph);
            System.out.println("------------- SORTED SINKS STATISTICS -----------------");
            GraphUtil.showRealDestinationsOrdered(graph);


            //BRAIN3 ALGORITMO QUATTRO
            graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);
            graph.setName(graphName);
            fileObject.getBw().write("************ ALGORITMO QUATTRO ***************");

            Brain3SimulatorALGORITMO4 brain3SimulatorALGORITMO4 = new Brain3SimulatorALGORITMO4(graph);
            brain3SimulatorALGORITMO4.execute();
            fileObject.getBw().write(GraphUtil.getResultsAsString(brain3SimulatorALGORITMO3.getAugmentedGraph()));

            GraphUtil.showRealDestinationsOrdered(graph);
            System.out.println("------------- SORTED SINKS STATISTICS -----------------");
            GraphUtil.showRealDestinationsOrdered(graph);

            FileSystemUtil.closeFileObject(fileObject);

        }

    }
}

package time;

import exception.GraphException;
import model.*;
import util.FileObject;
import util.FileSystemUtil;
import util.GraphUtil;

import java.io.IOException;
import java.util.*;

public class AnnualSimulation_Montecarlo_ALGORITMO4 {
    public static void main(String[] args) throws IOException, GraphException {

        AnnualSimulationUtil.checkParametersAnnualSimulation(args, 4);
        String dir = args[0];
        String outDir = args[1];
        Integer nIterazioni = Integer.valueOf(args[2]);
        String graphName = args[3];


        //Integer nIterazioni = 50;
        Integer numberOfDays = 365;
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

        Map<Integer, Map<String, Map<String, Double>>> montecarloCDF = new HashMap<>();

        Map<String, Map<String, Double>> finalCDF = new HashMap<>();

        Map<Integer, List<Double>> sourceFailurePerYear = AnnualSimulationUtil.generateFailureOnSourcePerYear(graph);
        Map<String, List<Double>> pipelineFailurePerYear = AnnualSimulationUtil.generateFailureOnPipelinePerYear(graph);

        List<Vertex> sourcesWithFailure = new ArrayList<>();
        List<Edge> failedEdges = new ArrayList<>();
        List<Edge> arcsWithReducedCapacity = new ArrayList<>();


        Graph augmentedGraph;
        //String outDir = "/Users/cristianocimino/NetBeansProjects/generic-graph/SIMULATION_ANNUAL_MONTECARLO";
        String mask = GraphUtil.createFileMask();
        String cdfOutDir = outDir+"/"+graphName+"_"+algoritmo+"_CDF_"+mask;
        FileObject fileObject = FileSystemUtil.openFileObject(outDir, graphName+"_"+algoritmo);

        for(int index=0; index<nIterazioni; index++){
            fileObject.getBw().write("\n---------------------------------- ITERATION " + index + "\n");
            CDF = new HashMap<>();

            sourcesWithFailure = new ArrayList<>();
            failedEdges = new ArrayList<>();
            arcsWithReducedCapacity = new ArrayList<>();

            sourceFailurePerYear = AnnualSimulationUtil.generateFailureOnSourcePerYear(graph);
            pipelineFailurePerYear = AnnualSimulationUtil.generateFailureOnPipelinePerYear(graph);

            for(int day=0; day<numberOfDays; day++){
                graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);
                graph.setName(graphName);
                Brain3SimulatorALGORITMO4 brain3SimulatorALGORITMO4 = new Brain3SimulatorALGORITMO4(graph);

                augmentedGraph = brain3SimulatorALGORITMO4.getAugmentedGraph();
                fileObject.getBw().write("\n---------------------------------- DAY " + day + "\n");


                String updateFixedElementsTxt = AnnualSimulationUtil.updateFixedElements(augmentedGraph, day, sourcesWithFailure, failedEdges, arcsWithReducedCapacity);
                fileObject.getBw().write(updateFixedElementsTxt);

                String propagateFailuresTxt = AnnualSimulationUtil.propagateFailures(augmentedGraph, day, sourcesWithFailure, arcsWithReducedCapacity, failedEdges);
                fileObject.getBw().write(propagateFailuresTxt);

                String injectFailureToSourceTxt = AnnualSimulationUtil.injectFailureToSource(augmentedGraph, day, sourceFailurePerYear, sourcesWithFailure, arcsWithReducedCapacity, failedEdges);
                fileObject.getBw().write(injectFailureToSourceTxt);

                String injectFailureToPipelineTxt = AnnualSimulationUtil.injectFailureToPipeline(augmentedGraph, day, pipelineFailurePerYear, failedEdges);
                fileObject.getBw().write(injectFailureToPipelineTxt);


                brain3SimulatorALGORITMO4.execute();
                GraphUtil.showRealDestinationsOrdered(graph);


                fileObject.getBw().write(GraphUtil.getResultsAsString(brain3SimulatorALGORITMO4.getAugmentedGraph()));

                Double totalDemand = GraphUtil.getTotalDemand(augmentedGraph);
                Double netSinkFlow = GraphUtil.getNetFlowOnSinks(augmentedGraph);
                GraphUtil.createCumulativeDistribution(CDF, augmentedGraph, totalDemand, netSinkFlow);
                System.out.println("-------");
            }


            GraphUtil.writeCDFonFile(CDF, cdfOutDir, index);
            System.out.println("END ANNUAL");
            montecarloCDF.put(index, CDF);
        }

        FileSystemUtil.closeFileObject(fileObject);
        System.out.println("END MONTECARLO");
        //create final CDF as mean of montecarloCDF values

        numberOfSamples = montecarloCDF.size();
        for(Integer i:montecarloCDF.keySet()){
            Map<String, Map<String,Double>> cdf = montecarloCDF.get(i);
            for(String caseKey: cdf.keySet()){
               if(!finalCDF.containsKey(caseKey)){
                   finalCDF.put(caseKey,new HashMap<>());
               }
               Map<String, Double> values = cdf.get(caseKey);
               Map<String, Double> hh = finalCDF.get(caseKey);
               for(String percentageKey: values.keySet()){
                   if(!hh.containsKey(percentageKey)){
                       hh.put(percentageKey, 0.0);
                   }
                   hh.put(percentageKey, hh.get(percentageKey)+values.get(percentageKey));
               }
            }
        }
        for(String caseKey: finalCDF.keySet()){
            Map<String, Double> caseCDF = finalCDF.get(caseKey);
            for(String percentageKey: caseCDF.keySet()){
                caseCDF.put(percentageKey, caseCDF.get(percentageKey)/numberOfSamples);
            }
        }

        GraphUtil.writeFinalCDFonFile(finalCDF, cdfOutDir);
        System.out.println("FINAL");
    }
}

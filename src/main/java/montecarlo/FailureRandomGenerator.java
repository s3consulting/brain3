package montecarlo;

import constant.FailureRate;
import constant.TypeConstant;
import constant.VertexConstant;
import constant.WeightConstant;
import model.Edge;
import model.Graph;
import model.Vertex;
import util.GraphUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FailureRandomGenerator {

    public static FailedElements setFailures(Graph graph) {
        Random random = new Random();
        FailureRate failureRate = new FailureRate();
        List<Vertex> sourcesWithFailures = new ArrayList<>();
        List<Edge> failedArcs = new ArrayList<>();
        List<Edge> arcsWithReducedCapacity = new ArrayList<>();

        for (Vertex vertex : graph.getVertexes()) {
            if (vertex.getWeight(VertexConstant.VERTEX_DEMAND) != null && vertex.getWeight(VertexConstant.VERTEX_DEMAND) < 0) {
                Double fr = failureRate.getFailureRate(vertex.getType());
                if (fr != null) {
                    Double r = random.nextDouble();
                    Double percentageReductionOfPipelinesConnected = 0.0;
                    if (r <= fr) {
                        sourcesWithFailures.add(vertex);
                        if (vertex.getType().equalsIgnoreCase(TypeConstant.COMPRESSION_STATION)) {
                            //se la sorgente è un compression station allora tutti gli archi connessi al nodo subiranno una riduzione della capacità del 20%
                            percentageReductionOfPipelinesConnected = .8;
                        } else if (vertex.getType().equalsIgnoreCase(TypeConstant.LNG)) {
                            //se la sorgente è un LNG allora tutti gli archi connessi al nodo subiranno una riduzione della capacità del 100%
                            percentageReductionOfPipelinesConnected = 0.0;
                        } else if (vertex.getType().equalsIgnoreCase(TypeConstant.UNDERGROUND_GAS_STORAGE)) {
                            //se la sorgente è un UGS allora tutti gli archi connessi al nodo subiranno una riduzione della capacità del 100%
                            percentageReductionOfPipelinesConnected = 0.0;
                        }
                        if(vertex.getInEdges()!=null) {
                            for (Edge e : vertex.getInEdges()) {
                                e.setWeight(WeightConstant.ORIGINAL_CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY) * percentageReductionOfPipelinesConnected);
                                e.setWeight(WeightConstant.CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY));
                                if(vertex.getType().equalsIgnoreCase(TypeConstant.COMPRESSION_STATION)){
                                    arcsWithReducedCapacity.add(e);
                                }
                                else{
                                    failedArcs.add(e);
                                }
                            }
                        }
                        if(vertex.getOutEdges()!=null) {
                            for (Edge e : vertex.getOutEdges()) {
                                e.setWeight(WeightConstant.ORIGINAL_CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY) * percentageReductionOfPipelinesConnected);
                                e.setWeight(WeightConstant.CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY));
                                if(vertex.getType().equalsIgnoreCase(TypeConstant.COMPRESSION_STATION)){
                                    arcsWithReducedCapacity.add(e);
                                }
                                else{
                                    failedArcs.add(e);
                                }
                            }
                        }
                    }
                }
            }
        }


        List<String> updatedEdges = new ArrayList<>();
        for (String edgeKey : graph.getEdgeMap().keySet()) {
            if (!updatedEdges.contains(edgeKey)) {
                Edge edge = graph.getEdgeMap().get(edgeKey);
                Double fr = failureRate.getFailureRate(edge.getType());
                if (fr != null) {
                    Integer n = random.nextInt(100000);
                    //Integer n=1;
                    Double r = n / 1.0E5;
                    if (r <= fr) {
                        edge.setWeight(WeightConstant.ORIGINAL_CAPACITY, 0.0);
                        edge.setWeight(WeightConstant.CAPACITY, 0.0);
                        updatedEdges.add(edgeKey);
                        failedArcs.add(edge);
                        String edgeKey1 = edgeKey.split("_")[1] + "_" + edgeKey.split("_")[0];
                        Edge edge1 = graph.getEdgeMap().get(edgeKey1);

                        //non tutti gli archi hanno il loro opposto, per esempio gli archi uscenti da un source.
                        if (edge1 != null) {
                            edge1.setWeight(WeightConstant.ORIGINAL_CAPACITY, 0.0);
                            edge1.setWeight(WeightConstant.CAPACITY, 0.0);
                            updatedEdges.add(edgeKey1);
                            failedArcs.add(edge1);
                        }

                    }
                }
            }
        }
        System.out.println("");
        FailedElements failedElements = new FailedElements();
        failedElements.setArcsWithReducedCapacity(arcsWithReducedCapacity);
        failedElements.setFailedEdges(failedArcs);
        failedElements.setSourcesWithFailures(sourcesWithFailures);
        return failedElements;
    }
}

package time;

import constant.*;
import model.Edge;
import model.Graph;
import model.Vertex;

import java.util.*;

public class IntrinsicFaultGenerator {

    public static List<Double> generateDailyFailure(int numberOfSamples, int summation) {
        List<Double> samples = new ArrayList<>();
        Random r = new Random();
        Double sum = 0.0;
        for (int i = 0; i < numberOfSamples; i++) {
            Double d = r.nextDouble();
            sum += d;
            samples.add(d);
        }
        Double adjust = summation / sum;
        Double finalSum = 0.0;
        for (int i = 0; i < numberOfSamples; i++) {
            samples.set(i, samples.get(i) * adjust);
            finalSum += samples.get(i);
        }

        //samples.forEach(x-> System.out.println(x));

        //System.out.println("NumberOfSamples: " + numberOfSamples + ", SOMMATORIA: " + finalSum);

        return samples;

    }

    public static Map<Integer, List<Double>> generateFailureOnSourcePerYear(Graph graph) {
        Map<Integer, List<Double>> sourceDailyFailure = new HashMap<>();
        for (Vertex vertex : graph.getVertexes()) {
            if (vertex.getWeight(VertexConstant.VERTEX_DEMAND) < 0) {
                List<Double> samples = generateDailyFailure(365, 1);
                sourceDailyFailure.put(vertex.getId(), samples);

            }
        }
        return sourceDailyFailure;
    }

    public static Map<String, List<Double>> generateFailureOnPipelinePerYear(Graph graph) {
        Map<String, List<Double>> pipelineDailyFailure = new HashMap<>();
        for (Edge edge : graph.getEdges()) {
            List<Double> samples = generateDailyFailure(365, 1);
            pipelineDailyFailure.put(edge.getSource().getId() + "_" + edge.getDestination().getId(), samples);
        }
        return pipelineDailyFailure;
    }

    public static String injectIntrinsicFaultToSource(Graph graph, int day, Map<Integer, List<Double>> sourceFailurePerYear, List<Vertex> sourcesWithIntrinsicFault, List<Edge> arcsWithReducedCapacityDueToIntrinsicFault) {
        String activity = "";
        if (sourceFailurePerYear != null) {
            activity = "\nInject Intrinsic Failure To Source";
            FailureRate failureRate = new FailureRate();
            Vertex vertexWithIntrinsicFault = null;
            String nodeType = "1";
            CapacityReductionIntrinsicFault capacityReductionIntrinsicFault = new CapacityReductionIntrinsicFault();
            for (Integer sourceKey : sourceFailurePerYear.keySet()) {
                Double x = sourceFailurePerYear.get(sourceKey).get(day);
                Vertex source = graph.getVertexMap().get(sourceKey);
                Double sourceFailureRate = failureRate.getFailureRate(source.getType());
                Double percentageReductionOfPipelinesConnected = 0.0;
                Vertex previousVertexWithIntrinsicFault = AnnualSimulationUtil.extractVertexById(sourcesWithIntrinsicFault, source.getId());
                //if (!sourcesWithIntrinsicFailure.contains(source)) {

                if (x <= sourceFailureRate / 365) {

                    activity += "\nSource " + source.getName() + " is failing for an Intrinsic Fault in day: " + day;
                    Integer vertexIndexWithIntrinsicFault = source.getId();
                    vertexWithIntrinsicFault = graph.getVertex(vertexIndexWithIntrinsicFault);
                    //se un intrinsic fault non è presente allora possiamo inserirlo
                    previousVertexWithIntrinsicFault = AnnualSimulationUtil.extractVertexById(sourcesWithIntrinsicFault, vertexIndexWithIntrinsicFault);
                    if (previousVertexWithIntrinsicFault == null) {
                        sourcesWithIntrinsicFault.add(vertexWithIntrinsicFault);
                    }
                    vertexWithIntrinsicFault.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                    percentageReductionOfPipelinesConnected = capacityReductionIntrinsicFault.getCapacityReduction(vertexWithIntrinsicFault.getType());
                    if (vertexWithIntrinsicFault.getInEdges() != null) {
                        for (Edge e : vertexWithIntrinsicFault.getInEdges()) {
                            if (e.getSource().getId() == graph.getVirtualSource().getId()) {
                                System.out.println("JJJ");
                            }
                            Edge ee = AnnualSimulationUtil.extractEdgeByName(arcsWithReducedCapacityDueToIntrinsicFault, e.getName());
                            if (ee == null) {
                                arcsWithReducedCapacityDueToIntrinsicFault.add(e);
                            }
                            e.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                            e.setWeight(WeightConstant.ORIGINAL_CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY) * percentageReductionOfPipelinesConnected);
                            e.setWeight(WeightConstant.CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY));
                            activity += "\npipeline " + e.getName() + " has reduced capacity due to " + vertexWithIntrinsicFault.getName() + " Intrinsic fault";
                            e.setValue(ValueConstant.REDUCED_CAPACITY, percentageReductionOfPipelinesConnected);
                        }
                    }
                    if (vertexWithIntrinsicFault.getOutEdges() != null) {
                        for (Edge e : vertexWithIntrinsicFault.getOutEdges()) {
                            Edge ee = AnnualSimulationUtil.extractEdgeByName(arcsWithReducedCapacityDueToIntrinsicFault, e.getName());
                            if (ee == null) {
                                arcsWithReducedCapacityDueToIntrinsicFault.add(e);
                            }
                            e.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                            e.setWeight(WeightConstant.ORIGINAL_CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY) * percentageReductionOfPipelinesConnected);
                            e.setWeight(WeightConstant.CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY));
                            activity += "\npipeline " + e.getName() + " has reduced capacity due to " + vertexWithIntrinsicFault.getName() + " Intrinsic fault";
                            e.setValue(ValueConstant.REDUCED_CAPACITY, percentageReductionOfPipelinesConnected);
                        }
                    }
                }
            }
        } else {
            activity = "\nNo intrinsic faults to inject";
        }
        return activity;
    }

    public static String injectIntrinsicFaultToPipeline(Graph graph, int day, Map<String, List<Double>> pipelineFailurePerYear, List<Edge> pipelinesWithIntrinsicFault) {
        String activity = "";
        if (pipelineFailurePerYear != null) {
            activity = "\nInject Intrinsic Fault To Pipeline";
            FailureRate failureRate = new FailureRate();
            CapacityReductionIntrinsicFault capacityReductionIntrinsicFault = new CapacityReductionIntrinsicFault();

            for (String edgeKey : pipelineFailurePerYear.keySet()) {
                Edge edgeWithIntrinsicFault = graph.getEdgeMap().get(edgeKey);
                Integer edgeIndexWithIntrinsicFault = edgeWithIntrinsicFault.getId();

                Double x = pipelineFailurePerYear.get(edgeKey).get(day);
                Double fr = failureRate.getFailureRate(edgeWithIntrinsicFault.getType());
                if (x <= fr / 365) {
                    Edge previousEdgeWithIntrinsicFault = AnnualSimulationUtil.extractEdgeById(pipelinesWithIntrinsicFault, edgeIndexWithIntrinsicFault);
                    if (previousEdgeWithIntrinsicFault == null) {
                        pipelinesWithIntrinsicFault.add(edgeWithIntrinsicFault);
                    }
                    Double percentageReductionOfPipelinesConnected = capacityReductionIntrinsicFault.getCapacityReduction(edgeWithIntrinsicFault.getType());
                    edgeWithIntrinsicFault.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                    edgeWithIntrinsicFault.setWeight(WeightConstant.ORIGINAL_CAPACITY, edgeWithIntrinsicFault.getWeight(WeightConstant.ORIGINAL_CAPACITY) * percentageReductionOfPipelinesConnected);
                    edgeWithIntrinsicFault.setWeight(WeightConstant.CAPACITY, edgeWithIntrinsicFault.getWeight(WeightConstant.ORIGINAL_CAPACITY));
                    edgeWithIntrinsicFault.setValue(ValueConstant.REDUCED_CAPACITY, percentageReductionOfPipelinesConnected);
                    activity += "\npipeline " + edgeWithIntrinsicFault.getName() + " has an Intrinsic Fault in day: " + day;
                    String edgeKey1 = edgeKey.split("_")[1] + "_" + edgeKey.split("_")[0];
                    Edge edge1 = graph.getEdgeMap().get(edgeKey1);

                    //non tutti gli archi hanno il loro opposto, per esempio gli archi uscenti da un source.
                    if (edge1 != null) {
                        if (AnnualSimulationUtil.extractEdgeById(pipelinesWithIntrinsicFault, edge1.getId()) == null) {
                            pipelinesWithIntrinsicFault.add(edge1);
                        }
                        activity += "\nPipeline " + edge1.getName() + " has an Intrinsic Fault in day: " + day;
                        edge1.setWeight(WeightConstant.ORIGINAL_CAPACITY, 0.0);
                        edge1.setWeight(WeightConstant.CAPACITY, 0.0);
                        edge1.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                        edge1.setValue(ValueConstant.REDUCED_CAPACITY, 0.0);
                    }
                }
            }
        } else {
            activity = "\nNo intrinsic faults to inject";
        }
        return activity;
    }

    public static String updateFixedElement(Graph graph, int day, List<Vertex> sourcesWithIntrinsicFault, List<Edge> arcsWithReducedCapacityDueToIntrinsicFault, List<Edge> pipelinesWithIntrinsicFault) {
        String activity = "\nUpdate Fixed Elements from Intrinsic Faults";
        ReparationTimeIntrinsicFault reparationTime = new ReparationTimeIntrinsicFault();
        List<Edge> fixedEdges = new ArrayList<>();
        //Pipelines con Intrinsic Faults
        for (Edge edge : pipelinesWithIntrinsicFault) {
            if (day - edge.getValue(ValueConstant.FIRST_DAY_OF_FAILURE) == reparationTime.getReparationTime(edge.getType())) {
                System.out.println("Pipeline " + edge.getName() + " fixed");
                activity += "\nPipeline " + edge.getName() + " fixed from Intrinsic Fault";
                fixedEdges.add(edge);
            }
        }
        for (Edge edge : fixedEdges) {
            String key = edge.getSource().getId() + "_" + edge.getDestination().getId();
            Edge arcToBeRemoved = AnnualSimulationUtil.extractEdgeById(pipelinesWithIntrinsicFault, edge.getId());
            if (arcToBeRemoved != null) {
                pipelinesWithIntrinsicFault.remove(arcToBeRemoved);
            }
        }
        List<Vertex> fixedSources = new ArrayList<>();
        List<Edge> affectedEdges = new ArrayList<>();

        //Sources With Intrinsic Faults
        for (Vertex vertex : sourcesWithIntrinsicFault) {

            if (day - vertex.getValue(ValueConstant.FIRST_DAY_OF_FAILURE) == reparationTime.getReparationTime(vertex.getType())) {
                System.out.println("Source " + vertex.getName() + " fixed");
                activity += "\nSource " + vertex.getName() + " fixed from Intrinsic Fault";
                fixedSources.add(vertex);
                if (vertex.getInEdges() != null) {
                    for (Edge e : vertex.getInEdges()) {
                        if (e.getSource().getId() == graph.getVirtualSource().getId()) {
                            System.out.println("KKK");
                        }
                        activity += "\nPipeline " + e.getName() + " fixed due to Source " + vertex.getName() + " fixing from Intrinsic Fault";
                        affectedEdges.add(e);
                    }
                }
                if (vertex.getOutEdges() != null) {
                    for (Edge e : vertex.getOutEdges()) {
                        activity += "\nPipeline " + e.getName() + " fixed due to Source " + vertex.getName() + " fixing from Intrinsic Fault";
                        affectedEdges.add(e);
                    }
                }
            }
        }

        for (Vertex vertex : fixedSources) {
            Vertex vertexToBeRemoved = AnnualSimulationUtil.extractVertexByName(sourcesWithIntrinsicFault, vertex.getName());
            if (vertexToBeRemoved != null) {
                sourcesWithIntrinsicFault.remove(vertexToBeRemoved);
            }
        }

        for (Edge edge : affectedEdges) {
            Edge arcToBeRemoved = AnnualSimulationUtil.extractEdgeByName(arcsWithReducedCapacityDueToIntrinsicFault, edge.getName());
            if (arcToBeRemoved != null) {
                arcsWithReducedCapacityDueToIntrinsicFault.remove(arcToBeRemoved);
            }
        }
        return activity;

    }

    public static String propagateIntrinsicFaults(Graph graph, List<Vertex> sourcesWithIntrinsicFaults, List<Edge> arcsWithReducedCapacityDueToIntrinsicFault, List<Edge> pipelinesWithIntrinsicFault) {

        String activity = "\nPropagate Intrinsic Fault";
        Double percentageReductionOfPipelinesConnected = 0.0;

        //propagate Intrinsic Fault on Sources
        for (Vertex source : sourcesWithIntrinsicFaults) {
            activity += "\nNode " + source.getName() + " has still failing due to Intrinsic Fault";
            Vertex newSource = graph.getVertexMap().get(source.getId());
            newSource.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, source.getValue(ValueConstant.FIRST_DAY_OF_FAILURE));
        }

        //propagate Intrinsic Fault on arcs with reduced capacity due to Intrinsic Fault
        for (Edge e : arcsWithReducedCapacityDueToIntrinsicFault) {
            System.out.println("pipeline " + e.getName() + " has reduced capacity");
            activity += "\npipeline " + e.getName() + " still has reduced capacity: " + e.getWeight(WeightConstant.ORIGINAL_CAPACITY) + " due to Intrinsic Fault";
            String key = e.getSource().getId() + "_" + e.getDestination().getId();
            Edge newArc = graph.getEdgeMap().get(key);
            newArc.setValue(ValueConstant.REDUCED_CAPACITY, e.getValue(ValueConstant.REDUCED_CAPACITY));
            newArc.setWeight(WeightConstant.ORIGINAL_CAPACITY, newArc.getWeight(WeightConstant.ORIGINAL_CAPACITY) * newArc.getValue(ValueConstant.REDUCED_CAPACITY));
            newArc.setWeight(WeightConstant.CAPACITY, newArc.getWeight(WeightConstant.ORIGINAL_CAPACITY));
            newArc.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, e.getValue(ValueConstant.FIRST_DAY_OF_FAILURE));
        }

        //propagate Intrinsic Fault on pipelines with Intrinsic Fault
        for (Edge e : pipelinesWithIntrinsicFault) {
            System.out.println("pipeline " + e.getName() + " still failing");
            activity += "\npipeline " + e.getName() + " still failing: " + e.getWeight(WeightConstant.ORIGINAL_CAPACITY) + " due to Intrinsic Fault";
            String key = e.getSource().getId() + "_" + e.getDestination().getId();
            Edge newArc = graph.getEdgeMap().get(key);
            newArc.setValue(ValueConstant.REDUCED_CAPACITY, e.getValue(ValueConstant.REDUCED_CAPACITY));
            newArc.setWeight(WeightConstant.ORIGINAL_CAPACITY, newArc.getWeight(WeightConstant.ORIGINAL_CAPACITY) * newArc.getValue(ValueConstant.REDUCED_CAPACITY));
            newArc.setWeight(WeightConstant.CAPACITY, newArc.getWeight(WeightConstant.ORIGINAL_CAPACITY));
            newArc.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, e.getValue(ValueConstant.FIRST_DAY_OF_FAILURE));
        }
        return activity;
    }


}

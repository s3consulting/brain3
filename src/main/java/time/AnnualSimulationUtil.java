package time;

import constant.*;
import exception.GraphException;
import model.Edge;
import model.Graph;
import model.Vertex;

import java.util.*;

public class AnnualSimulationUtil {
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

    public static String injectFailureToSource(Graph graph, Integer day, Map<Integer, List<Double>> sourceFailurePerYear, List<Vertex> sourcesWithFailure, List<Edge> arcsWithReducedCapacity, List<Edge> failedArcs) {
        String activity = "\nInject Failure To Source";
        FailureRate failureRate = new FailureRate();
        for (Integer sourceKey : sourceFailurePerYear.keySet()) {
            Double x = sourceFailurePerYear.get(sourceKey).get(day);
            Vertex source = graph.getVertexMap().get(sourceKey);
            Double sourceFailureRate = failureRate.getFailureRate(source.getType());
            Double percentageReductionOfPipelinesConnected = 0.0;
            Optional<Vertex> node = sourcesWithFailure.stream().filter(swf->swf.getId()==source.getId()).findFirst();
            //if (!sourcesWithFailure.contains(source)) {
            if(!node.isPresent()){
                if (x <= sourceFailureRate / 365) {
                    //in this case we have a failure
                    System.out.println("SOURCE: " + source.getName() + " is failing");
                    activity += "\nSOURCE: " + source.getName() + ", TYPE: " + source.getType() + " is failing in day " + day;
                    source.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                    sourcesWithFailure.add(source);

                    if (source.getType().equalsIgnoreCase(TypeConstant.LNG)) {
                        percentageReductionOfPipelinesConnected = 0.0;
                    }
                    if (source.getType().equalsIgnoreCase(TypeConstant.UNDERGROUND_GAS_STORAGE)) {
                        percentageReductionOfPipelinesConnected = 0.0;
                    }
                    if (source.getType().equalsIgnoreCase(TypeConstant.COMPRESSION_STATION)) {
                        percentageReductionOfPipelinesConnected = 0.8;
                    }
                    if (source.getInEdges() != null) {
                        for (Edge e : source.getInEdges()) {
                            e.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                            e.setWeight(WeightConstant.ORIGINAL_CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY) * percentageReductionOfPipelinesConnected);
                            e.setWeight(WeightConstant.CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY));
                            System.out.println("pipeline " + e.getName() + " has reduced capacity due to " + source.getName() + " failure");
                            activity += "\npipeline " + e.getName() + " has reduced capacity due to " + source.getName() + " failure in day " + day;
                            e.setValue(ValueConstant.REDUCED_CAPACITY, percentageReductionOfPipelinesConnected);
                            arcsWithReducedCapacity.add(e);
                        }
                    }
                    if (source.getOutEdges() != null) {
                        for (Edge e : source.getOutEdges()) {
                            e.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                            e.setWeight(WeightConstant.ORIGINAL_CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY) * percentageReductionOfPipelinesConnected);
                            e.setWeight(WeightConstant.CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY));
                            System.out.println("pipeline " + e.getName() + " has reduced capacity due to " + source.getName() + " failure");
                            activity += "\npipeline " + e.getName() + " has reduced capacity due to " + source.getName() + " failure in day " + day;
                            e.setValue(ValueConstant.REDUCED_CAPACITY, percentageReductionOfPipelinesConnected);
                            arcsWithReducedCapacity.add(e);
                        }
                    }
                }
            }
        }
        activity+="\nsourcesWithFailure size: "+sourcesWithFailure.size();
        activity+="\narcsWithReducedCapacity size: "+arcsWithReducedCapacity.size();
        return activity;
    }

    public static String injectFailureToPipeline(Graph graph, Integer day, Map<String, List<Double>> pipelineFailurePerYear, List<Edge> failedArcs) {
        String activity = "\nInject Failure To Pipeline";
        FailureRate failureRate = new FailureRate();
        List<String> updatedEdges = new ArrayList<>();

        for (String edgeKey : pipelineFailurePerYear.keySet()) {
            Optional<Edge> optionalEdge = failedArcs.stream().filter(swf->((swf.getSource().getId()+"_"+swf.getDestination().getId()).equalsIgnoreCase(edgeKey))).findFirst();


            if(!optionalEdge.isPresent()){
                Edge edge = graph.getEdgeMap().get(edgeKey);
                Double x = pipelineFailurePerYear.get(edgeKey).get(day);
                Double fr = failureRate.getFailureRate(edge.getType());
                if (x <= fr / 365) {
                    System.out.println("Pipeline " + edge.getName() + " is failing");

                    //if (!failedArcs.contains(edge)) {

                    activity += "\nPipeline " + edge.getName() + " is failing on its own in day: " + day;
                    edge.setWeight(WeightConstant.ORIGINAL_CAPACITY, 0.0);
                    edge.setWeight(WeightConstant.CAPACITY, 0.0);
                    edge.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                    updatedEdges.add(edgeKey);
                    failedArcs.add(edge);
                    edge.setValue(ValueConstant.REDUCED_CAPACITY, 0.0);
                    String edgeKey1 = edgeKey.split("_")[1] + "_" + edgeKey.split("_")[0];
                    Edge edge1 = graph.getEdgeMap().get(edgeKey1);

                    //non tutti gli archi hanno il loro opposto, per esempio gli archi uscenti da un source.
                    if (edge1 != null) {
                        activity += "\nPipeline " + edge1.getName() + " is failing on its own in day: " + day;
                        edge1.setWeight(WeightConstant.ORIGINAL_CAPACITY, 0.0);
                        edge1.setWeight(WeightConstant.CAPACITY, 0.0);
                        edge1.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                        edge1.setValue(ValueConstant.REDUCED_CAPACITY, 0.0);
                        updatedEdges.add(edgeKey1);
                        failedArcs.add(edge1);
                    }
                    //}
                }
            }
        }

        activity+="\nfailedArcs size: "+failedArcs.size();
        return activity;
    }

    public static String updateFixedElements(Graph graph, int day, List<Vertex> sourcesWithFailure, List<Edge> failedArcs, List<Edge> arcsWithReducedCapacity) {
         /*
         devo verificare che le sorgenti e gli archi failed abbiano superato il tempo di riparazione e ripristinare
         gli archi afferenti alle sorgenti e gli archi failed.
         settare a null last day of failure ed eliminare archi e sorgenti riparate dalla lista delle sorgenti con failures e archi con failure e con capacità ridotta
          */
        String activity = "\nUpdate Fixed Elements";
        ReparationTime reparationTime = new ReparationTime();
        Integer delta = 0;
        List<Edge> fixedEdges = new ArrayList<>();
        for (Edge edge : failedArcs) {

            //if (edge.getValue(ValueConstant.LAST_DAY_OF_FAILURE) != null && day - edge.getValue(ValueConstant.LAST_DAY_OF_FAILURE) == reparationTime.getReparationTime(edge.getType())) {
            if (day - edge.getValue(ValueConstant.FIRST_DAY_OF_FAILURE) == reparationTime.getReparationTime(edge.getType())) {
                System.out.println("Pipeline " + edge.getName() + " fixed");
                activity += "\nPipeline " + edge.getName() + " fixed";
                fixedEdges.add(edge);
            }

        }
        for (Edge edge : fixedEdges) {
            String key = edge.getSource().getId()+"_"+edge.getDestination().getId();
            Edge arcToBeRemoved = failedArcs.stream().filter(x->(x.getSource().getId()+"_"+x.getDestination().getId()).equalsIgnoreCase(key)).findFirst().get();
            failedArcs.remove(arcToBeRemoved);
        }

        List<Vertex> fixedSources = new ArrayList<>();
        List<Edge> affectedEdges = new ArrayList<>();
        for (Vertex vertex : sourcesWithFailure) {

            if (day - vertex.getValue(ValueConstant.FIRST_DAY_OF_FAILURE) == reparationTime.getReparationTime(vertex.getType())) {
                System.out.println("Source " + vertex.getName() + " fixed");
                activity += "\nSource " + vertex.getName() + " fixed";
                fixedSources.add(vertex);
                if (vertex.getInEdges() != null) {
                    for (Edge e : vertex.getInEdges()) {
                        activity += "\nPipeline " + e.getName() + " fixed due to Source " + vertex.getName() + " fixing";
                        affectedEdges.add(e);
                    }
                }
                if (vertex.getOutEdges() != null) {
                    for (Edge e : vertex.getOutEdges()) {
                        activity += "\nPipeline " + e.getName() + " fixed due to Source " + vertex.getName() + " fixing";
                        affectedEdges.add(e);
                    }
                }
            }
        }
        for (Vertex vertex : fixedSources) {
            sourcesWithFailure.remove(vertex);
        }

        for (Edge edge : affectedEdges) {
            String key = edge.getSource().getId()+"_"+edge.getDestination().getId();
            Optional<Edge> arcToBeRemoved = arcsWithReducedCapacity.stream().filter(x->(x.getSource().getId()+"_"+x.getDestination().getId()).equalsIgnoreCase(key)).findFirst();
            if(arcToBeRemoved.isPresent()) {
                arcsWithReducedCapacity.remove(arcToBeRemoved.get());
            }
        }
        activity += "\nsourcesWithFailure size: " + sourcesWithFailure.size();
        activity += "\narcsWithReducedCapacity size: " + arcsWithReducedCapacity.size();
        activity += "\nfailedArcs size: " + failedArcs.size();
        return activity;
    }

    public static String propagateFailures(Graph graph, int day, List<Vertex> sourcesWithFailure, List<Edge> arcsWithReducedCapacity, List<Edge> failedArcs) {
        /*
        mantenere lo stato degli elementi ancora con failures in modo che i failures si mantengano (se non riparati)
         */
        String activity = "\nPropagate Failures";
        Double percentageReductionOfPipelinesConnected = 0.0;
        for (Vertex source : sourcesWithFailure) {
            activity += "\nNode " + source.getName() + " is still failing";
            Vertex newSource = graph.getVertexMap().get(source.getId());
            newSource.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, source.getValue(ValueConstant.FIRST_DAY_OF_FAILURE));
        }
        for (Edge e : arcsWithReducedCapacity) {
            //e.setWeight(WeightConstant.ORIGINAL_CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY) * e.getValue(ValueConstant.REDUCED_CAPACITY));
            //e.setWeight(WeightConstant.CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY));
            System.out.println("pipeline " + e.getName() + " has reduced capacity");
            activity += "\npipeline " + e.getName() + " still has reduced capacity: " + e.getWeight(WeightConstant.ORIGINAL_CAPACITY);
            String key = e.getSource().getId() + "_" + e.getDestination().getId();
            Edge newArc = graph.getEdgeMap().get(key);
            newArc.setValue(ValueConstant.REDUCED_CAPACITY, e.getValue(ValueConstant.REDUCED_CAPACITY));
            newArc.setWeight(WeightConstant.ORIGINAL_CAPACITY, newArc.getWeight(WeightConstant.ORIGINAL_CAPACITY) * newArc.getValue(ValueConstant.REDUCED_CAPACITY));
            newArc.setWeight(WeightConstant.CAPACITY, newArc.getWeight(WeightConstant.ORIGINAL_CAPACITY));
            newArc.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, e.getValue(ValueConstant.FIRST_DAY_OF_FAILURE));
        }
        for (Edge e : failedArcs) {
            //e.setWeight(WeightConstant.ORIGINAL_CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY) * e.getValue(ValueConstant.REDUCED_CAPACITY));
            //e.setWeight(WeightConstant.CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY));
            System.out.println("pipeline " + e.getName() + " still failing");
            activity += "\npipeline " + e.getName() + " still failing: " + e.getWeight(WeightConstant.ORIGINAL_CAPACITY);
            String key = e.getSource().getId() + "_" + e.getDestination().getId();
            Edge newArc = graph.getEdgeMap().get(key);
            newArc.setValue(ValueConstant.REDUCED_CAPACITY, e.getValue(ValueConstant.REDUCED_CAPACITY));
            newArc.setWeight(WeightConstant.ORIGINAL_CAPACITY, newArc.getWeight(WeightConstant.ORIGINAL_CAPACITY) * newArc.getValue(ValueConstant.REDUCED_CAPACITY));
            newArc.setWeight(WeightConstant.CAPACITY, newArc.getWeight(WeightConstant.ORIGINAL_CAPACITY));
            newArc.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, e.getValue(ValueConstant.FIRST_DAY_OF_FAILURE));
        }

        activity += "\nsourcesWithFailure size: " + sourcesWithFailure.size();
        activity += "\narcsWithReducedCapacity size: " + arcsWithReducedCapacity.size();
        activity += "\nfailedArcs size: " + failedArcs.size();
        return activity;
    }

    public static void checkParametersAnnualSimulation(String[] args, int expectedParameters) throws GraphException {
        if(args.length!=expectedParameters){
            if(expectedParameters==3) {
                System.out.println("definire la directory dei grafi, la directory dei risultati e il nome del Grafo da caricare");
                System.out.println("ex. java -cp ./target/brain3Simulator-jar-with-dependencies.jar time.AnnualSimulation_ALGORITMO2 INPUT_DIR OUTPUT_DIR PRAKS_GRAPH_CASE_G");
            }
            else{
                System.out.println("definire la directory dei grafi, la directory dei risultati, il numero di iterazioni di montecarlo e il nome del Grafo da caricare");
                System.out.println("ex. java -cp ./target/brain3Simulator-jar-with-dependencies.jar time.AnnualSimulation_Montecarlo_ALGORITMO2 INPUT_DIR OUTPUT_DIR ITERATIONS PRAKS_GRAPH_CASE_G");
            }
            throw new GraphException("Numero parametri errato");
        }
    }
}



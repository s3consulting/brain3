package time;

import constant.*;
import model.Edge;
import model.Graph;
import model.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class HeavyFaultGenerator {

    public static String injectHeavyFailureToSource(Graph graph, int day, HeavyFaultKeeper heavyFaultKeeper, List<Vertex> sourcesWithHeavyFault, List<Edge> arcsWithReducedCapacityDueToHeavyFault) {
        String activity = "\nInject Heavy Fault To Sources";
        Vertex vertexWithHeavyFault = null;
        String nodeType = "1";
        CapacityReductionHeavyFault capacityReductionHeavyFault = new CapacityReductionHeavyFault();
        if (heavyFaultKeeper.getHeavyFaults().get(day) != null && heavyFaultKeeper.getHeavyFaults().get(day).getType().equalsIgnoreCase(nodeType)) {
            String name = heavyFaultKeeper.getHeavyFaults().get(day).getName();
            Optional<Vertex> optionalVertex = graph.getVertexes().stream().filter(n -> n.getName().equalsIgnoreCase(name)).findFirst();
            if (optionalVertex.isPresent()) {
                Integer vertexIndexWithHeavyFault = optionalVertex.get().getId();
                vertexWithHeavyFault = graph.getVertex(vertexIndexWithHeavyFault);
                activity+="\nSource "+vertexWithHeavyFault.getName()+" is failing for a Heavy Fault in day: "+day;

                //se un heavy fault non è presente allora possiamo inserirlo
                Vertex previousVertexWithHeavyFault = AnnualSimulationUtil.extractVertexById(sourcesWithHeavyFault, vertexIndexWithHeavyFault);
                if (previousVertexWithHeavyFault == null) {
                    sourcesWithHeavyFault.add(vertexWithHeavyFault);
                }
                vertexWithHeavyFault.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                Double percentageReductionOfPipelinesConnected = capacityReductionHeavyFault.getCapacityReduction(vertexWithHeavyFault.getType());
                if (vertexWithHeavyFault.getInEdges() != null) {
                    for (Edge e : vertexWithHeavyFault.getInEdges()) {
                        Edge ee = AnnualSimulationUtil.extractEdgeByName(arcsWithReducedCapacityDueToHeavyFault, e.getName());
                        if (ee == null) {
                            arcsWithReducedCapacityDueToHeavyFault.add(e);
                        }
                        e.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                        e.setWeight(WeightConstant.ORIGINAL_CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY) * percentageReductionOfPipelinesConnected);
                        e.setWeight(WeightConstant.CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY));
                        activity += "\npipeline " + e.getName() + " has reduced capacity due to " + vertexWithHeavyFault.getName() + " heavy fault";
                        e.setValue(ValueConstant.REDUCED_CAPACITY, percentageReductionOfPipelinesConnected);
                    }
                }
                if (vertexWithHeavyFault.getOutEdges() != null) {
                    for (Edge e : vertexWithHeavyFault.getOutEdges()) {
                        Edge ee = AnnualSimulationUtil.extractEdgeByName(arcsWithReducedCapacityDueToHeavyFault, e.getName());
                        if (ee == null) {
                            arcsWithReducedCapacityDueToHeavyFault.add(e);
                        }
                        e.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                        e.setWeight(WeightConstant.ORIGINAL_CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY) * percentageReductionOfPipelinesConnected);
                        e.setWeight(WeightConstant.CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY));
                        activity += "\npipeline " + e.getName() + " has reduced capacity due to " + vertexWithHeavyFault.getName() + " heavy fault";
                        e.setValue(ValueConstant.REDUCED_CAPACITY, percentageReductionOfPipelinesConnected);
                    }
                }
            }
        }
        return activity;
    }

    public static String injectHeavyFaultToPipeline(Graph graph, int day, HeavyFaultKeeper heavyFaultKeeper, List<Edge> pipelinesWithHeavyFault) {
        String activity = "\nInject Heavy Fault To Pipeline";
        String arcType = "0";
        CapacityReductionHeavyFault capacityReductionHeavyFault = new CapacityReductionHeavyFault();

        if (heavyFaultKeeper.getHeavyFaults().get(day) != null && heavyFaultKeeper.getHeavyFaults().get(day).getType().equalsIgnoreCase(arcType)) {
            String name = heavyFaultKeeper.getHeavyFaults().get(day).getName();

            Optional<Edge> optionalEdge = graph.getEdges().stream().filter(n -> n.getName().equalsIgnoreCase(name)).findFirst();
            if (optionalEdge.isPresent()) {
                Integer edgeIndexWithHeavyFault = optionalEdge.get().getId();
                Edge edgeWithHeavyFault = graph.getEdge(edgeIndexWithHeavyFault);

                Edge previousEdgeWithHeavyFault = AnnualSimulationUtil.extractEdgeByName(pipelinesWithHeavyFault, name);
                if (previousEdgeWithHeavyFault == null) {
                    pipelinesWithHeavyFault.add(edgeWithHeavyFault);
                }
                Double percentageReductionOfPipelinesConnected = capacityReductionHeavyFault.getCapacityReduction(edgeWithHeavyFault.getType());
                edgeWithHeavyFault.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                edgeWithHeavyFault.setWeight(WeightConstant.ORIGINAL_CAPACITY, edgeWithHeavyFault.getWeight(WeightConstant.ORIGINAL_CAPACITY) * percentageReductionOfPipelinesConnected);
                edgeWithHeavyFault.setWeight(WeightConstant.CAPACITY, edgeWithHeavyFault.getWeight(WeightConstant.ORIGINAL_CAPACITY));
                edgeWithHeavyFault.setValue(ValueConstant.REDUCED_CAPACITY, percentageReductionOfPipelinesConnected);
                activity += "\npipeline " + edgeWithHeavyFault.getName() + " has a heavy fault in day: " + day;
                String edgeKey = edgeWithHeavyFault.getSource().getId() + "_" + edgeWithHeavyFault.getDestination().getId();
                String edgeKey1 = edgeKey.split("_")[1] + "_" + edgeKey.split("_")[0];
                Edge edge1 = graph.getEdgeMap().get(edgeKey1);

                //non tutti gli archi hanno il loro opposto, per esempio gli archi uscenti da un source.
                if (edge1 != null) {
                    if(AnnualSimulationUtil.extractEdgeById(pipelinesWithHeavyFault, edge1.getId())==null){
                        pipelinesWithHeavyFault.add(edge1);

                    }
                    activity += "\nPipeline " + edge1.getName() + " has a heavy fault in day: " + day;
                    edge1.setWeight(WeightConstant.ORIGINAL_CAPACITY, 0.0);
                    edge1.setWeight(WeightConstant.CAPACITY, 0.0);
                    edge1.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                    edge1.setValue(ValueConstant.REDUCED_CAPACITY, 0.0);
                }
            }
        }
        return activity;
    }

    public static String updateFixedElement(Graph graph, int day, List<Vertex> sourcesWithHeavyFault, List<Edge> arcsWithReducedCapacityDueToHeavyFault, List<Edge> pipelinesWithHeavyFault){
        String activity = "\nUpdate Fixed Elements from Heavy Faults";
        ReparationTimeHeavyFault reparationTime = new ReparationTimeHeavyFault();
        Integer delta = 0;
        List<Edge> fixedEdges = new ArrayList<>();
        //Pipelines con Heavy Faults
        for(Edge edge: pipelinesWithHeavyFault){
            if (day - edge.getValue(ValueConstant.FIRST_DAY_OF_FAILURE) == reparationTime.getReparationTime(edge.getType())) {
                System.out.println("Pipeline " + edge.getName() + " fixed");
                activity += "\nPipeline " + edge.getName() + " fixed from Heavy Fault";
                fixedEdges.add(edge);
            }
        }
        for (Edge edge : fixedEdges) {
            String key = edge.getSource().getId() + "_" + edge.getDestination().getId();
            Edge arcToBeRemoved = AnnualSimulationUtil.extractEdgeById(pipelinesWithHeavyFault, edge.getId());
            if(arcToBeRemoved!=null) {
                pipelinesWithHeavyFault.remove(arcToBeRemoved);
            }
        }
        List<Vertex> fixedSources = new ArrayList<>();
        List<Edge> affectedEdges = new ArrayList<>();

        //Sources With Heavy Faults
        for (Vertex vertex : sourcesWithHeavyFault) {

            if (day - vertex.getValue(ValueConstant.FIRST_DAY_OF_FAILURE) == reparationTime.getReparationTime(vertex.getType())) {
                System.out.println("Source " + vertex.getName() + " fixed");
                activity += "\nSource " + vertex.getName() + " fixed from Heavy Fault";
                fixedSources.add(vertex);
                if (vertex.getInEdges() != null) {
                    for (Edge e : vertex.getInEdges()) {
                        activity += "\nPipeline " + e.getName() + " fixed due to Source " + vertex.getName() + " fixing from Heavy Fault";
                        affectedEdges.add(e);
                    }
                }
                if (vertex.getOutEdges() != null) {
                    for (Edge e : vertex.getOutEdges()) {
                        activity += "\nPipeline " + e.getName() + " fixed due to Source " + vertex.getName() + " fixing from Heavy Fault";
                        affectedEdges.add(e);
                    }
                }
            }
        }

        for (Vertex vertex : fixedSources) {
            Vertex vertexToBeRemoved = AnnualSimulationUtil.extractVertexByName(sourcesWithHeavyFault, vertex.getName());
            if(vertexToBeRemoved!=null) {
                sourcesWithHeavyFault.remove(vertexToBeRemoved);
            }
        }

        for (Edge edge : affectedEdges) {
            Edge arcToBeRemoved = AnnualSimulationUtil.extractEdgeByName(arcsWithReducedCapacityDueToHeavyFault, edge.getName());
            if(arcToBeRemoved!=null) {
                arcsWithReducedCapacityDueToHeavyFault.remove(arcToBeRemoved);
            }
        }
        return activity;

    }

    public static String propagateHeavyFaults(Graph graph, List<Vertex> sourcesWithHeavyFaults, List<Edge> arcsWithReducedCapacityDueToHeavyFault, List<Edge> pipelinesWithHeavyFault){

        String activity = "\nPropagate Heavy Faults";
        Double percentageReductionOfPipelinesConnected = 0.0;

        //propagate Heavy Fault on Sources
        for (Vertex source : sourcesWithHeavyFaults) {
            activity += "\nNode " + source.getName() + " has still failing due to Heavy Fault";
            Vertex newSource = graph.getVertexMap().get(source.getId());
            newSource.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, source.getValue(ValueConstant.FIRST_DAY_OF_FAILURE));
        }

        //propagate Heavy Fault on arcs with reduced capacity due to Heavy Fault
        for (Edge e : arcsWithReducedCapacityDueToHeavyFault) {
            System.out.println("pipeline " + e.getName() + " has reduced capacity");
            activity += "\npipeline " + e.getName() + " still has reduced capacity: " + e.getWeight(WeightConstant.ORIGINAL_CAPACITY)+" due to Heavy Fault";
            String key = e.getSource().getId() + "_" + e.getDestination().getId();
            Edge newArc = graph.getEdgeMap().get(key);
            newArc.setValue(ValueConstant.REDUCED_CAPACITY, e.getValue(ValueConstant.REDUCED_CAPACITY));
            newArc.setWeight(WeightConstant.ORIGINAL_CAPACITY, newArc.getWeight(WeightConstant.ORIGINAL_CAPACITY) * newArc.getValue(ValueConstant.REDUCED_CAPACITY));
            newArc.setWeight(WeightConstant.CAPACITY, newArc.getWeight(WeightConstant.ORIGINAL_CAPACITY));
            newArc.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, e.getValue(ValueConstant.FIRST_DAY_OF_FAILURE));
        }

        //propagate Heavy Fault on pipelines with Heavy Fault
        for (Edge e : pipelinesWithHeavyFault) {
            System.out.println("pipeline " + e.getName() + " still failing");
            activity += "\npipeline " + e.getName() + " still failing: " + e.getWeight(WeightConstant.ORIGINAL_CAPACITY)+" due to Heavy Fault";
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

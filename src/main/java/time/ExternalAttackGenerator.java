package time;

import constant.*;
import model.Edge;
import model.Graph;
import model.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExternalAttackGenerator {

    public static String injectAttackToSource(Graph graph, int day, ExternalAttackKeeper externalAttackKeeper, List<Vertex> sourcesWithAttack, List<Edge> arcsWithReducedCapacityDueToAttack) {
        String activity = "\nInject Attack To Source";
        Vertex vertexWithAttack = null;
        String nodeType = "1";
        CapacityReductionAttack capacityReductionAttack = new CapacityReductionAttack();
        if (externalAttackKeeper.getAttacks().get(day) != null && externalAttackKeeper.getAttacks().get(day).getType().equalsIgnoreCase(nodeType)) {
            String name = externalAttackKeeper.getAttacks().get(day).getName();
            Optional<Vertex> optionalVertex = graph.getVertexes().stream().filter(n -> n.getName().equalsIgnoreCase(name)).findFirst();
            if (optionalVertex.isPresent()) {
                Integer vertexIndexWithAttack = optionalVertex.get().getId();
                vertexWithAttack = graph.getVertex(vertexIndexWithAttack);
                //se un heavy fault non è presente allora possiamo inserirlo
                Vertex previousVertexWithAttack = AnnualSimulationUtil.extractVertexById(sourcesWithAttack, vertexIndexWithAttack);
                if (previousVertexWithAttack == null) {
                    sourcesWithAttack.add(vertexWithAttack);
                }
                activity+="\nSource "+vertexWithAttack.getName()+" is under Attack in day: "+day;
                vertexWithAttack.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                Double percentageReductionOfPipelinesConnected = capacityReductionAttack.getCapacityReduction(vertexWithAttack.getType());
                if (vertexWithAttack.getInEdges() != null) {
                    for (Edge e : vertexWithAttack.getInEdges()) {
                        Edge ee = AnnualSimulationUtil.extractEdgeByName(arcsWithReducedCapacityDueToAttack, e.getName());
                        if (ee == null) {
                            arcsWithReducedCapacityDueToAttack.add(e);
                        }
                        e.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                        e.setWeight(WeightConstant.ORIGINAL_CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY) * percentageReductionOfPipelinesConnected);
                        e.setWeight(WeightConstant.CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY));
                        activity += "\npipeline " + e.getName() + " has reduced capacity due to " + vertexWithAttack.getName() + " Attack";
                        e.setValue(ValueConstant.REDUCED_CAPACITY, percentageReductionOfPipelinesConnected);
                    }
                }
                if (vertexWithAttack.getOutEdges() != null) {
                    for (Edge e : vertexWithAttack.getOutEdges()) {
                        Edge ee = AnnualSimulationUtil.extractEdgeByName(arcsWithReducedCapacityDueToAttack, e.getName());
                        if (ee == null) {
                            arcsWithReducedCapacityDueToAttack.add(e);
                        }
                        e.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                        e.setWeight(WeightConstant.ORIGINAL_CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY) * percentageReductionOfPipelinesConnected);
                        e.setWeight(WeightConstant.CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY));
                        activity += "\npipeline " + e.getName() + " has reduced capacity due to " + vertexWithAttack.getName() + " Attack";
                        e.setValue(ValueConstant.REDUCED_CAPACITY, percentageReductionOfPipelinesConnected);
                    }
                }
            }
        }
        return activity;
    }

    public static String injectAttackToPipeline(Graph graph, int day, ExternalAttackKeeper externalAttackKeeper, List<Edge> pipelinesWithAttack) {
        String activity = "\nInject Attack To Pipeline";
        String arcType = "0";
        CapacityReductionAttack capacityReductionAttack = new CapacityReductionAttack();

        if (externalAttackKeeper.getAttacks().get(day) != null && externalAttackKeeper.getAttacks().get(day).getType().equalsIgnoreCase(arcType)) {
            String name = externalAttackKeeper.getAttacks().get(day).getName();

            Optional<Edge> optionalEdge = graph.getEdges().stream().filter(n -> n.getName().equalsIgnoreCase(name)).findFirst();
            if (optionalEdge.isPresent()) {
                Integer edgeIndexWithAttack = optionalEdge.get().getId();
                Edge edgeWithAttack = graph.getEdge(edgeIndexWithAttack);

                Edge previousEdgeWithAttack = AnnualSimulationUtil.extractEdgeByName(pipelinesWithAttack, name);
                if (previousEdgeWithAttack == null) {
                    pipelinesWithAttack.add(edgeWithAttack);
                }
                Double percentageReductionOfPipelinesConnected = capacityReductionAttack.getCapacityReduction(edgeWithAttack.getType());
                edgeWithAttack.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                edgeWithAttack.setWeight(WeightConstant.ORIGINAL_CAPACITY, edgeWithAttack.getWeight(WeightConstant.ORIGINAL_CAPACITY) * percentageReductionOfPipelinesConnected);
                edgeWithAttack.setWeight(WeightConstant.CAPACITY, edgeWithAttack.getWeight(WeightConstant.ORIGINAL_CAPACITY));
                edgeWithAttack.setValue(ValueConstant.REDUCED_CAPACITY, percentageReductionOfPipelinesConnected);
                activity += "\npipeline " + edgeWithAttack.getName() + " is under Attack in day " + day;
                String edgeKey = edgeWithAttack.getSource().getId() + "_" + edgeWithAttack.getDestination().getId();
                String edgeKey1 = edgeKey.split("_")[1] + "_" + edgeKey.split("_")[0];
                Edge edge1 = graph.getEdgeMap().get(edgeKey1);

                //non tutti gli archi hanno il loro opposto, per esempio gli archi uscenti da un source.
                if (edge1 != null) {
                    if(AnnualSimulationUtil.extractEdgeById(pipelinesWithAttack, edge1.getId())==null){
                        pipelinesWithAttack.add(edge1);
                    }
                    activity += "\nPipeline " + edge1.getName() + " is under Attack in day: " + day;
                    edge1.setWeight(WeightConstant.ORIGINAL_CAPACITY, 0.0);
                    edge1.setWeight(WeightConstant.CAPACITY, 0.0);
                    edge1.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                    edge1.setValue(ValueConstant.REDUCED_CAPACITY, 0.0);
                }
            }
        }
        return activity;
    }

    public static String updateFixedElement(Graph graph, int day, List<Vertex> sourcesWithAttack, List<Edge> arcsWithReducedCapacityDueToAttack, List<Edge> pipelinesWithAttack){
        String activity = "\nUpdate Fixed Elements from Attack";
        ReparationTimeAttack reparationTime = new ReparationTimeAttack();
        Integer delta = 0;
        List<Edge> fixedEdges = new ArrayList<>();
        //Pipelines con Attacks
        for(Edge edge: pipelinesWithAttack){
            if (day - edge.getValue(ValueConstant.FIRST_DAY_OF_FAILURE) == reparationTime.getReparationTime(edge.getType())) {
                System.out.println("Pipeline " + edge.getName() + " fixed");
                activity += "\nPipeline " + edge.getName() + " fixed from Attack";
                fixedEdges.add(edge);
            }
        }
        for (Edge edge : fixedEdges) {
            String key = edge.getSource().getId() + "_" + edge.getDestination().getId();
            Edge arcToBeRemoved = AnnualSimulationUtil.extractEdgeById(pipelinesWithAttack, edge.getId());
            if(arcToBeRemoved!=null) {
                pipelinesWithAttack.remove(arcToBeRemoved);
            }
        }
        List<Vertex> fixedSources = new ArrayList<>();
        List<Edge> affectedEdges = new ArrayList<>();

        //Sources With Attacks
        for (Vertex vertex : sourcesWithAttack) {

            if (day - vertex.getValue(ValueConstant.FIRST_DAY_OF_FAILURE) == reparationTime.getReparationTime(vertex.getType())) {
                System.out.println("Source " + vertex.getName() + " fixed");
                activity += "\nSource " + vertex.getName() + " fixed from Attack";
                fixedSources.add(vertex);
                if (vertex.getInEdges() != null) {
                    for (Edge e : vertex.getInEdges()) {
                        activity += "\nPipeline " + e.getName() + " fixed due to Source " + vertex.getName() + " fixing from Attack";
                        affectedEdges.add(e);
                    }
                }
                if (vertex.getOutEdges() != null) {
                    for (Edge e : vertex.getOutEdges()) {
                        activity += "\nPipeline " + e.getName() + " fixed due to Source " + vertex.getName() + " fixing from Attack";
                        affectedEdges.add(e);
                    }
                }
            }
        }

        for (Vertex vertex : fixedSources) {
            Vertex vertexToBeRemoved = AnnualSimulationUtil.extractVertexByName(sourcesWithAttack, vertex.getName());
            if(vertexToBeRemoved!=null) {
                sourcesWithAttack.remove(vertexToBeRemoved);
            }
        }

        for (Edge edge : affectedEdges) {
            Edge arcToBeRemoved = AnnualSimulationUtil.extractEdgeByName(arcsWithReducedCapacityDueToAttack, edge.getName());
            if(arcToBeRemoved!=null) {
                arcsWithReducedCapacityDueToAttack.remove(arcToBeRemoved);
            }
        }
        return activity;

    }

    public static String propagateAttacks(Graph graph, List<Vertex> sourcesWithAttack, List<Edge> arcsWithReducedCapacityDueToAttack, List<Edge> pipelinesWithAttack){

        String activity = "\nPropagate Attacks";
        Double percentageReductionOfPipelinesConnected = 0.0;

        //propagate Attack on Sources
        for (Vertex source : sourcesWithAttack) {
            activity += "\nNode " + source.getName() + " has still failing due to Attack";
            Vertex newSource = graph.getVertexMap().get(source.getId());
            newSource.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, source.getValue(ValueConstant.FIRST_DAY_OF_FAILURE));
        }

        //propagate Attack on arcs with reduced capacity due to Attack
        for (Edge e : arcsWithReducedCapacityDueToAttack) {
            System.out.println("pipeline " + e.getName() + " has reduced capacity");
            activity += "\npipeline " + e.getName() + " still has reduced capacity: " + e.getWeight(WeightConstant.ORIGINAL_CAPACITY)+" due to Attack";
            String key = e.getSource().getId() + "_" + e.getDestination().getId();
            Edge newArc = graph.getEdgeMap().get(key);
            newArc.setValue(ValueConstant.REDUCED_CAPACITY, e.getValue(ValueConstant.REDUCED_CAPACITY));
            newArc.setWeight(WeightConstant.ORIGINAL_CAPACITY, newArc.getWeight(WeightConstant.ORIGINAL_CAPACITY) * newArc.getValue(ValueConstant.REDUCED_CAPACITY));
            newArc.setWeight(WeightConstant.CAPACITY, newArc.getWeight(WeightConstant.ORIGINAL_CAPACITY));
            newArc.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, e.getValue(ValueConstant.FIRST_DAY_OF_FAILURE));
        }

        //propagate Attack on pipelines with Attack
        for (Edge e : pipelinesWithAttack) {
            System.out.println("pipeline " + e.getName() + " still failing");
            activity += "\npipeline " + e.getName() + " still failing: " + e.getWeight(WeightConstant.ORIGINAL_CAPACITY)+" due to Attack";
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

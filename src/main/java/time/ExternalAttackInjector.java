package time;

import constant.CapacityReductionAttack;
import constant.ReparationTimeAttack;
import constant.ValueConstant;
import constant.WeightConstant;
import model.Edge;
import model.Graph;
import model.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ExternalAttackInjector {

    public static String injectAttackToSource(Graph graph, int day, ExternalAttackKeeper externalAttackKeeper, List<Vertex> sourcesUnderAttack, List<Vertex> sourcesWithIntrinsicFailure, List<Edge> pipelinesUnderAttack, List<Edge> pipelinesWithReducedCapacityDueToAttack, List<Edge> arcsWithReducedCapacityDueToIntrinsicFailure, List<Edge> pipelinesWithIntrinsicFailure) {
        String activity = "\nInject Attack To Source";
        Vertex vertexUnderAttack = null;
        String nodeType = "1";
        CapacityReductionAttack capacityReductionAttack = new CapacityReductionAttack();
        if (externalAttackKeeper.getAttacks().get(day) != null && externalAttackKeeper.getAttacks().get(day).getType().equalsIgnoreCase(nodeType)) {

            String name = externalAttackKeeper.getAttacks().get(day).getName();
            Optional<Vertex> optionalVertex = graph.getVertexes().stream().filter(n -> n.getName().equalsIgnoreCase(name)).findFirst();
            if (optionalVertex.isPresent()) {
                Integer vertexIndexUnderAttack = optionalVertex.get().getId();
                vertexUnderAttack = graph.getVertex(vertexIndexUnderAttack);
                Optional<Vertex> nodeWithFailure = sourcesWithIntrinsicFailure.stream().filter(swf -> swf.getId() == vertexIndexUnderAttack).findFirst();
                Optional<Vertex> nodeUnderAttack = sourcesUnderAttack.stream().filter(swf -> swf.getId() == vertexIndexUnderAttack).findFirst();


                if (nodeWithFailure.isPresent()) {
                    Vertex vv = null;
                    for(Vertex v: sourcesWithIntrinsicFailure){
                        if(v.getId() == vertexIndexUnderAttack){
                            vv = v;
                            break;
                        }
                    }
                    if(vv!=null) {
                        sourcesWithIntrinsicFailure.remove(nodeWithFailure.get());
                    }
                }
                if (!nodeUnderAttack.isPresent()) {
                    boolean found = false;
                    for(Vertex v: sourcesUnderAttack){
                        if(v.getId()==vertexIndexUnderAttack){
                            found = true;
                        }
                    }
                    if(!found) {
                        sourcesUnderAttack.add(vertexUnderAttack);
                    }
                }

                activity += "\nSOURCE: " + vertexUnderAttack.getName() + ", TYPE: " + vertexUnderAttack.getType() + " is under Attack in day " + day;
                vertexUnderAttack.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                Double percentageReductionOfPipelinesConnected = capacityReductionAttack.getCapacityReduction(vertexUnderAttack.getType());
                if (vertexUnderAttack.getInEdges() != null) {
                    for (Edge e : vertexUnderAttack.getInEdges()) {
                        e.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                        e.setWeight(WeightConstant.ORIGINAL_CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY) * percentageReductionOfPipelinesConnected);
                        e.setWeight(WeightConstant.CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY));
                        activity+="\npipeline " + e.getName() + " has reduced capacity due to " + vertexUnderAttack.getName() + " attack";
                        e.setValue(ValueConstant.REDUCED_CAPACITY, percentageReductionOfPipelinesConnected);
                        Integer arcId = e.getId();
                        Optional<Edge> optionalEdgeWithReduceCapacity = arcsWithReducedCapacityDueToIntrinsicFailure.stream().filter(swf -> swf.getId() == arcId).findFirst();
                        Optional<Edge> optionalEdgeWithReduceCapacityDueToAttack = pipelinesWithReducedCapacityDueToAttack.stream().filter(swf -> swf.getId() == arcId).findFirst();
                        Optional<Edge> optionalEdgeUnderAttack = pipelinesUnderAttack.stream().filter(swf -> swf.getId() == arcId).findFirst();
                        Optional<Edge> optionalEdgeFailed = pipelinesUnderAttack.stream().filter(swf -> swf.getId() == arcId).findFirst();

                        if (optionalEdgeUnderAttack.isPresent()) {
                            Edge ee = null;
                            for(Edge e1: pipelinesUnderAttack){
                                if(e1.getId()==arcId){
                                    ee=e1;
                                    break;
                                }
                            }
                            if(ee!=null){
                              pipelinesUnderAttack.remove(ee);
                            }
                            //pipelinesUnderAttack.remove(e);
                        }
                        if (optionalEdgeWithReduceCapacity.isPresent()) {
                            Edge ee=null;
                            for(Edge e1:arcsWithReducedCapacityDueToIntrinsicFailure){
                                if(e1.getId()==arcId){
                                    ee=e1;
                                    break;
                                }
                            }
                            if(ee!=null) {
                                arcsWithReducedCapacityDueToIntrinsicFailure.remove(ee);
                            }
                            //arcsWithReducedCapacityDueToIntrinsicFailure.remove(e);
                        }
                        if (optionalEdgeFailed.isPresent()) {
                            Edge ee = null;
                            for(Edge e1: pipelinesWithIntrinsicFailure){
                              if(e1.getId()==arcId){
                                  ee=e1;
                                  break;
                              }
                            }
                            if(ee!=null){
                                pipelinesWithIntrinsicFailure.remove(ee);
                            }
                            //pipelinesWithIntrinsicFailure.remove(e);
                        }
                        if (!optionalEdgeWithReduceCapacityDueToAttack.isPresent()) {
                            boolean found=false;

                            for(Edge e1: pipelinesWithReducedCapacityDueToAttack) {
                                if (e1.getId() == arcId) {
                                    found = true;
                                }
                            }
                            if(!found){
                                pipelinesWithReducedCapacityDueToAttack.add(e);
                            }
                            //pipelinesWithReducedCapacityDueToAttack.add(e);
                        }
                    }
                }
                if (vertexUnderAttack.getOutEdges() != null) {
                    for (Edge e : vertexUnderAttack.getOutEdges()) {
                        e.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                        e.setWeight(WeightConstant.ORIGINAL_CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY) * percentageReductionOfPipelinesConnected);
                        e.setWeight(WeightConstant.CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY));
                        activity+="\npipeline " + e.getName() + " has reduced capacity due to " + vertexUnderAttack.getName() + " attack";
                        e.setValue(ValueConstant.REDUCED_CAPACITY, percentageReductionOfPipelinesConnected);
                        Integer arcId = e.getId();
                        Optional<Edge> optionalEdgeWithReduceCapacity = arcsWithReducedCapacityDueToIntrinsicFailure.stream().filter(swf -> swf.getId() == arcId).findFirst();
                        Optional<Edge> optionalEdgeWithReduceCapacityDueToAttack = pipelinesWithReducedCapacityDueToAttack.stream().filter(swf -> swf.getId() == arcId).findFirst();
                        Optional<Edge> optionalEdgeUnderAttack = pipelinesUnderAttack.stream().filter(swf -> swf.getId() == arcId).findFirst();
                        Optional<Edge> optionalEdgeFailed = pipelinesUnderAttack.stream().filter(swf -> swf.getId() == arcId).findFirst();


                        if (optionalEdgeUnderAttack.isPresent()) {
                            Edge ee = null;
                            for(Edge e1: pipelinesUnderAttack){
                                if(e1.getId()==arcId){
                                    ee=e1;
                                    break;
                                }
                            }
                            if(ee!=null){
                                pipelinesUnderAttack.remove(ee);
                            }
                            //pipelinesUnderAttack.remove(e);
                        }
                        if (optionalEdgeWithReduceCapacity.isPresent()) {
                            Edge ee=null;
                            for(Edge e1:arcsWithReducedCapacityDueToIntrinsicFailure){
                                if(e1.getId()==arcId){
                                    ee=e1;
                                    break;
                                }
                            }
                            if(ee!=null) {
                                arcsWithReducedCapacityDueToIntrinsicFailure.remove(ee);
                            }
                            //arcsWithReducedCapacityDueToIntrinsicFailure.remove(e);
                        }
                        if (optionalEdgeFailed.isPresent()) {
                            Edge ee = null;
                            for(Edge e1: pipelinesWithIntrinsicFailure){
                                if(e1.getId()==arcId){
                                    ee=e1;
                                    break;
                                }
                            }
                            if(ee!=null){
                                pipelinesWithIntrinsicFailure.remove(ee);
                            }
                            //pipelinesWithIntrinsicFailure.remove(e);
                        }
                        if (!optionalEdgeWithReduceCapacityDueToAttack.isPresent()) {
                            boolean found=false;

                            for(Edge e1: pipelinesWithReducedCapacityDueToAttack) {
                                if (e1.getId() == arcId) {
                                    found = true;
                                }
                            }
                            if(!found){
                                pipelinesWithReducedCapacityDueToAttack.add(e);
                            }
                            //pipelinesWithReducedCapacityDueToAttack.add(e);
                        }
                    }
                }
            }
        }
        activity += "\nsources Under Attack size: " + sourcesUnderAttack.size();
        activity += "\npipelines with reduced capacity due to Attack size: " + pipelinesWithReducedCapacityDueToAttack.size();
        activity += "\npipelines Under Attack size: " + pipelinesUnderAttack.size();
        return activity;

    }

    public static String injectAttackToPipeline(Graph graph, int day, ExternalAttackKeeper externalAttackKeeper, List<Edge> pipelinesUnderAttack, List<Edge> pipelinesWithReducedCapacityDueToAttack, List<Edge> arcsWithReducedCapacityDueToIntrinsicFailure, List<Edge> pipelinesWithIntrinsicFailure) {
        Edge edge = null;
        String activity = "\nInject Attacks To Pipeline";
        String arcType = "0";
        if (externalAttackKeeper.getAttacks().get(day) != null && externalAttackKeeper.getAttacks().get(day).getType().equalsIgnoreCase(arcType)) {

            String name = externalAttackKeeper.getAttacks().get(day).getName();
            Optional<Edge> optionalEdge = graph.getEdges().stream().filter(n -> n.getName().equalsIgnoreCase(name)).findFirst();

            if (optionalEdge.isPresent()) {
                Integer edgeIndexUnderAttack = optionalEdge.get().getId();
                edge = graph.getEdge(edgeIndexUnderAttack);
                List<String> updatedEdges = new ArrayList<>();
                Optional<Edge> optionalEdgeWithReducedCapacity = arcsWithReducedCapacityDueToIntrinsicFailure.stream().filter(swf -> swf.getId() == edgeIndexUnderAttack).findFirst();
                Optional<Edge> optionalEdgeFailed = pipelinesWithIntrinsicFailure.stream().filter(swf -> swf.getId() == edgeIndexUnderAttack).findFirst();
                Optional<Edge> optionalEdgeUnderAttack = pipelinesUnderAttack.stream().filter(swf -> swf.getId() == edgeIndexUnderAttack).findFirst();
                Optional<Edge> optionalEdgeWithReducedCapacityDueToAttack = pipelinesWithReducedCapacityDueToAttack.stream().filter(swf -> swf.getId() == edgeIndexUnderAttack).findFirst();



                if (optionalEdgeWithReducedCapacityDueToAttack.isPresent()) {
                    Edge ee = null;
                    for(Edge e: pipelinesWithReducedCapacityDueToAttack){
                        if(e.getId()==edgeIndexUnderAttack){
                            ee = e;
                            break;
                        }
                    }
                    if(ee!=null) {
                        pipelinesWithReducedCapacityDueToAttack.remove(ee);
                    }
                    //pipelinesWithReducedCapacityDueToAttack.remove(optionalEdgeWithReducedCapacity.get());
                }
                if (optionalEdgeWithReducedCapacity.isPresent()) {
                    Edge ee = null;
                    for(Edge e:arcsWithReducedCapacityDueToIntrinsicFailure){
                        if(e.getId()==edgeIndexUnderAttack){
                            ee = e;
                            break;
                        }
                    }
                    if(ee!=null) {
                        arcsWithReducedCapacityDueToIntrinsicFailure.remove(ee);
                    }
                    //arcsWithReducedCapacityDueToIntrinsicFailure.remove(optionalEdgeWithReducedCapacity.get());
                }
                if (optionalEdgeFailed.isPresent()) {
                    Edge ee=null;
                    for(Edge e: pipelinesWithIntrinsicFailure){
                       if(e.getId()==edgeIndexUnderAttack){
                           ee = e;
                           break;
                       }
                    }
                    if(ee!=null) {
                        pipelinesWithIntrinsicFailure.remove(ee);
                    }
                    //pipelinesWithIntrinsicFailure.remove(optionalEdgeWithReducedCapacity.get());
                }
                if (!optionalEdgeUnderAttack.isPresent()) {
                    boolean found=false;
                    for(Edge e: pipelinesUnderAttack){
                        if(e.getId()==edgeIndexUnderAttack){
                            found = true;
                        }
                    }
                    if(!found) {
                        pipelinesUnderAttack.add(edge);
                    }
                }

                activity += "\nPipeline " + edge.getName() + " is under Attack in day: " + day;

                String edgeKey = edge.getSource().getId() + "_" + edge.getDestination().getId();
                edge.setWeight(WeightConstant.ORIGINAL_CAPACITY, 0.0);
                edge.setWeight(WeightConstant.CAPACITY, 0.0);
                edge.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                updatedEdges.add(edgeKey);
                //pipelinesWithIntrinsicFailure.add(edge);
                edge.setValue(ValueConstant.REDUCED_CAPACITY, 0.0);
                String edgeKey1 = edgeKey.split("_")[1] + "_" + edgeKey.split("_")[0];
                Edge edge1 = graph.getEdgeMap().get(edgeKey1);

                //non tutti gli archi hanno il loro opposto, per esempio gli archi uscenti da un source.
                if (edge1 != null) {
                    activity += "\nPipeline " + edge1.getName() + " is under Attack in day: " + day;
                    edge1.setWeight(WeightConstant.ORIGINAL_CAPACITY, 0.0);
                    edge1.setWeight(WeightConstant.CAPACITY, 0.0);
                    edge1.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, Double.valueOf("" + day));
                    edge1.setValue(ValueConstant.REDUCED_CAPACITY, 0.0);
                    updatedEdges.add(edgeKey1);
                    //pipelinesWithIntrinsicFailure.add(edge1);
                    pipelinesUnderAttack.add(edge1);
                }
            }
        }
        return activity;
    }

    public static String updateFixedElements(Graph graph, int day, List<Vertex> sourcesUnderAttack, List<Edge> pipelinesUnderAttack, List<Edge> pipelinesWithReducedCapacityDueToAttack) {
         /*
         devo verificare che le sorgenti e gli archi failed abbiano superato il tempo di riparazione e ripristinare
         gli archi afferenti alle sorgenti e gli archi failed.
         settare a null last day of failure ed eliminare archi e sorgenti riparate dalla lista delle sorgenti con failures e archi con failure e con capacità ridotta
          */
        String activity = "\nUpdate Fixed Elements";
        ReparationTimeAttack reparationTime = new ReparationTimeAttack();
        Integer delta = 0;
        List<Edge> fixedEdges = new ArrayList<>();
        for (Edge edge : pipelinesUnderAttack) {

            //if (edge.getValue(ValueConstant.LAST_DAY_OF_FAILURE) != null && day - edge.getValue(ValueConstant.LAST_DAY_OF_FAILURE) == reparationTime.getReparationTime(edge.getType())) {
            if (day - edge.getValue(ValueConstant.FIRST_DAY_OF_FAILURE) == reparationTime.getReparationTime(edge.getType())) {
                System.out.println("Pipeline " + edge.getName() + " fixed");
                activity += "\nPipeline " + edge.getName() + " fixed";
                fixedEdges.add(edge);
            }

        }
        for (Edge edge : fixedEdges) {
            String key = edge.getSource().getId() + "_" + edge.getDestination().getId();
            Edge arcToBeRemoved = pipelinesUnderAttack.stream().filter(x -> (x.getSource().getId() + "_" + x.getDestination().getId()).equalsIgnoreCase(key)).findFirst().get();
            pipelinesUnderAttack.remove(arcToBeRemoved);
        }

        List<Vertex> fixedSources = new ArrayList<>();
        List<Edge> affectedEdges = new ArrayList<>();
        for (Vertex vertex : sourcesUnderAttack) {

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
            sourcesUnderAttack.remove(vertex);
        }

        for (Edge edge : affectedEdges) {
            String key = edge.getSource().getId() + "_" + edge.getDestination().getId();
            Optional<Edge> arcToBeRemoved = pipelinesWithReducedCapacityDueToAttack.stream().filter(x -> (x.getSource().getId() + "_" + x.getDestination().getId()).equalsIgnoreCase(key)).findFirst();
            if(arcToBeRemoved.isPresent()) {
                pipelinesWithReducedCapacityDueToAttack.remove(arcToBeRemoved.get());
            }
        }
        activity += "\nsources Under Attack size: " + sourcesUnderAttack.size();
        activity += "\npipelines with reduced capacity due to Attack size: " + pipelinesWithReducedCapacityDueToAttack.size();
        activity += "\npipelines Under Attack size: " + pipelinesUnderAttack.size();
        return activity;
    }


    public static String propagateAttacks(Graph graph, int day, List<Vertex> sourcesUnderAttack, List<Edge> pipelinesUnderAttack, List<Edge> pipelinesUnderWithReducedCapacityDueToAttack) {
        /*
        mantenere lo stato degli elementi ancora con failures in modo che i failures si mantengano (se non riparati)
         */
        String activity = "\nPropagate Attacks";
        Double percentageReductionOfPipelinesConnected = 0.0;
        for (Vertex source : sourcesUnderAttack) {
            activity += "\nNode " + source.getName() + " is still under Attack";
            Vertex newSource = graph.getVertexMap().get(source.getId());
            newSource.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, source.getValue(ValueConstant.FIRST_DAY_OF_FAILURE));
        }
        for (Edge e : pipelinesUnderWithReducedCapacityDueToAttack) {
            //e.setWeight(WeightConstant.ORIGINAL_CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY) * e.getValue(ValueConstant.REDUCED_CAPACITY));
            //e.setWeight(WeightConstant.CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY));
            System.out.println("pipeline " + e.getName() + " still has reduced capacity due to Attack");
            activity += "\npipeline " + e.getName() + " still has reduced capacity due to Attack: " + e.getWeight(WeightConstant.ORIGINAL_CAPACITY);
            String key = e.getSource().getId() + "_" + e.getDestination().getId();
            Edge newArc = graph.getEdgeMap().get(key);
            newArc.setValue(ValueConstant.REDUCED_CAPACITY, e.getValue(ValueConstant.REDUCED_CAPACITY));
            newArc.setWeight(WeightConstant.ORIGINAL_CAPACITY, newArc.getWeight(WeightConstant.ORIGINAL_CAPACITY) * newArc.getValue(ValueConstant.REDUCED_CAPACITY));
            newArc.setWeight(WeightConstant.CAPACITY, newArc.getWeight(WeightConstant.ORIGINAL_CAPACITY));
            newArc.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, e.getValue(ValueConstant.FIRST_DAY_OF_FAILURE));
        }

        for (Edge e : pipelinesUnderAttack) {
            //e.setWeight(WeightConstant.ORIGINAL_CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY) * e.getValue(ValueConstant.REDUCED_CAPACITY));
            //e.setWeight(WeightConstant.CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY));
            System.out.println("pipeline " + e.getName() + " still under Attack");
            activity += "\npipeline " + e.getName() + " still under Attack: " + e.getWeight(WeightConstant.ORIGINAL_CAPACITY);
            String key = e.getSource().getId() + "_" + e.getDestination().getId();
            Edge newArc = graph.getEdgeMap().get(key);
            newArc.setValue(ValueConstant.REDUCED_CAPACITY, e.getValue(ValueConstant.REDUCED_CAPACITY));
            newArc.setWeight(WeightConstant.ORIGINAL_CAPACITY, newArc.getWeight(WeightConstant.ORIGINAL_CAPACITY) * newArc.getValue(ValueConstant.REDUCED_CAPACITY));
            newArc.setWeight(WeightConstant.CAPACITY, newArc.getWeight(WeightConstant.ORIGINAL_CAPACITY));
            newArc.setValue(ValueConstant.FIRST_DAY_OF_FAILURE, e.getValue(ValueConstant.FIRST_DAY_OF_FAILURE));
        }

        activity += "\nsources Under Attack size: " + sourcesUnderAttack.size();
        activity += "\npipelines Under Attack size: " + pipelinesUnderAttack.size();
        return activity;
    }

}

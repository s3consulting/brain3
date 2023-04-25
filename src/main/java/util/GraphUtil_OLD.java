package util;

import constant.ValueConstant;
import constant.VertexConstant;
import constant.WeightConstant;
import model.Edge;
import model.Graph;
import model.MaxFlowResponse;
import model.Vertex;

import java.util.ArrayList;
import java.util.List;

public class GraphUtil_OLD {

    public GraphUtil_OLD() {
        //private constructor to hide the implicit public one
    }

    public static Graph addVirtualSourceAndSink(Graph g) {
        Double virtualDistance = 1.0;
        Vertex virtualSource = new Vertex();
        virtualSource.setId(g.getVertexes().size());
        virtualSource.setCode("V_S");
        virtualSource.setName("Virtual_Source");

        Vertex virtualSink = new Vertex();
        virtualSink.setId(g.getVertexes().size() + 1);
        virtualSink.setCode("V_T");
        virtualSink.setName("Virtual_Sink");

        Integer numberOfEdges = g.getEdges().size();

        List<Edge> edges = new ArrayList<>();
        List<Vertex> vertices = new ArrayList<>();

        //copia dei vertici
        for (Vertex v : g.getVertexes()) {
            vertices.add(v);
        }
        //copia degli edges
        for (Edge e : g.getEdges()) {
            edges.add(e);
        }
        vertices.add(virtualSource);
        vertices.add(virtualSink);
        for (Vertex v : g.getVertexes()) {
            Double demand = v.getWeight(VertexConstant.VERTEX_DEMAND);
            if (demand != null) {
                //il nodo v è un source
                if (demand < 0) {
                    Edge e = new Edge();

                    e.setId(numberOfEdges);
                    numberOfEdges++;
                    e.setName("V_EDGE_" + virtualSource.getId() + "_" + v.getId());
                    e.setWeight(WeightConstant.CAPACITY, -demand);
                    e.setWeight(WeightConstant.ORIGINAL_CAPACITY, e.getWeight(WeightConstant.CAPACITY));
                    e.setWeight(WeightConstant.LENGTH, virtualDistance);
                    e.setSource(virtualSource);
                    e.setDestination(v);
                    e.setBidirectional(false);
                    edges.add(e);
                }
                //il nodo v è un sink
                else if (demand > 0) {
                    Edge e = new Edge();

                    e.setId(numberOfEdges);
                    numberOfEdges++;
                    e.setName("V_EDGE_" + v.getId() + "_" + virtualSink.getId());
                    e.setWeight(WeightConstant.CAPACITY, demand);
                    e.setWeight(WeightConstant.ORIGINAL_CAPACITY, e.getWeight(WeightConstant.CAPACITY));
                    e.setWeight(WeightConstant.LENGTH, virtualDistance);
                    e.setSource(v);
                    e.setDestination(virtualSink);
                    e.setBidirectional(false);
                    edges.add(e);
                }


            }
        }
        Graph graph = new Graph();
        graph.setName("AUGMENTED_" + g.getName());
        graph.build(vertices, edges);
        graph.setVirtualSource(virtualSource);
        graph.setVirtualSink(virtualSink);
        return graph;

    }

/*
    public static void updateCapacityBasedOnDistance(Graph g, Double max, Map<String, Double> distances) {
        Double maxDistance = max / 2;
        List<Edge> edges = g.getEdges();
        Vertex virtualSource = g.getVirtualSource();
        Vertex virtualSink = g.getVirtualSink();
        for (Edge edge : edges) {
            Vertex source = edge.getSource();
            Vertex destination = edge.getDestination();
            if (source != virtualSource && destination != virtualSink) {
                String keySource = virtualSource.getId() + "_" + source.getId();
                Double minPathLengthToSource = distances.get(keySource);
                String keyDestination = virtualSource.getId() + "_" + destination.getId();
                Double minPathLengthToDestination = distances.get(keyDestination);
                edge.getWeights().put(WeightConstant.ORIGINAL_CAPACITY, edge.getWeight(WeightConstant.CAPACITY));
                Double capacity_distance = edge.getWeight(WeightConstant.CAPACITY) * maxDistance * (1 / (minPathLengthToSource + edge.getWeight(WeightConstant.LENGTH)));
                //Double capacity_distance = edge.getWeight(WeightConstant.CAPACITY)*maxDistance/(minPathLengthToDestination+edge.getWeight(WeightConstant.LENGTH));
                //Double capacity_distance = edge.getWeight(WeightConstant.CAPACITY)*maxDistance/(minPathLengthToDestination);
                edge.setWeight(WeightConstant.CAPACITY, capacity_distance);
            }
        }
    }

*/
    /*
    public static void updateFlowsAndCapacities(Graph graph, MaxFlowResponse maxFlowResponse, Vertex realSink) {
        Double demand = realSink.getWeight(VertexConstant.VERTEX_DEMAND);
        Double maxFlow = maxFlowResponse.getMaxFlow();
        //calcola il numero di archi entranti nel real sink appartenenti al max flow
        int cnt = 0;
        for (Edge edge : maxFlowResponse.getEdgesMaxFlow()) {
            if (edge.getDestination() == realSink) {
                cnt++;
            }
        }
        Double delta = (maxFlow - demand) / cnt;
        if (maxFlow > demand) {
            for (Edge edge : maxFlowResponse.getEdgesMaxFlow()) {
                edge.setWeight(WeightConstant.CAPACITY, edge.getWeight(WeightConstant.CAPACITY) - delta);
                if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) == null) {
                    edge.setValue(ValueConstant.CUMULATIVE_FLOW, edge.getValue(ValueConstant.FLOW) - delta);
                } else {
                    edge.setValue(ValueConstant.CUMULATIVE_FLOW, edge.getValue(ValueConstant.CUMULATIVE_FLOW) + edge.getValue(ValueConstant.FLOW) - delta);
                }

                edge.setValue(ValueConstant.FLOW, 0.0);


                System.out.println("");
            }
        } else {
            for (Edge edge : maxFlowResponse.getEdgesMaxFlow()) {
                edge.setWeight(WeightConstant.CAPACITY, edge.getWeight(WeightConstant.CAPACITY) + delta);
                if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) == null) {
                    edge.setValue(ValueConstant.CUMULATIVE_FLOW, edge.getValue(ValueConstant.FLOW));
                } else {
                    edge.setValue(ValueConstant.CUMULATIVE_FLOW, edge.getValue(ValueConstant.CUMULATIVE_FLOW) + edge.getValue(ValueConstant.FLOW));
                }

                edge.setValue(ValueConstant.FLOW, 0.0);


                System.out.println("");
            }
        }
    }

     */

    /*
    public static void updateFlowsAndCapacitiesRecursive(Graph graph, MaxFlowResponse maxFlowResponse, Vertex realSink) {
        if (realSink.getWeight(VertexConstant.CUMULATIVE_DEMAND) == null) {
            realSink.setWeight(VertexConstant.CUMULATIVE_DEMAND, 0.0);
        }
        //Double extra = realSink.getWeight(VertexConstant.VERTEX_DEMAND)-realSink.getWeight(VertexConstant.CUMULATIVE_DEMAND)- maxFlowResponse.getMaxFlow();
        Double extra = maxFlowResponse.getMaxFlow() - realSink.getWeight(VertexConstant.VERTEX_DEMAND) - realSink.getWeight(VertexConstant.CUMULATIVE_DEMAND);

        if (maxFlowResponse.getMaxFlow() == 0.0) {
            return;
        } else {
            if (extra > 0) {
                //Il massimo flusso calcolato è superiore alla domanda. Dobbiamo togliere il delta al flusso e alla capacità
                updateFlowsAndCapacitiesWhenMaxFlowGreaterThanDemand(maxFlowResponse, realSink);
                return;


            } else {
                //delta<0, Il massimo flusso è inferiore alla domanda. NON dobbiamo toglere il delta al flusso ma dobbiamo togliere il flusso  alla capacità
                realSink.setWeight(VertexConstant.CUMULATIVE_DEMAND, realSink.getWeight(VertexConstant.VERTEX_DEMAND) - maxFlowResponse.getMaxFlow());
                for (Edge edge : maxFlowResponse.getEdgesMaxFlow()) {
                    edge.setWeight(WeightConstant.CAPACITY, edge.getWeight(WeightConstant.CAPACITY) - edge.getValue(ValueConstant.FLOW));
                    if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) == null) {
                        edge.setValue(ValueConstant.CUMULATIVE_FLOW, edge.getValue(ValueConstant.FLOW));
                    } else {
                        edge.setValue(ValueConstant.CUMULATIVE_FLOW, edge.getValue(ValueConstant.CUMULATIVE_FLOW) + edge.getValue(ValueConstant.FLOW));
                    }

                    edge.setValue(ValueConstant.FLOW, 0.0);


                    System.out.println("");

                }
                MaxFlowResponse maxFlowResponse1 = FordFulkersonUtil.maxFlow(graph, graph.getVirtualSource().getId(), realSink.getId());
                updateFlowsAndCapacitiesRecursive(graph, maxFlowResponse1, realSink);
            }
        }

    }

     */

    /*
    public static void updateFlowsAndCapacitiesWhenMaxFlowGreaterThanDemand(MaxFlowResponse maxFlowResponse, Vertex realSink) {
        if (realSink.getWeight(VertexConstant.CUMULATIVE_DEMAND) == null) {
            realSink.setWeight(VertexConstant.CUMULATIVE_DEMAND, 0.0);
        }
        //Double extra = realSink.getWeight(VertexConstant.VERTEX_DEMAND)-realSink.getWeight(VertexConstant.CUMULATIVE_DEMAND)- maxFlowResponse.getMaxFlow();
        Double rimanente = maxFlowResponse.getMaxFlow() - realSink.getWeight(VertexConstant.VERTEX_DEMAND) - realSink.getWeight(VertexConstant.CUMULATIVE_DEMAND);
        while (rimanente > 0) {

            //calcola il numero di archi entranti nel real sink appartenenti al max flow
            int cnt = 0;
            for (Edge edge : maxFlowResponse.getEdgesMaxFlow()) {
                if (edge.getDestination() == realSink && edge.getValue(ValueConstant.FLOW) > 0) {
                    cnt++;
                }
            }


            Double minFlow = rimanente;
            for (Edge edge : maxFlowResponse.getEdgesMaxFlow()) {
                if (edge.getValue(ValueConstant.FLOW) > 0 && edge.getValue(ValueConstant.FLOW) < minFlow) {
                    minFlow = edge.getValue(ValueConstant.FLOW);
                }
            }
            Double aggiustamento = minFlow;
            for (Edge edge : maxFlowResponse.getEdgesMaxFlow()) {
                if (edge.getValue(ValueConstant.FLOW) >= aggiustamento) {
                    edge.setValue(ValueConstant.FLOW, edge.getValue(ValueConstant.FLOW) - aggiustamento);
                }
                System.out.println();
            }
            rimanente = rimanente - aggiustamento * cnt;
            System.out.println("RIMANENTE: " + rimanente + ", minFlow: " + minFlow + ", CNT: " + cnt + ", Aggiustamento: " + aggiustamento);
        }
        for (Edge edge : maxFlowResponse.getEdgesMaxFlow()) {
            if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) == null) {
                edge.setValue(ValueConstant.CUMULATIVE_FLOW, 0.0);
            }
            edge.setValue(ValueConstant.CUMULATIVE_FLOW, edge.getValue(ValueConstant.CUMULATIVE_FLOW) + edge.getValue(ValueConstant.FLOW));
            edge.setWeight(WeightConstant.CAPACITY, edge.getWeight(WeightConstant.ORIGINAL_CAPACITY) - edge.getValue(ValueConstant.CUMULATIVE_FLOW));
        }
        System.out.println("££££");
    }
*/
    /*
    public static void maxFlowPropagationPathsAdjustment(Graph graph, MaxFlowResponse maxFlowResponse, Vertex realSink) {

        Vertex virtualSource = graph.getVirtualSource();

        if (realSink.getWeight(VertexConstant.CUMULATIVE_DEMAND) == null) {
            realSink.setWeight(VertexConstant.CUMULATIVE_DEMAND, 0.0);
        }
        Double rimanente = maxFlowResponse.getMaxFlow() - realSink.getWeight(VertexConstant.VERTEX_DEMAND) - realSink.getWeight(VertexConstant.CUMULATIVE_DEMAND);


        while (rimanente > 0) {

            for (Edge edge : maxFlowResponse.getEdgesMaxFlow()) {
                edge.setValue(ValueConstant.COUNTED, 0.0);
            }
            List<Edge> list = new ArrayList<>();
            findMaxFlowPaths(graph, maxFlowResponse, realSink, list);

            updateLastArcsCount(maxFlowResponse, realSink);

            Double counted = 0.0;
            for (Edge edge : maxFlowResponse.getEdgesMaxFlow()) {
                if (edge.getSource().getId() == virtualSource.getId() && edge.getValue(ValueConstant.COUNTED) > 0) {
                    counted = counted + edge.getValue(ValueConstant.COUNTED);
                }
            }

            Double minFlow = rimanente;
            for (Edge edge : maxFlowResponse.getEdgesMaxFlow()) {
                if (edge.getValue(ValueConstant.FLOW) > 0 && edge.getValue(ValueConstant.FLOW) < minFlow) {
                    minFlow = edge.getValue(ValueConstant.FLOW);
                }
            }

            if (rimanente < minFlow * counted) {
                minFlow = (minFlow * counted - rimanente) / counted;
            }

            Double riduzione = minFlow;

            for (Edge edge : maxFlowResponse.getEdgesMaxFlow()) {
                if (edge.getValue(ValueConstant.FLOW) > 0) {
                    edge.setValue(ValueConstant.FLOW, edge.getValue(ValueConstant.FLOW) - riduzione * edge.getValue(ValueConstant.COUNTED));
                    System.out.println();
                }
            }

            rimanente = rimanente - minFlow * counted;
        }
        for (Edge edge : maxFlowResponse.getEdgesMaxFlow()) {
            if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) == null) {
                edge.setValue(ValueConstant.CUMULATIVE_FLOW, edge.getValue(ValueConstant.FLOW));
            } else {
                edge.setValue(ValueConstant.CUMULATIVE_FLOW, edge.getValue(ValueConstant.CUMULATIVE_FLOW) + edge.getValue(ValueConstant.FLOW));
            }
            Double originalCapacity = edge.getWeight(WeightConstant.ORIGINAL_CAPACITY);
            Double cumulativeFlow = edge.getValue(ValueConstant.CUMULATIVE_FLOW);
            edge.setWeight(WeightConstant.CAPACITY, originalCapacity - cumulativeFlow);
        }
        System.out.println("END UPDATING FLOW FOR SINK " + realSink.getId());

    }

     */

    /*
    public static void adjustFlow(Vertex vertex, MaxFlowResponse maxFlowResponse, Double riduzione, Vertex realSink) {
        if (vertex == realSink) {
            return;
        }

        int cnt = 0;
        for (Edge outEdge : vertex.getOutEdges()) {

            if (maxFlowResponse.getEdgesMaxFlow().contains(outEdge) && outEdge.getValue(ValueConstant.FLOW) > 0) {
                cnt++;
            }
        }
        Double forwardRiduzione = riduzione / cnt;
        for (Edge outEdge : vertex.getOutEdges()) {
            if (maxFlowResponse.getEdgesMaxFlow().contains(outEdge)) {
                if (outEdge.getValue(ValueConstant.FLOW) > 0) {
                    outEdge.setValue(ValueConstant.FLOW, outEdge.getValue(ValueConstant.FLOW) - forwardRiduzione);
                    adjustFlow(outEdge.getDestination(), maxFlowResponse, forwardRiduzione, realSink);
                }
            }
        }

    }

     */

    /*
    public static void findMaxFlowPaths(Graph graph, MaxFlowResponse maxFlowResponse, Vertex destination, List<Edge> path) {
        Vertex virtualSource = graph.getVirtualSource();

        for (Edge edge : maxFlowResponse.getEdgesMaxFlow()) {
            if (edge.getValue(ValueConstant.FLOW) > 0) {
                if (edge.getDestination().getId() == destination.getId()) {
                    path.add(edge);
                    if (edge.getValue(ValueConstant.COUNTED) == null) {
                        edge.setValue(ValueConstant.COUNTED, 1.0);
                    } else {
                        edge.setValue(ValueConstant.COUNTED, edge.getValue(ValueConstant.COUNTED) + 1.0);
                    }
                    if (edge.getSource().getId() == virtualSource.getId()) {
                        return;
                    } else {
                        findMaxFlowPaths(graph, maxFlowResponse, edge.getSource(), path);
                    }
                }
            }
        }
    }
*/
    /*
    public static List<List<Edge>> organizeMaxFlowPaths(Graph graph, Vertex realSink, List<Edge> list) {
        Vertex virtualSource = graph.getVirtualSource();
        List<List<Edge>> paths = new ArrayList<>();
        List<Edge> path = new ArrayList<>();
        for (Edge edge : list) {
            if (edge.getDestination().getId() == realSink.getId()) {
                path = new ArrayList<>();
                path.add(edge);
            } else {
                path.add(edge);
                if (edge.getSource().getId() == virtualSource.getId()) {
                    paths.add(path);
                }
            }
        }
        return paths;
    }
*/

    /*

    public static void updateLastArcsCount(MaxFlowResponse maxFlowResponse, Vertex realSink) {

        for (Edge edge : maxFlowResponse.getEdgesMaxFlow()) {
            if (edge.getDestination().getId() == realSink.getId()) {
                Double counted = 0.0;
                for (Edge edge1 : maxFlowResponse.getEdgesMaxFlow()) {
                    if (edge1.getDestination().getId() == edge.getSource().getId()) {
                        counted = counted + edge1.getValue(ValueConstant.COUNTED);
                    }
                }
                for (Edge edge1 : maxFlowResponse.getEdgesMaxFlow()) {
                    if (edge1.getSource().getId() == edge.getSource().getId() && edge1.getDestination().getId() != realSink.getId()) {
                        counted = counted - edge1.getValue(ValueConstant.COUNTED);
                    }
                }
                edge.setValue(ValueConstant.COUNTED, counted);
            }
        }
    }

     */

    public static void buildMaxFlowPaths(MaxFlowResponse maxFlowResponse, Vertex source, Vertex realSink, List<Edge> list, List<String> visited, String key) {

        for (Edge edge : source.getOutEdges()) {

            //System.out.println("SOURCE: " + source.getId());
            if (maxFlowResponse.getEdgesMaxFlow().contains(edge)) {
                if (edge.getValue(ValueConstant.FLOW) > 0) {
                    if (key.equalsIgnoreCase("")) {
                        key = source.getId() + "";
                    } else {
                        if (!key.endsWith("" + source.getId())) {
                            key = key + "_" + source.getId();
                        }
                    }
                    //System.out.println("Considering edge " + edge.getSource().getId() + "_" + edge.getDestination().getId());
                    list.add(edge);
                    if (edge.getDestination().getId() == realSink.getId()) {
                        String key1 = key + "_" + edge.getDestination().getId();
                        if (!visited.contains(key1)) {
                            visited.add(key1);
                        }
                    } else {
                        buildMaxFlowPaths(maxFlowResponse, edge.getDestination(), realSink, list, visited, key);
                    }
                }
            }
        }
    }

    public static void updateCountedOnArcs(Graph graph, List<String> paths) {
        for (String path : paths) {
            String[] nodes = path.split("_");
            for (int i = 0; i < nodes.length - 1; i++) {
                String key = nodes[i] + "_" + nodes[i + 1];
                Edge edge = graph.getEdgeMap().get(key);
                if (edge.getValue(ValueConstant.FLOW) > 0) {
                    if (edge.getValue(ValueConstant.COUNTED) == null) {
                        edge.setValue(ValueConstant.COUNTED, 1.0);
                    } else {
                        edge.setValue(ValueConstant.COUNTED, edge.getValue(ValueConstant.COUNTED) + 1.0);
                    }
                    //System.out.println("Edge: " + edge);
                }
            }
        }
    }

    public static void flowsAdjustmentOnMaxFlowPathsWhenMaxFlowIsGreaterThanDemand(Graph graph, MaxFlowResponse maxFlowResponse, Vertex realSink) {

        Vertex virtualSource = graph.getVirtualSource();

        if (realSink.getWeight(VertexConstant.CUMULATIVE_DEMAND) == null) {
            realSink.setWeight(VertexConstant.CUMULATIVE_DEMAND, 0.0);
        }
        Double rimanente = maxFlowResponse.getMaxFlow() - realSink.getWeight(VertexConstant.VERTEX_DEMAND) - realSink.getWeight(VertexConstant.CUMULATIVE_DEMAND);


        while (rimanente > 0) {

            for (Edge edge : maxFlowResponse.getEdgesMaxFlow()) {
                edge.setValue(ValueConstant.COUNTED, 0.0);
            }

            List<Edge> list = new ArrayList<>();
            List<String> visited = new ArrayList<>();
            String key = "";
            GraphUtil_OLD.buildMaxFlowPaths(maxFlowResponse, graph.getVirtualSource(), realSink, list, visited, key);
            GraphUtil_OLD.updateCountedOnArcs(graph, visited);


            Double counted = 0.0;
            for (Edge edge : maxFlowResponse.getEdgesMaxFlow()) {
                if (edge.getDestination().getId() == realSink.getId() && edge.getValue(ValueConstant.COUNTED) > 0) {
                    counted = counted + edge.getValue(ValueConstant.COUNTED);
                }
            }

            Double minFlow = rimanente;
            for (Edge edge : maxFlowResponse.getEdgesMaxFlow()) {
                if (edge.getValue(ValueConstant.FLOW) > 0 && edge.getValue(ValueConstant.FLOW) / edge.getValue(ValueConstant.COUNTED) < minFlow) {
                    minFlow = edge.getValue(ValueConstant.FLOW) / edge.getValue(ValueConstant.COUNTED);
                }
            }

            if (rimanente < minFlow * counted) {
                minFlow = rimanente / counted;
            }

            Double riduzione = minFlow;

            for (Edge edge : maxFlowResponse.getEdgesMaxFlow()) {
                if (edge.getValue(ValueConstant.FLOW) > 0) {
                    edge.setValue(ValueConstant.FLOW, edge.getValue(ValueConstant.FLOW) - riduzione * edge.getValue(ValueConstant.COUNTED));
                    //System.out.println();
                }
            }

            rimanente = rimanente - minFlow * counted;
            if (rimanente < 0) {
                //System.out.println();
            }
        }
        for (Edge edge : maxFlowResponse.getEdgesMaxFlow()) {
            if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) == null) {
                edge.setValue(ValueConstant.CUMULATIVE_FLOW, edge.getValue(ValueConstant.FLOW));
            } else {
                edge.setValue(ValueConstant.CUMULATIVE_FLOW, edge.getValue(ValueConstant.CUMULATIVE_FLOW) + edge.getValue(ValueConstant.FLOW));
            }
            Double originalCapacity = edge.getWeight(WeightConstant.ORIGINAL_CAPACITY);
            Double cumulativeFlow = edge.getValue(ValueConstant.CUMULATIVE_FLOW);
            edge.setWeight(WeightConstant.CAPACITY, originalCapacity - cumulativeFlow);
        }
        System.out.println("END UPDATING FLOW FOR SINK " + realSink.getId());

    }

    public static void updateFlowsInMaxFlow(Graph graph, MaxFlowResponse maxFlowResponse, Vertex realSink){

        if (realSink.getWeight(VertexConstant.CUMULATIVE_DEMAND) == null) {
            realSink.setWeight(VertexConstant.CUMULATIVE_DEMAND, 0.0);
        }
        Double extra = maxFlowResponse.getMaxFlow() - realSink.getWeight(VertexConstant.VERTEX_DEMAND) - realSink.getWeight(VertexConstant.CUMULATIVE_DEMAND);
        if(extra>0){
            //il massimo flusso al nodo realSink è maggiore della demand del nodo
            //dobbiamo aggiornare (diminuendoli) i flussi e le capacità degli archi coinvolti nel massimo flusso in modo
            //da rispettare la demand sul nodo realSink
            flowsAdjustmentOnMaxFlowPathsWhenMaxFlowIsGreaterThanDemand(graph, maxFlowResponse, realSink);
        } else{
            //il massimo flusso al nodo realSink è minore o uguale della sua demand
            //dobbiamo aggiornare solo le capacità su gli archi coinvolti nel massimo flow
            flowsAdjustmentOnMaxFlowPathsWhenMaxFlowIsLowerThanDemand(graph, maxFlowResponse, realSink);
        }

    }

    public static void flowsAdjustmentOnMaxFlowPathsWhenMaxFlowIsLowerThanDemand(Graph graph, MaxFlowResponse maxFlowResponse, Vertex realSink) {
        //in questo caso si aggiornano solo i cumulative flow e le capacità per tutti gli archi coinvolti nel MaxFlow
        for(Edge edge: maxFlowResponse.getEdgesMaxFlow()){
            if(edge.getValue(ValueConstant.CUMULATIVE_FLOW)==null){
                edge.setValue(ValueConstant.CUMULATIVE_FLOW, 0.0);
            }
            edge.setValue(ValueConstant.CUMULATIVE_FLOW, edge.getValue(ValueConstant.CUMULATIVE_FLOW)+ edge.getValue(ValueConstant.FLOW));
            edge.setWeight(WeightConstant.CAPACITY, edge.getWeight(WeightConstant.ORIGINAL_CAPACITY)- edge.getValue(ValueConstant.CUMULATIVE_FLOW));
        }

    }
}

package util;

import constant.TypeConstant;
import constant.ValueConstant;
import constant.VertexConstant;
import constant.WeightConstant;
import model.*;

import java.io.*;
import java.util.*;

public class GraphUtil {

    public GraphUtil() {
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

        //Integer numberOfEdges = g.getEdges().size();
        Integer numberOfEdges = 0;
        for (Edge e : g.getEdges()) {
            if (e.getId() > numberOfEdges) {
                numberOfEdges = e.getId();
            }
        }
        numberOfEdges += 1;

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
                    e.setType(TypeConstant.PIPELINE);
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
                    e.setType(TypeConstant.PIPELINE);
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


    public static void buildMaxFlowPaths(MaxFlowResponse maxFlowResponse, Vertex source, Vertex realSink, List<Edge> list, List<String> visited, String key) {

        for (Edge edge : source.getOutEdges()) {

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
        //System.out.println("Updating flows and capacity when MaxFlow is GREATER than Demand");
        Vertex virtualSource = graph.getVirtualSource();

        if (realSink.getWeight(VertexConstant.CUMULATIVE_DEMAND) == null) {
            realSink.setWeight(VertexConstant.CUMULATIVE_DEMAND, 0.0);
        }
        Double rimanente = maxFlowResponse.getMaxFlow() - realSink.getWeight(VertexConstant.VERTEX_DEMAND) - realSink.getWeight(VertexConstant.CUMULATIVE_DEMAND);

        List<String> visited = new ArrayList<>();
        while (rimanente > 0) {

            for (Edge edge : maxFlowResponse.getEdgesMaxFlow()) {
                edge.setValue(ValueConstant.COUNTED, 0.0);
            }

            List<Edge> list = new ArrayList<>();
            //List<String> visited = new ArrayList<>();
            visited = new ArrayList<>();
            String key = "";
            GraphUtil.buildMaxFlowPaths(maxFlowResponse, graph.getVirtualSource(), realSink, list, visited, key);
            GraphUtil.updateCountedOnArcs(graph, visited);
            //visited.forEach(x-> System.out.println("FLOW_PATH: "+x));

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
        //System.out.println("END UPDATING FLOW FOR SINK " + realSink.getId());


    }

    public static void updateFlowsInMaxFlow(Graph graph, MaxFlowResponse maxFlowResponse, Vertex realSink) {

        if (realSink.getWeight(VertexConstant.CUMULATIVE_DEMAND) == null) {
            realSink.setWeight(VertexConstant.CUMULATIVE_DEMAND, 0.0);
        }
        Double extra = maxFlowResponse.getMaxFlow() - realSink.getWeight(VertexConstant.VERTEX_DEMAND) - realSink.getWeight(VertexConstant.CUMULATIVE_DEMAND);
        if (extra > 0) {
            //il massimo flusso al nodo realSink è maggiore della demand del nodo
            //dobbiamo aggiornare (diminuendoli) i flussi e le capacità degli archi coinvolti nel massimo flusso in modo
            //da rispettare la demand sul nodo realSink
            flowsAdjustmentOnMaxFlowPathsWhenMaxFlowIsGreaterThanDemand(graph, maxFlowResponse, realSink);
        } else {
            //il massimo flusso al nodo realSink è minore o uguale della sua demand
            //dobbiamo aggiornare solo le capacità su gli archi coinvolti nel massimo flow
            flowsAdjustmentOnMaxFlowPathsWhenMaxFlowIsLowerThanDemand(graph, maxFlowResponse, realSink);
        }
        //showMaxFlow(maxFlowResponse, graph, realSink);

    }

    public static void flowsAdjustmentOnMaxFlowPathsWhenMaxFlowIsLowerThanDemand(Graph graph, MaxFlowResponse maxFlowResponse, Vertex realSink) {
        //in questo caso si aggiornano solo i cumulative flow e le capacità per tutti gli archi coinvolti nel MaxFlow
        //System.out.println("Updating flows and capacity when MaxFlow is LOWER than Demand");
        for (Edge edge : maxFlowResponse.getEdgesMaxFlow()) {
            if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) == null) {
                edge.setValue(ValueConstant.CUMULATIVE_FLOW, 0.0);
            }
            edge.setValue(ValueConstant.CUMULATIVE_FLOW, edge.getValue(ValueConstant.CUMULATIVE_FLOW) + edge.getValue(ValueConstant.FLOW));
            edge.setWeight(WeightConstant.CAPACITY, edge.getWeight(WeightConstant.ORIGINAL_CAPACITY) - edge.getValue(ValueConstant.CUMULATIVE_FLOW));
        }
        //System.out.println("END UPDATING FLOW FOR SINK " + realSink.getId());
    }

    public static void showResult(Graph augmentedGraph, Map<String, Double> sorted) {

        System.out.println("Flussi e Capacità sugli archi");
        for (Edge edge : augmentedGraph.getEdges()) {
            System.out.println("Edge " + edge.getId() + " from " + edge.getSource().getId() + " to " + edge.getDestination().getId() + " Cumulative Flow: " + edge.getValue(ValueConstant.CUMULATIVE_FLOW) + ", Capacity: " + edge.getWeight(WeightConstant.CAPACITY) + ", Original Capacity: " + edge.getWeight(WeightConstant.ORIGINAL_CAPACITY));
        }

        System.out.println("--------- Check sugli archi connessi ai realSink---------------");
        for (String key : sorted.keySet()) {
            Integer realsinkKey = Integer.valueOf(key.split("_")[1]);
            Vertex realSink = augmentedGraph.getVertexMap().get(realsinkKey);
            Double sinkDemand = realSink.getWeight(VertexConstant.VERTEX_DEMAND);
            Double sumOfFlow = 0.0;
            Double flussoUscente = 0.0;
            for (Edge edge : realSink.getOutEdges()) {
                Double flow = edge.getValue(ValueConstant.CUMULATIVE_FLOW) != null ? edge.getValue(ValueConstant.CUMULATIVE_FLOW) : 0.0;
                flussoUscente += flow;
            }
            System.out.println("Check flow on real Sink " + realSink.getId());
            for (Edge edge : augmentedGraph.getEdges()) {
                if (edge.getDestination().getId() == realSink.getId()) {
                    Double flow = edge.getValue(ValueConstant.CUMULATIVE_FLOW) != null ? edge.getValue(ValueConstant.CUMULATIVE_FLOW) : 0.0;
                    System.out.println("Edge " + edge.getId() + " from " + edge.getSource().getId() + " to " + edge.getDestination().getId() + " Cumulative Flow: " + edge.getValue(ValueConstant.CUMULATIVE_FLOW) + ", Capacity: " + edge.getWeight(WeightConstant.CAPACITY) + ", Original Capacity: " + edge.getWeight(WeightConstant.ORIGINAL_CAPACITY));
                    sumOfFlow += flow;
                }
            }
            System.out.println("Demand del Real Sink: " + sinkDemand + ", flusso Totale entrante: " + sumOfFlow + ", flusso uscente: " + flussoUscente + ", flusso netto: " + (sumOfFlow - flussoUscente));
            System.out.println();

        }

        System.out.println("--------- Check sugli archi connessi dal Virtual Source ai realSources---------------");
        Vertex virtualSource = augmentedGraph.getVirtualSource();
        Double flussoTotaleErogato = 0.0;
        Double flussoTotaleEntrante = 0.0;

        for (Edge edge : augmentedGraph.getEdges()) {
            if (edge.getSource().getId() == virtualSource.getId()) {
                Vertex realSource = edge.getDestination();
                Double realSourceDemand = realSource.getWeight(VertexConstant.VERTEX_DEMAND);
                Double flussoErogato = 0.0;
                Double flussoEntrante = 0.0;
                if (realSource.getOutEdges() != null) {
                    for (Edge edge1 : realSource.getOutEdges()) {
                        Double flow = edge1.getValue(ValueConstant.CUMULATIVE_FLOW) != null ? edge1.getValue(ValueConstant.CUMULATIVE_FLOW) : 0.0;
                        flussoErogato += flow;
                        flussoTotaleErogato += flow;
                    }
                }
                if (realSource.getInEdges() != null) {
                    for (Edge edge1 : realSource.getInEdges()) {
                        Double flow = edge1.getValue(ValueConstant.CUMULATIVE_FLOW) != null ? edge1.getValue(ValueConstant.CUMULATIVE_FLOW) : 0.0;
                        flussoEntrante += flow;
                        flussoTotaleEntrante += flow;

                    }
                }
                System.out.println("Demand del real Source[" + realSource.getId() + "]: " + realSourceDemand + ", flusso erogato: " + flussoErogato + ", flusso entrante: " + flussoEntrante + ", efficienza: " + (flussoErogato / realSourceDemand) * (-100) + " %");
            }
        }
        System.out.println("Flusso totale entrante nei Real Source: " + flussoTotaleEntrante);
        System.out.println("Flusso totale uscente dai Real Source: " + flussoTotaleErogato);
        System.out.println("Flusso netto sui real Source: " + (flussoTotaleEntrante - flussoTotaleErogato));


        Double flussoEntrante = 0.0;
        for (Edge edge : augmentedGraph.getEdges()) {
            if (edge.getDestination().getWeight(VertexConstant.VERTEX_DEMAND) != null && edge.getDestination().getWeight(VertexConstant.VERTEX_DEMAND) > 0) {
                if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) != null) {
                    flussoEntrante += edge.getValue(ValueConstant.CUMULATIVE_FLOW);
                }
            }
        }
        System.out.println("Flusso totale entrante nei Real Sink: " + flussoEntrante);

        Double flussoUscente = 0.0;
        for (Edge edge : augmentedGraph.getEdges()) {
            if (edge.getSource().getWeight(VertexConstant.VERTEX_DEMAND) != null && edge.getSource().getWeight(VertexConstant.VERTEX_DEMAND) > 0) {
                if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) != null) {
                    flussoUscente += edge.getValue(ValueConstant.CUMULATIVE_FLOW);
                }
            }
        }
        System.out.println("Flusso totale uscente dai Real Sink: " + flussoUscente);

        Double delta = flussoEntrante - flussoUscente;
        System.out.println("Flusso netto sui Real Sink: " + delta);
        Double totalSinkDemand = 0.0;
        Double totalGivenSinkDemand = 0.0;
        for (Edge edge : augmentedGraph.getEdges()) {
            if (edge.getDestination().getWeight(VertexConstant.VERTEX_DEMAND) != null && edge.getDestination().getWeight(VertexConstant.VERTEX_DEMAND) > 0) {
                totalSinkDemand += edge.getDestination().getWeight(VertexConstant.VERTEX_DEMAND);
                if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) != null) {
                    totalGivenSinkDemand += edge.getValue(ValueConstant.CUMULATIVE_FLOW);
                }

            }

        }
        System.out.println("Flusso totale atteso sui sink: " + totalSinkDemand);
        System.out.println("Flusso totale effettivo sui sink: " + totalGivenSinkDemand);
    }

    public static void initializeGraph(Graph graph) {
        for (Edge edge : graph.getEdges()) {
            edge.setValue(ValueConstant.CUMULATIVE_FLOW, 0.0);
            edge.setWeight(WeightConstant.ORIGINAL_CAPACITY, edge.getWeight(WeightConstant.CAPACITY));
            edge.setType(TypeConstant.PIPELINE);
        }
        for (Vertex vertex : graph.getVertexes()) {
            if (vertex.getWeight(VertexConstant.VERTEX_DEMAND) == null) {
                vertex.setWeight(VertexConstant.VERTEX_DEMAND, 0.0);
            }
        }
    }

    public static void buildMaxFlowPaths1(Vertex source, MaxFlowResponse maxFlowResponse, Vertex realSink) {

        System.out.println("SOURCE: " + source.getId());
        if (source.getId() == realSink.getId()) {
            for (Edge edge : source.getInEdges()) {
                if (edge.getValue(ValueConstant.FLOW) != null && edge.getValue(ValueConstant.FLOW) > 0) {

                }
            }
            return;
        }

        for (Edge outEdge : source.getOutEdges()) {
            if (outEdge.getValue(ValueConstant.FLOW) != null && outEdge.getValue(ValueConstant.FLOW) > 0) {
                if (source.getInEdges() == null || source.getInEdges().size() == 0) {
                    outEdge.getIncomingPaths().add(source.getId() + "");
                } else {
                    for (Edge inEdge : source.getInEdges()) {
                        if (inEdge.getValue(ValueConstant.FLOW) != null && inEdge.getValue(ValueConstant.FLOW) > 0) {
                            for (String path : inEdge.getIncomingPaths()) {
                                outEdge.getIncomingPaths().add(path + "_" + outEdge.getSource().getId());
                            }
                        }
                    }
                }
                Vertex destination = outEdge.getDestination();
                buildMaxFlowPaths1(destination, maxFlowResponse, realSink);
            }
        }
    }


    public static void writeResultToFile(Graph augmentedGraph, Map<String, Double> sorted, String graphName, String directoryName) throws IOException {

        String outputFileName = "";
        if (augmentedGraph.getName() != null) {
            outputFileName = augmentedGraph.getName();
        } else {
            outputFileName = graphName;
        }

        String newDir = directoryName + "/" + graphName;
        File theDir = new File(newDir);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }

        String resultName = newDir + "/" + outputFileName + "RESULT.csv";
        FileOutputStream fos = new FileOutputStream(new File(resultName));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));


        bw.write("Flussi e Capacità sugli archi");
        bw.newLine();
        for (Edge edge : augmentedGraph.getEdges()) {
            bw.write("Edge " + edge.getId() + " from " + edge.getSource().getId() + " to " + edge.getDestination().getId() + " Cumulative Flow: " + edge.getValue(ValueConstant.CUMULATIVE_FLOW) + ", Capacity: " + edge.getWeight(WeightConstant.CAPACITY) + ", Original Capacity: " + edge.getWeight(WeightConstant.ORIGINAL_CAPACITY));
            bw.newLine();
        }

        bw.write("--------- Check sugli archi connessi ai realSink---------------");
        bw.newLine();
        for (String key : sorted.keySet()) {
            Integer realsinkKey = Integer.valueOf(key.split("_")[1]);
            Vertex realSink = augmentedGraph.getVertexMap().get(realsinkKey);
            Double sinkDemand = realSink.getWeight(VertexConstant.VERTEX_DEMAND);
            Double sumOfFlow = 0.0;
            Double flussoUscente = 0.0;
            for (Edge edge : realSink.getOutEdges()) {
                Double flow = edge.getValue(ValueConstant.CUMULATIVE_FLOW) != null ? edge.getValue(ValueConstant.CUMULATIVE_FLOW) : 0.0;
                flussoUscente += flow;
            }
            bw.write("Check flow on real Sink " + realSink.getId());
            bw.newLine();
            for (Edge edge : augmentedGraph.getEdges()) {
                if (edge.getDestination().getId() == realSink.getId()) {
                    Double flow = edge.getValue(ValueConstant.CUMULATIVE_FLOW) != null ? edge.getValue(ValueConstant.CUMULATIVE_FLOW) : 0.0;
                    bw.write("Edge " + edge.getId() + " from " + edge.getSource().getId() + " to " + edge.getDestination().getId() + " Cumulative Flow: " + edge.getValue(ValueConstant.CUMULATIVE_FLOW) + ", Capacity: " + edge.getWeight(WeightConstant.CAPACITY) + ", Original Capacity: " + edge.getWeight(WeightConstant.ORIGINAL_CAPACITY));
                    bw.newLine();
                    sumOfFlow += flow;
                }
            }
            bw.write("Demand del Real SInk: " + sinkDemand + ", flusso Totale entrante: " + sumOfFlow + ", flusso uscente: " + flussoUscente);
            bw.newLine();

        }

        bw.write("--------- Check sugli archi connessi dal Virtual Source ai realSources---------------");
        bw.newLine();
        Vertex virtualSource = augmentedGraph.getVirtualSource();
        Double flussoTotaleErogato = 0.0;
        Double flussoTotaleEntrante = 0.0;

        for (Edge edge : augmentedGraph.getEdges()) {
            if (edge.getSource().getId() == virtualSource.getId()) {
                Vertex realSource = edge.getDestination();
                Double realSourceDemand = realSource.getWeight(VertexConstant.VERTEX_DEMAND);
                Double flussoErogato = 0.0;
                Double flussoEntrante = 0.0;
                if (realSource.getOutEdges() != null) {
                    for (Edge edge1 : realSource.getOutEdges()) {
                        Double flow = edge1.getValue(ValueConstant.CUMULATIVE_FLOW) != null ? edge1.getValue(ValueConstant.CUMULATIVE_FLOW) : 0.0;
                        flussoErogato += flow;
                        flussoTotaleErogato += flow;
                    }
                }
                if (realSource.getInEdges() != null) {
                    for (Edge edge1 : realSource.getInEdges()) {
                        Double flow = edge1.getValue(ValueConstant.CUMULATIVE_FLOW) != null ? edge1.getValue(ValueConstant.CUMULATIVE_FLOW) : 0.0;
                        flussoEntrante += flow;
                        flussoTotaleEntrante += flow;

                    }
                }
                bw.write("Demand del real Source[" + realSource.getId() + "]: " + realSourceDemand + ", flusso erogato: " + flussoErogato + ", flusso entrante: " + flussoEntrante + ", efficienza: " + (flussoErogato / realSourceDemand) * (-100) + " %");
                bw.newLine();
            }
        }
        bw.write("Flusso totale entrante nei Real Source: " + flussoTotaleEntrante);
        bw.newLine();
        bw.write("Flusso totale uscente dai Real Source: " + flussoTotaleErogato);
        bw.newLine();
        bw.write("Flusso netto sui real Source: " + (flussoTotaleEntrante - flussoTotaleErogato));
        bw.newLine();


        Double flussoEntrante = 0.0;
        for (Edge edge : augmentedGraph.getEdges()) {
            if (edge.getDestination().getWeight(VertexConstant.VERTEX_DEMAND) != null && edge.getDestination().getWeight(VertexConstant.VERTEX_DEMAND) > 0) {
                if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) != null) {
                    flussoEntrante += edge.getValue(ValueConstant.CUMULATIVE_FLOW);
                }
            }
        }
        bw.write("Flusso totale entrante nei Real Sink: " + flussoEntrante);
        bw.newLine();

        Double flussoUscente = 0.0;
        for (Edge edge : augmentedGraph.getEdges()) {
            if (edge.getSource().getWeight(VertexConstant.VERTEX_DEMAND) != null && edge.getSource().getWeight(VertexConstant.VERTEX_DEMAND) > 0) {
                if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) != null) {
                    flussoUscente += edge.getValue(ValueConstant.CUMULATIVE_FLOW);
                }
            }
        }
        bw.write("Flusso totale uscente dai Real Sink: " + flussoUscente);
        bw.newLine();

        Double delta = flussoEntrante - flussoUscente;
        bw.write("Flusso netto sui Real Sink: " + delta);
        bw.newLine();
        Double totalSinkDemand = 0.0;
        Double totalGivenSinkDemand = 0.0;
        for (Edge edge : augmentedGraph.getEdges()) {
            if (edge.getDestination().getWeight(VertexConstant.VERTEX_DEMAND) != null && edge.getDestination().getWeight(VertexConstant.VERTEX_DEMAND) > 0) {
                totalSinkDemand += edge.getDestination().getWeight(VertexConstant.VERTEX_DEMAND);
                if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) != null) {
                    totalGivenSinkDemand += edge.getValue(ValueConstant.CUMULATIVE_FLOW);
                }

            }

        }
        bw.write("Flusso totale atteso sui sink: " + totalSinkDemand);
        bw.newLine();
        bw.write("Flusso totale effettivo sui sink: " + totalGivenSinkDemand);
        bw.newLine();

        bw.close();
        fos.close();
    }


    public static void showMaxFlow(MaxFlowResponse maxFlowResponse, Graph graph, Vertex realSink) {
        Double updatedMaxFlow = 0.0;
        if (realSink.getInEdges() != null) {
            for (Edge inEdge : realSink.getInEdges()) {
                updatedMaxFlow = updatedMaxFlow + inEdge.getValue(ValueConstant.CUMULATIVE_FLOW);
            }
        } else {
            System.out.println("Real Sink " + realSink.getId() + " doesn't have incoming edges.");
        }

        System.out.println("FLOWS FROM SOURCE " + graph.getVirtualSource().getId() + " TO REAL SINK " + realSink.getId() + ", updatedMaxFlow: " + updatedMaxFlow + ", sink Demand: " + realSink.getWeight(VertexConstant.VERTEX_DEMAND));
        for (Edge edge : maxFlowResponse.getEdgesMaxFlow()) {
            if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) >= 0) {
                System.out.println("\t from " + edge.getSource().getId() + " to " + edge.getDestination().getId() + " FLOW: " + edge.getValue(ValueConstant.CUMULATIVE_FLOW) + ", CAPACITY: " + edge.getWeight(WeightConstant.CAPACITY) + ", ORIGINAL CAPACITY: " + edge.getWeight(WeightConstant.ORIGINAL_CAPACITY));
            }
        }
    }


    public static void test(Graph graph) {
        Integer sourceId = graph.getVirtualSource().getId();
        Integer sinkId = graph.getVirtualSink().getId();
        MaxFlowResponse finalMaxResponse = FordFulkersonUtil.maxFlow(graph, sourceId, sinkId);

        System.out.println("MAX FLOW TEST from " + sourceId + " to " + sinkId + ": " + finalMaxResponse.getMaxFlow());

        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        for (Edge edge : finalMaxResponse.getEdgesMaxFlow()) {
            if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) != null) {
                System.out.println("Edge(" + edge.getId() + ") [" + edge.getSource().getId() + " --> " + edge.getDestination().getId() + "] flow: " + edge.getValue(ValueConstant.CUMULATIVE_FLOW) + ", capacity: " + edge.getWeight(WeightConstant.CAPACITY) + ", original Capacity: " + edge.getWeight(WeightConstant.ORIGINAL_CAPACITY));
            }
        }
        System.out.println("---------------------------------------------------- " + graph.getEdges().size());
        for (Edge edge : graph.getEdges()) {
            if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) != null && edge.getValue(ValueConstant.CUMULATIVE_FLOW) > 0) {
                System.out.println("Edge(" + edge.getId() + ") [" + edge.getSource().getId() + " --> " + edge.getDestination().getId() + "] flow: " + edge.getValue(ValueConstant.CUMULATIVE_FLOW) + ", capacity: " + edge.getWeight(WeightConstant.CAPACITY) + ", original Capacity: " + edge.getWeight(WeightConstant.ORIGINAL_CAPACITY));
            }
        }

    }

    public static void showUsedArcsInFlow(Graph graph) {
        for (Edge edge : graph.getEdges()) {
            if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) != null && edge.getValue(ValueConstant.CUMULATIVE_FLOW) > 1.0E-10) {
                System.out.println("Arco [" + edge.getId() + "] (" + edge.getSource().getId() + "_" + edge.getDestination().getId() + ") Flusso: " + edge.getValue(ValueConstant.CUMULATIVE_FLOW) + ", Capacità Residua: " + edge.getWeight(WeightConstant.CAPACITY) + ", Capacità Originale: " + edge.getWeight(WeightConstant.ORIGINAL_CAPACITY));
            }
        }
    }


    public static void makeSymmetric(Graph graph) {
        //Integer max = graph.getEdges().size();
        Integer max = 0;
        for (Edge e : graph.getEdges()) {
            if (e.getId() > max) {
                max = e.getId();
            }
        }
        Integer i = 0;
        List<Edge> newEdges = new ArrayList<>();
        for (Edge edge : graph.getEdges()) {
            Vertex source = edge.getSource();
            //if (source.getWeight(VertexConstant.VERTEX_DEMAND) == null || source.getWeight(VertexConstant.VERTEX_DEMAND) > 0) {
            Vertex destination = edge.getDestination();
            Integer id = max + i;
            Edge e = new Edge();
            e.setId(id);
            e.setName("ARCO_" + id);
            e.setWeight(WeightConstant.LENGTH, edge.getWeight(WeightConstant.LENGTH));
            e.setWeight(WeightConstant.CAPACITY, edge.getWeight(WeightConstant.CAPACITY));
            e.setWeight(WeightConstant.ORIGINAL_CAPACITY, e.getWeight(WeightConstant.CAPACITY));
            e.setValue(ValueConstant.FLOW, 0.0);
            e.setValue(ValueConstant.CUMULATIVE_FLOW, 0.0);
            e.setSource(edge.getDestination());
            e.setDestination(edge.getSource());
            e.setBidirectional(false);
            e.setType(edge.getType());
            i++;
            newEdges.add(e);
            if (source.getInEdges() != null) {
                source.getInEdges().add(e);
            } else {
                List<Edge> inEdges = new ArrayList<>();
                inEdges.add(e);
                source.setInEdges(inEdges);
            }
            if (destination.getOutEdges() != null) {
                destination.getOutEdges().add(e);
            } else {
                List<Edge> outEdges = new ArrayList<>();
                outEdges.add(e);
                destination.setOutEdges(outEdges);
            }
            //}
        }
        newEdges.forEach(e -> graph.getEdges().add(e));
        graph.buildEdgeMap();
    }

    public static void removeArcsToRealSources(Graph graph) {
        /*
        List<Edge> edgesToBeRemoved = new ArrayList<>();
        for (Edge edge : graph.getEdges()) {
            Vertex destination = edge.getDestination();
            if (destination.getWeight(VertexConstant.VERTEX_DEMAND) != null && destination.getWeight(VertexConstant.VERTEX_DEMAND) < 0) {
                //if(!destination.getType().equalsIgnoreCase(TypeConstant.LNG)) {
                edgesToBeRemoved.add(edge);
                destination.getInEdges().remove(edge);
                //}
            }
        }
        edgesToBeRemoved.forEach(e -> graph.getEdges().remove(e));

         */
    }

    public static void removeArcsFromRealSinks(Graph graph) {
        List<Edge> edgesToBeRemoved = new ArrayList<>();
        for (Edge edge : graph.getEdges()) {
            Vertex source = edge.getSource();
            Vertex destination = edge.getDestination();
            if (source.getWeight(VertexConstant.VERTEX_DEMAND) != null && source.getWeight(VertexConstant.VERTEX_DEMAND) > 0) {
                if (destination.getWeight(VertexConstant.VERTEX_DEMAND) != null && destination.getWeight(VertexConstant.VERTEX_DEMAND) > 0) {
                    //if(destination.getWeight(VertexConstant.VERTEX_DEMAND) == null || destination.getWeight(VertexConstant.VERTEX_DEMAND) == 0){
                    source.getOutEdges().remove(edge);
                    destination.getInEdges().remove(edge);
                    edgesToBeRemoved.add(edge);
                }
            }
        }
        edgesToBeRemoved.forEach(e -> graph.getEdges().remove(e));

    }

    public static void finalFlowsUpdate(Graph graph) {
        System.out.println("FINAL FLOWS UPDATE");
        for (String key : graph.getEdgeMap().keySet()) {
            String from = key.split("_")[0];
            String to = key.split("_")[1];
            String reverseKey = to + "_" + from;
            Edge e1 = graph.getEdgeMap().get(key);
            Edge e2 = graph.getEdgeMap().get(reverseKey);


            Vertex source = e1.getSource();
            Vertex destination = e1.getDestination();

            //if((source.getWeight(VertexConstant.VERTEX_DEMAND)==null || source.getWeight(VertexConstant.VERTEX_DEMAND)==0.0) && (destination.getWeight(VertexConstant.VERTEX_DEMAND)==null || destination.getWeight(VertexConstant.VERTEX_DEMAND)==0.0)) {
            //if(source.getWeight(VertexConstant.VERTEX_DEMAND)!=null  && destination.getWeight(VertexConstant.VERTEX_DEMAND)!=null ) {
            //if ((source.getWeight(VertexConstant.VERTEX_DEMAND) > 0.0 && destination.getWeight(VertexConstant.VERTEX_DEMAND) > 0.0) || (source.getWeight(VertexConstant.VERTEX_DEMAND) == 0.0 && destination.getWeight(VertexConstant.VERTEX_DEMAND) == 0.0)) {
            if (e1 != null && e2 != null) {

                if (e1.getValue(ValueConstant.CUMULATIVE_FLOW) != null && e2.getValue(ValueConstant.CUMULATIVE_FLOW) != null) {
                    Double diff = 0.0;
                    if (e1.getValue(ValueConstant.CUMULATIVE_FLOW) > e2.getValue(ValueConstant.CUMULATIVE_FLOW)) {
                        diff = e1.getValue(ValueConstant.CUMULATIVE_FLOW) - e2.getValue(ValueConstant.CUMULATIVE_FLOW);
                        e1.setValue(ValueConstant.CUMULATIVE_FLOW, diff);
                        e1.setWeight(WeightConstant.CAPACITY, e1.getWeight(WeightConstant.ORIGINAL_CAPACITY) - diff);
                        e2.setValue(ValueConstant.CUMULATIVE_FLOW, 0.0);
                        e2.setWeight(WeightConstant.CAPACITY, e2.getWeight(WeightConstant.ORIGINAL_CAPACITY));

                    } else {
                        diff = e2.getValue(ValueConstant.CUMULATIVE_FLOW) - e1.getValue(ValueConstant.CUMULATIVE_FLOW);
                        e2.setValue(ValueConstant.CUMULATIVE_FLOW, diff);
                        e2.setWeight(WeightConstant.CAPACITY, e1.getWeight(WeightConstant.ORIGINAL_CAPACITY) - diff);
                        e1.setValue(ValueConstant.CUMULATIVE_FLOW, 0.0);
                        e1.setWeight(WeightConstant.CAPACITY, e2.getWeight(WeightConstant.ORIGINAL_CAPACITY));

                    }
                }
            }
            //}
            //}
        }
    }


    public static void checkEquilibriumOnNodes(Graph graph) {
        System.out.println("EUILIBRIO SUI NODI CHECK");
        for (Vertex vertex : graph.getVertexes()) {
            if (vertex.getWeight(VertexConstant.VERTEX_DEMAND) == null) {
                Double flowIn = 0.0;
                Double flowOut = 0.0;
                if (vertex.getInEdges() != null && vertex.getOutEdges() != null) {
                    for (Edge inEdge : vertex.getInEdges()) {
                        if (inEdge.getValue(ValueConstant.CUMULATIVE_FLOW) != null) {
                            flowIn += inEdge.getValue(ValueConstant.CUMULATIVE_FLOW);
                        }
                    }

                    for (Edge outEdge : vertex.getOutEdges()) {
                        if (outEdge.getValue(ValueConstant.CUMULATIVE_FLOW) != null) {
                            flowOut += outEdge.getValue(ValueConstant.CUMULATIVE_FLOW);
                        }
                    }
                    if (flowIn - flowOut != 0.0) {
                        System.out.println("!!!!! ATTENZIONE differenza di flusso: " + Math.abs(flowIn - flowOut) + " sul nodo " + vertex.getId());
                    }
                }
            }
        }
    }

    public static void showSinksStatistics(Graph graph) {
        System.out.println("--- SINKS STATISTICS ---");
        Double totalFlowIn = 0.0;
        Double totalFlowOut = 0.0;
        Double totalDemand = 0.0;
        for (Vertex vertex : graph.getVertexes()) {
            if (vertex.getWeight(VertexConstant.VERTEX_DEMAND) != null && vertex.getWeight(VertexConstant.VERTEX_DEMAND) > 0) {
                Double flowIn = 0.0;
                Double flowOut = 0.0;
                for (Edge e : vertex.getInEdges()) {
                    if (e.getValue(ValueConstant.CUMULATIVE_FLOW) != null) {
                        flowIn += e.getValue(ValueConstant.CUMULATIVE_FLOW);
                    }
                }
                totalFlowIn += flowIn;
                for (Edge e : vertex.getOutEdges()) {
                    if (e.getValue(ValueConstant.CUMULATIVE_FLOW) != null && e.getDestination().getId() != graph.getVirtualSink().getId()) {
                        //if (e.getValue(ValueConstant.CUMULATIVE_FLOW) != null) {
                        flowOut += e.getValue(ValueConstant.CUMULATIVE_FLOW);
                    }
                }
                totalFlowOut += flowOut;
                Double flowNet = flowIn - flowOut;
                Double maxError = 0.001;
                Double percentage = 0.0;
                if (flowNet < 0) {
                    System.out.println("Sink " + vertex.getId() + " FLUSSO USCENTE MAGGIORE DEL FLUSSO ENTRANTE: " + Math.abs(flowNet));
                }
                if (vertex.getWeight(VertexConstant.VERTEX_DEMAND) - flowNet > maxError) {
                    percentage = (flowNet / vertex.getWeight(VertexConstant.VERTEX_DEMAND)) * 100;
                    System.out.println("Sink " + vertex.getId() + " non è soddisfatto. Flusso netto: " + flowNet + ", Demand: " + vertex.getWeight(VertexConstant.VERTEX_DEMAND) + ", percentage: " + percentage);
                } else {
                    percentage = (flowNet / vertex.getWeight(VertexConstant.VERTEX_DEMAND)) * 100;
                    System.out.println("Sink " + vertex.getId() + " SODDISFATTO. Flusso netto: " + flowNet + ", Demand: " + vertex.getWeight(VertexConstant.VERTEX_DEMAND) + ", percentage: " + percentage);
                }
                totalDemand += vertex.getWeight(VertexConstant.VERTEX_DEMAND);
            }

        }
        System.out.println("Flusso totale entrante nei SINK: " + totalFlowIn);
        System.out.println("Flusso totale uscente nei SINK: " + totalFlowOut);
        System.out.println("Flusso totale netto nei SINK: " + (totalFlowIn - totalFlowOut));
        System.out.println("Total Demand dei SINK: " + totalDemand);
    }

    public static void isolateVerticesFromGraph(List<Vertex> vertices, Graph graph) {
        for (Vertex vertex : vertices) {
            if (vertex.getInEdges() != null) {
                for (Edge inEdge : vertex.getInEdges()) {
                    graph.getEdges().remove(inEdge);
                }
            }
            if (vertex.getOutEdges() != null) {
                for (Edge outEdge : vertex.getOutEdges()) {
                    graph.getEdges().remove(outEdge);
                }
            }
        }
        graph.build(graph.getVertexes(), graph.getEdges());
    }

    public static void removeArcsFromGraph(List<Edge> edges, Graph graph) {
        List<Edge> originalEdges = graph.getEdges();
        for (Edge edge : edges) {
            originalEdges.remove(edge);
        }
        graph.build(graph.getVertexes(), originalEdges);
    }

    public static void checkArcsInArcsOutForVertices(Graph graph) {
        System.out.println("Check archi entranti e uscenti sul vertice");
        for (Vertex vertex : graph.getVertexes()) {
            if (vertex.getInEdges() == null || vertex.getInEdges().size() == 0) {
                System.out.println("Node " + vertex.getName() + " non ha archi entranti...");
            }
            if (vertex.getOutEdges() == null || vertex.getOutEdges().size() == 0) {
                System.out.println("Node " + vertex.getName() + " non ha archi uscenti...");
            }
        }
    }

    public static void showSourceStatistics(Graph augmentedGraph) {
        System.out.println("--------- SOURCES STATISTICS ---------------");
        Vertex virtualSource = augmentedGraph.getVirtualSource();
        Double flussoTotaleErogato = 0.0;
        Double flussoTotaleEntrante = 0.0;

        for (Edge edge : augmentedGraph.getEdges()) {
            if (edge.getSource().getId() == virtualSource.getId()) {
                Vertex realSource = edge.getDestination();
                Double realSourceDemand = realSource.getWeight(VertexConstant.VERTEX_DEMAND);
                Double flussoErogato = 0.0;
                Double flussoEntrante = 0.0;
                if (realSource.getOutEdges() != null) {
                    for (Edge edge1 : realSource.getOutEdges()) {
                        Double flow = edge1.getValue(ValueConstant.CUMULATIVE_FLOW) != null ? edge1.getValue(ValueConstant.CUMULATIVE_FLOW) : 0.0;
                        flussoErogato += flow;
                        flussoTotaleErogato += flow;
                    }
                }
                if (realSource.getInEdges() != null) {
                    for (Edge edge1 : realSource.getInEdges()) {
                        Double flow = edge1.getValue(ValueConstant.CUMULATIVE_FLOW) != null ? edge1.getValue(ValueConstant.CUMULATIVE_FLOW) : 0.0;
                        flussoEntrante += flow;
                        flussoTotaleEntrante += flow;

                    }
                }
                System.out.println("Demand del real Source[" + realSource.getId() + "]: " + realSourceDemand + ", flusso erogato: " + flussoErogato + ", flusso entrante: " + flussoEntrante + ", efficienza: " + (flussoErogato / realSourceDemand) * (-100) + " %");
            }
        }
        System.out.println("Flusso totale entrante nei Real Source: " + flussoTotaleEntrante);
        System.out.println("Flusso totale uscente dai Real Source: " + flussoTotaleErogato);
        System.out.println("Flusso netto sui real Source: " + (flussoTotaleEntrante - flussoTotaleErogato));

    }

    public static String getResultsAsString(Graph augmentedGraph) {

        String result = "";

        result += "\n" + ("--------- SOURCES STATISTICS ---------------");
        Vertex virtualSource = augmentedGraph.getVirtualSource();
        Double flussoTotaleErogato = 0.0;
        Double flussoTotaleEntrante = 0.0;

        for (Edge edge : augmentedGraph.getEdges()) {
            if (edge.getSource().getId() == virtualSource.getId()) {
                Vertex realSource = edge.getDestination();
                Double realSourceDemand = realSource.getWeight(VertexConstant.VERTEX_DEMAND);
                Double flussoErogato = 0.0;
                Double flussoEntrante = 0.0;
                if (realSource.getOutEdges() != null) {
                    for (Edge edge1 : realSource.getOutEdges()) {
                        Double flow = edge1.getValue(ValueConstant.CUMULATIVE_FLOW) != null ? edge1.getValue(ValueConstant.CUMULATIVE_FLOW) : 0.0;
                        flussoErogato += flow;
                        flussoTotaleErogato += flow;
                    }
                }
                if (realSource.getInEdges() != null) {
                    for (Edge edge1 : realSource.getInEdges()) {
                        Double flow = edge1.getValue(ValueConstant.CUMULATIVE_FLOW) != null ? edge1.getValue(ValueConstant.CUMULATIVE_FLOW) : 0.0;
                        flussoEntrante += flow;
                        flussoTotaleEntrante += flow;

                    }
                }
                result += "\n" + "Demand del real Source[" + realSource.getId() + "]: " + realSourceDemand + ", flusso erogato: " + flussoErogato + ", flusso entrante: " + flussoEntrante + ", efficienza: " + (flussoErogato / realSourceDemand) * (-100) + " %";

            }
        }
        result += "\n" + "Flusso totale entrante nei Real Source: " + flussoTotaleEntrante;

        result += "\n" + "Flusso totale uscente dai Real Source: " + flussoTotaleErogato;

        result += "\n" + "Flusso netto sui real Source: " + (flussoTotaleEntrante - flussoTotaleErogato);


        result += "\n" + "--- SINKS STATISTICS ---";

        Double totalFlowIn = 0.0;
        Double totalFlowOut = 0.0;
        Double totalDemand = 0.0;
        Double numberOfSinks = 0.0;
        Double numberOfSatisfiedSinks = 0.0;

        for (Vertex vertex : augmentedGraph.getVertexes()) {
            if (vertex.getWeight(VertexConstant.VERTEX_DEMAND) != null && vertex.getWeight(VertexConstant.VERTEX_DEMAND) > 0) {
                Double flowIn = 0.0;
                Double flowOut = 0.0;
                numberOfSinks++;
                for (Edge e : vertex.getInEdges()) {
                    if (e.getValue(ValueConstant.CUMULATIVE_FLOW) != null) {
                        flowIn += e.getValue(ValueConstant.CUMULATIVE_FLOW);
                    }
                }
                totalFlowIn += flowIn;
                for (Edge e : vertex.getOutEdges()) {
                    if (e.getValue(ValueConstant.CUMULATIVE_FLOW) != null && e.getDestination().getId() != augmentedGraph.getVirtualSink().getId()) {
                        //if (e.getValue(ValueConstant.CUMULATIVE_FLOW) != null) {
                        flowOut += e.getValue(ValueConstant.CUMULATIVE_FLOW);
                    }
                }
                totalFlowOut += flowOut;
                Double flowNet = flowIn - flowOut;
                Double maxError = 0.001;
                Double percentage = 0.0;
                if (flowNet < 0) {
                    result += "\n" + "Sink " + vertex.getId() + " FLUSSO USCENTE MAGGIORE DEL FLUSSO ENTRANTE: " + Math.abs(flowNet);

                }
                if (vertex.getWeight(VertexConstant.VERTEX_DEMAND) - flowNet > maxError) {
                    percentage = (flowNet / vertex.getWeight(VertexConstant.VERTEX_DEMAND)) * 100;
                    result += "\n" + "Sink " + vertex.getId() + " non è soddisfatto. Flusso netto: " + flowNet + ", Demand: " + vertex.getWeight(VertexConstant.VERTEX_DEMAND) + ", percentage: " + percentage;

                } else {
                    percentage = (flowNet / vertex.getWeight(VertexConstant.VERTEX_DEMAND)) * 100;
                    result += "\n" + "Sink " + vertex.getId() + " SODDISFATTO. Flusso netto: " + flowNet + ", Demand: " + vertex.getWeight(VertexConstant.VERTEX_DEMAND) + ", percentage: " + percentage;
                    numberOfSatisfiedSinks++;
                }
                totalDemand += vertex.getWeight(VertexConstant.VERTEX_DEMAND);
            }
        }
        result += "\nFlusso totale entrante nei SINK: " + totalFlowIn;
        result += "\nFlusso totale uscente nei SINK: " + totalFlowOut;
        result += "\nFlusso totale netto nei SINK: " + (totalFlowIn - totalFlowOut);
        result += "\nTotal DEMAND dei SINK: " + totalDemand;
        result += "\nPercentual di flusso ai SINK: " + (totalFlowIn - totalFlowOut) / totalDemand;

        result += "\nNumero di Sink soddisfatti: " + numberOfSatisfiedSinks + ", numero totale di sink: " + numberOfSinks;
        result += "\nPercentuale di Sink soddisfatti: " + numberOfSatisfiedSinks / numberOfSinks;
        return result + "\n";

    }

    public static Double getTotalDemand(Graph graph) {
        Double totalDemand = 0.0;
        for (Vertex vertex : graph.getVertexes()) {
            if (vertex.getWeight(VertexConstant.VERTEX_DEMAND) != null && vertex.getWeight(VertexConstant.VERTEX_DEMAND) > 0) {
                totalDemand += vertex.getWeight(VertexConstant.VERTEX_DEMAND);
            }
        }
        return totalDemand;
    }

    public static Double getNetFlowOnSinks(Graph graph) {
        Double flowIn = 0.0;
        Double flowOut = 0.0;
        Double flowNet = 0.0;
        for (Vertex vertex : graph.getVertexes()) {
            if (vertex.getWeight(VertexConstant.VERTEX_DEMAND) != null && vertex.getWeight(VertexConstant.VERTEX_DEMAND) > 0) {
                if (vertex.getInEdges() != null) {
                    for (Edge edge : vertex.getInEdges()) {
                        if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) != null) {
                            flowIn += edge.getValue(ValueConstant.CUMULATIVE_FLOW);
                        }
                    }
                }
                if (vertex.getOutEdges() != null) {
                    for (Edge edge : vertex.getOutEdges()) {
                        Vertex destination = edge.getDestination();
                        if (destination != graph.getVirtualSink()) {
                            if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) != null) {
                                flowOut += edge.getValue(ValueConstant.CUMULATIVE_FLOW);
                            }
                        }
                    }
                }
            }
        }
        flowNet = flowIn - flowOut;
        return flowNet;
    }

    public static void createCumulativeDistribution(Map<String, Map<String, Double>> CDF, Graph graph, Double totalDemand, Double flowNet) {
        if (CDF.get(graph.getName()) == null) {
            Map<String, Double> percentage = new HashMap<>();
            percentage.put(".1", 0.0);
            percentage.put(".2", 0.0);
            percentage.put(".3", 0.0);
            percentage.put(".4", 0.0);
            percentage.put(".5", 0.0);
            percentage.put(".6", 0.0);
            percentage.put(".7", 0.0);
            percentage.put(".8", 0.0);
            percentage.put(".9", 0.0);
            percentage.put("1", 0.0);
            CDF.put(graph.getName(), percentage);
        }
        Double pp = flowNet / totalDemand;
        Map<String, Double> percentage = CDF.get(graph.getName());
        if (pp < .1) {
            percentage.put(".1", percentage.get(".1") + 1.0);
        }
        if (pp < .2) {
            percentage.put(".2", percentage.get(".2") + 1.0);
        }
        if (pp < .3) {
            percentage.put(".3", percentage.get(".3") + 1.0);
        }
        if (pp < .4) {
            percentage.put(".4", percentage.get(".4") + 1.0);
        }
        if (pp < .5) {
            percentage.put(".5", percentage.get(".5") + 1.0);
        }
        if (pp < .6) {
            percentage.put(".6", percentage.get(".6") + 1.0);
        }
        if (pp < .7) {
            percentage.put(".7", percentage.get(".7") + 1.0);
        }
        if (pp < .8) {
            percentage.put(".8", percentage.get(".8") + 1.0);
        }
        if (pp < .9) {
            percentage.put(".9", percentage.get(".9") + 1.0);
        }
        if (pp < 1.0) {
            percentage.put("1", percentage.get("1") + 1.0);
        }


    }

    public static void writeStatisticOnFile(List<Double> gasReceivedPercentage, List<Double> satisfiedSinksPercentage, String directoryName) throws IOException {


        String newDir = directoryName + "/STATISTICS";
        File theDir = new File(newDir);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        String resultName = newDir + "/STATISTICS.csv";
        FileOutputStream fos = new FileOutputStream(new File(resultName));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        for (int day = 0; day < gasReceivedPercentage.size(); day++) {
            bw.write("\nDAY: " + day + ", GasOnDestination: " + gasReceivedPercentage.get(day) + "%, SatisfiedDestination:"+satisfiedSinksPercentage.get(day)+"%");
        }
        bw.newLine();
        bw.close();
        fos.close();
    }

    public static void writeCDFonFile(Map<String, Map<String, Double>> CDF, String directoryName) throws IOException {
        //String newDir = directoryName + "/SIMULAZIONE_" + new Date(System.currentTimeMillis()).toString();
        String newDir = directoryName + "/CDFs";
        File theDir = new File(newDir);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }

        String resultName = newDir + "/CDF.csv";
        FileOutputStream fos = new FileOutputStream(new File(resultName));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));


        for (String key : CDF.keySet()) {
            bw.write("\nCumulative Distribution Function CASE: " + key);
            bw.newLine();
            Map<String, Double> pp = CDF.get(key);
            for (String ppKey : pp.keySet()) {
                bw.write("\n" + ppKey + ": " + pp.get(ppKey));
            }

        }
        bw.newLine();
        bw.close();
        fos.close();
    }

    public static void writeCDFonFile(Map<String, Map<String, Double>> CDF, String directoryName, Integer index) throws IOException {

        //String newDir = directoryName + "/SIMULAZIONE_" + index + "___" + new Date(System.currentTimeMillis()).toString();

        String newDir = directoryName + "/CDFs";
        File theDir = new File(newDir);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }


        String resultName = newDir + "/CDF_" + index + ".csv";
        //String resultName = directoryName + "/CDF_"+index+".csv";
        FileOutputStream fos = new FileOutputStream(new File(resultName));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));


        for (String key : CDF.keySet()) {
            bw.write("\nCumulative Distribution Function CASE: " + key);
            bw.newLine();
            Map<String, Double> pp = CDF.get(key);
            for (String ppKey : pp.keySet()) {
                bw.write("\n" + ppKey + ": " + pp.get(ppKey));
            }

        }
        bw.newLine();
        bw.close();
        fos.close();
    }

    public static void showSinksStatistics(Graph graph, Map<String, Double> sorted) {
        System.out.println("--- SINKS STATISTICS ---");
        Double totalFlowIn = 0.0;
        Double totalFlowOut = 0.0;
        Double totalDemand = 0.0;
        List<Vertex> satisfiedSinks = new ArrayList<>();
        Integer numberOfRealSink = 0;
        //for (Vertex vertex : graph.getVertexes()) {
        for (String key : sorted.keySet()) {
            Integer vertexKey = Integer.valueOf(key.split("_")[1]);
            Vertex vertex = graph.getVertexMap().get(vertexKey);
            if (vertex.getWeight(VertexConstant.VERTEX_DEMAND) != null && vertex.getWeight(VertexConstant.VERTEX_DEMAND) > 0) {
                numberOfRealSink++;
                Double flowIn = 0.0;
                Double flowOut = 0.0;
                for (Edge e : vertex.getInEdges()) {
                    if (e.getValue(ValueConstant.CUMULATIVE_FLOW) != null) {
                        flowIn += e.getValue(ValueConstant.CUMULATIVE_FLOW);
                    }
                }
                totalFlowIn += flowIn;
                for (Edge e : vertex.getOutEdges()) {
                    if (e.getValue(ValueConstant.CUMULATIVE_FLOW) != null && e.getDestination().getId() != graph.getVirtualSink().getId()) {
                        //if (e.getValue(ValueConstant.CUMULATIVE_FLOW) != null) {
                        flowOut += e.getValue(ValueConstant.CUMULATIVE_FLOW);
                    }
                }
                totalFlowOut += flowOut;
                Double flowNet = flowIn - flowOut;
                Double maxError = 0.001;
                Double percentage = 0.0;
                if (flowNet < 0) {
                    System.out.println("Sink " + vertex.getId() + " FLUSSO USCENTE MAGGIORE DEL FLUSSO ENTRANTE: " + Math.abs(flowNet));
                }
                if (vertex.getWeight(VertexConstant.VERTEX_DEMAND) - flowNet > maxError) {
                    percentage = (flowNet / vertex.getWeight(VertexConstant.VERTEX_DEMAND)) * 100;
                    System.out.println("Sink " + vertex.getId() + " [" + vertex.getName() + "] non è soddisfatto. Flusso netto: " + flowNet + ", Demand: " + vertex.getWeight(VertexConstant.VERTEX_DEMAND) + ", percentage: " + percentage);
                } else {
                    percentage = (flowNet / vertex.getWeight(VertexConstant.VERTEX_DEMAND)) * 100;
                    System.out.println("Sink " + vertex.getId() + " [" + vertex.getName() + "] SODDISFATTO. Flusso netto: " + flowNet + ", Demand: " + vertex.getWeight(VertexConstant.VERTEX_DEMAND) + ", percentage: " + percentage);
                    satisfiedSinks.add(vertex);
                }
                totalDemand += vertex.getWeight(VertexConstant.VERTEX_DEMAND);
            }

        }
        System.out.println("Flusso totale entrante nei SINK: " + totalFlowIn);
        System.out.println("Flusso totale uscente nei SINK: " + totalFlowOut);
        System.out.println("Flusso totale netto nei SINK: " + (totalFlowIn - totalFlowOut));
        System.out.println("Total Demand dei SINK: " + totalDemand);
        System.out.println("Percentuale di flusso ai SINK: " + (totalFlowIn - totalFlowOut) / totalDemand);
        System.out.println("Numero di sink soddisfatti: " + satisfiedSinks.size() + ", numero totale di sink: " + numberOfRealSink);
        System.out.println("Percentuale di Sink soddisfatti: " + Double.valueOf(satisfiedSinks.size()) / Double.valueOf(numberOfRealSink));
    }

    public static void showRealDestinationsOrdered(Graph graph) {
        Map<String, DijkstraResponse> distances = new HashMap<>();
        Graph augmentedGraph = GraphUtil.addVirtualSourceAndSink(graph);
        //System.out.println("Distanze dei percorsi minimi dal virtual source verso ogni real sink");
        for (Vertex v : graph.getVertexes()) {
            if (v.getWeight(VertexConstant.VERTEX_DEMAND) != null && v.getWeight(VertexConstant.VERTEX_DEMAND) > 0.0) {
                DijkstraResponse dr = DijkstraUtil.findMultipleShortestPathsWithEdges(augmentedGraph, augmentedGraph.getVirtualSource().getId(), v.getId());
                distances.put(augmentedGraph.getVirtualSource().getId() + "_" + v.getId(), dr);
            }
        }
        Map<String, Double> dist = new HashMap<>();
        for (String key : distances.keySet()) {
            dist.put(key, distances.get(key).getLength());
        }

        Map<String, Double> sorted = GenericUtil.sortByValue(dist);
        for (String key : sorted.keySet()) {
            System.out.println(key + ": " + sorted.get(key));
        }

        showSinksStatistics(augmentedGraph, sorted);
    }

    /**
     * Elimina gli archi dai real sink verso il virtual sink tranne quello relativo al closest sink
     *
     * @param graph
     * @param closestSink
     */
    public static void updateCapacitiesOnArcsToVirtualSink(Graph graph, Vertex closestSink) {
        Vertex virtualSink = graph.getVirtualSink();
        for (Vertex v : graph.getVertexes()) {
            if (v.getWeight(VertexConstant.VERTEX_DEMAND) != null && v.getWeight(VertexConstant.VERTEX_DEMAND) > 0) {
                if (v.getOutEdges() != null) {
                    for (Edge e : v.getOutEdges()) {
                        if (e.getDestination() == virtualSink) {
                            if (e.getValue(ValueConstant.CUMULATIVE_FLOW) == null) {
                                e.setValue(ValueConstant.CUMULATIVE_FLOW, 0.0);
                            }

                            if (v == closestSink) {
                                e.setWeight(WeightConstant.CAPACITY, e.getWeight(WeightConstant.ORIGINAL_CAPACITY) - e.getValue(ValueConstant.CUMULATIVE_FLOW));
                            } else {

                                e.setWeight(WeightConstant.CAPACITY, 0.0);
                            }
                        }
                    }
                }
            }
        }
    }

    public static Double getNumberOfSatisfiedSinks(Graph graph) {
        Double cnt = 0.0;
        Double maxError = 0.001;
        for (Vertex vertex : graph.getVertexes()) {
            Double flowIn = 0.0;
            Double flowOut = 0.0;
            Double flowNet = 0.0;
            if (vertex.getWeight(VertexConstant.VERTEX_DEMAND) != null && vertex.getWeight(VertexConstant.VERTEX_DEMAND) > 0) {
                if (vertex.getInEdges() != null) {
                    for (Edge edge : vertex.getInEdges()) {
                        if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) != null) {
                            flowIn += edge.getValue(ValueConstant.CUMULATIVE_FLOW);
                        }
                    }
                }
                if (vertex.getOutEdges() != null) {
                    for (Edge edge : vertex.getOutEdges()) {
                        Vertex destination = edge.getDestination();
                        if (destination != graph.getVirtualSink()) {
                            if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) != null) {
                                flowOut += edge.getValue(ValueConstant.CUMULATIVE_FLOW);
                            }
                        }
                    }
                }
                flowNet = flowIn - flowOut;
                if (Math.abs(flowNet - vertex.getWeight(VertexConstant.VERTEX_DEMAND)) <= maxError) {
                    cnt=cnt+1.0;
                }
            }
        }

        return cnt;
    }

    public static Double getNumberOfSinks(Graph graph) {
        Double cnt = 0.0;
        for (Vertex vertex : graph.getVertexes()) {
            if (vertex.getWeight(VertexConstant.VERTEX_DEMAND) != null && vertex.getWeight(VertexConstant.VERTEX_DEMAND) > 0) {
                cnt++;
            }
        }
        return cnt;
    }

    public static void writeFinalCDFonFile(Map<String, Map<String, Double>> CDF, String directoryName) throws IOException {
        //String newDir = directoryName + "/SIMULAZIONE_" + new Date(System.currentTimeMillis()).toString();
        String newDir = directoryName + "/CDFs";
        File theDir = new File(newDir);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }

        String resultName = newDir + "/CDF_FINAL.csv";
        FileOutputStream fos = new FileOutputStream(new File(resultName));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));


        for (String key : CDF.keySet()) {
            bw.write("\nCumulative Distribution Function CASE: " + key);
            bw.newLine();
            Map<String, Double> pp = CDF.get(key);
            for (String ppKey : pp.keySet()) {
                bw.write("\n" + ppKey + ": " + pp.get(ppKey));
            }

        }
        bw.newLine();
        bw.close();
        fos.close();
    }


    public static String createFileMask(){
        Date date = new Date(System.currentTimeMillis());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Integer day = calendar.get(Calendar.DAY_OF_MONTH);
        Integer month = calendar.get(Calendar.MONTH)+1;
        Integer year = calendar.get(Calendar.YEAR);
        Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
        Integer min = calendar.get(Calendar.MINUTE);
        Integer sec = calendar.get(Calendar.SECOND);
        String mask = day+"-"+month+"-"+year+"_"+hour+""+min+""+sec;
        return mask;
    }

    public static void updateFlowOnArcsToVirtualDestination(Graph graph){
        Vertex virtualSink = graph.getVirtualSink();

        for(Vertex vertex: graph.getVertexes()){
            if(vertex.getWeight(VertexConstant.VERTEX_DEMAND)!=null && vertex.getWeight(VertexConstant.VERTEX_DEMAND)>0){
                Double flowNet = vertex.getFlowNet();
                if(vertex.getOutEdges()!=null){
                    for(Edge edge: vertex.getOutEdges()){
                        Vertex destination = edge.getDestination();
                        if(destination.getId() == virtualSink.getId()){
                            edge.setValue(ValueConstant.CUMULATIVE_FLOW, flowNet);
                            edge.setValue(ValueConstant.FLOW, flowNet);
                        }
                    }
                }
            }
        }
    }
}

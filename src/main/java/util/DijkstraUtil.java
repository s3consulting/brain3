package util;

import constant.ValueConstant;
import constant.WeightConstant;
import model.*;

import java.util.*;
import java.util.stream.Collectors;

public class DijkstraUtil {

    // A utility function to find the vertex with minimum distance value,
    // from the set of vertices not yet included in shortest path tree
    public static int minDistanceIndex(double dist[], Boolean visitedNodes[]) {
        // Initialize min value
        double min = Integer.MAX_VALUE;
        int min_index = -1;
        int n = dist.length;

        for (int v = 0; v < n; v++) {
            if (visitedNodes[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
        }
        return min_index;
    }

    public static List<List<Integer>> buildPathRecursive(double[][] a, int source, int destination, List<Integer> path, double[] distances, List<Integer> visitedNodes, List<List<Integer>> paths) {
        if (destination != source) {
            List<Integer> predecessors = getPredecessors(a, destination, visitedNodes);
            List<Integer> pathNew = new ArrayList<>();
            for (Integer n : path) {
                pathNew.add(n);
            }
            for (Integer predecessor : predecessors) {
                if (a[predecessor][destination] + distances[predecessor] <= distances[destination]) {
                    if (predecessor == source) {
                        path.add(new Integer(source));
                        paths.add(buildSinglePath(path));
                        //String pathStr = path.stream().map(p->String.valueOf(p)).collect(Collectors.joining("->"));
                        //System.out.println(pathStr);
                        path.remove(new Integer(source));

                    } else {
                        pathNew.add(predecessor);
                        List<Integer> visitedNodesNew = new ArrayList<>();
                        for (Integer vn : visitedNodes) {
                            visitedNodesNew.add(vn);
                        }
                        visitedNodesNew.add(new Integer(predecessor));
                        buildPathRecursive(a, source, predecessor, pathNew, distances, visitedNodesNew, paths);
                    }
                }
            }
            path.remove(new Integer(destination));
        }
        return paths;
    }

    public static List<Integer> getPredecessors(double[][] a, int node, List<Integer> visitedNodes) {
        List<Integer> predecessors = new ArrayList<>();
        int j;
        int n = a.length;
        for (j = 0; j < n; j++) {
            //if (a[node][j] != 0 && !visitedNodes.contains(new Integer(j))) {
            if (a[j][node] != 0 && !visitedNodes.contains(new Integer(j))) {
                predecessors.add(new Integer(j));
            }
        }
        return predecessors;
    }

    public static double[] findShortestPaths(double graph[][], int src) {

        int n = graph.length;
        double dist[] = new double[n];

        Boolean visitedNodes[] = new Boolean[n];


        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MAX_VALUE;
            visitedNodes[i] = false;
        }


        dist[src] = 0;

        // Find shortest path for all vertices
        for (int count = 0; count < n; count++) {
            int u = minDistanceIndex(dist, visitedNodes);
            // Mark the picked vertex as processed
            visitedNodes[u] = true;

            // Update dist value of the adjacent vertices of the
            // picked vertex.
            for (int v = 0; v < n; v++)
                if (!visitedNodes[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                }
        }

        return dist;

    }

    public static boolean checkPathsBetween(double[][] a, int source, int destination) {
        double[] distances = findShortestPaths(a, source);
        List<List<Integer>> paths = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        path.add(new Integer(destination));
        List<Integer> visitedNodes = new ArrayList<>();
        visitedNodes.add(new Integer(destination));

        List<List<Integer>> finalPaths = buildPathRecursive(a, source, destination, path, distances, visitedNodes, paths);
        if (finalPaths.size() == 0) {
            return false;
        }
        return true;
    }

    public static List<List<Integer>> findMultipleShortestPaths(double[][] a, int source, int destination) {


        double[] distances = findShortestPaths(a, source);
        List<List<Integer>> paths = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        path.add(new Integer(destination));
        List<Integer> visitedNodes = new ArrayList<>();
        visitedNodes.add(new Integer(destination));

        List<List<Integer>> finalPaths = buildPathRecursive(a, source, destination, path, distances, visitedNodes, paths);

        System.out.println("Final Paths");
        for (List<Integer> list : finalPaths) {
            System.out.println(list);
        }

        return finalPaths;
    }

    public static List<List<Integer>> findMultipleShortestPaths(Graph graph, int source, int destination) {
        double[][] a = graph.getLengthMatrix();
        double[] distances = findShortestPaths(a, source);
        List<List<Integer>> paths = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        path.add(new Integer(destination));
        List<Integer> visitedNodes = new ArrayList<>();
        visitedNodes.add(new Integer(destination));

        List<List<Integer>> finalPaths = buildPathRecursive(a, source, destination, path, distances, visitedNodes, paths);
        System.out.println("Final Paths");
        Double minimalLength = 0.0;

        List<List<Edge>> edgePaths = new ArrayList<>();
        System.out.println("Minimum Length Paths from " + source + " to " + destination);
        for (List<Integer> list : finalPaths) {
            System.out.print(list);
            List<Edge> ep = DijkstraUtil.fromIntegerToEdges(graph, list);
            Double length = ep.stream().mapToDouble(x -> x.getWeight(WeightConstant.LENGTH)).sum();
            System.out.println(" Length: " + length);
        }

        edgePaths = DijkstraUtil.fromIntegerPathsToEdgePaths(graph, finalPaths);

        return finalPaths;
    }

    public static DijkstraResponse findMultipleShortestPathsWithEdges(Graph graph, int source, int destination) {
        double[][] a = graph.getLengthMatrix();
        double[] distances = findShortestPaths(a, source);
        List<List<Integer>> paths = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        path.add(new Integer(destination));
        List<Integer> visitedNodes = new ArrayList<>();
        visitedNodes.add(new Integer(destination));

        List<List<Integer>> finalPaths = buildPathRecursive(a, source, destination, path, distances, visitedNodes, paths);
        //System.out.println("Final Paths");
        Double minimalLength = 0.0;

        List<List<Edge>> edgePaths = new ArrayList<>();
        Double length = 0.0;

        for (List<Integer> list : finalPaths) {
            //System.out.println("Minimum Length Paths from "+source+" to "+destination);
            System.out.print(list);
            List<Edge> ep = DijkstraUtil.fromIntegerToEdges(graph, list);
            length = ep.stream().mapToDouble(x -> x.getWeight(WeightConstant.LENGTH)).sum();
            System.out.println(" Length: " + length);
        }

        edgePaths = DijkstraUtil.fromIntegerPathsToEdgePaths(graph, finalPaths);

        DijkstraResponse dijkstraResponse = new DijkstraResponse();
        dijkstraResponse.setEdgePaths(edgePaths);
        dijkstraResponse.setNodePaths(finalPaths);
        dijkstraResponse.setLength(length == 0 ? Double.MAX_VALUE : length);
        return dijkstraResponse;
    }



    public static List<Integer> buildSinglePath(List<Integer> path) {
        List<Integer> list = Arrays.asList(new Integer[path.size()]);
        Collections.copy(list, path);
        Collections.reverse(list);
        return list;
    }

    public static Map<String, List<List<Integer>>> findMultipleShortestPaths(Graph graph) {
        double[][] a = graph.getAdjacency();
        Map<String, List<List<Integer>>> allPaths = new HashMap<>();
        for (Vertex vIndex : graph.getVertexes()) {
            int nodeIndex = vIndex.getId();
            for (Vertex v : graph.getVertexes()) {
                int source = v.getId();
                if (source != nodeIndex) {
                    double[] distances = findShortestPaths(a, source);
                    for (Vertex u : graph.getVertexes()) {
                        int destination = u.getId();
                        if (destination != nodeIndex && destination != source) {
                            List<List<Integer>> paths = new ArrayList<>();
                            List<Integer> path = new ArrayList<>();

                            path.add(new Integer(destination));
                            List<Integer> visitedNodes = new ArrayList<>();
                            visitedNodes.add(new Integer(destination));

                            List<List<Integer>> finalPaths = buildPathRecursive(a, source, destination, path, distances, visitedNodes, paths);
                            String key = source + "_" + destination;
                            allPaths.put(key, finalPaths);
                        }
                    }
                }
            }
        }
        for (String key : allPaths.keySet()) {
            System.out.println(key + " ---");
            for (List<Integer> list : allPaths.get(key)) {
                System.out.println("\t" + list);
            }
        }
        return allPaths;
    }

    //public static Map<String, List<List<Integer>>> findAllShortestPaths(Graph graph) {
    public static GlobalPaths findAllShortestPaths(Graph graph) {
        GlobalPaths globalPaths = new GlobalPaths();
        double[][] a = graph.getAdjacency();
        Map<String, List<List<Integer>>> allPaths = new HashMap<>();
        Map<String, List<List<Edge>>> allEdgePaths = new HashMap<>();
        Map<Integer, Vertex> vertexMap = new HashMap<>();
        Map<String, Edge> edgeMap = new HashMap<>();
        for (Vertex v : graph.getVertexes()) {
            vertexMap.put(v.getId(), v);
        }
        for (Edge e : graph.getEdges()) {
            String key = e.getSource().getId() + "_" + e.getDestination().getId();
            edgeMap.put(key, e);
        }

        for (Vertex v : graph.getVertexes()) {
            int source = v.getId();
            double[] distances = findShortestPaths(a, source);
            for (Vertex u : graph.getVertexes()) {
                int destination = u.getId();
                if (destination != source) {
                    List<List<Integer>> paths = new ArrayList<>();
                    List<Integer> path = new ArrayList<>();

                    path.add(new Integer(destination));
                    List<Integer> visitedNodes = new ArrayList<>();
                    visitedNodes.add(new Integer(destination));

                    List<List<Integer>> finalPaths = buildPathRecursive(a, source, destination, path, distances, visitedNodes, paths);
                    String key = source + "_" + destination;
                    allPaths.put(key, finalPaths);

                    allEdgePaths.put(key, vertexPaths2edgePaths(finalPaths, edgeMap));
                }
            }
        }


        for (String key : allPaths.keySet()) {
            System.out.println(key + " ---");
            for (List<Integer> list : allPaths.get(key)) {
                System.out.println("\t" + list);
            }
            for (List<Edge> list : allEdgePaths.get(key)) {
                System.out.println("\t" + list.stream().map(e -> e.getName()).collect(Collectors.joining(" -> ")));

            }
        }
        globalPaths.setNodePaths(allPaths);
        globalPaths.setEdgePaths(allEdgePaths);
        return globalPaths;
        //return allPaths;
    }

    public static List<List<Edge>> vertexPaths2edgePaths
            (List<List<Integer>> finalPaths, Map<String, Edge> edgeMap) {
        List<List<Edge>> finalEdgePaths = new ArrayList<>();
        for (List<Integer> path : finalPaths) {
            List<Edge> edgePath = new ArrayList<>();
            for (int i = 0; i < path.size() - 1; i++) {
                String key = path.get(i) + "_" + path.get(i + 1);
                String key1 = path.get(i + 1) + "_" + path.get(i);
                if (edgeMap.get(key) != null) {
                    edgePath.add(edgeMap.get(key));
                } else {
                    edgePath.add(edgeMap.get(key1));
                }
            }
            finalEdgePaths.add(edgePath);
        }
        return finalEdgePaths;
    }

    public static List<Edge> fromIntegerToEdges(Graph g, List<Integer> nodes) {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < nodes.size() - 1; i++) {
            Edge e = g.getEdgeMap().get(nodes.get(i) + "_" + nodes.get(i + 1));
            edges.add(e);
        }
        return edges;
    }

    public static List<List<Edge>> fromIntegerPathsToEdgePaths(Graph g, List<List<Integer>> paths) {
        List<List<Edge>> edgePaths = new ArrayList<>();
        for (List<Integer> nodes : paths) {
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < nodes.size() - 1; i++) {
                Edge e = g.getEdgeMap().get(nodes.get(i) + "_" + nodes.get(i + 1));
                edges.add(e);
            }
            edgePaths.add(edges);
        }
        return edgePaths;
    }

    public static Double lengthBasedOnCapacity(Edge edge) {
        if(edge.getName().equalsIgnoreCase("ARCO_2")){
            System.out.println("&&&&&&");
        }
        if (edge.getWeight(WeightConstant.CAPACITY) != null && edge.getWeight(WeightConstant.CAPACITY) > 0) {
            return edge.getWeight(WeightConstant.LENGTH);
        }
        return 0.0;
    }

    public static double[][] getLengthMatrixBasedOnCapacity(Graph graph) {
        double[][] L = new double[graph.getVertexes().size()][graph.getVertexes().size()];
        List<Edge> edges = graph.getEdges();
        for (Edge e : edges) {
            Vertex source = e.getSource();
            Vertex destination = e.getDestination();
            if (e.getWeight(WeightConstant.LENGTH) != null) {
                Double w = lengthBasedOnCapacity(e);
                L[source.getId()][destination.getId()] = w;
            }
        }

        return L;
    }

    public static DijkstraResponse findMultipleShortestPathsWithEdgesConsideringCapacity(Graph graph, int source, int destination) {

        double[][] a = getLengthMatrixBasedOnCapacity(graph);
        double[] distances = findShortestPaths(a, source);
        List<List<Integer>> paths = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        path.add(new Integer(destination));
        List<Integer> visitedNodes = new ArrayList<>();
        visitedNodes.add(new Integer(destination));

        List<List<Integer>> finalPaths = buildPathRecursive(a, source, destination, path, distances, visitedNodes, paths);
        //System.out.println("Final Paths");
        Double minimalLength = 0.0;

        List<List<Edge>> edgePaths = new ArrayList<>();
        Double length = 0.0;

        for (List<Integer> list : finalPaths) {
            //System.out.println("Minimum Length Paths from "+source+" to "+destination);
            System.out.print(list);
            List<Edge> ep = DijkstraUtil.fromIntegerToEdges(graph, list);
            length = ep.stream().mapToDouble(x -> x.getWeight(WeightConstant.LENGTH)).sum();
            System.out.println(" Length: " + length);
        }

        edgePaths = DijkstraUtil.fromIntegerPathsToEdgePaths(graph, finalPaths);

        DijkstraResponse dijkstraResponse = new DijkstraResponse();
        dijkstraResponse.setEdgePaths(edgePaths);
        dijkstraResponse.setNodePaths(finalPaths);
        dijkstraResponse.setLength(length == 0 ? Double.MAX_VALUE : length);
        return dijkstraResponse;
    }

}

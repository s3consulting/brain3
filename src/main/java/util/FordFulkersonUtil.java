package util;

import constant.ValueConstant;
import exception.GraphException;
import model.Edge;
import model.Graph;
import model.MaxFlowResponse;

import java.util.*;

public class FordFulkersonUtil {
    private FordFulkersonUtil() {
        //private constructor to hide the implicit public one
    }


    /* Returns true if there is a path from source 's' to
    sink 't' in residual graph. Also fills parent[] to
    store the path */
    public static boolean bfs(double[][] a, int s, int t, int parent[]) {

        Integer V = a.length;
        // Create a visited array and mark all vertices as
        // not visited
        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; ++i)
            visited[i] = false;

        // Create a queue, enqueue source vertex and mark
        // source vertex as visited
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        parent[s] = -1;

        // Standard BFS Loop
        while (queue.size() != 0) {
            int u = queue.poll();

            for (int v = 0; v < V; v++) {
                if (visited[v] == false && a[u][v] > 0) {
                    // If we find a connection to the sink
                    // node, then there is no point in BFS
                    // anymore We just have to set its parent
                    // and can return true
                    if (v == t) {
                        parent[v] = u;
                        return true;
                    }
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }

        // We didn't reach sink in BFS starting from source,
        // so return false
        return false;
    }

    // Returns tne maximum flow from s to t in the given
    // graph
    public static MaxFlowResponse maxFlow(Graph graph, int s, int t) {
        List<Edge> edgesInFlow = new ArrayList<>();

        //double[][] a = graph.getCapacityMatrix();
        double[][] a = graph.buildCapacityMatrix();
        int u, v;
        Integer V = a.length;

        // Create a residual graph and fill the residual
        // graph with given capacities in the original graph
        // as residual capacities in residual graph

        // Residual graph where rGraph[i][j] indicates
        // residual capacity of edge from i to j (if there
        // is an edge. If rGraph[i][j] is 0, then there is
        // not)
        double[][] rGraph = new double[V][V];

        for (u = 0; u < V; u++) {
            for (v = 0; v < V; v++) {
                rGraph[u][v] = a[u][v];
            }
        }

        // This array is filled by BFS and to store path
        int parent[] = new int[V];

        Double max_flow = 0D; // There is no flow initially

        // Augment the flow while tere is path from source
        // to sink
        while (bfs(rGraph, s, t, parent)) {
            // Find minimum residual capacity of the edges
            // along the path filled by BFS. Or we can say
            // find the maximum flow through the path found.
            Double path_flow = Double.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                path_flow = Math.min(path_flow, rGraph[u][v]);
            }

            // update residual capacities of the edges and
            // reverse edges along the path
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
            }

            // Add path flow to overall flow
            max_flow += path_flow;
        }
        try {
            Map<String, Double> flows=buildEdgeFlow(a, rGraph);
            for(String key: flows.keySet()){
                //System.out.println("Flow in "+key+": "+flows.get(key));
            }
            //build the edges
            for(String key: flows.keySet()){
                Edge e = graph.getEdgeMap().get(key);
                e.setValue(ValueConstant.FLOW, flows.get(key));
                edgesInFlow.add(e);
            }

        } catch (GraphException ge){
            System.out.println(ge.getMessage());
        }

        MaxFlowResponse maxFlowResponse = new MaxFlowResponse();
        maxFlowResponse.setMaxFlow(max_flow);
        maxFlowResponse.setEdgesMaxFlow(edgesInFlow);
        return maxFlowResponse;
        // Return the overall flow
        //return max_flow;
    }

    public static Map<String, Double> buildEdgeFlow(double[][] a, double[][] b) throws GraphException {

        double[][] c = MatrixUtil.subtract(a, b);
        Map<String, Double> flows = new HashMap<>();

        int i, j;
        for (i = 0; i < a.length; i++) {
            for (j = 0; j < a.length; j++) {
                if (c[i][j]>0){
                    String key=i+"_"+j;
                    flows.put(key, c[i][j]);
                }
            }
        }
        return flows;
    }

    // Returns tne maximum flow from s to t in the given
    // graph shown as a double[][]

    /*
    public static MaxFlowResponse maxFlow(Graph graph, int s, int t) {
        List<Edge> edgesInFlow = new ArrayList<>();
        double[][] a = graph.getAdjacency();
        int u, v;
        Integer V = a.length;

        // Create a residual graph and fill the residual
        // graph with given capacities in the original graph
        // as residual capacities in residual graph

        // Residual graph where rGraph[i][j] indicates
        // residual capacity of edge from i to j (if there
        // is an edge. If rGraph[i][j] is 0, then there is
        // not)
        double[][] rGraph = new double[V][V];

        for (u = 0; u < V; u++) {
            for (v = 0; v < V; v++) {
                rGraph[u][v] = a[u][v];
            }
        }

        // This array is filled by BFS and to store path
        int parent[] = new int[V];

        Double max_flow = 0D; // There is no flow initially

        // Augment the flow while tere is path from source
        // to sink
        while (bfs(rGraph, s, t, parent)) {
            // Find minimum residual capacity of the edges
            // along the path filled by BFS. Or we can say
            // find the maximum flow through the path found.
            Double path_flow = Double.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                path_flow = Math.min(path_flow, rGraph[u][v]);
            }

            // update residual capacities of the edges and
            // reverse edges along the path
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
            }

            // Add path flow to overall flow
            max_flow += path_flow;
        }
        try {
            Map<String, Double> flows=buildEdgeFlow(a, rGraph);
            for(String key: flows.keySet()){
                System.out.println("Flow in "+key+": "+flows.get(key));
            }
            //build the edges
            for(String key: flows.keySet()){
                Edge e = graph.getEdgeMap().get(key);
                e.setValue(ValueConstant.FLOW, flows.get(key));
                edgesInFlow.add(e);
            }

        } catch (GraphException ge){
            System.out.println(ge.getMessage());
        }

        MaxFlowResponse maxFlowResponse = new MaxFlowResponse();
        maxFlowResponse.setMaxFlow(max_flow);
        maxFlowResponse.setEdgesMaxFlow(edgesInFlow);
        return maxFlowResponse;
        // Return the overall flow
        //return max_flow;
    }

     */

}

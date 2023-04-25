package util;

import constant.ValueConstant;
import constant.WeightConstant;
import model.Edge;
import model.GlobalPaths;
import model.Graph;
import model.Vertex;

import java.util.List;
import java.util.Map;

public class CentralityUtil {
    private CentralityUtil() {
        //private constructor that hide the implicit public one
    }

    public static void degreeCentrality(Graph graph) {
        int n = graph.getVertexes().size();
        for (Vertex v : graph.getVertexes()) {
            double degreeCentrality = 0.0;
            if(v.getInEdges()!=null) {
                for (Edge e : v.getInEdges()) {
                    if (e.getBidirectional()) {
                        degreeCentrality += e.getWeight(WeightConstant.LENGTH) / 2;
                    } else {
                        degreeCentrality += e.getWeight(WeightConstant.LENGTH);
                    }
                }
            }
            if(v.getOutEdges()!=null) {
                for (Edge e : v.getOutEdges()) {
                    if (e.getBidirectional()) {
                        degreeCentrality += e.getWeight(WeightConstant.LENGTH) / 2;
                    } else {
                        degreeCentrality += e.getWeight(WeightConstant.LENGTH);
                    }
                }
            }
            v.setValue(ValueConstant.DEGREE_CENTRALITY, degreeCentrality / (n - 1));
        }
    }

    public static void closenessCentrality(Graph graph) {
        int n = graph.getVertexes().size();
        for (Vertex v : graph.getVertexes()) {
            double[] distances = DijkstraUtil.findShortestPaths(graph.getAdjacency(), v.getId());
            double d = 0D;
            for (int i = 0; i < distances.length; i++) {
                d += distances[i];
            }
            v.setValue(ValueConstant.CLOSENESS_CENTRALITY,d / (n - 1));
        }
    }

    public static void betweeneessCentrality(Graph graph) {
        //Map<String, List<List<Integer>>> allPaths = DijkstraUtil.findAllShortestPaths(graph);
        GlobalPaths globalPaths = DijkstraUtil.findAllShortestPaths(graph);
        Map<String, List<List<Integer>>> allPaths = globalPaths.getNodePaths();
        for (Vertex v : graph.getVertexes()) {
            double bc = 0D;
            for (String key : allPaths.keySet()) {
                Integer source = Integer.valueOf(key.split("_")[0]);
                Integer destination = Integer.valueOf(key.split("_")[1]);
                if (source != v.getId() && destination != v.getId()) {
                    double numerator = 0D;
                    double denominator = 0D;
                    List<List<Integer>> paths = allPaths.get(key);
                    if(paths.size()>0) {
                        denominator = paths.size();
                        for (List<Integer> path : paths) {
                            if (path.contains(v.getId())) {
                                numerator++;
                            }
                        }
                        bc += (numerator / denominator);
                    }
                }
            }
            v.setValue(ValueConstant.BETWEENNESS_CENTRALITY,bc);
        }
        Map<String, List<List<Edge>>> allEdgePaths = globalPaths.getEdgePaths();
        for (Edge e : graph.getEdges()) {
            double bc = 0D;

            for (String key : allEdgePaths.keySet()) {
                Integer source = Integer.valueOf(key.split("_")[0]);
                Integer destination = Integer.valueOf(key.split("_")[1]);
                double numerator = 0D;
                double denominator = 0D;
                if ((source != e.getSource().getId() && destination != e.getDestination().getId()) || (source != e.getDestination().getId() && destination != e.getSource().getId())) {
                    List<List<Edge>> edgePaths = allEdgePaths.get(key);
                    if(edgePaths.size()>0) {
                        denominator = edgePaths.size();
                        for (List<Edge> edgePath : edgePaths) {
                            if (edgePath.contains(e)) {
                                numerator++;
                            }
                        }
                        bc += (numerator / denominator);
                    }
                }
            }
            e.setValue(ValueConstant.BETWEENNESS_CENTRALITY,bc);
        }
    }
}

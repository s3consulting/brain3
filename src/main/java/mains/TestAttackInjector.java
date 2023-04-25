package mains;

import model.Graph;
import montecarlo.AttackInjector;
import util.FileSystemUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TestAttackInjector {
    public static void main(String[] args) throws IOException {
        String dir = "/Users/cristianocimino/NetBeansProjects/generic-graph/GRAPHS";
        //String graphName = "PraksGasGraph";
        //String graphName = "PraksGasGraph_MAX_CAP";
        //String graphName = "PRAKS_GRAPH_CASE_A";
        //String graphName = "PRAKS_GRAPH_CASE_B";
        //String graphName = "PRAKS_GRAPH_CASE_C";
        //String graphName = "PRAKS_GRAPH_CASE_D";
        //String graphName = "PRAKS_GRAPH_CASE_E";
        //String graphName = "PRAKS_GRAPH_CASE_F";
        String graphName = "PRAKS_GRAPH_CASE_G";

        Graph graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);
        graph.setName(graphName);

        Random r = new Random();
        Double percentageDamage =r.nextDouble();
        AttackInjector.injectRandomAttack(graph, percentageDamage);


        graph = FileSystemUtil.loadGraphFromFile1(graphName, dir);
        graph.setName(graphName);
        List<Integer> attackedVertexIds = Arrays.asList(13,21,19,17);
        List<Integer> attackedEdgeIds = Arrays.asList(113,53,49,71,83);

        AttackInjector.injectAimedAttack(graph, attackedVertexIds, attackedEdgeIds, percentageDamage);
    }
}

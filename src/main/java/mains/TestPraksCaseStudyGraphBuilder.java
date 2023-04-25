package mains;

import model.Graph;
import util.FileSystemUtil;

import java.io.IOException;

public class TestPraksCaseStudyGraphBuilder {

    public static void main(String[] args) throws IOException {

        String dir = "/Users/cristianocimino/NetBeansProjects/generic-graph/GRAPHS";

        System.out.println("---Creazione Case A------");
        Graph graph_CASE_A = PraksCaseStudyGraphBuilder.graphBuilderCase_A();
        graph_CASE_A.setName("PRAKS_GRAPH_CASE_A");
        String graphName_CASE_A = "PRAKS_GRAPH_CASE_A";
        FileSystemUtil.writeGraphToFile(graph_CASE_A, graphName_CASE_A, dir);

        System.out.println("---Creazione Case B------");
        Graph graph_CASE_B = PraksCaseStudyGraphBuilder.graphBuilderCase_B();
        graph_CASE_B.setName("PRAKS_GRAPH_CASE_B");
        String graphName_CASE_B = "PRAKS_GRAPH_CASE_B";
        FileSystemUtil.writeGraphToFile(graph_CASE_B, graphName_CASE_B, dir);

        System.out.println("---Creazione Case C------");
        Graph graph_CASE_C = PraksCaseStudyGraphBuilder.graphBuilderCase_C();
        graph_CASE_C.setName("PRAKS_GRAPH_CASE_C");
        String graphName_CASE_C = "PRAKS_GRAPH_CASE_C";
        FileSystemUtil.writeGraphToFile(graph_CASE_C, graphName_CASE_C, dir);

        System.out.println("---Creazione Case D------");
        Graph graph_CASE_D = PraksCaseStudyGraphBuilder.graphBuilderCase_D();
        graph_CASE_D.setName("PRAKS_GRAPH_CASE_D");
        String graphName_CASE_D = "PRAKS_GRAPH_CASE_D";
        FileSystemUtil.writeGraphToFile(graph_CASE_D, graphName_CASE_D, dir);

        System.out.println("---Creazione Case E------");
        Graph graph_CASE_E = PraksCaseStudyGraphBuilder.graphBuilderCase_E();
        graph_CASE_E.setName("PRAKS_GRAPH_CASE_E");
        String graphName_CASE_E = "PRAKS_GRAPH_CASE_E";
        FileSystemUtil.writeGraphToFile(graph_CASE_E, graphName_CASE_E, dir);

        System.out.println("---Creazione Case F------");
        Graph graph_CASE_F = PraksCaseStudyGraphBuilder.graphBuilderCase_F();
        graph_CASE_F.setName("PRAKS_GRAPH_CASE_F");
        String graphName_CASE_F = "PRAKS_GRAPH_CASE_F";
        FileSystemUtil.writeGraphToFile(graph_CASE_F, graphName_CASE_F, dir);

        System.out.println("---Creazione Case G------");
        Graph graph_CASE_G = PraksCaseStudyGraphBuilder.graphBuilderCase_G();
        graph_CASE_G.setName("PRAKS_GRAPH_CASE_G");
        String graphName_CASE_G = "PRAKS_GRAPH_CASE_G";
        FileSystemUtil.writeGraphToFile(graph_CASE_G, graphName_CASE_G, dir);


    }
}

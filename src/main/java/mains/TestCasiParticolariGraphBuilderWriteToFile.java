package mains;

import model.Graph;
import util.FileSystemUtil;

import java.io.IOException;

public class TestCasiParticolariGraphBuilderWriteToFile {
    public static void main(String[] args) throws IOException {

        String dir = "/Users/cristianocimino/NetBeansProjects/generic-graph/GRAPHS";
        Graph graph = CasiParticolariGraphBuilder.casoParticolareAlgoritmo2();
        System.out.println("---"+graph.getName()+"------");



        FileSystemUtil.writeGraphToFile(graph, graph.getName(), dir);
    }
}

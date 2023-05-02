package time;

import constant.ValueConstant;
import model.Edge;
import model.Graph;
import model.Vertex;

import java.io.*;

public class OutputFormatterUtil {

    public static void writeAdjacencyMatrix(String outputDir, Integer day, Graph graph) throws IOException {

        File theDir = new File(outputDir);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        String outputFileName = theDir + "/ADJACENCY_DAY" + day + ".csv";
        Integer numberOfVertices = graph.getVertexMap().size();

        FileOutputStream fos = new FileOutputStream(new File(outputFileName));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        String row = "";
        for (int i = 0; i < numberOfVertices; i++) {
            row = "";
            Vertex source = graph.getVertexMap().get(i);
            for (int j = 0; j < numberOfVertices; j++) {
                Vertex destination = graph.getVertexMap().get(j);
                if (source.getOutEdges() != null) {
                    boolean found = false;
                    String val = "";
                    for (Edge edge : source.getOutEdges()) {
                        if (edge.getDestination().getId() == destination.getId()) {
                            found = true;
                            val = String.valueOf(edge.getValue(ValueConstant.CUMULATIVE_FLOW));
                            if (edge.getValue(ValueConstant.CUMULATIVE_FLOW)==null) {
                                val = "0.0";
                            }
                            break;
                        }
                    }
                    if (found) {
                        row += "\t" + val;
                    } else {
                        row += "\t" + 0.0;
                    }
                } else {
                    row += "\t0.0";
                }
            }
            if(row.indexOf("null")!=-1){
                System.out.println("RRR");
            }
            row += "\n";
            bw.write(row);
        }
        bw.close();
        fos.close();
    }
}

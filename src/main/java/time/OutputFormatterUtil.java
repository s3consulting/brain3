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
                            if (edge.getValue(ValueConstant.CUMULATIVE_FLOW) == null) {
                                val = "0.0";
                            }
                            break;
                        }
                    }
                    if (found) {
                        if (!row.equals("")) {
                            row += "\t" + val;
                        } else {
                            row += val;
                        }
                    } else {
                        if (!row.equals("")) {
                            row += "\t" + 0.0;
                        } else {
                            row += 0.0;
                        }
                    }
                } else {
                    if (!row.equals("")) {
                        row += "\t0.0";
                    }
                    else{
                        row+="0.0";
                    }
                }
            }
            if (row.indexOf("null") != -1) {
                System.out.println("RRR");
            }
            row += "\n";
            bw.write(row);
        }
        bw.close();
        fos.close();
    }


    public static void writeAverageAdjacencyMatrix(String outputDir, double[][][] a, int numberOfDays) throws IOException {
        File theDir = new File(outputDir);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        String outputFileName;


        for(int day=0; day<numberOfDays; day++) {
            outputFileName = theDir + "/ADJACENCY_DAY" + day + ".csv";
            FileOutputStream fos = new FileOutputStream(new File(outputFileName));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            String row = "";
            for (int i = 0; i < a[0].length; i++) {
                row = "";

                for (int j = 0; j < a[0].length; j++) {
                    row+=a[i][j][day]+" ";
                }
                row += "\n";
                bw.write(row);
            }
            bw.close();
            fos.close();
        }
    }
}

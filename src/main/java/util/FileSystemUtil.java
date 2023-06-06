package util;

import constant.VertexConstant;
import constant.WeightConstant;
import model.Edge;
import model.Graph;
import model.Vertex;

import java.io.*;
import java.util.*;

public class FileSystemUtil {
    public static void writeLengthAndCapacityToFile(Graph graph, String graphName, String directoryName) throws IOException {
        //scrive la matrice delle distanze (LengthMatrix) e delle capacità (CapacityMatrix)

        String outputFileName = "";
        if (graph.getName() != null) {
            outputFileName = graph.getName();
        } else {
            outputFileName = graphName;
        }

        String newDir = directoryName+"/"+graphName;
        File theDir = new File(newDir);
        if (!theDir.exists()){
            theDir.mkdirs();
        }

        String capacityName = newDir + "/" + outputFileName + "_CAPACITY.csv";
        String lengthName = newDir + "/" + outputFileName + "_LENGTH.csv";


        FileOutputStream fos = new FileOutputStream(new File(capacityName));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        for (int i = 0; i < graph.getCapacityMatrix().length; i++) {
            String row = "";
            for (int j = 0; j < graph.getCapacityMatrix().length; j++) {
                if (j == 0) {
                    row = row + graph.getCapacityMatrix()[i][j];
                } else {
                    row = row + "\t" + graph.getCapacityMatrix()[i][j];
                }
            }


            bw.write(row);
            bw.newLine();
        }
        bw.close();
        fos.close();

        fos = new FileOutputStream(new File(lengthName));
        bw = new BufferedWriter(new OutputStreamWriter(fos));
        for (int i = 0; i < graph.getLengthMatrix().length; i++) {
            String row = "";
            for (int j = 0; j < graph.getLengthMatrix().length; j++) {
                if (j == 0) {
                    row = row + graph.getLengthMatrix()[i][j];
                } else {
                    row = row + "\t" + graph.getLengthMatrix()[i][j];
                }
            }


            bw.write(row);
            bw.newLine();
        }
        bw.close();
        fos.close();
    }

    public static void loadLengthAndCapacityFromFile(Graph graph, String graphName, String directoryName) throws IOException {
        //scrive la matrice delle distanze (LengthMatrix) e delle capacità (CapacityMatrix)

        String outputFileName = "";
        if (graph.getName() != null) {
            outputFileName = graph.getName();
        } else {
            outputFileName = graphName;
        }
        String newDir = directoryName+"/"+graphName;

        String capacityName = newDir + "/" + outputFileName + "_CAPACITY.csv";
        String lengthName = newDir + "/" + outputFileName + "_LENGTH.csv";


        BufferedReader reader = new BufferedReader(new FileReader(capacityName));

        double[][] capacityMatrix = new double[1][1];
        String line;
        int cnt=0;
        int ll=0;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split("\t");
            if(cnt==0){
                ll = data.length;
                capacityMatrix = new double[ll][ll];
            }
            for(int j=0; j<ll; j++){
                capacityMatrix[cnt][j]=Double.valueOf(data[j]);
            }
            cnt++;
        }
        reader.close();

        reader = new BufferedReader(new FileReader(lengthName));

        double[][] lengthMatrix = new double[1][1];

        cnt=0;
        ll=0;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split("\t");
            if(cnt==0){
                ll = data.length;
                lengthMatrix = new double[ll][ll];
            }
            for(int j=0; j<ll; j++){
                lengthMatrix[cnt][j]=Double.valueOf(data[j]);
            }
            cnt++;
        }
        reader.close();
        graph.setCapacityMatrix(capacityMatrix);
        graph.setLengthMatrix(lengthMatrix);
    }

    public static void writeGraphToFile(Graph graph, String graphName, String directoryName) throws IOException {
        String outputFileName = "";
        if (graph.getName() != null) {
            outputFileName = graph.getName();
        } else {
            outputFileName = graphName;
        }

        String newDir = directoryName+"/"+graphName;
        File theDir = new File(newDir);
        if (!theDir.exists()){
            theDir.mkdirs();
        }

        String vertexName = newDir + "/" + outputFileName + "_VERTEXES.csv";
        String edgeName = newDir + "/" + outputFileName + "_EDGES.csv";

        FileOutputStream fos = new FileOutputStream(new File(vertexName));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        for(Vertex v: graph.getVertexes()){
            String line = v.toLine();
            bw.write(line);
            bw.newLine();
        }
        bw.close();
        fos.close();

        fos = new FileOutputStream(new File(edgeName));
        bw = new BufferedWriter(new OutputStreamWriter(fos));
        for(Edge v: graph.getEdges()){
            String line = v.toLine();
            bw.write(line);
            bw.newLine();
        }
        bw.close();
        fos.close();

    }


    public static Graph loadGraphFromFile(String graphName, String directoryName) throws IOException {
        String outputFileName = "";

        outputFileName = graphName;
        String newDir = directoryName+"/"+graphName;
        String vertexName = newDir + "/" + outputFileName + "_VERTEXES.csv";
        String edgeName = newDir + "/" + outputFileName + "_EDGES.csv";

        Graph graph = new Graph();

        List<Vertex> vertices = readVertexFromFile(vertexName);
        List<Edge> edges = readEdgesFromFile(edgeName, vertices);

        graph.build(vertices, edges);

        return graph;
    }

    public static List<Vertex> readVertexFromFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line="";
        List<Vertex> vertexes = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            String[] data = line.split("\t");
            Vertex v = new Vertex();
            v.setId(Integer.valueOf(data[0]));
            v.setCode(data[1]);
            v.setName(data[2]);
            v.setWeight(VertexConstant.VERTEX_DEMAND, Double.valueOf(data[3]));
            if(data.length==5) {
                v.setType(data[4]);
            }
            v.setEnabled(true);
            vertexes.add(v);
        }
        reader.close();
        return vertexes;
    }

    public static List<Edge> readEdgesFromFile(String fileName, List<Vertex> vertexes) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line="";
        List<Edge> edges = new ArrayList<>();
        Map<Integer, Vertex> mapVertex = new HashMap<>();
        for(Vertex v: vertexes){
            mapVertex.put(v.getId(), v);
        }
        while ((line = reader.readLine()) != null) {
            String[] data = line.split("\t");
            Edge e = new Edge();
            e.setId(Integer.valueOf(data[0]));
            e.setName(data[1]);
            e.setSource(mapVertex.get(Integer.valueOf(data[2])));
            e.setDestination(mapVertex.get(Integer.valueOf(data[3])));
            e.setWeight(WeightConstant.LENGTH, Double.valueOf(data[4]));
            e.setWeight(WeightConstant.CAPACITY, Double.valueOf(data[5]));
            e.setWeight(WeightConstant.ORIGINAL_CAPACITY, Double.valueOf(data[5]));
            e.setType(data[6]);
            e.setBidirectional(false);
            e.setEnabled(true);
            edges.add(e);
        }
        reader.close();
        return edges;
    }


    public static Graph loadGraphFromFile(String graphName, String directoryName, String length_capacity) throws IOException {
        String outputFileName = "";

        outputFileName = graphName;
        String newDir = directoryName+"/"+graphName;

        String vertexName = newDir + "/" + outputFileName + "_VERTEXES.csv";
        String edgeName = newDir + "/" + outputFileName + "_EDGES.csv";

        Graph graph = new Graph();

        List<Vertex> vertices = readVertexFromFile(vertexName);
        List<Edge> edges = readEdgesFromFile(edgeName, vertices);

        graph.build(vertices, edges);
        //graph.build(vertices, edges, length_capacity);
        return graph;
    }

    public static Graph loadGraphFromFile1(String graphName, String directoryName) throws IOException {
        String outputFileName = "";

        outputFileName = graphName;
        String newDir = directoryName+"/"+graphName;

        String vertexName = newDir + "/" + outputFileName + "_VERTEXES.csv";
        String edgeName = newDir + "/" + outputFileName + "_EDGES.csv";

        Graph graph = new Graph();

        List<Vertex> vertices = readVertexFromFile(vertexName);
        List<Edge> edges = readEdgesFromFile(edgeName, vertices);

        graph.build(vertices, edges);
        GraphUtil.initializeGraph(graph);
        return graph;
    }

    public static FileObject openFileObject(String directoryName, String fileName) throws FileNotFoundException {

        String mask = GraphUtil.createFileMask();
        //String outputFileName = fileName+"__"+date.toString();
        String outputFileName = fileName+"_ITERAZIONI_"+mask;

        String newDir = directoryName + "/" + outputFileName;
        File theDir = new File(newDir);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }

        String resultName = newDir + "/" + outputFileName + "RESULT.csv";
        FileOutputStream fos = new FileOutputStream(new File(resultName));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        FileObject fileObject = new FileObject();
        fileObject.setBw(bw);
        fileObject.setFos(fos);
        return fileObject;
    }

    public static FileObject openFileObject(String directoryName, String fileName, int iteration) throws FileNotFoundException {

        String mask = GraphUtil.createFileMask();
        //String outputFileName = fileName+"__"+date.toString();
        String outputFileName = fileName+"_ITERAZIONI_ITERATION_"+iteration+"___"+mask;

        String newDir = directoryName + "/" + outputFileName;
        File theDir = new File(newDir);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }

        String resultName = newDir + "/" + outputFileName + "RESULT.csv";
        FileOutputStream fos = new FileOutputStream(new File(resultName));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        FileObject fileObject = new FileObject();
        fileObject.setBw(bw);
        fileObject.setFos(fos);
        return fileObject;
    }

    public static void closeFileObject(FileObject fileObject) throws IOException {
        fileObject.getBw().close();
        fileObject.getFos().close();

    }

}

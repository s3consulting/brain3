package mains;

import constant.TypeConstant;
import constant.VertexConstant;
import constant.WeightConstant;
import model.Edge;
import model.Graph;
import model.Vertex;
import util.GraphUtil;

import java.util.ArrayList;
import java.util.List;

public class CasiParticolariGraphBuilder {

    public static Graph casoParticolareAlgoritmo2() {


        Graph graph = new Graph();
        graph.setName("CasoParticolare_ALGORITMO2");
        List<Vertex> vertexes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();


        //VERTICES

        //Source 1
        Vertex v2 = new Vertex();
        v2.setId(0);
        v2.setCode("V_2");
        v2.setName("NODO_2");
        v2.setWeight(VertexConstant.VERTEX_DEMAND, -20.0);
        v2.setType(TypeConstant.COMPRESSION_STATION);

        //Source 2
        Vertex v3 = new Vertex();
        v3.setId(1);
        v3.setCode("V_3");
        v3.setName("NODO_3");
        v3.setWeight(VertexConstant.VERTEX_DEMAND, -10.0);
        v3.setType(TypeConstant.COMPRESSION_STATION);

        Vertex v4 = new Vertex();
        v4.setId(2);
        v4.setCode("V_4");
        v4.setName("NODO_4");
        v4.setWeight(VertexConstant.VERTEX_DEMAND, 0.0);

        Vertex v5 = new Vertex();
        v5.setId(3);
        v5.setCode("V_5");
        v5.setName("NODO_5");
        v5.setWeight(VertexConstant.VERTEX_DEMAND, 0.0);

        Vertex v6 = new Vertex();
        v6.setId(4);
        v6.setCode("V_6");
        v6.setName("NODO_6");
        v6.setWeight(VertexConstant.VERTEX_DEMAND, 0.0);

        Vertex v7 = new Vertex();
        v7.setId(5);
        v7.setCode("V_7");
        v7.setName("NODO_7");
        v7.setWeight(VertexConstant.VERTEX_DEMAND, 0.0);


        //Sink 1
        Vertex v8 = new Vertex();
        v8.setId(6);
        v8.setCode("V_8");
        v8.setName("NODO_8");
        v8.setWeight(VertexConstant.VERTEX_DEMAND, 10.0);

        //Sink 2
        Vertex v9 = new Vertex();
        v9.setId(7);
        v9.setCode("V_9");
        v9.setName("NODO_9");
        v9.setWeight(VertexConstant.VERTEX_DEMAND, 4.0);

        //Edges
        Edge e0 = new Edge();
        e0.setId(0);
        e0.setName("ARCO_0");
        e0.setWeight(WeightConstant.LENGTH,1.0);
        e0.setWeight(WeightConstant.CAPACITY,10.0);
        e0.setSource(v2);
        e0.setDestination(v4);
        e0.setBidirectional(false);

        Edge e1 = new Edge();
        e1.setId(1);
        e1.setName("ARCO_1");
        e1.setWeight(WeightConstant.LENGTH,1.0);
        e1.setWeight(WeightConstant.CAPACITY,5.0);
        e1.setSource(v4);
        e1.setDestination(v5);
        e1.setBidirectional(false);

        Edge e2 = new Edge();
        e2.setId(2);
        e2.setName("ARCO_2");
        e2.setWeight(WeightConstant.LENGTH,7.0);
        e2.setWeight(WeightConstant.CAPACITY,5.0);
        e2.setSource(v4);
        e2.setDestination(v6);
        e2.setBidirectional(false);

        Edge e3 = new Edge();
        e3.setId(3);
        e3.setName("ARCO_3");
        e3.setWeight(WeightConstant.LENGTH,10.0);
        e3.setWeight(WeightConstant.CAPACITY,5.0);
        e3.setSource(v6);
        e3.setDestination(v8);
        e3.setBidirectional(false);

        Edge e4 = new Edge();
        e4.setId(4);
        e4.setName("ARCO_4");
        e4.setWeight(WeightConstant.LENGTH,2.0);
        e4.setWeight(WeightConstant.CAPACITY,7.0);
        e4.setSource(v6);
        e4.setDestination(v7);
        e4.setBidirectional(false);

        Edge e5 = new Edge();
        e5.setId(5);
        e5.setName("ARCO_5");
        e5.setWeight(WeightConstant.LENGTH,5.0);
        e5.setWeight(WeightConstant.CAPACITY,6.0);
        e5.setSource(v3);
        e5.setDestination(v6);
        e5.setBidirectional(false);

        Edge e6 = new Edge();
        e6.setId(6);
        e6.setName("ARCO_6");
        e6.setWeight(WeightConstant.LENGTH,2.0);
        e6.setWeight(WeightConstant.CAPACITY,7.0);
        e6.setSource(v6);
        e6.setDestination(v7);
        e6.setBidirectional(false);

        Edge e7 = new Edge();
        e7.setId(7);
        e7.setName("ARCO_7");
        e7.setWeight(WeightConstant.LENGTH,2.0);
        e7.setWeight(WeightConstant.CAPACITY,7.0);
        e7.setSource(v7);
        e7.setDestination(v9);
        e7.setBidirectional(false);

        Edge e8 = new Edge();
        e8.setId(8);
        e8.setName("ARCO_8");
        e8.setWeight(WeightConstant.LENGTH,1.0);
        e8.setWeight(WeightConstant.CAPACITY,5.0);
        e8.setSource(v5);
        e8.setDestination(v8);
        e8.setBidirectional(false);

        vertexes.add(v2);
        vertexes.add(v3);
        vertexes.add(v4);
        vertexes.add(v5);
        vertexes.add(v6);
        vertexes.add(v7);
        vertexes.add(v8);
        vertexes.add(v9);

        edges.add(e0);
        edges.add(e1);
        edges.add(e2);
        edges.add(e3);
        edges.add(e4);
        edges.add(e5);
        edges.add(e6);
        edges.add(e7);
        edges.add(e8);



        graph.build(vertexes, edges);
        GraphUtil.initializeGraph(graph);
        GraphUtil.makeSymmetric(graph);
        GraphUtil.removeArcsToRealSources(graph);


        return graph;
    }
}

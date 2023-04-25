package mains;



import constant.ValueConstant;
import constant.VertexConstant;
import constant.WeightConstant;
import model.Edge;
import model.Graph;
import model.Vertex;

import java.util.ArrayList;
import java.util.List;

public class GraphBuilder {
    public static Graph createGraph5X5() {
        Graph graph = new Graph();
        graph.setName("Graph5X5");
        List<Vertex> vertexes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        Vertex v0 = new Vertex();
        v0.setId(0);
        v0.setCode("V_0");
        v0.setName("NODO_0");

        Vertex v1 = new Vertex();
        v1.setId(1);
        v1.setCode("V_1");
        v1.setName("NODO_1");

        Vertex v2 = new Vertex();
        v2.setId(2);
        v2.setCode("V_2");
        v2.setName("NODO_2");

        Vertex v3 = new Vertex();
        v3.setId(3);
        v3.setCode("V_3");
        v3.setName("NODO_3");

        Vertex v4 = new Vertex();
        v4.setId(4);
        v4.setCode("V_4");
        v4.setName("NODO_4");

        Edge e0 = new Edge();
        e0.setId(0);
        e0.setName("ARCO_0");
        e0.setWeight(WeightConstant.LENGTH,1.0);
        e0.setWeight(WeightConstant.CAPACITY,10.0);
        e0.setSource(v0);
        e0.setDestination(v1);
        e0.setBidirectional(true);

        Edge e1 = new Edge();
        e1.setId(1);
        e1.setName("ARCO_1");
        e1.setWeight(WeightConstant.LENGTH,3.0);
        e1.setWeight(WeightConstant.CAPACITY,11.0);
        e1.setSource(v0);
        e1.setDestination(v2);
        e1.setBidirectional(true);

        Edge e2 = new Edge();
        e2.setId(2);
        e2.setName("ARCO_2");
        e2.setWeight(WeightConstant.LENGTH,2.0);
        e2.setWeight(WeightConstant.CAPACITY,9.0);
        e2.setSource(v1);
        e2.setDestination(v2);
        e2.setBidirectional(true);

        Edge e3 = new Edge();
        e3.setId(3);
        e3.setName("ARCO_3");
        e3.setWeight(WeightConstant.LENGTH,1.0);
        e3.setWeight(WeightConstant.CAPACITY,14.0);
        e3.setSource(v1);
        e3.setDestination(v3);
        e3.setBidirectional(true);

        Edge e4 = new Edge();
        e4.setId(4);
        e4.setName("ARCO_4");
        e4.setWeight(WeightConstant.LENGTH,3.0);
        e4.setWeight(WeightConstant.CAPACITY,8.0);
        e4.setSource(v1);
        e4.setDestination(v4);
        e4.setBidirectional(true);

        Edge e5 = new Edge();
        e5.setId(5);
        e5.setName("ARCO_5");
        e5.setWeight(WeightConstant.LENGTH,1.0);
        e5.setWeight(WeightConstant.CAPACITY,12.0);
        e5.setSource(v2);
        e5.setDestination(v4);
        e5.setBidirectional(true);

        Edge e6 = new Edge();
        e6.setId(6);
        e6.setName("ARCO_6");
        e6.setWeight(WeightConstant.LENGTH,2.0);
        e6.setWeight(WeightConstant.CAPACITY,17.0);
        e6.setSource(v3);
        e6.setDestination(v4);
        e6.setBidirectional(true);

        vertexes.add(v0);
        vertexes.add(v1);
        vertexes.add(v2);
        vertexes.add(v3);
        vertexes.add(v4);

        edges.add(e0);
        edges.add(e1);
        edges.add(e2);
        edges.add(e3);
        edges.add(e4);
        edges.add(e5);
        edges.add(e6);

        graph.build(vertexes, edges);

        return graph;
    }

    public static Graph createGraphMaxFlow4X4__Esercizio_capacity(){
        Graph graph = new Graph();
        graph.setName("GraphMaxFlow4X4__Esercizio_capacity");
        List<Vertex> vertexes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        Vertex v0 = new Vertex();
        v0.setId(0);
        v0.setCode("V_0");
        v0.setName("NODO_0");
        v0.setWeight(VertexConstant.VERTEX_DEMAND, -5.0);

        Vertex v1 = new Vertex();
        v1.setId(1);
        v1.setCode("V_1");
        v1.setName("NODO_1");
        v1.setWeight(VertexConstant.VERTEX_DEMAND, -7.0);

        Vertex v2 = new Vertex();
        v2.setId(2);
        v2.setCode("V_2");
        v2.setName("NODO_2");
        v2.setWeight(VertexConstant.VERTEX_DEMAND, 0.0);

        Vertex v3 = new Vertex();
        v3.setId(3);
        v3.setCode("V_3");
        v3.setName("NODO_3");
        v3.setWeight(VertexConstant.VERTEX_DEMAND, 12.0);


        Edge e0 = new Edge();
        e0.setId(0);
        e0.setName("ARCO_A");
        e0.setWeight(WeightConstant.CAPACITY,10.0);
        e0.setSource(v0);
        e0.setDestination(v1);
        e0.setBidirectional(false);

        Edge e1 = new Edge();
        e1.setId(1);
        e1.setName("ARCO_B");
        e1.setWeight(WeightConstant.CAPACITY,8.0);
        e1.setSource(v0);
        e1.setDestination(v2);
        e1.setBidirectional(false);

        Edge e2 = new Edge();
        e2.setId(2);
        e2.setName("ARCO_C");
        e2.setWeight(WeightConstant.CAPACITY,3.0);
        e2.setSource(v1);
        e2.setDestination(v2);
        e2.setBidirectional(false);

        Edge e3 = new Edge();
        e3.setId(3);
        e3.setName("ARCO_D");
        e3.setWeight(WeightConstant.CAPACITY,4.0);
        e3.setSource(v2);
        e3.setDestination(v1);
        e3.setBidirectional(false);

        Edge e4 = new Edge();
        e4.setId(4);
        e4.setName("ARCO_E");
        e4.setWeight(WeightConstant.CAPACITY,5.0);
        e4.setSource(v1);
        e4.setDestination(v3);
        e4.setBidirectional(false);

        Edge e5 = new Edge();
        e5.setId(5);
        e5.setName("ARCO_F");
        e5.setWeight(WeightConstant.CAPACITY,10.0);
        e5.setSource(v2);
        e5.setDestination(v3);
        e5.setBidirectional(false);

        vertexes.add(v0);
        vertexes.add(v1);
        vertexes.add(v2);
        vertexes.add(v3);

        edges.add(e0);
        edges.add(e1);
        edges.add(e2);
        edges.add(e3);
        edges.add(e4);
        edges.add(e5);


        graph.build(vertexes, edges);
        return graph;

    }

    public static Graph createGraphMaxFlow6X6__YouTube(){
        Graph graph = new Graph();
        graph.setName("GraphMaxFlow6X6__YouTube");
        List<Vertex> vertexes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        Vertex v0 = new Vertex();
        v0.setId(0);
        v0.setCode("V_0");
        v0.setName("NODO_0");
        v0.setWeight(VertexConstant.VERTEX_DEMAND, -5.0);

        Vertex v1 = new Vertex();
        v1.setId(1);
        v1.setCode("V_1");
        v1.setName("NODO_1");
        v1.setWeight(VertexConstant.VERTEX_DEMAND, -7.0);

        Vertex v2 = new Vertex();
        v2.setId(2);
        v2.setCode("V_2");
        v2.setName("NODO_2");

        Vertex v3 = new Vertex();
        v3.setId(3);
        v3.setCode("V_3");
        v3.setName("NODO_3");

        Vertex v4 = new Vertex();
        v4.setId(4);
        v4.setCode("V_4");
        v4.setName("NODO_4");
        v4.setWeight(VertexConstant.VERTEX_DEMAND, 10.0);

        Vertex v5 = new Vertex();
        v5.setId(5);
        v5.setCode("V_5");
        v5.setName("NODO_5");
        v5.setWeight(VertexConstant.VERTEX_DEMAND, 2.0);


        Edge e0 = new Edge();
        e0.setId(0);
        e0.setName("ARCO_0");
        e0.setWeight(WeightConstant.CAPACITY,10.0);
        e0.setWeight(WeightConstant.LENGTH,3.0);
        e0.setSource(v0);
        e0.setDestination(v1);
        e0.setBidirectional(false);

        Edge e1 = new Edge();
        e1.setId(1);
        e1.setName("ARCO_1");
        e1.setWeight(WeightConstant.CAPACITY,10.0);
        e1.setWeight(WeightConstant.LENGTH,5.0);
        e1.setSource(v0);
        e1.setDestination(v3);
        e1.setBidirectional(false);

        Edge e2 = new Edge();
        e2.setId(2);
        e2.setName("ARCO_2");
        e2.setWeight(WeightConstant.CAPACITY,2.0);
        e2.setWeight(WeightConstant.LENGTH,2.0);
        e2.setSource(v1);
        e2.setDestination(v3);
        e2.setBidirectional(false);

        Edge e3 = new Edge();
        e3.setId(3);
        e3.setName("ARCO_3");
        e3.setWeight(WeightConstant.CAPACITY,4.0);
        e3.setWeight(WeightConstant.LENGTH,6.0);
        e3.setSource(v1);
        e3.setDestination(v2);
        e3.setBidirectional(false);

        Edge e4 = new Edge();
        e4.setId(4);
        e4.setName("ARCO_4");
        e4.setWeight(WeightConstant.CAPACITY,8.0);
        e4.setWeight(WeightConstant.LENGTH,5.0);
        e4.setSource(v1);
        e4.setDestination(v4);
        e4.setBidirectional(false);

        Edge e5 = new Edge();
        e5.setId(5);
        e5.setName("ARCO_5");
        e5.setWeight(WeightConstant.CAPACITY,9.0);
        e5.setWeight(WeightConstant.LENGTH,4.0);
        e5.setSource(v3);
        e5.setDestination(v4);
        e5.setBidirectional(false);

        Edge e6 = new Edge();
        e6.setId(6);
        e6.setName("ARCO_6");
        e6.setWeight(WeightConstant.CAPACITY,6.0);
        e6.setWeight(WeightConstant.LENGTH,7.0);
        e6.setSource(v4);
        e6.setDestination(v2);
        e6.setBidirectional(false);

        Edge e7 = new Edge();
        e7.setId(7);
        e7.setName("ARCO_7");
        e7.setWeight(WeightConstant.CAPACITY,10.0);
        e7.setWeight(WeightConstant.LENGTH,4.0);
        e7.setSource(v2);
        e7.setDestination(v5);
        e7.setBidirectional(false);

        Edge e8 = new Edge();
        e8.setId(8);
        e8.setName("ARCO_8");
        e8.setWeight(WeightConstant.CAPACITY,10.0);
        e8.setSource(v4);
        e8.setDestination(v5);
        e8.setBidirectional(false);


        vertexes.add(v0);
        vertexes.add(v1);
        vertexes.add(v2);
        vertexes.add(v3);
        vertexes.add(v4);
        vertexes.add(v5);

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
        return graph;

    }


}

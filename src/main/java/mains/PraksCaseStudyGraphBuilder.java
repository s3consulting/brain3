package mains;

import constant.VertexConstant;
import model.Edge;
import model.Graph;
import model.Vertex;
import util.GraphUtil;

import java.util.ArrayList;
import java.util.List;

public class PraksCaseStudyGraphBuilder {

    public static Graph graphBuilderCase_A(){
        Graph graph = GasGraphBuilder.createPraksGasGraph();
        graph.setName("graphBuilderCase_A");
        Vertex node11 = graph.getVertexMap().get(9);
        Edge edge8_51 = graph.getEdgeMap().get("8_51");
        List<Vertex> verticesToBeIsolated = new ArrayList<>();
        verticesToBeIsolated.add(node11);

        List<Edge> edgesToBeRemoved = new ArrayList<>();
        edgesToBeRemoved.add(edge8_51);

        GraphUtil.isolateVerticesFromGraph(verticesToBeIsolated, graph);
        GraphUtil.removeArcsFromGraph(edgesToBeRemoved, graph);



        System.out.println("---------------------------");
        return graph;
    }

    public static Graph graphBuilderCase_B(){
        Graph graph = GasGraphBuilder.createPraksGasGraph_CASE_B();
        graph.setName("graphBuilderCase_B");
        Vertex node11 = graph.getVertexMap().get(9);
        Edge edge8_51 = graph.getEdgeMap().get("8_51");
        List<Vertex> verticesToBeIsolated = new ArrayList<>();
        verticesToBeIsolated.add(node11);


        List<Edge> edgesToBeRemoved = new ArrayList<>();
        edgesToBeRemoved.add(edge8_51);

        GraphUtil.isolateVerticesFromGraph(verticesToBeIsolated, graph);
        GraphUtil.removeArcsFromGraph(edgesToBeRemoved, graph);

        System.out.println("---------------------------");
        return graph;

    }

    public static Graph graphBuilderCase_C(){
        Graph graph = GasGraphBuilder.createPraksGasGraph();
        graph.setName("graphBuilderCase_C");
        Vertex node11 = graph.getVertexMap().get(9);
        Vertex node2 = graph.getVertexMap().get(0);
        Vertex node19 = graph.getVertexMap().get(17);
        Vertex node51 = graph.getVertexMap().get(49);
        Edge edge8_51 = graph.getEdgeMap().get("8_51");
        List<Vertex> verticesToBeIsolated = new ArrayList<>();

        node51.setWeight(VertexConstant.VERTEX_DEMAND, 0.0);

        verticesToBeIsolated.add(node11);
        verticesToBeIsolated.add(node2);
        verticesToBeIsolated.add(node19);


        List<Edge> edgesToBeRemoved = new ArrayList<>();
        edgesToBeRemoved.add(edge8_51);

        GraphUtil.isolateVerticesFromGraph(verticesToBeIsolated, graph);
        GraphUtil.removeArcsFromGraph(edgesToBeRemoved, graph);

        System.out.println("---------------------------");
        return graph;

    }

    public static Graph graphBuilderCase_D(){
        Graph graph = GasGraphBuilder.createPraksGasGraph();
        graph.setName("graphBuilderCase_D");
        Vertex node11 = graph.getVertexMap().get(9);
        Vertex node2 = graph.getVertexMap().get(0);
        Vertex node19 = graph.getVertexMap().get(17);
        Vertex node10 = graph.getVertexMap().get(8);
        Vertex node51 = graph.getVertexMap().get(49);

        Edge edge8_51 = graph.getEdgeMap().get("8_51");
        List<Vertex> verticesToBeIsolated = new ArrayList<>();

        node51.setWeight(VertexConstant.VERTEX_DEMAND, 0.0);

        verticesToBeIsolated.add(node11);
        verticesToBeIsolated.add(node2);
        verticesToBeIsolated.add(node19);

        node10.setWeight(VertexConstant.VERTEX_DEMAND, -10.5);

        List<Edge> edgesToBeRemoved = new ArrayList<>();
        edgesToBeRemoved.add(edge8_51);

        GraphUtil.isolateVerticesFromGraph(verticesToBeIsolated, graph);
        //GraphUtil.removeArcsFromGraph(edgesToBeRemoved, graph);

        System.out.println("---------------------------");
        return graph;

    }

    public static Graph graphBuilderCase_E(){
        Graph graph = GasGraphBuilder.createPraksGasGraph();
        graph.setName("graphBuilderCase_E");
        Vertex node11 = graph.getVertexMap().get(9);
        Vertex node2 = graph.getVertexMap().get(0);
        Vertex node19 = graph.getVertexMap().get(17);
        Vertex node10 = graph.getVertexMap().get(8);
        Vertex node51 = graph.getVertexMap().get(49);

        Edge edge8_51 = graph.getEdgeMap().get("8_51");

        node51.setWeight(VertexConstant.VERTEX_DEMAND, 0.0);

        List<Vertex> verticesToBeIsolated = new ArrayList<>();
        verticesToBeIsolated.add(node11);
        verticesToBeIsolated.add(node2);


        node10.setWeight(VertexConstant.VERTEX_DEMAND, -10.5);
        node19.setWeight(VertexConstant.VERTEX_DEMAND, -25.0);

        List<Edge> edgesToBeRemoved = new ArrayList<>();
        edgesToBeRemoved.add(edge8_51);

        GraphUtil.isolateVerticesFromGraph(verticesToBeIsolated, graph);
        //GraphUtil.removeArcsFromGraph(edgesToBeRemoved, graph);

        System.out.println("---------------------------");
        return graph;

    }

    public static Graph graphBuilderCase_F(){

        Graph graph = GasGraphBuilder.createPraksGasGraph();
        graph.setName("graphBuilderCase_F");
        Vertex node11 = graph.getVertexMap().get(9);
        Vertex node10 = graph.getVertexMap().get(8);
        Edge edge8_51 = graph.getEdgeMap().get("8_51");
        List<Vertex> verticesToBeIsolated = new ArrayList<>();
        verticesToBeIsolated.add(node11);



        node10.setWeight(VertexConstant.VERTEX_DEMAND, -10.5);


        List<Edge> edgesToBeRemoved = new ArrayList<>();
        edgesToBeRemoved.add(edge8_51);

        GraphUtil.isolateVerticesFromGraph(verticesToBeIsolated, graph);
        //GraphUtil.removeArcsFromGraph(edgesToBeRemoved, graph);

        System.out.println("---------------------------");
        return graph;

    }

    public static Graph graphBuilderCase_G(){
        Graph graph = GasGraphBuilder.createPraksGasGraph();
        graph.setName("graphBuilderCase_G");
        Vertex node11 = graph.getVertexMap().get(9);
        Vertex node10 = graph.getVertexMap().get(8);
        Edge edge8_51 = graph.getEdgeMap().get("8_51");
        List<Vertex> verticesToBeIsolated = new ArrayList<>();
        verticesToBeIsolated.add(node11);



        node10.setWeight(VertexConstant.VERTEX_DEMAND, -10.5);


        List<Edge> edgesToBeRemoved = new ArrayList<>();
        edgesToBeRemoved.add(edge8_51);

        //GraphUtil.isolateVerticesFromGraph(verticesToBeIsolated, graph);
        //GraphUtil.removeArcsFromGraph(edgesToBeRemoved, graph);

        System.out.println("---------------------------");
        return graph;

    }

}

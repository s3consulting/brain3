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

public class GasGraphBuilder {

    public static Graph createPraksGasGraph() {
        Graph graph = new Graph();
        graph.setName("PraksGasGraph");
        List<Vertex> vertexes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();


        //VERTICES

        //Source 1
        Vertex v2 = new Vertex();
        v2.setId(0);
        v2.setCode("V_2");
        v2.setName("NODO_2");
        v2.setWeight(VertexConstant.VERTEX_DEMAND, -31.0);
        v2.setType(TypeConstant.COMPRESSION_STATION);

        Vertex v3 = new Vertex();
        v3.setId(1);
        v3.setCode("V_3");
        v3.setName("NODO_3");


        Vertex v4 = new Vertex();
        v4.setId(2);
        v4.setCode("V_4");
        v4.setName("NODO_4");


        //Sink 1
        Vertex v5 = new Vertex();
        v5.setId(3);
        v5.setCode("V_5");
        v5.setName("NODO_5");
        v5.setWeight(VertexConstant.VERTEX_DEMAND, 3.43);

        //Sink 2
        Vertex v6 = new Vertex();
        v6.setId(4);
        v6.setCode("V_6");
        v6.setName("NODO_6");
        v6.setWeight(VertexConstant.VERTEX_DEMAND, 0.57);

        //Sink 3
        Vertex v7 = new Vertex();
        v7.setId(5);
        v7.setCode("V_7");
        v7.setName("NODO_7");
        v7.setWeight(VertexConstant.VERTEX_DEMAND, 0.66);

        Vertex v8 = new Vertex();
        v8.setId(6);
        v8.setCode("V_8");
        v8.setName("NODO_8");

        Vertex v9 = new Vertex();
        v9.setId(7);
        v9.setCode("V_9");
        v9.setName("NODO_9");

        //Source 2
        Vertex v10 = new Vertex();
        v10.setId(8);
        v10.setCode("V_10");
        v10.setName("NODO_10");
        v10.setWeight(VertexConstant.VERTEX_DEMAND, -4.0);
        v10.setType(TypeConstant.LNG);

        //Source 3
        Vertex v11 = new Vertex();
        v11.setId(9);
        v11.setCode("V_11");
        v11.setName("NODO_11");
        v11.setWeight(VertexConstant.VERTEX_DEMAND, -7.1);
        v11.setType(TypeConstant.COMPRESSION_STATION);

        Vertex v12 = new Vertex();
        v12.setId(10);
        v12.setCode("V_12");
        v12.setName("NODO_12");

        Vertex v13 = new Vertex();
        v13.setId(11);
        v13.setCode("V_13");
        v13.setName("NODO_13");
        v13.setWeight(VertexConstant.VERTEX_DEMAND, 1.03);

        Vertex v14 = new Vertex();
        v14.setId(12);
        v14.setCode("V_14");
        v14.setName("NODO_14");

        Vertex v15 = new Vertex();
        v15.setId(13);
        v15.setCode("V_15");
        v15.setName("NODO_15");


        Vertex v16 = new Vertex();
        v16.setId(14);
        v16.setCode("V_16");
        v16.setName("NODO_16");

        Vertex v17 = new Vertex();
        v17.setId(15);
        v17.setCode("V_17");
        v17.setName("NODO_17");
        v17.setWeight(VertexConstant.VERTEX_DEMAND, 0.46);

        Vertex v18 = new Vertex();
        v18.setId(16);
        v18.setCode("V_18");
        v18.setName("NODO_18");
        v18.setWeight(VertexConstant.VERTEX_DEMAND, 8.4);

        //Source 4
        Vertex v19 = new Vertex();
        v19.setId(17);
        v19.setCode("V_19");
        v19.setName("NODO_19");
        v19.setWeight(VertexConstant.VERTEX_DEMAND, -25.0);
        v19.setType(TypeConstant.UNDERGROUND_GAS_STORAGE);

        Vertex v20 = new Vertex();
        v20.setId(18);
        v20.setCode("V_20");
        v20.setName("NODO_20");

        Vertex v21 = new Vertex();
        v21.setId(19);
        v21.setCode("V_21");
        v21.setName("NODO_21");
        v21.setWeight(VertexConstant.VERTEX_DEMAND, 0.54);

        Vertex v22 = new Vertex();
        v22.setId(20);
        v22.setCode("V_22");
        v22.setName("NODO_22");

        Vertex v23 = new Vertex();
        v23.setId(21);
        v23.setCode("V_23");
        v23.setName("NODO_23");

        Vertex v24 = new Vertex();
        v24.setId(22);
        v24.setCode("V_24");
        v24.setName("NODO_24");

        Vertex v25 = new Vertex();
        v25.setId(23);
        v25.setCode("V_25");
        v25.setName("NODO_25");
        v25.setWeight(VertexConstant.VERTEX_DEMAND, 0.6);


        Vertex v26 = new Vertex();
        v26.setId(24);
        v26.setCode("V_26");
        v26.setName("NODO_26");
        v26.setWeight(VertexConstant.VERTEX_DEMAND, 0.8);


        Vertex v27 = new Vertex();
        v27.setId(25);
        v27.setCode("V_27");
        v27.setName("NODO_27");
        v27.setWeight(VertexConstant.VERTEX_DEMAND, 3.5);

        Vertex v28 = new Vertex();
        v28.setId(26);
        v28.setCode("V_28");
        v28.setName("NODO_28");
        v28.setWeight(VertexConstant.VERTEX_DEMAND, 6.0);

        Vertex v29 = new Vertex();
        v29.setId(27);
        v29.setCode("V_29");
        v29.setName("NODO_29");

        Vertex v30 = new Vertex();
        v30.setId(28);
        v30.setCode("V_30");
        v30.setName("NODO_30");
        v30.setWeight(VertexConstant.VERTEX_DEMAND, 0.4);

        Vertex v31 = new Vertex();
        v31.setId(29);
        v31.setCode("V_31");
        v31.setName("NODO_31");

        Vertex v32 = new Vertex();
        v32.setId(30);
        v32.setCode("V_32");
        v32.setName("NODO_32");

        Vertex v33 = new Vertex();
        v33.setId(31);
        v33.setCode("V_33");
        v33.setName("NODO_33");
        v33.setWeight(VertexConstant.VERTEX_DEMAND, 0.4);

        Vertex v34 = new Vertex();
        v34.setId(32);
        v34.setCode("V_34");
        v34.setName("NODO_34");
        v34.setWeight(VertexConstant.VERTEX_DEMAND, 1.0);

        Vertex v35 = new Vertex();
        v35.setId(33);
        v35.setCode("V_35");
        v35.setName("NODO_35");

        Vertex v36 = new Vertex();
        v36.setId(34);
        v36.setCode("V_36");
        v36.setName("NODO_36");
        v36.setWeight(VertexConstant.VERTEX_DEMAND, 1.74);

        Vertex v37 = new Vertex();
        v37.setId(35);
        v37.setCode("V_37");
        v37.setName("NODO_37");
        v37.setWeight(VertexConstant.VERTEX_DEMAND, 1.3);

        Vertex v38 = new Vertex();
        v38.setId(36);
        v38.setCode("V_38");
        v38.setName("NODO_38");

        Vertex v39 = new Vertex();
        v39.setId(37);
        v39.setCode("V_39");
        v39.setName("NODO_39");
        v39.setWeight(VertexConstant.VERTEX_DEMAND, 1.0);

        Vertex v40 = new Vertex();
        v40.setId(38);
        v40.setCode("V_40");
        v40.setName("NODO_40");

        Vertex v41 = new Vertex();
        v41.setId(39);
        v41.setCode("V_41");
        v41.setName("NODO_41");
        v41.setWeight(VertexConstant.VERTEX_DEMAND, 0.4);

        Vertex v42 = new Vertex();
        v42.setId(40);
        v42.setCode("V_42");
        v42.setName("NODO_42");
        v42.setWeight(VertexConstant.VERTEX_DEMAND, 0.5);

        Vertex v43 = new Vertex();
        v43.setId(41);
        v43.setCode("V_43");
        v43.setName("NODO_43");
        v43.setWeight(VertexConstant.VERTEX_DEMAND, 1.06);

        Vertex v44 = new Vertex();
        v44.setId(42);
        v44.setCode("V_44");
        v44.setName("NODO_44");
        v44.setWeight(VertexConstant.VERTEX_DEMAND, 2.82);

        Vertex v45 = new Vertex();
        v45.setId(43);
        v45.setCode("V_45");
        v45.setName("NODO_45");


        Vertex v46 = new Vertex();
        v46.setId(44);
        v46.setCode("V_46");
        v46.setName("NODO_46");

        Vertex v47 = new Vertex();
        v47.setId(45);
        v47.setCode("V_47");
        v47.setName("NODO_47");
        v47.setWeight(VertexConstant.VERTEX_DEMAND, 0.68);

        Vertex v48 = new Vertex();
        v48.setId(46);
        v48.setCode("V_48");
        v48.setName("NODO_48");
        v48.setWeight(VertexConstant.VERTEX_DEMAND, 1.17);

        Vertex v49 = new Vertex();
        v49.setId(47);
        v49.setCode("V_49");
        v49.setName("NODO_49");


        Vertex v50 = new Vertex();
        v50.setId(48);
        v50.setCode("V_50");
        v50.setName("NODO_50");


        Vertex v51 = new Vertex();
        v51.setId(49);
        v51.setCode("V_51");
        v51.setName("NODO_51");
        v51.setWeight(VertexConstant.VERTEX_DEMAND, 7.0);

        Vertex v52 = new Vertex();
        v52.setId(50);
        v52.setCode("V_52");
        v52.setName("NODO_52");
        v32.setWeight(VertexConstant.VERTEX_DEMAND, 0.98);

        Vertex v53 = new Vertex();
        v53.setId(51);
        v53.setCode("V_53");
        v53.setName("NODO_53");


        Vertex v54 = new Vertex();
        v54.setId(52);
        v54.setCode("V_54");
        v54.setName("NODO_54");

        //EDGES
        Edge e0 = new Edge();
        e0.setId(0);
        e0.setName("ARCO_0");
        e0.setWeight(WeightConstant.LENGTH,23.0);
        e0.setWeight(WeightConstant.CAPACITY,31.0);
        e0.setSource(v2);
        e0.setDestination(v50);
        e0.setBidirectional(false);

        Edge e1 = new Edge();
        e1.setId(1);
        e1.setName("ARCO_1");
        e1.setWeight(WeightConstant.LENGTH,0.01);
        e1.setWeight(WeightConstant.CAPACITY,49.16);
        e1.setSource(v3);
        e1.setDestination(v4);
        e1.setBidirectional(false);

        Edge e2 = new Edge();
        e2.setId(2);
        e2.setName("ARCO_2");
        e2.setWeight(WeightConstant.LENGTH,32.0);
        e2.setWeight(WeightConstant.CAPACITY,12.11);
        e2.setSource(v3);
        e2.setDestination(v5);
        e2.setBidirectional(false);

        Edge e3 = new Edge();
        e3.setId(3);
        e3.setName("ARCO_3");
        e3.setWeight(WeightConstant.LENGTH,29.0);
        e3.setWeight(WeightConstant.CAPACITY,12.11);
        e3.setSource(v3);
        e3.setDestination(v11);
        e3.setBidirectional(false);

        Edge e4 = new Edge();
        e4.setId(4);
        e4.setName("ARCO_4");
        e4.setWeight(WeightConstant.LENGTH,22.0);
        e4.setWeight(WeightConstant.CAPACITY,17.13);
        e4.setSource(v3);
        e4.setDestination(v46);
        //e4.setSource(v46);
        //e4.setDestination(v3);
        e4.setBidirectional(false);


        Edge e5 = new Edge();
        e5.setId(5);
        e5.setName("ARCO_5");
        e5.setWeight(WeightConstant.LENGTH,32.0);
        e5.setWeight(WeightConstant.CAPACITY,12.11);
        e5.setSource(v4);
        e5.setDestination(v5);
        e5.setBidirectional(false);

        Edge e6 = new Edge();
        e6.setId(6);
        e6.setName("ARCO_6");
        e6.setWeight(WeightConstant.LENGTH,22.0);
        e6.setWeight(WeightConstant.CAPACITY,2.0);
        e6.setSource(v4);
        e6.setDestination(v47);
        e6.setBidirectional(false);

        Edge e7 = new Edge();
        e7.setId(7);
        e7.setName("ARCO_7");
        e7.setWeight(WeightConstant.LENGTH,2.0);
        e7.setWeight(WeightConstant.CAPACITY,12.11);
        e7.setSource(v4);
        e7.setDestination(v48);
        e7.setBidirectional(false);

        Edge e8 = new Edge();
        e8.setId(8);
        e8.setName("ARCO_8");
        e8.setWeight(WeightConstant.LENGTH,5.0);
        e8.setWeight(WeightConstant.CAPACITY,5.05);
        e8.setSource(v5);
        e8.setDestination(v43);
        e8.setBidirectional(false);

        Edge e9 = new Edge();
        e9.setId(9);
        e9.setName("ARCO_9");
        e9.setWeight(WeightConstant.LENGTH,80.0);
        e9.setWeight(WeightConstant.CAPACITY,12.11);
        e9.setSource(v6);
        e9.setDestination(v7);
        e9.setBidirectional(false);

        Edge e10 = new Edge();
        e10.setId(10);
        e10.setName("ARCO_10");
        e10.setWeight(WeightConstant.LENGTH,80.0);
        e10.setWeight(WeightConstant.CAPACITY,5.05);
        e10.setSource(v6);
        e10.setDestination(v8);
        e10.setBidirectional(false);

        Edge e11 = new Edge();
        e11.setId(11);
        e11.setName("ARCO_11");
        e11.setWeight(WeightConstant.LENGTH,30.0);
        e11.setWeight(WeightConstant.CAPACITY,5.05);
        e11.setSource(v6);
        e11.setDestination(v35);
        e11.setBidirectional(false);

        Edge e12 = new Edge();
        e12.setId(12);
        e12.setName("ARCO_12");
        e12.setWeight(WeightConstant.LENGTH,11.6);
        e12.setWeight(WeightConstant.CAPACITY,5.05);
        e12.setSource(v6);
        e12.setDestination(v44);
        e12.setBidirectional(false);

        Edge e13 = new Edge();
        e13.setId(13);
        e13.setName("ARCO_13");
        e13.setWeight(WeightConstant.LENGTH,0.01);
        e13.setWeight(WeightConstant.CAPACITY,49.16);
        e13.setSource(v7);
        e13.setDestination(v8);
        e13.setBidirectional(false);

        Edge e14 = new Edge();
        e14.setId(14);
        e14.setName("ARCO_14");
        e14.setWeight(WeightConstant.LENGTH,200.0);
        e14.setWeight(WeightConstant.CAPACITY,12.11);
        e14.setSource(v7);
        e14.setDestination(v51);
        e14.setBidirectional(false);

        Edge e15 = new Edge();
        e15.setId(15);
        e15.setName("ARCO_15");
        e15.setWeight(WeightConstant.LENGTH,25.0);
        e15.setWeight(WeightConstant.CAPACITY,2.83);
        e15.setSource(v8);
        e15.setDestination(v9);
        e15.setBidirectional(false);

        Edge e16 = new Edge();
        e16.setId(16);
        e16.setName("ARCO_16");
        e16.setWeight(WeightConstant.LENGTH,200.0);
        e16.setWeight(WeightConstant.CAPACITY,12.11);
        e16.setSource(v8);
        e16.setDestination(v51);
        e16.setBidirectional(false);

        Edge e17 = new Edge();
        e17.setId(17);
        e17.setName("ARCO_17");
        e17.setWeight(WeightConstant.LENGTH,162.0);
        e17.setWeight(WeightConstant.CAPACITY,2.83);
        e17.setSource(v9);
        e17.setDestination(v10);
        e17.setBidirectional(false);

        Edge e18 = new Edge();
        e18.setId(18);
        e18.setName("ARCO_18");
        e18.setWeight(WeightConstant.LENGTH,144.0);
        e18.setWeight(WeightConstant.CAPACITY,1.34);
        e18.setSource(v10);
        e18.setDestination(v53);
        e18.setBidirectional(false);

        Edge e19 = new Edge();
        e19.setId(19);
        e19.setName("ARCO_19");
        e19.setWeight(WeightConstant.LENGTH,144.0);
        e19.setWeight(WeightConstant.CAPACITY,5.05);
        e19.setSource(v10);
        e19.setDestination(v54);
        e19.setBidirectional(false);

        Edge e20 = new Edge();
        e20.setId(20);
        e20.setName("ARCO_20");
        e20.setWeight(WeightConstant.LENGTH,103.0);
        e20.setWeight(WeightConstant.CAPACITY,2.0);
        e20.setSource(v11);
        e20.setDestination(v12);
        e20.setBidirectional(false);

        Edge e21 = new Edge();
        e21.setId(21);
        e21.setName("ARCO_21");
        e21.setWeight(WeightConstant.LENGTH,34.0);
        e21.setWeight(WeightConstant.CAPACITY,12.11);
        e21.setSource(v11);
        e21.setDestination(v43);
        e21.setBidirectional(false);

        Edge e22 = new Edge();
        e22.setId(22);
        e22.setName("ARCO_22");
        e22.setWeight(WeightConstant.LENGTH,31.0);
        e22.setWeight(WeightConstant.CAPACITY,49.16);
        e22.setSource(v11);
        e22.setDestination(v50);
        e22.setBidirectional(false);

        Edge e23 = new Edge();
        e23.setId(23);
        e23.setName("ARCO_23");
        e23.setWeight(WeightConstant.LENGTH,85.0);
        e23.setWeight(WeightConstant.CAPACITY,49.16);
        e23.setSource(v12);
        e23.setDestination(v13);
        e23.setBidirectional(false);

        Edge e24 = new Edge();
        e24.setId(24);
        e24.setName("ARCO_24");
        e24.setWeight(WeightConstant.LENGTH,62.0);
        e24.setWeight(WeightConstant.CAPACITY,49.16);
        e24.setSource(v12);
        e24.setDestination(v17);
        e24.setBidirectional(false);

        Edge e25 = new Edge();
        e25.setId(25);
        e25.setName("ARCO_25");
        e25.setWeight(WeightConstant.LENGTH,10.0);
        e25.setWeight(WeightConstant.CAPACITY,12.11);
        e25.setSource(v12);
        e25.setDestination(v52);
        e25.setBidirectional(false);

        Edge e26 = new Edge();
        e26.setId(26);
        e26.setName("ARCO_26");
        e26.setWeight(WeightConstant.LENGTH,0.01);
        e26.setWeight(WeightConstant.CAPACITY,30.6);
        e26.setSource(v13);
        e26.setDestination(v14);
        e26.setBidirectional(false);

        Edge e27 = new Edge();
        e27.setId(27);
        e27.setName("ARCO_27");
        e27.setWeight(WeightConstant.LENGTH,30.0);
        e27.setWeight(WeightConstant.CAPACITY,2.0);
        e27.setSource(v13);
        e27.setDestination(v53);
        e27.setBidirectional(false);

        Edge e28 = new Edge();
        e28.setId(28);
        e28.setName("ARCO_28");
        e28.setWeight(WeightConstant.LENGTH,85.0);
        e28.setWeight(WeightConstant.CAPACITY,5.05);
        e28.setSource(v14);
        e28.setDestination(v15);
        e28.setBidirectional(false);

        Edge e29 = new Edge();
        e29.setId(29);
        e29.setName("ARCO_29");
        e29.setWeight(WeightConstant.LENGTH,30.0);
        e29.setWeight(WeightConstant.CAPACITY,5.05);
        e29.setSource(v14);
        e29.setDestination(v54);
        e29.setBidirectional(false);

        Edge e30 = new Edge();
        e30.setId(30);
        e30.setName("ARCO_30");
        e30.setWeight(WeightConstant.LENGTH,62.0);
        e30.setWeight(WeightConstant.CAPACITY,12.11);
        e30.setSource(v15);
        e30.setDestination(v16);
        e30.setBidirectional(false);

        Edge e31 = new Edge();
        e31.setId(31);
        e31.setName("ARCO_31");
        e31.setWeight(WeightConstant.LENGTH,132.0);
        e31.setWeight(WeightConstant.CAPACITY,12.11);
        e31.setSource(v15);
        e31.setDestination(v43);
        e31.setBidirectional(false);

        Edge e32 = new Edge();
        e32.setId(32);
        e32.setName("ARCO_32");
        e32.setWeight(WeightConstant.LENGTH,0.01);
        e32.setWeight(WeightConstant.CAPACITY,25.0);
        e32.setSource(v16);
        e32.setDestination(v17);
        e32.setBidirectional(false);

        Edge e33 = new Edge();
        e33.setId(33);
        e33.setName("ARCO_33");
        e33.setWeight(WeightConstant.LENGTH,24.0);
        e33.setWeight(WeightConstant.CAPACITY,4.0);
        e33.setSource(v16);
        e33.setDestination(v34);
        e33.setBidirectional(false);

        Edge e34 = new Edge();
        e34.setId(34);
        e34.setName("ARCO_34");
        e34.setWeight(WeightConstant.LENGTH,24.0);
        e34.setWeight(WeightConstant.CAPACITY,12.11);
        e34.setSource(v17);
        e34.setDestination(v34);
        e34.setBidirectional(false);

        Edge e35 = new Edge();
        e35.setId(35);
        e35.setName("ARCO_35");
        e35.setWeight(WeightConstant.LENGTH,43.0);
        e35.setWeight(WeightConstant.CAPACITY,12.11);
        e35.setSource(v18);
        e35.setDestination(v19);
        e35.setBidirectional(false);

        Edge e36 = new Edge();
        e36.setId(36);
        e36.setName("ARCO_36");
        e36.setWeight(WeightConstant.LENGTH,43.0);
        e36.setWeight(WeightConstant.CAPACITY,49.16);
        e36.setSource(v18);
        e36.setDestination(v23);
        e36.setBidirectional(false);


        Edge e37 = new Edge();
        e37.setId(37);
        e37.setName("ARCO_37");
        e37.setWeight(WeightConstant.LENGTH,43.0);
        e37.setWeight(WeightConstant.CAPACITY,2.83);
        e37.setSource(v18);
        e37.setDestination(v34);
        e37.setBidirectional(false);

        Edge e38 = new Edge();
        e38.setId(38);
        e38.setName("ARCO_38");
        e38.setWeight(WeightConstant.LENGTH,5.05);
        e38.setWeight(WeightConstant.CAPACITY,148.0);
        e38.setSource(v18);
        e38.setDestination(v40);
        e38.setBidirectional(false);

        Edge e39 = new Edge();
        e39.setId(39);
        e39.setName("ARCO_39");
        e39.setWeight(WeightConstant.LENGTH,60.0);
        e39.setWeight(WeightConstant.CAPACITY,12.11);
        e39.setSource(v19);
        e39.setDestination(v20);
        e39.setBidirectional(false);

        Edge e40 = new Edge();
        e40.setId(40);
        e40.setName("ARCO_40");
        e40.setWeight(WeightConstant.LENGTH,0.01);
        e40.setWeight(WeightConstant.CAPACITY,12.11);
        e40.setSource(v19);
        e40.setDestination(v23);
        e40.setBidirectional(false);

        Edge e41 = new Edge();
        e41.setId(41);
        e41.setName("ARCO_41");
        e41.setWeight(WeightConstant.LENGTH,90.0);
        e41.setWeight(WeightConstant.CAPACITY,49.16);
        e41.setSource(v20);
        e41.setDestination(v21);
        e41.setBidirectional(false);

        Edge e42 = new Edge();
        e42.setId(42);
        e42.setName("ARCO_42");
        e42.setWeight(WeightConstant.LENGTH,90.0);
        e42.setWeight(WeightConstant.CAPACITY,12.11);
        e42.setSource(v20);
        e42.setDestination(v22);
        e42.setBidirectional(false);

        Edge e43 = new Edge();
        e43.setId(43);
        e43.setName("ARCO_43");
        e43.setWeight(WeightConstant.LENGTH,90.0);
        e43.setWeight(WeightConstant.CAPACITY,12.11);
        e43.setSource(v21);
        e43.setDestination(v22);
        e43.setBidirectional(false);

        Edge e44 = new Edge();
        e44.setId(44);
        e44.setName("ARCO_44");
        e44.setWeight(WeightConstant.LENGTH,86.0);
        e44.setWeight(WeightConstant.CAPACITY,12.11);
        e44.setSource(v21);
        e44.setDestination(v28);
        e44.setBidirectional(false);

        Edge e45 = new Edge();
        e45.setId(45);
        e45.setName("ARCO_45");
        e45.setWeight(WeightConstant.LENGTH,60.0);
        e45.setWeight(WeightConstant.CAPACITY,7.0);
        e45.setSource(v22);
        e45.setDestination(v23);
        e45.setBidirectional(false);

        Edge e46 = new Edge();
        e46.setId(46);
        e46.setName("ARCO_46");
        e46.setWeight(WeightConstant.LENGTH,86.0);
        e46.setWeight(WeightConstant.CAPACITY,12.11);
        e46.setSource(v22);
        e46.setDestination(v24);
        e46.setBidirectional(false);

        Edge e47 = new Edge();
        e47.setId(47);
        e47.setName("ARCO_47");
        e47.setWeight(WeightConstant.LENGTH,86.0);
        e47.setWeight(WeightConstant.CAPACITY,0.83);
        e47.setSource(v24);
        e47.setDestination(v25);
        e47.setBidirectional(false);

        Edge e48 = new Edge();
        e48.setId(48);
        e48.setName("ARCO_48");
        e48.setWeight(WeightConstant.LENGTH,46.0);
        e48.setWeight(WeightConstant.CAPACITY,12.11);
        e48.setSource(v25);
        e48.setDestination(v26);
        e48.setBidirectional(false);

        Edge e49 = new Edge();
        e49.setId(49);
        e49.setName("ARCO_49");
        e49.setWeight(WeightConstant.LENGTH,100.0);
        e49.setWeight(WeightConstant.CAPACITY,49.16);
        e49.setSource(v25);
        e49.setDestination(v27);
        e49.setBidirectional(false);

        Edge e50 = new Edge();
        e50.setId(50);
        e50.setName("ARCO_50");
        e50.setWeight(WeightConstant.LENGTH,0.01);
        e50.setWeight(WeightConstant.CAPACITY,5.05);
        e50.setSource(v27);
        e50.setDestination(v31);
        e50.setBidirectional(false);

        Edge e51 = new Edge();
        e51.setId(51);
        e51.setName("ARCO_51");
        e51.setWeight(WeightConstant.LENGTH,70.0);
        e51.setWeight(WeightConstant.CAPACITY,5.05);
        e51.setSource(v27);
        e51.setDestination(v32);
        e51.setBidirectional(false);

        Edge e52 = new Edge();
        e52.setId(52);
        e52.setName("ARCO_52");
        e52.setWeight(WeightConstant.LENGTH,50.0);
        e52.setWeight(WeightConstant.CAPACITY,49.16);
        e52.setSource(v28);
        e52.setDestination(v29);
        e52.setBidirectional(false);

        Edge e53 = new Edge();
        e53.setId(53);
        e53.setName("ARCO_53");
        e53.setWeight(WeightConstant.LENGTH,195.0);
        e53.setWeight(WeightConstant.CAPACITY,49.16);
        e53.setSource(v29);
        e53.setDestination(v32);
        e53.setBidirectional(false);

        Edge e54 = new Edge();
        e54.setId(54);
        e54.setName("ARCO_54");
        e54.setWeight(WeightConstant.LENGTH,70.0);
        e54.setWeight(WeightConstant.CAPACITY,5.05);
        e54.setSource(v30);
        e54.setDestination(v31);
        e54.setBidirectional(false);

        Edge e55 = new Edge();
        e55.setId(55);
        e55.setName("ARCO_55");
        e55.setWeight(WeightConstant.LENGTH,0.01);
        e55.setWeight(WeightConstant.CAPACITY,0.47);
        e55.setSource(v30);
        e55.setDestination(v32);
        e55.setBidirectional(false);

        Edge e56 = new Edge();
        e56.setId(56);
        e56.setName("ARCO_56");
        e56.setWeight(WeightConstant.LENGTH,60.0);
        e56.setWeight(WeightConstant.CAPACITY,0.47);
        e56.setSource(v30);
        e56.setDestination(v33);
        e56.setBidirectional(false);

        Edge e57 = new Edge();
        e57.setId(57);
        e57.setName("ARCO_57");
        e57.setWeight(WeightConstant.LENGTH,60.0);
        e57.setWeight(WeightConstant.CAPACITY,2.0);
        e57.setSource(v32);
        e57.setDestination(v33);
        e57.setBidirectional(false);

        Edge e58 = new Edge();
        e58.setId(58);
        e58.setName("ARCO_58");
        e58.setWeight(WeightConstant.LENGTH,60.0);
        e58.setWeight(WeightConstant.CAPACITY,5.05);
        e58.setSource(v33);
        e58.setDestination(v38);
        e58.setBidirectional(false);

        Edge e59 = new Edge();
        e59.setId(59);
        e59.setName("ARCO_59");
        e59.setWeight(WeightConstant.LENGTH,200.0);
        e59.setWeight(WeightConstant.CAPACITY,2.83);
        e59.setSource(v34);
        e59.setDestination(v37);
        e59.setBidirectional(false);

        Edge e60 = new Edge();
        e60.setId(60);
        e60.setName("ARCO_60");
        e60.setWeight(WeightConstant.LENGTH,24.0);
        e60.setWeight(WeightConstant.CAPACITY,5.05);
        e60.setSource(v36);
        e60.setDestination(v46);
        e60.setBidirectional(false);

        Edge e61 = new Edge();
        e61.setId(61);
        e61.setName("ARCO_61");
        e61.setWeight(WeightConstant.LENGTH,24.0);
        e61.setWeight(WeightConstant.CAPACITY,5.05);
        e61.setSource(v36);
        e61.setDestination(v47);
        e61.setBidirectional(false);

        Edge e62 = new Edge();
        e62.setId(62);
        e62.setName("ARCO_62");
        e62.setWeight(WeightConstant.LENGTH,106.0);
        e62.setWeight(WeightConstant.CAPACITY,1.34);
        e62.setSource(v39);
        e62.setDestination(v50);
        e62.setBidirectional(false);

        Edge e63 = new Edge();
        e63.setId(63);
        e63.setName("ARCO_63");
        e63.setWeight(WeightConstant.LENGTH,32.0);
        e63.setWeight(WeightConstant.CAPACITY,5.05);
        e63.setSource(v40);
        e63.setDestination(v41);
        e63.setBidirectional(false);

        Edge e64 = new Edge();
        e64.setId(64);
        e64.setName("ARCO_64");
        e64.setWeight(WeightConstant.LENGTH,63.0);
        e64.setWeight(WeightConstant.CAPACITY,12.11);
        e64.setSource(v40);
        e64.setDestination(v42);
        e64.setBidirectional(false);

        Edge e65 = new Edge();
        e65.setId(65);
        e65.setName("ARCO_65");
        e65.setWeight(WeightConstant.LENGTH,1.0);
        e65.setWeight(WeightConstant.CAPACITY,5.05);
        e65.setSource(v44);
        e65.setDestination(v45);
        e65.setBidirectional(false);

        Edge e66 = new Edge();
        e66.setId(66);
        e66.setName("ARCO_66");
        e66.setWeight(WeightConstant.LENGTH,23.0);
        e66.setWeight(WeightConstant.CAPACITY,17.13);
        e66.setSource(v44);
        e66.setDestination(v46);
        e66.setBidirectional(false);

        Edge e67 = new Edge();
        e67.setId(67);
        e67.setName("ARCO_67");
        e67.setWeight(WeightConstant.LENGTH,23.0);
        e67.setWeight(WeightConstant.CAPACITY,2.0);
        e67.setSource(v44);
        e67.setDestination(v47);
        e67.setBidirectional(false);

        Edge e68 = new Edge();
        e68.setId(68);
        e68.setName("ARCO_68");
        e68.setWeight(WeightConstant.LENGTH,0.01);
        e68.setWeight(WeightConstant.CAPACITY,49.16);
        e68.setSource(v46);
        e68.setDestination(v47);
        e68.setBidirectional(false);

        Edge e69 = new Edge();
        e69.setId(69);
        e69.setName("ARCO_69");
        e69.setWeight(WeightConstant.LENGTH,40.0);
        e69.setWeight(WeightConstant.CAPACITY,0.83);
        e69.setSource(v49);
        e69.setDestination(v54);
        e69.setBidirectional(false);

        Edge e70 = new Edge();
        e70.setId(70);
        e70.setName("ARCO_70");
        e70.setWeight(WeightConstant.LENGTH,0.01);
        e70.setWeight(WeightConstant.CAPACITY,49.16);
        e70.setSource(v53);
        e70.setDestination(v54);
        e70.setBidirectional(false);


        vertexes.add(v2);
        vertexes.add(v3);
        vertexes.add(v4);
        vertexes.add(v5);
        vertexes.add(v6);
        vertexes.add(v7);
        vertexes.add(v8);
        vertexes.add(v9);
        vertexes.add(v10);
        vertexes.add(v11);
        vertexes.add(v12);
        vertexes.add(v13);
        vertexes.add(v14);
        vertexes.add(v15);
        vertexes.add(v16);
        vertexes.add(v17);
        vertexes.add(v18);
        vertexes.add(v19);
        vertexes.add(v20);
        vertexes.add(v21);
        vertexes.add(v22);
        vertexes.add(v23);
        vertexes.add(v24);
        vertexes.add(v25);
        vertexes.add(v26);
        vertexes.add(v27);
        vertexes.add(v28);
        vertexes.add(v29);
        vertexes.add(v30);
        vertexes.add(v31);
        vertexes.add(v32);
        vertexes.add(v33);
        vertexes.add(v34);
        vertexes.add(v35);
        vertexes.add(v36);
        vertexes.add(v37);
        vertexes.add(v38);
        vertexes.add(v39);
        vertexes.add(v40);
        vertexes.add(v41);
        vertexes.add(v42);
        vertexes.add(v43);
        vertexes.add(v44);
        vertexes.add(v45);
        vertexes.add(v46);
        vertexes.add(v47);
        vertexes.add(v48);
        vertexes.add(v49);
        vertexes.add(v50);
        vertexes.add(v51);
        vertexes.add(v52);
        vertexes.add(v53);
        vertexes.add(v54);

        edges.add(e0);
        edges.add(e1);
        edges.add(e2);
        edges.add(e3);
        edges.add(e4);
        edges.add(e5);
        edges.add(e6);
        edges.add(e7);
        edges.add(e8);
        edges.add(e9);
        edges.add(e10);
        edges.add(e11);
        edges.add(e12);
        edges.add(e13);
        edges.add(e14);
        edges.add(e15);
        edges.add(e16);
        edges.add(e17);
        edges.add(e18);
        edges.add(e19);
        edges.add(e20);
        edges.add(e21);
        edges.add(e22);
        edges.add(e23);
        edges.add(e24);
        edges.add(e25);
        edges.add(e26);
        edges.add(e27);
        edges.add(e28);
        edges.add(e29);
        edges.add(e30);
        edges.add(e31);
        edges.add(e32);
        edges.add(e33);
        edges.add(e34);
        edges.add(e35);
        edges.add(e36);
        edges.add(e37);
        edges.add(e38);
        edges.add(e39);
        edges.add(e40);
        edges.add(e41);
        edges.add(e42);
        edges.add(e43);
        edges.add(e44);
        edges.add(e45);
        edges.add(e46);
        edges.add(e47);
        edges.add(e48);
        edges.add(e49);
        edges.add(e50);
        edges.add(e51);
        edges.add(e52);
        edges.add(e53);
        edges.add(e54);
        edges.add(e55);
        edges.add(e56);
        edges.add(e57);
        edges.add(e58);
        edges.add(e59);
        edges.add(e60);
        edges.add(e61);
        edges.add(e62);
        edges.add(e63);
        edges.add(e64);
        edges.add(e65);
        edges.add(e66);
        edges.add(e67);
        edges.add(e68);
        edges.add(e69);
        edges.add(e70);


        graph.build(vertexes, edges);
        GraphUtil.initializeGraph(graph);
        GraphUtil.makeSymmetric(graph);
        GraphUtil.removeArcsToRealSources(graph);

        return graph;
    }

    public static Graph GasNetworkEsercizio(){
        Graph graph = new Graph();
        graph.setName("GasNetworkEsercizio");
        List<Vertex> vertexes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        //VERTICES
        for(int i=0; i<7; i++){
            Vertex v = new Vertex();
            v.setId(i);
            v.setCode("V_"+i);
            v.setName("NODE_"+i);
            vertexes.add(v);
        }

        //Sources
        vertexes.get(0).setWeight(VertexConstant.VERTEX_DEMAND,-9.0);
        vertexes.get(1).setWeight(VertexConstant.VERTEX_DEMAND, -12.0);
        //Sinks
        vertexes.get(5).setWeight(VertexConstant.VERTEX_DEMAND, 13.0);
        vertexes.get(6).setWeight(VertexConstant.VERTEX_DEMAND, 8.0);


        //EDGES
        Edge e0 = new Edge();
        e0.setId(0);
        e0.setName("ARCO_0");
        e0.setWeight(WeightConstant.LENGTH,5.0);
        e0.setWeight(WeightConstant.CAPACITY,10.0);
        e0.setWeight(WeightConstant.ORIGINAL_CAPACITY,e0.getWeight(WeightConstant.CAPACITY));
        e0.setSource(vertexes.get(0));
        e0.setDestination(vertexes.get(3));
        e0.setBidirectional(false);

        Edge e1 = new Edge();
        e1.setId(1);
        e1.setName("ARCO_1");
        e1.setWeight(WeightConstant.LENGTH,13.0);
        e1.setWeight(WeightConstant.CAPACITY,8.0);
        e1.setWeight(WeightConstant.ORIGINAL_CAPACITY,e1.getWeight(WeightConstant.CAPACITY));
        e1.setSource(vertexes.get(0));
        e1.setDestination(vertexes.get(2));
        e1.setBidirectional(false);

        Edge e2 = new Edge();
        e2.setId(2);
        e2.setName("ARCO_2");
        e2.setWeight(WeightConstant.LENGTH,10.0);
        e2.setWeight(WeightConstant.CAPACITY,5.0);
        e2.setWeight(WeightConstant.ORIGINAL_CAPACITY,e2.getWeight(WeightConstant.CAPACITY));
        e2.setSource(vertexes.get(0));
        e2.setDestination(vertexes.get(1));
        e2.setBidirectional(false);

        Edge e3 = new Edge();
        e3.setId(3);
        e3.setName("ARCO_3");
        e3.setWeight(WeightConstant.LENGTH,16.0);
        e3.setWeight(WeightConstant.CAPACITY,10.0);
        e3.setWeight(WeightConstant.ORIGINAL_CAPACITY,e3.getWeight(WeightConstant.CAPACITY));
        e3.setSource(vertexes.get(1));
        e3.setDestination(vertexes.get(2));
        e3.setBidirectional(false);

        Edge e4 = new Edge();
        e4.setId(4);
        e4.setName("ARCO_4");
        e4.setWeight(WeightConstant.LENGTH,20.0);
        e4.setWeight(WeightConstant.CAPACITY,12.0);
        e4.setWeight(WeightConstant.ORIGINAL_CAPACITY,e4.getWeight(WeightConstant.CAPACITY));
        e4.setSource(vertexes.get(1));
        e4.setDestination(vertexes.get(4));
        e4.setBidirectional(false);

        Edge e5 = new Edge();
        e5.setId(5);
        e5.setName("ARCO_5");
        e5.setWeight(WeightConstant.LENGTH,8.0);
        e5.setWeight(WeightConstant.CAPACITY,9.0);
        e5.setWeight(WeightConstant.ORIGINAL_CAPACITY,e5.getWeight(WeightConstant.CAPACITY));
        e5.setSource(vertexes.get(2));
        e5.setDestination(vertexes.get(3));
        e5.setBidirectional(false);

        Edge e6 = new Edge();
        e6.setId(6);
        e6.setName("ARCO_6");
        e6.setWeight(WeightConstant.LENGTH,20.0);
        e6.setWeight(WeightConstant.CAPACITY,12.0);
        e6.setWeight(WeightConstant.ORIGINAL_CAPACITY,e6.getWeight(WeightConstant.CAPACITY));
        e6.setSource(vertexes.get(3));
        e6.setDestination(vertexes.get(5));
        e6.setBidirectional(false);

        Edge e7 = new Edge();
        e7.setId(7);
        e7.setName("ARCO_7");
        e7.setWeight(WeightConstant.LENGTH,1.0);
        e7.setWeight(WeightConstant.CAPACITY,12.0);
        e7.setWeight(WeightConstant.ORIGINAL_CAPACITY,e7.getWeight(WeightConstant.CAPACITY));
        e7.setSource(vertexes.get(3));
        e7.setDestination(vertexes.get(6));
        e7.setBidirectional(false);

        Edge e8 = new Edge();
        e8.setId(8);
        e8.setName("ARCO_8");
        e8.setWeight(WeightConstant.LENGTH,12.0);
        e8.setWeight(WeightConstant.CAPACITY,14.0);
        e8.setWeight(WeightConstant.ORIGINAL_CAPACITY,e8.getWeight(WeightConstant.CAPACITY));
        e8.setSource(vertexes.get(3));
        e8.setDestination(vertexes.get(4));
        e8.setBidirectional(false);

        Edge e9 = new Edge();
        e9.setId(9);
        e9.setName("ARCO_9");
        e9.setWeight(WeightConstant.LENGTH,11.0);
        e9.setWeight(WeightConstant.CAPACITY,11.0);
        e9.setWeight(WeightConstant.ORIGINAL_CAPACITY,e9.getWeight(WeightConstant.CAPACITY));
        e9.setSource(vertexes.get(4));
        e9.setDestination(vertexes.get(2));
        e9.setBidirectional(false);

        Edge e10 = new Edge();
        e10.setId(10);
        e10.setName("ARCO_10");
        e10.setWeight(WeightConstant.LENGTH,13.0);
        e10.setWeight(WeightConstant.CAPACITY,15.0);
        e10.setWeight(WeightConstant.ORIGINAL_CAPACITY,e10.getWeight(WeightConstant.CAPACITY));
        e10.setSource(vertexes.get(4));
        e10.setDestination(vertexes.get(5));
        e10.setBidirectional(false);

        Edge e11 = new Edge();
        e11.setId(11);
        e11.setName("ARCO_11");
        e11.setWeight(WeightConstant.LENGTH,18.0);
        e11.setWeight(WeightConstant.CAPACITY,14.0);
        e11.setWeight(WeightConstant.ORIGINAL_CAPACITY,e11.getWeight(WeightConstant.CAPACITY));
        e11.setSource(vertexes.get(4));
        e11.setDestination(vertexes.get(6));
        e11.setBidirectional(false);

        Edge e12 = new Edge();
        e12.setId(12);
        e12.setName("ARCO_12");
        e12.setWeight(WeightConstant.LENGTH,9.0);
        e12.setWeight(WeightConstant.CAPACITY,5.0);
        e12.setWeight(WeightConstant.ORIGINAL_CAPACITY,e12.getWeight(WeightConstant.CAPACITY));
        e12.setSource(vertexes.get(6));
        e12.setDestination(vertexes.get(5));
        e12.setBidirectional(false);

        edges.add(e0);
        edges.add(e1);
        edges.add(e2);
        edges.add(e3);
        edges.add(e4);
        edges.add(e5);
        edges.add(e6);
        edges.add(e7);
        edges.add(e8);
        edges.add(e9);
        edges.add(e10);
        edges.add(e11);
        edges.add(e12);

        graph.build(vertexes, edges);

        return graph;
    }

    public static Graph GasNetworkEsercizio1(){
        Graph graph = new Graph();
        graph.setName("GasNetworkEsercizio");
        List<Vertex> vertexes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        //VERTICES
        for(int i=0; i<8; i++){
            Vertex v = new Vertex();
            v.setId(i);
            v.setCode("V_"+i);
            v.setName("NODE_"+i);
            vertexes.add(v);
        }

        //Sources
        vertexes.get(0).setWeight(VertexConstant.VERTEX_DEMAND,-16.0);
        vertexes.get(1).setWeight(VertexConstant.VERTEX_DEMAND, -15.0);
        //Sinks
        vertexes.get(5).setWeight(VertexConstant.VERTEX_DEMAND, 13.0);
        vertexes.get(6).setWeight(VertexConstant.VERTEX_DEMAND, 8.0);
        vertexes.get(7).setWeight(VertexConstant.VERTEX_DEMAND, 10.0);


        //EDGES
        Edge e0 = new Edge();
        e0.setId(0);
        e0.setName("ARCO_0");
        e0.setWeight(WeightConstant.LENGTH,5.0);
        e0.setWeight(WeightConstant.CAPACITY,10.0);
        e0.setWeight(WeightConstant.ORIGINAL_CAPACITY,e0.getWeight(WeightConstant.CAPACITY));
        e0.setSource(vertexes.get(0));
        e0.setDestination(vertexes.get(3));
        e0.setBidirectional(false);

        Edge e1 = new Edge();
        e1.setId(1);
        e1.setName("ARCO_1");
        e1.setWeight(WeightConstant.LENGTH,13.0);
        e1.setWeight(WeightConstant.CAPACITY,8.0);
        e1.setWeight(WeightConstant.ORIGINAL_CAPACITY,e1.getWeight(WeightConstant.CAPACITY));
        e1.setSource(vertexes.get(0));
        e1.setDestination(vertexes.get(2));
        e1.setBidirectional(false);

        Edge e2 = new Edge();
        e2.setId(2);
        e2.setName("ARCO_2");
        e2.setWeight(WeightConstant.LENGTH,10.0);
        e2.setWeight(WeightConstant.CAPACITY,5.0);
        e2.setWeight(WeightConstant.ORIGINAL_CAPACITY,e2.getWeight(WeightConstant.CAPACITY));
        e2.setSource(vertexes.get(0));
        e2.setDestination(vertexes.get(1));
        e2.setBidirectional(false);

        Edge e3 = new Edge();
        e3.setId(3);
        e3.setName("ARCO_3");
        e3.setWeight(WeightConstant.LENGTH,16.0);
        e3.setWeight(WeightConstant.CAPACITY,10.0);
        e3.setWeight(WeightConstant.ORIGINAL_CAPACITY,e3.getWeight(WeightConstant.CAPACITY));
        e3.setSource(vertexes.get(1));
        e3.setDestination(vertexes.get(2));
        e3.setBidirectional(false);

        Edge e4 = new Edge();
        e4.setId(4);
        e4.setName("ARCO_4");
        e4.setWeight(WeightConstant.LENGTH,20.0);
        e4.setWeight(WeightConstant.CAPACITY,12.0);
        e4.setWeight(WeightConstant.ORIGINAL_CAPACITY,e4.getWeight(WeightConstant.CAPACITY));
        e4.setSource(vertexes.get(1));
        e4.setDestination(vertexes.get(4));
        e4.setBidirectional(false);

        Edge e5 = new Edge();
        e5.setId(5);
        e5.setName("ARCO_5");
        e5.setWeight(WeightConstant.LENGTH,8.0);
        e5.setWeight(WeightConstant.CAPACITY,9.0);
        e5.setWeight(WeightConstant.ORIGINAL_CAPACITY,e5.getWeight(WeightConstant.CAPACITY));
        e5.setSource(vertexes.get(2));
        e5.setDestination(vertexes.get(3));
        e5.setBidirectional(false);

        Edge e6 = new Edge();
        e6.setId(6);
        e6.setName("ARCO_6");
        e6.setWeight(WeightConstant.LENGTH,20.0);
        e6.setWeight(WeightConstant.CAPACITY,12.0);
        e6.setWeight(WeightConstant.ORIGINAL_CAPACITY,e6.getWeight(WeightConstant.CAPACITY));
        e6.setSource(vertexes.get(3));
        e6.setDestination(vertexes.get(5));
        e6.setBidirectional(false);

        Edge e7 = new Edge();
        e7.setId(7);
        e7.setName("ARCO_7");
        e7.setWeight(WeightConstant.LENGTH,1.0);
        e7.setWeight(WeightConstant.CAPACITY,12.0);
        e7.setWeight(WeightConstant.ORIGINAL_CAPACITY,e7.getWeight(WeightConstant.CAPACITY));
        e7.setSource(vertexes.get(3));
        e7.setDestination(vertexes.get(6));
        e7.setBidirectional(false);

        Edge e8 = new Edge();
        e8.setId(8);
        e8.setName("ARCO_8");
        e8.setWeight(WeightConstant.LENGTH,12.0);
        e8.setWeight(WeightConstant.CAPACITY,14.0);
        e8.setWeight(WeightConstant.ORIGINAL_CAPACITY,e8.getWeight(WeightConstant.CAPACITY));
        e8.setSource(vertexes.get(3));
        e8.setDestination(vertexes.get(4));
        e8.setBidirectional(false);

        Edge e9 = new Edge();
        e9.setId(9);
        e9.setName("ARCO_9");
        e9.setWeight(WeightConstant.LENGTH,11.0);
        e9.setWeight(WeightConstant.CAPACITY,11.0);
        e9.setWeight(WeightConstant.ORIGINAL_CAPACITY,e9.getWeight(WeightConstant.CAPACITY));
        e9.setSource(vertexes.get(4));
        e9.setDestination(vertexes.get(2));
        e9.setBidirectional(false);

        Edge e10 = new Edge();
        e10.setId(10);
        e10.setName("ARCO_10");
        e10.setWeight(WeightConstant.LENGTH,13.0);
        e10.setWeight(WeightConstant.CAPACITY,15.0);
        e10.setWeight(WeightConstant.ORIGINAL_CAPACITY,e10.getWeight(WeightConstant.CAPACITY));
        e10.setSource(vertexes.get(4));
        e10.setDestination(vertexes.get(5));
        e10.setBidirectional(false);

        Edge e11 = new Edge();
        e11.setId(11);
        e11.setName("ARCO_11");
        e11.setWeight(WeightConstant.LENGTH,18.0);
        e11.setWeight(WeightConstant.CAPACITY,14.0);
        e11.setWeight(WeightConstant.ORIGINAL_CAPACITY,e11.getWeight(WeightConstant.CAPACITY));
        e11.setSource(vertexes.get(4));
        e11.setDestination(vertexes.get(6));
        e11.setBidirectional(false);

        Edge e12 = new Edge();
        e12.setId(12);
        e12.setName("ARCO_12");
        e12.setWeight(WeightConstant.LENGTH,9.0);
        e12.setWeight(WeightConstant.CAPACITY,5.0);
        e12.setWeight(WeightConstant.ORIGINAL_CAPACITY,e12.getWeight(WeightConstant.CAPACITY));
        e12.setSource(vertexes.get(6));
        e12.setDestination(vertexes.get(5));
        e12.setBidirectional(false);

        Edge e13 = new Edge();
        e13.setId(13);
        e13.setName("ARCO_13");
        e13.setWeight(WeightConstant.LENGTH,1.0);
        e13.setWeight(WeightConstant.CAPACITY,15.0);
        e13.setWeight(WeightConstant.ORIGINAL_CAPACITY,e12.getWeight(WeightConstant.CAPACITY));
        e13.setSource(vertexes.get(6));
        e13.setDestination(vertexes.get(7));
        e13.setBidirectional(false);

        Edge e14 = new Edge();
        e14.setId(14);
        e14.setName("ARCO_14");
        e14.setWeight(WeightConstant.LENGTH,3.0);
        e14.setWeight(WeightConstant.CAPACITY,20.0);
        e14.setWeight(WeightConstant.ORIGINAL_CAPACITY,e12.getWeight(WeightConstant.CAPACITY));
        e14.setSource(vertexes.get(3));
        e14.setDestination(vertexes.get(7));
        e14.setBidirectional(false);

        Edge e15 = new Edge();
        e15.setId(15);
        e15.setName("ARCO_15");
        e15.setWeight(WeightConstant.LENGTH,20.0);
        e15.setWeight(WeightConstant.CAPACITY,10.0);
        e15.setWeight(WeightConstant.ORIGINAL_CAPACITY,e12.getWeight(WeightConstant.CAPACITY));
        e15.setSource(vertexes.get(4));
        e15.setDestination(vertexes.get(7));
        e15.setBidirectional(false);

        edges.add(e0);
        edges.add(e1);
        edges.add(e2);
        edges.add(e3);
        edges.add(e4);
        edges.add(e5);
        edges.add(e6);
        edges.add(e7);
        edges.add(e8);
        edges.add(e9);
        edges.add(e10);
        edges.add(e11);
        edges.add(e12);
        edges.add(e13);
        edges.add(e14);
        edges.add(e15);

        graph.build(vertexes, edges);
        GraphUtil.initializeGraph(graph);
        GraphUtil.makeSymmetric(graph);
        return graph;
    }


    public static Graph createPraksGraphWithIncreasedCapacities(){
        Graph graph = createPraksGasGraph();
        for(Edge edge: graph.getEdges()){
            if(edge.getWeight(WeightConstant.CAPACITY)<10.0){
                edge.setWeight(WeightConstant.CAPACITY, edge.getWeight(WeightConstant.CAPACITY)+10);
                edge.setWeight(WeightConstant.ORIGINAL_CAPACITY, edge.getWeight(WeightConstant.CAPACITY));
            }
        }
        return graph;
    }

    public static Graph createPraksGraphWithIncreasedSourceDemand(){
        Graph graph = createPraksGasGraph();
        for(Vertex vertex: graph.getVertexes()){
            if(vertex.getWeight(VertexConstant.VERTEX_DEMAND)!=null && vertex.getWeight(VertexConstant.VERTEX_DEMAND)<0){
                vertex.setWeight(VertexConstant.VERTEX_DEMAND, vertex.getWeight(VertexConstant.VERTEX_DEMAND)*3);
            }
        }
        return graph;
    }

    public static Graph createPraksGasGraph_CASE_B() {
        Graph graph = new Graph();
        graph.setName("PraksGasGraph");
        List<Vertex> vertexes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();


        //VERTICES

        //Source 1
        Vertex v2 = new Vertex();
        v2.setId(0);
        v2.setCode("V_2");
        v2.setName("NODO_2");
        v2.setWeight(VertexConstant.VERTEX_DEMAND, -31.0);
        v2.setType(TypeConstant.COMPRESSION_STATION);

        Vertex v3 = new Vertex();
        v3.setId(1);
        v3.setCode("V_3");
        v3.setName("NODO_3");


        Vertex v4 = new Vertex();
        v4.setId(2);
        v4.setCode("V_4");
        v4.setName("NODO_4");


        //Sink 1
        Vertex v5 = new Vertex();
        v5.setId(3);
        v5.setCode("V_5");
        v5.setName("NODO_5");
        v5.setWeight(VertexConstant.VERTEX_DEMAND, 3.43);

        //Sink 2
        Vertex v6 = new Vertex();
        v6.setId(4);
        v6.setCode("V_6");
        v6.setName("NODO_6");
        v6.setWeight(VertexConstant.VERTEX_DEMAND, 0.57);

        //Sink 3
        Vertex v7 = new Vertex();
        v7.setId(5);
        v7.setCode("V_7");
        v7.setName("NODO_7");
        v7.setWeight(VertexConstant.VERTEX_DEMAND, 0.66);

        Vertex v8 = new Vertex();
        v8.setId(6);
        v8.setCode("V_8");
        v8.setName("NODO_8");

        Vertex v9 = new Vertex();
        v9.setId(7);
        v9.setCode("V_9");
        v9.setName("NODO_9");

        //Source 2
        Vertex v10 = new Vertex();
        v10.setId(8);
        v10.setCode("V_10");
        v10.setName("NODO_10");
        v10.setWeight(VertexConstant.VERTEX_DEMAND, 2.02);
        v10.setType(TypeConstant.LNG);

        //Source 3
        Vertex v11 = new Vertex();
        v11.setId(9);
        v11.setCode("V_11");
        v11.setName("NODO_11");
        v11.setWeight(VertexConstant.VERTEX_DEMAND, -7.1);
        v11.setType(TypeConstant.COMPRESSION_STATION);

        Vertex v12 = new Vertex();
        v12.setId(10);
        v12.setCode("V_12");
        v12.setName("NODO_12");

        Vertex v13 = new Vertex();
        v13.setId(11);
        v13.setCode("V_13");
        v13.setName("NODO_13");
        v13.setWeight(VertexConstant.VERTEX_DEMAND, 1.03);

        Vertex v14 = new Vertex();
        v14.setId(12);
        v14.setCode("V_14");
        v14.setName("NODO_14");

        Vertex v15 = new Vertex();
        v15.setId(13);
        v15.setCode("V_15");
        v15.setName("NODO_15");


        Vertex v16 = new Vertex();
        v16.setId(14);
        v16.setCode("V_16");
        v16.setName("NODO_16");

        Vertex v17 = new Vertex();
        v17.setId(15);
        v17.setCode("V_17");
        v17.setName("NODO_17");
        v17.setWeight(VertexConstant.VERTEX_DEMAND, 0.46);

        Vertex v18 = new Vertex();
        v18.setId(16);
        v18.setCode("V_18");
        v18.setName("NODO_18");
        v18.setWeight(VertexConstant.VERTEX_DEMAND, 8.4);

        //Source 4
        Vertex v19 = new Vertex();
        v19.setId(17);
        v19.setCode("V_19");
        v19.setName("NODO_19");
        v19.setWeight(VertexConstant.VERTEX_DEMAND, -25.0);
        v19.setType(TypeConstant.UNDERGROUND_GAS_STORAGE);

        Vertex v20 = new Vertex();
        v20.setId(18);
        v20.setCode("V_20");
        v20.setName("NODO_20");

        Vertex v21 = new Vertex();
        v21.setId(19);
        v21.setCode("V_21");
        v21.setName("NODO_21");
        v21.setWeight(VertexConstant.VERTEX_DEMAND, 0.54);

        Vertex v22 = new Vertex();
        v22.setId(20);
        v22.setCode("V_22");
        v22.setName("NODO_22");

        Vertex v23 = new Vertex();
        v23.setId(21);
        v23.setCode("V_23");
        v23.setName("NODO_23");

        Vertex v24 = new Vertex();
        v24.setId(22);
        v24.setCode("V_24");
        v24.setName("NODO_24");

        Vertex v25 = new Vertex();
        v25.setId(23);
        v25.setCode("V_25");
        v25.setName("NODO_25");
        v25.setWeight(VertexConstant.VERTEX_DEMAND, 0.6);


        Vertex v26 = new Vertex();
        v26.setId(24);
        v26.setCode("V_26");
        v26.setName("NODO_26");
        v26.setWeight(VertexConstant.VERTEX_DEMAND, 0.8);


        Vertex v27 = new Vertex();
        v27.setId(25);
        v27.setCode("V_27");
        v27.setName("NODO_27");
        v27.setWeight(VertexConstant.VERTEX_DEMAND, 3.5);

        Vertex v28 = new Vertex();
        v28.setId(26);
        v28.setCode("V_28");
        v28.setName("NODO_28");
        v28.setWeight(VertexConstant.VERTEX_DEMAND, 6.0);

        Vertex v29 = new Vertex();
        v29.setId(27);
        v29.setCode("V_29");
        v29.setName("NODO_29");

        Vertex v30 = new Vertex();
        v30.setId(28);
        v30.setCode("V_30");
        v30.setName("NODO_30");
        v30.setWeight(VertexConstant.VERTEX_DEMAND, 0.4);

        Vertex v31 = new Vertex();
        v31.setId(29);
        v31.setCode("V_31");
        v31.setName("NODO_31");

        Vertex v32 = new Vertex();
        v32.setId(30);
        v32.setCode("V_32");
        v32.setName("NODO_32");

        Vertex v33 = new Vertex();
        v33.setId(31);
        v33.setCode("V_33");
        v33.setName("NODO_33");
        v33.setWeight(VertexConstant.VERTEX_DEMAND, 0.4);

        Vertex v34 = new Vertex();
        v34.setId(32);
        v34.setCode("V_34");
        v34.setName("NODO_34");
        v34.setWeight(VertexConstant.VERTEX_DEMAND, 1.0);

        Vertex v35 = new Vertex();
        v35.setId(33);
        v35.setCode("V_35");
        v35.setName("NODO_35");

        Vertex v36 = new Vertex();
        v36.setId(34);
        v36.setCode("V_36");
        v36.setName("NODO_36");
        v36.setWeight(VertexConstant.VERTEX_DEMAND, 1.74);

        Vertex v37 = new Vertex();
        v37.setId(35);
        v37.setCode("V_37");
        v37.setName("NODO_37");
        v37.setWeight(VertexConstant.VERTEX_DEMAND, 1.3);

        Vertex v38 = new Vertex();
        v38.setId(36);
        v38.setCode("V_38");
        v38.setName("NODO_38");

        Vertex v39 = new Vertex();
        v39.setId(37);
        v39.setCode("V_39");
        v39.setName("NODO_39");
        v39.setWeight(VertexConstant.VERTEX_DEMAND, 1.0);

        Vertex v40 = new Vertex();
        v40.setId(38);
        v40.setCode("V_40");
        v40.setName("NODO_40");

        Vertex v41 = new Vertex();
        v41.setId(39);
        v41.setCode("V_41");
        v41.setName("NODO_41");
        v41.setWeight(VertexConstant.VERTEX_DEMAND, 0.4);

        Vertex v42 = new Vertex();
        v42.setId(40);
        v42.setCode("V_42");
        v42.setName("NODO_42");
        v42.setWeight(VertexConstant.VERTEX_DEMAND, 0.5);

        Vertex v43 = new Vertex();
        v43.setId(41);
        v43.setCode("V_43");
        v43.setName("NODO_43");
        v43.setWeight(VertexConstant.VERTEX_DEMAND, 1.06);

        Vertex v44 = new Vertex();
        v44.setId(42);
        v44.setCode("V_44");
        v44.setName("NODO_44");
        v44.setWeight(VertexConstant.VERTEX_DEMAND, 2.82);

        Vertex v45 = new Vertex();
        v45.setId(43);
        v45.setCode("V_45");
        v45.setName("NODO_45");


        Vertex v46 = new Vertex();
        v46.setId(44);
        v46.setCode("V_46");
        v46.setName("NODO_46");

        Vertex v47 = new Vertex();
        v47.setId(45);
        v47.setCode("V_47");
        v47.setName("NODO_47");
        v47.setWeight(VertexConstant.VERTEX_DEMAND, 0.68);

        Vertex v48 = new Vertex();
        v48.setId(46);
        v48.setCode("V_48");
        v48.setName("NODO_48");
        v48.setWeight(VertexConstant.VERTEX_DEMAND, 1.17);

        Vertex v49 = new Vertex();
        v49.setId(47);
        v49.setCode("V_49");
        v49.setName("NODO_49");


        Vertex v50 = new Vertex();
        v50.setId(48);
        v50.setCode("V_50");
        v50.setName("NODO_50");


        Vertex v51 = new Vertex();
        v51.setId(49);
        v51.setCode("V_51");
        v51.setName("NODO_51");
        v51.setWeight(VertexConstant.VERTEX_DEMAND, 7.0);

        Vertex v52 = new Vertex();
        v52.setId(50);
        v52.setCode("V_52");
        v52.setName("NODO_52");
        v32.setWeight(VertexConstant.VERTEX_DEMAND, 0.98);

        Vertex v53 = new Vertex();
        v53.setId(51);
        v53.setCode("V_53");
        v53.setName("NODO_53");


        Vertex v54 = new Vertex();
        v54.setId(52);
        v54.setCode("V_54");
        v54.setName("NODO_54");

        //EDGES
        Edge e0 = new Edge();
        e0.setId(0);
        e0.setName("ARCO_0");
        e0.setWeight(WeightConstant.LENGTH,23.0);
        e0.setWeight(WeightConstant.CAPACITY,31.0);
        e0.setSource(v2);
        e0.setDestination(v50);
        e0.setBidirectional(false);

        Edge e1 = new Edge();
        e1.setId(1);
        e1.setName("ARCO_1");
        e1.setWeight(WeightConstant.LENGTH,0.01);
        e1.setWeight(WeightConstant.CAPACITY,49.16);
        e1.setSource(v3);
        e1.setDestination(v4);
        e1.setBidirectional(false);

        Edge e2 = new Edge();
        e2.setId(2);
        e2.setName("ARCO_2");
        e2.setWeight(WeightConstant.LENGTH,32.0);
        e2.setWeight(WeightConstant.CAPACITY,12.11);
        e2.setSource(v3);
        e2.setDestination(v5);
        e2.setBidirectional(false);

        Edge e3 = new Edge();
        e3.setId(3);
        e3.setName("ARCO_3");
        e3.setWeight(WeightConstant.LENGTH,29.0);
        e3.setWeight(WeightConstant.CAPACITY,12.11);
        e3.setSource(v3);
        e3.setDestination(v11);
        e3.setBidirectional(false);

        Edge e4 = new Edge();
        e4.setId(4);
        e4.setName("ARCO_4");
        e4.setWeight(WeightConstant.LENGTH,22.0);
        e4.setWeight(WeightConstant.CAPACITY,17.13);
        e4.setSource(v3);
        e4.setDestination(v46);
        //e4.setSource(v46);
        //e4.setDestination(v3);
        e4.setBidirectional(false);


        Edge e5 = new Edge();
        e5.setId(5);
        e5.setName("ARCO_5");
        e5.setWeight(WeightConstant.LENGTH,32.0);
        e5.setWeight(WeightConstant.CAPACITY,12.11);
        e5.setSource(v4);
        e5.setDestination(v5);
        e5.setBidirectional(false);

        Edge e6 = new Edge();
        e6.setId(6);
        e6.setName("ARCO_6");
        e6.setWeight(WeightConstant.LENGTH,22.0);
        e6.setWeight(WeightConstant.CAPACITY,2.0);
        e6.setSource(v4);
        e6.setDestination(v47);
        e6.setBidirectional(false);

        Edge e7 = new Edge();
        e7.setId(7);
        e7.setName("ARCO_7");
        e7.setWeight(WeightConstant.LENGTH,2.0);
        e7.setWeight(WeightConstant.CAPACITY,12.11);
        e7.setSource(v4);
        e7.setDestination(v48);
        e7.setBidirectional(false);

        Edge e8 = new Edge();
        e8.setId(8);
        e8.setName("ARCO_8");
        e8.setWeight(WeightConstant.LENGTH,5.0);
        e8.setWeight(WeightConstant.CAPACITY,5.05);
        e8.setSource(v5);
        e8.setDestination(v43);
        e8.setBidirectional(false);

        Edge e9 = new Edge();
        e9.setId(9);
        e9.setName("ARCO_9");
        e9.setWeight(WeightConstant.LENGTH,80.0);
        e9.setWeight(WeightConstant.CAPACITY,12.11);
        e9.setSource(v6);
        e9.setDestination(v7);
        e9.setBidirectional(false);

        Edge e10 = new Edge();
        e10.setId(10);
        e10.setName("ARCO_10");
        e10.setWeight(WeightConstant.LENGTH,80.0);
        e10.setWeight(WeightConstant.CAPACITY,5.05);
        e10.setSource(v6);
        e10.setDestination(v8);
        e10.setBidirectional(false);

        Edge e11 = new Edge();
        e11.setId(11);
        e11.setName("ARCO_11");
        e11.setWeight(WeightConstant.LENGTH,30.0);
        e11.setWeight(WeightConstant.CAPACITY,5.05);
        e11.setSource(v6);
        e11.setDestination(v35);
        e11.setBidirectional(false);

        Edge e12 = new Edge();
        e12.setId(12);
        e12.setName("ARCO_12");
        e12.setWeight(WeightConstant.LENGTH,11.6);
        e12.setWeight(WeightConstant.CAPACITY,5.05);
        e12.setSource(v6);
        e12.setDestination(v44);
        e12.setBidirectional(false);

        Edge e13 = new Edge();
        e13.setId(13);
        e13.setName("ARCO_13");
        e13.setWeight(WeightConstant.LENGTH,0.01);
        e13.setWeight(WeightConstant.CAPACITY,49.16);
        e13.setSource(v7);
        e13.setDestination(v8);
        e13.setBidirectional(false);

        Edge e14 = new Edge();
        e14.setId(14);
        e14.setName("ARCO_14");
        e14.setWeight(WeightConstant.LENGTH,200.0);
        e14.setWeight(WeightConstant.CAPACITY,12.11);
        e14.setSource(v7);
        e14.setDestination(v51);
        e14.setBidirectional(false);

        Edge e15 = new Edge();
        e15.setId(15);
        e15.setName("ARCO_15");
        e15.setWeight(WeightConstant.LENGTH,25.0);
        e15.setWeight(WeightConstant.CAPACITY,2.83);
        e15.setSource(v8);
        e15.setDestination(v9);
        e15.setBidirectional(false);

        Edge e16 = new Edge();
        e16.setId(16);
        e16.setName("ARCO_16");
        e16.setWeight(WeightConstant.LENGTH,200.0);
        e16.setWeight(WeightConstant.CAPACITY,12.11);
        e16.setSource(v8);
        e16.setDestination(v51);
        e16.setBidirectional(false);

        Edge e17 = new Edge();
        e17.setId(17);
        e17.setName("ARCO_17");
        e17.setWeight(WeightConstant.LENGTH,162.0);
        e17.setWeight(WeightConstant.CAPACITY,2.83);
        e17.setSource(v9);
        e17.setDestination(v10);
        e17.setBidirectional(false);

        Edge e18 = new Edge();
        e18.setId(18);
        e18.setName("ARCO_18");
        e18.setWeight(WeightConstant.LENGTH,144.0);
        e18.setWeight(WeightConstant.CAPACITY,1.34);
        e18.setSource(v10);
        e18.setDestination(v53);
        e18.setBidirectional(false);

        Edge e19 = new Edge();
        e19.setId(19);
        e19.setName("ARCO_19");
        e19.setWeight(WeightConstant.LENGTH,144.0);
        e19.setWeight(WeightConstant.CAPACITY,5.05);
        e19.setSource(v10);
        e19.setDestination(v54);
        e19.setBidirectional(false);

        Edge e20 = new Edge();
        e20.setId(20);
        e20.setName("ARCO_20");
        e20.setWeight(WeightConstant.LENGTH,103.0);
        e20.setWeight(WeightConstant.CAPACITY,2.0);
        e20.setSource(v11);
        e20.setDestination(v12);
        e20.setBidirectional(false);

        Edge e21 = new Edge();
        e21.setId(21);
        e21.setName("ARCO_21");
        e21.setWeight(WeightConstant.LENGTH,34.0);
        e21.setWeight(WeightConstant.CAPACITY,12.11);
        e21.setSource(v11);
        e21.setDestination(v43);
        e21.setBidirectional(false);

        Edge e22 = new Edge();
        e22.setId(22);
        e22.setName("ARCO_22");
        e22.setWeight(WeightConstant.LENGTH,31.0);
        e22.setWeight(WeightConstant.CAPACITY,49.16);
        e22.setSource(v11);
        e22.setDestination(v50);
        e22.setBidirectional(false);

        Edge e23 = new Edge();
        e23.setId(23);
        e23.setName("ARCO_23");
        e23.setWeight(WeightConstant.LENGTH,85.0);
        e23.setWeight(WeightConstant.CAPACITY,49.16);
        e23.setSource(v12);
        e23.setDestination(v13);
        e23.setBidirectional(false);

        Edge e24 = new Edge();
        e24.setId(24);
        e24.setName("ARCO_24");
        e24.setWeight(WeightConstant.LENGTH,62.0);
        e24.setWeight(WeightConstant.CAPACITY,49.16);
        e24.setSource(v12);
        e24.setDestination(v17);
        e24.setBidirectional(false);

        Edge e25 = new Edge();
        e25.setId(25);
        e25.setName("ARCO_25");
        e25.setWeight(WeightConstant.LENGTH,10.0);
        e25.setWeight(WeightConstant.CAPACITY,12.11);
        e25.setSource(v12);
        e25.setDestination(v52);
        e25.setBidirectional(false);

        Edge e26 = new Edge();
        e26.setId(26);
        e26.setName("ARCO_26");
        e26.setWeight(WeightConstant.LENGTH,0.01);
        e26.setWeight(WeightConstant.CAPACITY,30.6);
        e26.setSource(v13);
        e26.setDestination(v14);
        e26.setBidirectional(false);

        Edge e27 = new Edge();
        e27.setId(27);
        e27.setName("ARCO_27");
        e27.setWeight(WeightConstant.LENGTH,30.0);
        e27.setWeight(WeightConstant.CAPACITY,2.0);
        e27.setSource(v13);
        e27.setDestination(v53);
        e27.setBidirectional(false);

        Edge e28 = new Edge();
        e28.setId(28);
        e28.setName("ARCO_28");
        e28.setWeight(WeightConstant.LENGTH,85.0);
        e28.setWeight(WeightConstant.CAPACITY,5.05);
        e28.setSource(v14);
        e28.setDestination(v15);
        e28.setBidirectional(false);

        Edge e29 = new Edge();
        e29.setId(29);
        e29.setName("ARCO_29");
        e29.setWeight(WeightConstant.LENGTH,30.0);
        e29.setWeight(WeightConstant.CAPACITY,5.05);
        e29.setSource(v14);
        e29.setDestination(v54);
        e29.setBidirectional(false);

        Edge e30 = new Edge();
        e30.setId(30);
        e30.setName("ARCO_30");
        e30.setWeight(WeightConstant.LENGTH,62.0);
        e30.setWeight(WeightConstant.CAPACITY,12.11);
        e30.setSource(v15);
        e30.setDestination(v16);
        e30.setBidirectional(false);

        Edge e31 = new Edge();
        e31.setId(31);
        e31.setName("ARCO_31");
        e31.setWeight(WeightConstant.LENGTH,132.0);
        e31.setWeight(WeightConstant.CAPACITY,12.11);
        e31.setSource(v15);
        e31.setDestination(v43);
        e31.setBidirectional(false);

        Edge e32 = new Edge();
        e32.setId(32);
        e32.setName("ARCO_32");
        e32.setWeight(WeightConstant.LENGTH,0.01);
        e32.setWeight(WeightConstant.CAPACITY,25.0);
        e32.setSource(v16);
        e32.setDestination(v17);
        e32.setBidirectional(false);

        Edge e33 = new Edge();
        e33.setId(33);
        e33.setName("ARCO_33");
        e33.setWeight(WeightConstant.LENGTH,24.0);
        e33.setWeight(WeightConstant.CAPACITY,4.0);
        e33.setSource(v16);
        e33.setDestination(v34);
        e33.setBidirectional(false);

        Edge e34 = new Edge();
        e34.setId(34);
        e34.setName("ARCO_34");
        e34.setWeight(WeightConstant.LENGTH,24.0);
        e34.setWeight(WeightConstant.CAPACITY,12.11);
        e34.setSource(v17);
        e34.setDestination(v34);
        e34.setBidirectional(false);

        Edge e35 = new Edge();
        e35.setId(35);
        e35.setName("ARCO_35");
        e35.setWeight(WeightConstant.LENGTH,43.0);
        e35.setWeight(WeightConstant.CAPACITY,12.11);
        e35.setSource(v18);
        e35.setDestination(v19);
        e35.setBidirectional(false);

        Edge e36 = new Edge();
        e36.setId(36);
        e36.setName("ARCO_36");
        e36.setWeight(WeightConstant.LENGTH,43.0);
        e36.setWeight(WeightConstant.CAPACITY,49.16);
        e36.setSource(v18);
        e36.setDestination(v23);
        e36.setBidirectional(false);


        Edge e37 = new Edge();
        e37.setId(37);
        e37.setName("ARCO_37");
        e37.setWeight(WeightConstant.LENGTH,43.0);
        e37.setWeight(WeightConstant.CAPACITY,2.83);
        e37.setSource(v18);
        e37.setDestination(v34);
        e37.setBidirectional(false);

        Edge e38 = new Edge();
        e38.setId(38);
        e38.setName("ARCO_38");
        e38.setWeight(WeightConstant.LENGTH,5.05);
        e38.setWeight(WeightConstant.CAPACITY,148.0);
        e38.setSource(v18);
        e38.setDestination(v40);
        e38.setBidirectional(false);

        Edge e39 = new Edge();
        e39.setId(39);
        e39.setName("ARCO_39");
        e39.setWeight(WeightConstant.LENGTH,60.0);
        e39.setWeight(WeightConstant.CAPACITY,12.11);
        e39.setSource(v19);
        e39.setDestination(v20);
        e39.setBidirectional(false);

        Edge e40 = new Edge();
        e40.setId(40);
        e40.setName("ARCO_40");
        e40.setWeight(WeightConstant.LENGTH,0.01);
        e40.setWeight(WeightConstant.CAPACITY,12.11);
        e40.setSource(v19);
        e40.setDestination(v23);
        e40.setBidirectional(false);

        Edge e41 = new Edge();
        e41.setId(41);
        e41.setName("ARCO_41");
        e41.setWeight(WeightConstant.LENGTH,90.0);
        e41.setWeight(WeightConstant.CAPACITY,49.16);
        e41.setSource(v20);
        e41.setDestination(v21);
        e41.setBidirectional(false);

        Edge e42 = new Edge();
        e42.setId(42);
        e42.setName("ARCO_42");
        e42.setWeight(WeightConstant.LENGTH,90.0);
        e42.setWeight(WeightConstant.CAPACITY,12.11);
        e42.setSource(v20);
        e42.setDestination(v22);
        e42.setBidirectional(false);

        Edge e43 = new Edge();
        e43.setId(43);
        e43.setName("ARCO_43");
        e43.setWeight(WeightConstant.LENGTH,90.0);
        e43.setWeight(WeightConstant.CAPACITY,12.11);
        e43.setSource(v21);
        e43.setDestination(v22);
        e43.setBidirectional(false);

        Edge e44 = new Edge();
        e44.setId(44);
        e44.setName("ARCO_44");
        e44.setWeight(WeightConstant.LENGTH,86.0);
        e44.setWeight(WeightConstant.CAPACITY,12.11);
        e44.setSource(v21);
        e44.setDestination(v28);
        e44.setBidirectional(false);

        Edge e45 = new Edge();
        e45.setId(45);
        e45.setName("ARCO_45");
        e45.setWeight(WeightConstant.LENGTH,60.0);
        e45.setWeight(WeightConstant.CAPACITY,7.0);
        e45.setSource(v22);
        e45.setDestination(v23);
        e45.setBidirectional(false);

        Edge e46 = new Edge();
        e46.setId(46);
        e46.setName("ARCO_46");
        e46.setWeight(WeightConstant.LENGTH,86.0);
        e46.setWeight(WeightConstant.CAPACITY,12.11);
        e46.setSource(v22);
        e46.setDestination(v24);
        e46.setBidirectional(false);

        Edge e47 = new Edge();
        e47.setId(47);
        e47.setName("ARCO_47");
        e47.setWeight(WeightConstant.LENGTH,86.0);
        e47.setWeight(WeightConstant.CAPACITY,0.83);
        e47.setSource(v24);
        e47.setDestination(v25);
        e47.setBidirectional(false);

        Edge e48 = new Edge();
        e48.setId(48);
        e48.setName("ARCO_48");
        e48.setWeight(WeightConstant.LENGTH,46.0);
        e48.setWeight(WeightConstant.CAPACITY,12.11);
        e48.setSource(v25);
        e48.setDestination(v26);
        e48.setBidirectional(false);

        Edge e49 = new Edge();
        e49.setId(49);
        e49.setName("ARCO_49");
        e49.setWeight(WeightConstant.LENGTH,100.0);
        e49.setWeight(WeightConstant.CAPACITY,49.16);
        e49.setSource(v25);
        e49.setDestination(v27);
        e49.setBidirectional(false);

        Edge e50 = new Edge();
        e50.setId(50);
        e50.setName("ARCO_50");
        e50.setWeight(WeightConstant.LENGTH,0.01);
        e50.setWeight(WeightConstant.CAPACITY,5.05);
        e50.setSource(v27);
        e50.setDestination(v31);
        e50.setBidirectional(false);

        Edge e51 = new Edge();
        e51.setId(51);
        e51.setName("ARCO_51");
        e51.setWeight(WeightConstant.LENGTH,70.0);
        e51.setWeight(WeightConstant.CAPACITY,5.05);
        e51.setSource(v27);
        e51.setDestination(v32);
        e51.setBidirectional(false);

        Edge e52 = new Edge();
        e52.setId(52);
        e52.setName("ARCO_52");
        e52.setWeight(WeightConstant.LENGTH,50.0);
        e52.setWeight(WeightConstant.CAPACITY,49.16);
        e52.setSource(v28);
        e52.setDestination(v29);
        e52.setBidirectional(false);

        Edge e53 = new Edge();
        e53.setId(53);
        e53.setName("ARCO_53");
        e53.setWeight(WeightConstant.LENGTH,195.0);
        e53.setWeight(WeightConstant.CAPACITY,49.16);
        e53.setSource(v29);
        e53.setDestination(v32);
        e53.setBidirectional(false);

        Edge e54 = new Edge();
        e54.setId(54);
        e54.setName("ARCO_54");
        e54.setWeight(WeightConstant.LENGTH,70.0);
        e54.setWeight(WeightConstant.CAPACITY,5.05);
        e54.setSource(v30);
        e54.setDestination(v31);
        e54.setBidirectional(false);

        Edge e55 = new Edge();
        e55.setId(55);
        e55.setName("ARCO_55");
        e55.setWeight(WeightConstant.LENGTH,0.01);
        e55.setWeight(WeightConstant.CAPACITY,0.47);
        e55.setSource(v30);
        e55.setDestination(v32);
        e55.setBidirectional(false);

        Edge e56 = new Edge();
        e56.setId(56);
        e56.setName("ARCO_56");
        e56.setWeight(WeightConstant.LENGTH,60.0);
        e56.setWeight(WeightConstant.CAPACITY,0.47);
        e56.setSource(v30);
        e56.setDestination(v33);
        e56.setBidirectional(false);

        Edge e57 = new Edge();
        e57.setId(57);
        e57.setName("ARCO_57");
        e57.setWeight(WeightConstant.LENGTH,60.0);
        e57.setWeight(WeightConstant.CAPACITY,2.0);
        e57.setSource(v32);
        e57.setDestination(v33);
        e57.setBidirectional(false);

        Edge e58 = new Edge();
        e58.setId(58);
        e58.setName("ARCO_58");
        e58.setWeight(WeightConstant.LENGTH,60.0);
        e58.setWeight(WeightConstant.CAPACITY,5.05);
        e58.setSource(v33);
        e58.setDestination(v38);
        e58.setBidirectional(false);

        Edge e59 = new Edge();
        e59.setId(59);
        e59.setName("ARCO_59");
        e59.setWeight(WeightConstant.LENGTH,200.0);
        e59.setWeight(WeightConstant.CAPACITY,2.83);
        e59.setSource(v34);
        e59.setDestination(v37);
        e59.setBidirectional(false);

        Edge e60 = new Edge();
        e60.setId(60);
        e60.setName("ARCO_60");
        e60.setWeight(WeightConstant.LENGTH,24.0);
        e60.setWeight(WeightConstant.CAPACITY,5.05);
        e60.setSource(v36);
        e60.setDestination(v46);
        e60.setBidirectional(false);

        Edge e61 = new Edge();
        e61.setId(61);
        e61.setName("ARCO_61");
        e61.setWeight(WeightConstant.LENGTH,24.0);
        e61.setWeight(WeightConstant.CAPACITY,5.05);
        e61.setSource(v36);
        e61.setDestination(v47);
        e61.setBidirectional(false);

        Edge e62 = new Edge();
        e62.setId(62);
        e62.setName("ARCO_62");
        e62.setWeight(WeightConstant.LENGTH,106.0);
        e62.setWeight(WeightConstant.CAPACITY,1.34);
        e62.setSource(v39);
        e62.setDestination(v50);
        e62.setBidirectional(false);

        Edge e63 = new Edge();
        e63.setId(63);
        e63.setName("ARCO_63");
        e63.setWeight(WeightConstant.LENGTH,32.0);
        e63.setWeight(WeightConstant.CAPACITY,5.05);
        e63.setSource(v40);
        e63.setDestination(v41);
        e63.setBidirectional(false);

        Edge e64 = new Edge();
        e64.setId(64);
        e64.setName("ARCO_64");
        e64.setWeight(WeightConstant.LENGTH,63.0);
        e64.setWeight(WeightConstant.CAPACITY,12.11);
        e64.setSource(v40);
        e64.setDestination(v42);
        e64.setBidirectional(false);

        Edge e65 = new Edge();
        e65.setId(65);
        e65.setName("ARCO_65");
        e65.setWeight(WeightConstant.LENGTH,1.0);
        e65.setWeight(WeightConstant.CAPACITY,5.05);
        e65.setSource(v44);
        e65.setDestination(v45);
        e65.setBidirectional(false);

        Edge e66 = new Edge();
        e66.setId(66);
        e66.setName("ARCO_66");
        e66.setWeight(WeightConstant.LENGTH,23.0);
        e66.setWeight(WeightConstant.CAPACITY,17.13);
        e66.setSource(v44);
        e66.setDestination(v46);
        e66.setBidirectional(false);

        Edge e67 = new Edge();
        e67.setId(67);
        e67.setName("ARCO_67");
        e67.setWeight(WeightConstant.LENGTH,23.0);
        e67.setWeight(WeightConstant.CAPACITY,2.0);
        e67.setSource(v44);
        e67.setDestination(v47);
        e67.setBidirectional(false);

        Edge e68 = new Edge();
        e68.setId(68);
        e68.setName("ARCO_68");
        e68.setWeight(WeightConstant.LENGTH,0.01);
        e68.setWeight(WeightConstant.CAPACITY,49.16);
        e68.setSource(v46);
        e68.setDestination(v47);
        e68.setBidirectional(false);

        Edge e69 = new Edge();
        e69.setId(69);
        e69.setName("ARCO_69");
        e69.setWeight(WeightConstant.LENGTH,40.0);
        e69.setWeight(WeightConstant.CAPACITY,0.83);
        e69.setSource(v49);
        e69.setDestination(v54);
        e69.setBidirectional(false);

        Edge e70 = new Edge();
        e70.setId(70);
        e70.setName("ARCO_70");
        e70.setWeight(WeightConstant.LENGTH,0.01);
        e70.setWeight(WeightConstant.CAPACITY,49.16);
        e70.setSource(v53);
        e70.setDestination(v54);
        e70.setBidirectional(false);


        vertexes.add(v2);
        vertexes.add(v3);
        vertexes.add(v4);
        vertexes.add(v5);
        vertexes.add(v6);
        vertexes.add(v7);
        vertexes.add(v8);
        vertexes.add(v9);
        vertexes.add(v10);
        vertexes.add(v11);
        vertexes.add(v12);
        vertexes.add(v13);
        vertexes.add(v14);
        vertexes.add(v15);
        vertexes.add(v16);
        vertexes.add(v17);
        vertexes.add(v18);
        vertexes.add(v19);
        vertexes.add(v20);
        vertexes.add(v21);
        vertexes.add(v22);
        vertexes.add(v23);
        vertexes.add(v24);
        vertexes.add(v25);
        vertexes.add(v26);
        vertexes.add(v27);
        vertexes.add(v28);
        vertexes.add(v29);
        vertexes.add(v30);
        vertexes.add(v31);
        vertexes.add(v32);
        vertexes.add(v33);
        vertexes.add(v34);
        vertexes.add(v35);
        vertexes.add(v36);
        vertexes.add(v37);
        vertexes.add(v38);
        vertexes.add(v39);
        vertexes.add(v40);
        vertexes.add(v41);
        vertexes.add(v42);
        vertexes.add(v43);
        vertexes.add(v44);
        vertexes.add(v45);
        vertexes.add(v46);
        vertexes.add(v47);
        vertexes.add(v48);
        vertexes.add(v49);
        vertexes.add(v50);
        vertexes.add(v51);
        vertexes.add(v52);
        vertexes.add(v53);
        vertexes.add(v54);

        edges.add(e0);
        edges.add(e1);
        edges.add(e2);
        edges.add(e3);
        edges.add(e4);
        edges.add(e5);
        edges.add(e6);
        edges.add(e7);
        edges.add(e8);
        edges.add(e9);
        edges.add(e10);
        edges.add(e11);
        edges.add(e12);
        edges.add(e13);
        edges.add(e14);
        edges.add(e15);
        edges.add(e16);
        edges.add(e17);
        edges.add(e18);
        edges.add(e19);
        edges.add(e20);
        edges.add(e21);
        edges.add(e22);
        edges.add(e23);
        edges.add(e24);
        edges.add(e25);
        edges.add(e26);
        edges.add(e27);
        edges.add(e28);
        edges.add(e29);
        edges.add(e30);
        edges.add(e31);
        edges.add(e32);
        edges.add(e33);
        edges.add(e34);
        edges.add(e35);
        edges.add(e36);
        edges.add(e37);
        edges.add(e38);
        edges.add(e39);
        edges.add(e40);
        edges.add(e41);
        edges.add(e42);
        edges.add(e43);
        edges.add(e44);
        edges.add(e45);
        edges.add(e46);
        edges.add(e47);
        edges.add(e48);
        edges.add(e49);
        edges.add(e50);
        edges.add(e51);
        edges.add(e52);
        edges.add(e53);
        edges.add(e54);
        edges.add(e55);
        edges.add(e56);
        edges.add(e57);
        edges.add(e58);
        edges.add(e59);
        edges.add(e60);
        edges.add(e61);
        edges.add(e62);
        edges.add(e63);
        edges.add(e64);
        edges.add(e65);
        edges.add(e66);
        edges.add(e67);
        edges.add(e68);
        edges.add(e69);
        edges.add(e70);


        graph.build(vertexes, edges);
        GraphUtil.initializeGraph(graph);
        GraphUtil.makeSymmetric(graph);
        GraphUtil.removeArcsToRealSources(graph);

        return graph;
    }
}

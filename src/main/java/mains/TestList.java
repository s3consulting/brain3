package mains;

import model.Edge;

import java.util.ArrayList;
import java.util.List;

public class TestList {
    public static void main(String[] args){
        Edge edge1 = new Edge();
        edge1.setId(1);
        Edge edge2 = new Edge();
        edge2.setId(2);

        List<Edge> list = new ArrayList<>();

        list.add(edge1);
        list.add(edge1);
        list.add(edge1);
        list.add(edge2);

        System.out.println("List Size: "+list.size());
    }
}

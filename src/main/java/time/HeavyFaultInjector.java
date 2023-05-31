package time;

import constant.CapacityReductionHeavyFault;
import model.Edge;
import model.Graph;
import model.Vertex;

import java.util.List;
import java.util.Optional;


public class HeavyFaultInjector {

    public static String injectHeavyFailureToSource(Graph graph, int day, HeavyFaultKeeper heavyFaultKeeper, List<Vertex> sourcesWithHeavyFault, List<Edge> arcsWithReducedCapacityDueToHeavyFault, List<Edge> pipelinesWithHeavyFault) {
        String activity = "\nInject Heavy Fault To Source";
        Vertex vertexWithHeavyFault = null;
        String nodeType = "1";
        CapacityReductionHeavyFault capacityReductionHeavyFault = new CapacityReductionHeavyFault();
        if (heavyFaultKeeper.getHeavyFaults().get(day) != null && heavyFaultKeeper.getHeavyFaults().get(day).getType().equalsIgnoreCase(nodeType)) {
            String name = heavyFaultKeeper.getHeavyFaults().get(day).getName();
            Optional<Vertex> optionalVertex = graph.getVertexes().stream().filter(n -> n.getName().equalsIgnoreCase(name)).findFirst();
            if (optionalVertex.isPresent()) {
                Integer vertexIndexWithHeavyFault = optionalVertex.get().getId();
                vertexWithHeavyFault = graph.getVertex(vertexIndexWithHeavyFault);
                

            }
        }




        return activity;
    }


}

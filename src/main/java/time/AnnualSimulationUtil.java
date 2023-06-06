package time;

import exception.GraphException;
import model.Edge;
import model.Vertex;

import java.util.List;

public class AnnualSimulationUtil {
    public static void checkParametersAnnualSimulation(String[] args, int expectedParameters) throws GraphException {
        if (args.length != expectedParameters) {
            if (expectedParameters == 4) {
                System.out.println("definire la directory dei grafi, la directory dei risultati, il nome del Grafo da caricare e la directory da cui prelevare gli attacchi");
                System.out.println("ex. java -cp ./target/brain3Simulator-jar-with-dependencies.jar time.AnnualSimulation_ALGORITMO2 INPUT_DIR OUTPUT_DIR PRAKS_GRAPH_CASE_G ATTACK_DIR");
            }
            else if (expectedParameters == 3) {
                System.out.println("definire la directory dei grafi, la directory dei risultati e il nome del Grafo da caricare");
                System.out.println("ex. java -cp ./target/brain3Simulator-jar-with-dependencies.jar time.AnnualSimulation_ALGORITMO2 INPUT_DIR OUTPUT_DIR PRAKS_GRAPH_CASE_G");
            } else {
                System.out.println("definire la directory dei grafi, la directory dei risultati, il numero di iterazioni di montecarlo e il nome del Grafo da caricare");
                System.out.println("ex. java -cp ./target/brain3Simulator-jar-with-dependencies.jar time.AnnualSimulation_Montecarlo_ALGORITMO2 INPUT_DIR OUTPUT_DIR ITERATIONS PRAKS_GRAPH_CASE_G");
            }
            throw new GraphException("Numero parametri errato");
        }
    }

    public static Vertex extractVertexById(List<Vertex> list, Integer id){
        Vertex vertex = null;
        for(Vertex v: list){
            if(v.getId().intValue()==id.intValue()){
                vertex = v;
                break;
            }
        }

        return vertex;
    }

    public static Vertex extractVertexByName(List<Vertex> list, String name){
        Vertex vertex = null;
        for(Vertex v: list){
            if(v.getName().equalsIgnoreCase(name)){
                vertex = v;
                break;
            }
        }

        return vertex;
    }

    public static Edge extractEdgeById(List<Edge> list, Integer id){
        Edge edge = null;
        for(Edge e: list){
            if(e.getId().intValue()==id.intValue()){
                edge = e;
                break;
            }
        }

        return edge;
    }

    public static Edge extractEdgeByName(List<Edge> list, String name){
        Edge edge = null;
        for(Edge e: list){
            if(e.getName().equalsIgnoreCase(name)){
                edge = e;
                break;
            }
        }

        return edge;
    }

    public static String getStatistics(List<Vertex> sourcesUnderAttack,
                                       List<Vertex> sourcesWithHeavyFault,
                                       List<Vertex> sourcesWithIntrinsicFault,
                                       List<Edge> arcsWithReducedCapacityDueToAttack,
                                       List<Edge> pipelinesUnderAttack,
                                       List<Edge> arcsWithReducedCapacityDueHeavyFault,
                                       List<Edge> pipelinesWithHeavyFault,
                                       List<Edge> arcsWithReducedCapacityDueToIntrinsicFault,
                                       List<Edge> pipelinesWithIntrinsicFault){
        String activity = "\n----------- Statistics ------------";

        activity += "\n\tSources Under Attack: "+sourcesUnderAttack.size();
        activity += "\n\tSources with Heavy Fault: "+sourcesWithHeavyFault.size();
        activity += "\n\tSources with Intrinsic Fault: "+sourcesWithIntrinsicFault.size();

        activity += "\n\tArcs with reduce capacity due to Attack: "+arcsWithReducedCapacityDueToAttack.size();
        activity += "\n\tArcs with reduce capacity due to Heavy Fault: "+arcsWithReducedCapacityDueHeavyFault.size();
        activity += "\n\tArcs with reduce capacity due to Intrinsic Fault: "+arcsWithReducedCapacityDueToIntrinsicFault.size();

        activity += "\n\tArcs with Attacks: "+pipelinesUnderAttack.size();
        activity += "\n\tArcs with Heavy Fault: "+pipelinesWithHeavyFault.size();
        activity += "\n\tArcs with Intrinsic Fault: "+pipelinesWithIntrinsicFault.size();
        return activity;
    }

}

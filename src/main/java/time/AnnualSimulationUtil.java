package time;

import exception.GraphException;

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
}

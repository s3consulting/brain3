package finale;

import time.ExternalAttackGenerator;
import time.ExternalAttackMultipleKeeper;

import java.io.IOException;

public class TestExternalAttackMultipleKeeper {
    public static void main(String[] args) throws IOException {
        String attackDir = "/Users/cristianocimino/NetBeansProjects/generic-graph/TELEGRAM/";

        String attackFile = "attacks.csv";

        ExternalAttackMultipleKeeper externalAttackMultipleKeeper = new ExternalAttackMultipleKeeper();

        externalAttackMultipleKeeper.loadAttackFromFile(attackDir, attackFile);

        System.out.println("");

    }
}

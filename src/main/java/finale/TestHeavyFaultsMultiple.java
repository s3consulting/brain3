package finale;

import time.HeavyFaultMultipleKeeper;

import java.io.IOException;

public class TestHeavyFaultsMultiple {
    public static void main(String[] args) throws IOException {
        String heavyFaultDir = "/Users/cristianocimino/NetBeansProjects/generic-graph/HEAVY_FAULT/";

        String heavyFaultFile = "heavyFaults.csv";

        HeavyFaultMultipleKeeper heavyFaultMultipleKeeper = new HeavyFaultMultipleKeeper();

        heavyFaultMultipleKeeper.loadHeavyFaultsFromFile(heavyFaultDir, heavyFaultFile);

        System.out.println("");

    }
}

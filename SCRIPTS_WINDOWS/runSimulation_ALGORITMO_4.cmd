
REM java -cp ./target/brain3Simulator-jar-with-dependencies.jar finale.RunGenericAnnualSimulator_ALGORITMO4 GRAPH_DIR GRAPH_FILE_NAME OUTPUT_DIR ATTACK_DIR HEAVY_FAULT_DIR ITERATIONS CONFIGURATION

REM CONFIGURATION = comma separated list of configuration to test
REM Allowed values for CONFIGURATION:
REM     OPTIMUM
REM     INTRINSIC_FAULTS
REM     HEAVY_FAULTS
REM     MILD_ATTACKS
REM     MEDIUM_ATTACKS
REM     HEAVY_ATTACKS






java -cp ./target/brain3Simulator-jar-with-dependencies.jar finale.RunGenericAnnualSimulator_ALGORITMO4  /Users/cristianocimino/NetBeansProjects/generic-graph/GRAPHS PRAKS_GRAPH_CASE_G /Users/cristianocimino/NetBeansProjects/generic-graph/FINAL_SIMULATION /Users/cristianocimino/NetBeansProjects/generic-graph/TELEGRAM/ /Users/cristianocimino/NetBeansProjects/generic-graph/HEAVY_FAULT/  100

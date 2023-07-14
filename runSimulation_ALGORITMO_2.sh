

#java -cp ./target/brain3Simulator-jar-with-dependencies.jar finale.RunGenericAnnualSimulator_ALGORITMO2 GRAPH_DIR GRAPH_FILE_NAME OUTPUT_DIR ATTACK_DIR HEAVY_FAULT_DIR ITERATIONS CONFIGURATION

#CONFIGURATION = comma separated list of configuration to test
#Allowed values for CONFIGURATION:
#    OPTIMUM
#    INTRINSIC_FAULTS
#    HEAVY_FAULTS
#    MILD_ATTACKS
#    MEDIUM_ATTACKS
#    HEAVY_ATTACKS






#java -cp ./target/brain3Simulator-jar-with-dependencies.jar finale.RunGenericAnnualSimulator_ALGORITMO2  /Users/cristianocimino/NetBeansProjects/generic-graph/GRAPHS PRAKS_GRAPH_CASE_G /Users/cristianocimino/NetBeansProjects/generic-graph/FINAL_SIMULATION /Users/cristianocimino/NetBeansProjects/generic-graph/TELEGRAM/ /Users/cristianocimino/NetBeansProjects/generic-graph/HEAVY_FAULT/  100 INTRINSIC_FAULTS,HEAVY_FAULTS,MILD_ATTACKS,MEDIUM_ATTACKS,HEAVY_ATTACKS


java -cp ./target/brain3Simulator-jar-with-dependencies.jar finale.RunGenericAnnualSimulator_ALGORITMO2  /Users/cristianocimino/NetBeansProjects/generic-graph/GRAPHS PRAKS_GRAPH_CASE_G /Users/cristianocimino/NetBeansProjects/generic-graph/FINAL_TELEDIFE /Users/cristianocimino/NetBeansProjects/generic-graph/TELEGRAM/ /Users/cristianocimino/NetBeansProjects/generic-graph/HEAVY_FAULT/  5 INTRINSIC_FAULTS,HEAVY_FAULTS,MILD_ATTACKS,MEDIUM_ATTACKS,HEAVY_ATTACKS
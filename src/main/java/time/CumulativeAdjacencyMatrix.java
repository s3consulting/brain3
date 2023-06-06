package time;

import java.io.IOException;

public class CumulativeAdjacencyMatrix {

    private double[][][] matrix;
    private int numberOfVertices;
    private final int numberOfDays = 365;


    public CumulativeAdjacencyMatrix(int n) {
        numberOfVertices = n;
        matrix = new double[numberOfVertices][numberOfVertices][numberOfDays];
        resetMatrix();
    }

    private void resetMatrix() {
        for (int day = 0; day < numberOfDays; day++) {
            for (int i = 0; i < numberOfVertices; i++) {
                for (int j = 0; j < numberOfVertices; j++) {
                    matrix[i][j][day] = 0.0;
                }
            }
        }

    }

    public void updateMatrix(double[][] a, int d) {
        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = 0; j < numberOfVertices; j++) {
                matrix[i][j][d] += a[i][j];
                if(matrix[i][j][d]>0){

                }
            }
        }
    }

    public double[][][] arithmeticAverage(int n) {
        double[][][] average = new double[numberOfVertices][numberOfVertices][numberOfDays];
        for (int day = 0; day < numberOfDays; day++) {
            for (int i = 0; i < numberOfVertices; i++) {
                for (int j = 0; j < numberOfVertices; j++) {
                    average[i][j][day] = matrix[i][j][day] / n;
                }
            }
        }
        return average;
    }


    public void showMatrixAtDay(int day){
        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = 0; j < numberOfVertices; j++) {
                System.out.print(matrix[i][j][day]+"\t");
            }
            System.out.println();
        }
    }

    public void writeAverageAdjacencyMatrix(String averageAdjacencyOutputDir, int numberOfIterations, int numberOfDays) throws IOException {
        double[][][] averageAjacencyMatrix = arithmeticAverage(numberOfIterations);
        System.out.println("END");
        OutputFormatterUtil.writeAverageAdjacencyMatrix(averageAdjacencyOutputDir, averageAjacencyMatrix, numberOfDays);

    }

}

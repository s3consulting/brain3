package util;

import exception.GraphException;
import model.Edge;
import model.Graph;
import model.Vertex;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MatrixUtil {
    private MatrixUtil() {
        // private constructor to hide implicit public one
    }

    public static void printMatrix(double[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println("");
        }
    }


    public static double[][] graph2double(Graph graph, String name) {

        double[][] adjacency = new double[graph.getVertexes().size()][graph.getVertexes().size()];
        for (Edge e : graph.getEdges()) {
            Vertex source = e.getSource();
            Vertex destination = e.getDestination();
            if (source.getEnabled() && destination.getEnabled()) {
                adjacency[source.getId()][destination.getId()] = e.getWeight(name);
                if (e.getBidirectional()) {
                    adjacency[destination.getId()][source.getId()] = e.getWeight(name);
                }
            } else {
                adjacency[source.getId()][destination.getId()] = 0;
            }
        }
        return adjacency;
    }


    public static double[][] transpose(double[][] a) {
        double[][] b = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                b[j][i] = a[i][j];
            }
        }
        return b;
    }

    public static double[][] getMinoreComplementare(double[][] d, int a, int b) //minoreComplementare
    {
        int i, j, x, y;
        int k = d.length;
        double[][] f = new double[k - 1][k - 1];
        x = 0;
        y = 0;
        for (i = 0; i < k; i++) {
            y = 0;
            if (i != a) {
                for (j = 0; j < k; j++) {
                    if (j != b) {
                        f[x][y] = d[i][j];
                        y++;
                    }
                }
                x++;
            }
        }
        return f;
    }

    public static double det(double[][] a) {

        int i, j;
        double d = 0.0;
        int k = a.length;
        if (a.length == 2) {
            d = d + a[0][0] * a[1][1] - a[1][0] * a[0][1];
        } else {
            i = 0;
            for (j = 0; j < k; j++) {
                if (a[i][j] != 0) {
                    d = d + Math.pow(-1, i + j) * a[i][j] * det(getMinoreComplementare(a, i, j));
                }
            }
        }
        return d;
    }

    public static double[][] invert(double[][] a) {
        int k = a.length;
        int i, j;
        double[][] t = new double[k][k];
        double[][] h = new double[k][k];

        double det = det(a);
        t = transpose(a);
        for (i = 0; i < k; i++) {
            for (j = 0; j < k; j++) {
                h[i][j] = Math.pow(-1, i + j) * det(getMinoreComplementare(t, i, j)) / det;
            }
        }
        return h;
    }

    public static double[][] product(double[][] a, double k) {
        double[][] c = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                c[i][j] = k * a[i][j];
            }
        }
        return c;
    }

    public static double[][] product(double[][] a, double[][] b) throws GraphException {
        int rows1 = a.length;
        int columns1 = a[0].length;
        int rows2 = b.length;
        int columns2 = b[0].length;

        if (columns1 != rows2) {
            throw new GraphException("column's number of first matrix has to be equal to the row's number of the second matrix");
        } else {
            double[][] c = new double[rows1][columns2];
            int i = 0;
            int j = 0;
            int z = 0;
            for (i = 0; i < rows1; i++) {
                for (z = 0; z < columns2; z++) {
                    double h = 0.0;
                    for (j = 0; j < columns1; j++) {
                        h = h + a[i][j] * b[j][z];
                    }
                    c[i][z] = h;
                }
            }
            return c;
        }
    }

    public static double[][] divide(double[][] a, double[][] b) throws GraphException {
        return product(a, invert(b));
    }

    public static double[][] sum(double[][] a, double[][] b) throws GraphException {
        int rows1 = a.length;
        int columns1 = a[0].length;
        int rows2 = b.length;
        int columns2 = b[0].length;
        if (rows1 != rows2 || columns1 != columns2) {
            throw new GraphException("number of rows and columns have to be the same in both matrices");

        } else {
            double[][] c = new double[rows1][columns2];
            int i = 0;
            int j = 0;
            for (i = 0; i < rows1; i++) {
                for (j = 0; j < columns1; j++) {
                    c[i][j] = a[i][j] + b[i][j];
                }
            }

            return c;
        }
    }

    public static double[][] subtract(double[][] a, double[][] b) throws GraphException {
        return sum(a, product(b, -1.0));
    }

    public static double[][] matriceDiGrado(double[][] a) {
        int n = a.length;
        int i, j;
        double[][] b = new double[n][n];
        for (i = 0; i < n; i++) {
            double grado = 0.0;
            for (j = 0; j < n; j++) {
                grado += a[i][j];
                b[i][j] = 0.0;
            }
            b[i][i] = grado;
        }
        return b;
    }


    public static double[][] subMatrix(double[][] a, int row, int column) {

        //remove row "row: and column "column" from a
        double[][] b = new double[a.length - 1][a.length - 1];
        int ii = 0;
        for (int i = 0; i < a.length; i++) {
            if (i != row) {
                int jj = 0;
                for (int j = 0; j < a.length; j++) {
                    if (j != column) {
                        b[ii][jj] = a[i][j];
                        jj++;
                    }
                }
                ii++;
            }
        }
        return b;
    }

    public static void print(double[][] a) {
        DecimalFormat df = new DecimalFormat("#####.####");
        df.setRoundingMode(RoundingMode.CEILING);
        int i, j;
        int rows = a.length;
        int columns = a[0].length;
        System.out.println();
        for (i = 0; i < rows; i++) {
            for (j = 0; j < columns; j++) {
                System.out.print(df.format(a[i][j]) + "\t");
            }
            System.out.println();
        }
    }

    public static List<List<Double>> vector2List(double[][] a) {
        List<List<Double>> result = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            List<Double> row = new ArrayList<>();
            for (int j = 0; j < a[0].length; j++) {
                row.add(a[i][j]);
            }
            result.add(row);
        }
        return result;
    }
/*
    //aggiorna il campo weight con il valore del campo length per gli algoritmi basati sulla distanza (Dijkstra, Bellman-Ford, Centrility)
    public static void setWeightFromLength(Graph g) {
        for (Edge e : g.getEdges()) {
            if (e.getLength() != null) {
                e.setWeight(e.getLength());
            }
        }
    }

    //aggiorna il campo weight con il valore del campo capacity per gli algoritmi basati sul flusso (Ford-Fulkerson)
    public static void setWeightFromCapacity(Graph g) {
        for (Edge e : g.getEdges()) {
            if (e.getCapacity() != null) {
                e.setWeight(e.getCapacity());
            }
        }
    }

    public static void setFlowFromWeight(Graph g) {
        for (Edge e : g.getEdges()) {
            if (e.getWeight() != null) {
                e.setFlow(e.getWeight());
            }
        }
    }

    public static void checkWeights(List<Edge> edges) {
        for (Edge e : edges) {
            if (e.getWeight() == null) {
                if (e.getLength() != null) {
                    e.setWeight(e.getLength());
                } else if (e.getCapacity() != null) {
                    e.setWeight(e.getCapacity());
                }
            }
        }
    }

    public static void setWeightFromCapacity(List<Edge> edges) {
        for (Edge e : edges) {
            if (e.getCapacity() != null) {
                e.setWeight(e.getCapacity());
            }
        }
    }

    public static void setWeightFromLength(List<Edge> edges) {
        for (Edge e : edges) {
            if (e.getLength() != null) {
                e.setWeight(e.getLength());
            }
        }
    }

 */

}

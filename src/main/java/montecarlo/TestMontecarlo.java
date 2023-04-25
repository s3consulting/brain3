package montecarlo;

import java.util.function.Function;
import java.util.function.Supplier;

public class TestMontecarlo {

    public static void main(String[] args) {
        Integer n = 5;
        Function<Double, Double>[][] P = new Function[n][n];
        Function<Double, Double> p = (x) -> Math.sin(x);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                P[i][j] = p;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println("P[" + i + "][" + j + "]: " + p.apply(Double.valueOf(i + j)));
            }
        }

        Double a = 2.0;
        Double b = 3.0;
        Double mean = 0.5;
        Double sigma = Math.sqrt(.05);


        Function<Double, Double> uniform_distribution = ProbabilityDensityFunctions.getPDF(DistributionConstant.UNIFORM_DISTRIBUTION, a, b, sigma, mean);
        System.out.println("-----------------------Uniform Distribution----------------------");
        for (int i = 0; i <= 40; i++) {
            Double x = i * .1;
            System.out.println(x + "--> " + uniform_distribution.apply(x));
        }
        Function<Double, Double> standard_uniform_distribution = ProbabilityDensityFunctions.getPDF(DistributionConstant.STANDARD_UNIFORM_DISTRIBUTION, a, b, sigma, mean);
        System.out.println("------------------------Standard Uniform Distribution---------------------");
        for (int i = -10; i <= 30; i++) {
            Double x = i * .1;
            System.out.println(x + "--> " + standard_uniform_distribution.apply(x));
        }
        Function<Double, Double> normal_distribution = ProbabilityDensityFunctions.getPDF(DistributionConstant.NORMAL_DISTRIBUTION, a, b, sigma, mean);
        System.out.println("----------------------Normal Distribution-----------------------");
        for (int i = 0; i <= 10; i++) {
            Double x = i * .1;
            System.out.println(x + "--> " + normal_distribution.apply(x));
        }



    }



}

package montecarlo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ProbabilityDensityFunctions {

    public static Function<Double, Double> getPDF(String name, Double a, Double b, Double sigma, Double mean){
        Map<String, Function<Double, Double>> pdfs = new HashMap<>();
        Function<Double, Double> uniformDistribution = x->{
            if(x>b||x<a){
                return 0.0;
            }
            return (x-a)/(b-a);
        };
        Function<Double, Double> standardUniformDistribution = x->{
            if(x>1||x<0){
                return 0.0;
            }
            return 1.0;
        };
        Function<Double, Double> normalDistribution = x->{
            Double A = 1/(sigma*Math.sqrt(2*Math.PI));
            Double P = -.5*Math.pow((x-mean)/sigma, 2);
            Double B = Math.pow(Math.E, P);
            Double N = sigma*Math.sqrt(2*Math.PI);
            return N*A*B;
        };

        pdfs.put(DistributionConstant.UNIFORM_DISTRIBUTION, uniformDistribution);
        pdfs.put(DistributionConstant.STANDARD_UNIFORM_DISTRIBUTION, standardUniformDistribution);
        pdfs.put(DistributionConstant.NORMAL_DISTRIBUTION, normalDistribution);

        return pdfs.get(name);
    }
}

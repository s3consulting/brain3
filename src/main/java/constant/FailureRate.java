package constant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;


public class FailureRate {

    private Map<String, Double> map = new HashMap<>();

    public FailureRate() {
        map.put(TypeConstant.COMPRESSION_STATION, FailureRateConstant.CS_FAILURE_RATE);
        map.put(TypeConstant.UNDERGROUND_GAS_STORAGE, FailureRateConstant.UGS_FAILURE_RATE);
        map.put(TypeConstant.LNG, FailureRateConstant.LNG_FAILURE_RATE);
        map.put(TypeConstant.PIPELINE, FailureRateConstant.PIPELINE_FAILURE_RATE);
    }

    public Double getFailureRate(String type) {
        return map.get(type);
    }
}

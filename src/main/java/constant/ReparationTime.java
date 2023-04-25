package constant;

import java.util.HashMap;
import java.util.Map;

public class ReparationTime {
    private Map<String, Double> map = new HashMap<>();

    public ReparationTime() {
        map.put(TypeConstant.COMPRESSION_STATION, ReparationTimeConstant.CS_REPARATION_TIME);
        map.put(TypeConstant.UNDERGROUND_GAS_STORAGE, ReparationTimeConstant.UGS_REPARATION_TIME);
        map.put(TypeConstant.LNG, ReparationTimeConstant.LNG_REPARATION_TIME);
        map.put(TypeConstant.PIPELINE, ReparationTimeConstant.PIPELINE_REPARATION_TIME);
    }

    public Double getReparationTime(String type) {
        return map.get(type);
    }
}

package constant;

import java.util.HashMap;
import java.util.Map;

public class ReparationTimeIntrinsicFault {
    private Map<String, Double> map = new HashMap<>();

    public ReparationTimeIntrinsicFault() {
        map.put(TypeConstant.COMPRESSION_STATION, ReparationTimeConstant.CS_REPARATION_TIME_INTRINSIC_FAULT);
        map.put(TypeConstant.UNDERGROUND_GAS_STORAGE, ReparationTimeConstant.UGS_REPARATION_TIME_INTRINSIC_FAULT);
        map.put(TypeConstant.LNG, ReparationTimeConstant.LNG_REPARATION_TIME_INTRINSIC_FAULT);
        map.put(TypeConstant.PIPELINE, ReparationTimeConstant.PIPELINE_REPARATION_TIME_INTRINC_FAULT);
    }

    public Double getReparationTime(String type) {
        return map.get(type);
    }
}

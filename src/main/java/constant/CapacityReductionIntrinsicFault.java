package constant;

import java.util.HashMap;
import java.util.Map;

public class CapacityReductionIntrinsicFault {
    private Map<String, Double> map = new HashMap<>();

    public CapacityReductionIntrinsicFault() {
        map.put(TypeConstant.COMPRESSION_STATION, CapacityReductionCostant.CS_PIPELINE_CAPACITY_INTRINSIC_FAULT);
        map.put(TypeConstant.UNDERGROUND_GAS_STORAGE, CapacityReductionCostant.UGS_PIPELINE_CAPACITY_INTRINSIC_FAULT);
        map.put(TypeConstant.LNG, CapacityReductionCostant.LNG_PIPELINE_CAPACITY_INTRINSIC_FAULT);
        map.put(TypeConstant.PIPELINE, CapacityReductionCostant.PIPELINE_CAPACITY_INTRINSIC_FAULT);
    }

    public Double getCapacityReduction(String type) {
        return map.get(type);
    }
}

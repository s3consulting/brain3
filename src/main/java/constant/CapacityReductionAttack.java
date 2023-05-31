package constant;

import java.util.HashMap;
import java.util.Map;

public class CapacityReductionAttack {
    private Map<String, Double> map = new HashMap<>();

    public CapacityReductionAttack() {
        map.put(TypeConstant.COMPRESSION_STATION, CapacityReductionCostant.CS_PIPELINE_CAPACITY_ATTACK);
        map.put(TypeConstant.UNDERGROUND_GAS_STORAGE, CapacityReductionCostant.UGS_PIPELINE_CAPACITY_ATTACK);
        map.put(TypeConstant.LNG, CapacityReductionCostant.LNG_PIPELINE_CAPACITY_ATTACK);
        map.put(TypeConstant.PIPELINE, CapacityReductionCostant.PIPELINE_CAPACITY_ATTACK);
    }

    public Double getCapacityReduction(String type) {
        return map.get(type);
    }
}

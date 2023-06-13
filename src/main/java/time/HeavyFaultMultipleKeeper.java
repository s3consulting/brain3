package time;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeavyFaultMultipleKeeper {
    private Map<Integer, List<MultipleFaultData>> heavyFaults = new HashMap<>();

    public HeavyFaultMultipleKeeper() {
    }

    public Map<Integer, List<MultipleFaultData>> getHeavyFaults() {
        return heavyFaults;
    }

    public void loadHeavyFaultsFromFile(String dir, String attackFileName) throws IOException {
        String fileName = dir + "/" + attackFileName;
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        String line;

        while ((line = reader.readLine()) != null) {
            if(!line.trim().equalsIgnoreCase("")) {
                String[] data = line.split(",");
                Integer day = Integer.valueOf(data[0]);
                MultipleFaultData faultData = new MultipleFaultData(data[1], data[2]);
                if(heavyFaults.get(day)==null) {
                    heavyFaults.put(day, new ArrayList<>());
                }
                heavyFaults.get(day).add(faultData);
            }
        }
        reader.close();
    }
}

